package com.mattel.ecomm.pdp.core.servicesimpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.PDPOfferPrice;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.PDPOfferPriceResponse;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@Component(service = PDPOfferPrice.class)
@Designate(ocd = PDPOfferPriceImpl.Config.class)
public class PDPOfferPriceImpl implements PDPOfferPrice {

  private static final String END_POINT_URL_KEY = "endPointUrl";
  private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
  private static final String PART_NUMBER = "PART_NUMBER";
  private static final String GET_OFFERPRICE_SERVICE = "getPDPOfferPrice";
  private static final String EXECUTE_SERVICE = "execute";
  private static final Logger LOGGER = LoggerFactory.getLogger(PDPOfferPriceImpl.class);
  private static final ObjectReader RESP_READER = ResourceMapper
      .getReaderInstance(PDPOfferPriceResponse.class);
  @Reference
  PropertyReaderService propertyReaderService;
  private String getPDPOfferPriceEndpoint;

  @Override
  public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap)
      throws ServiceException {
    PDPOfferPriceImpl.LOGGER.info("{} - start",  PDPOfferPriceImpl.EXECUTE_SERVICE);
    final long startTime = System.currentTimeMillis();

    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
      @SuppressWarnings("unchecked")
      final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
      final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
      final HttpResponse httpResponse = HttpRequestHandler.get(httpClient,
          dataMap.get(PDPOfferPriceImpl.END_POINT_URL_KEY).toString(), requestCookies, null,
          httpClientContext);
      final int status = httpResponse.getStatusLine().getStatusCode();

      PDPOfferPriceImpl.LOGGER.debug("Response status is {}", status);
      if (null != httpResponse.getEntity() && !isError(status)) {
        try (InputStream inputStream = httpResponse.getEntity().getContent()) {
          final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");
          PDPOfferPriceResponse pdpOfferPriceResponse;
          pdpOfferPriceResponse = PDPOfferPriceImpl.RESP_READER.readValue(inputStream);
          if (null != cookieResheader && cookieResheader.length > 0) {
            final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
            final String domain = (String) dataMap.get("domain");
            final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain,
                httpClientContext.getCookieStore().getCookies());
            pdpOfferPriceResponse.setCookieList(cookieList);
          }
          PDPOfferPriceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
              PDPOfferPriceImpl.EXECUTE_SERVICE, System.currentTimeMillis() - startTime);
          return pdpOfferPriceResponse;
        }
      } else {
        final long endTime = System.currentTimeMillis();
        PDPOfferPriceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
            PDPOfferPriceImpl.EXECUTE_SERVICE, endTime - startTime);
        generalExceptionHandling(status);
      }
    } catch (final IOException io) {
      final long endTime = System.currentTimeMillis();
      PDPOfferPriceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, PDPOfferPriceImpl.EXECUTE_SERVICE,
          endTime - startTime);
      PDPOfferPriceImpl.LOGGER.error("Encountered error: {}", io);
      throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
    }
    final long endTime = System.currentTimeMillis();
    PDPOfferPriceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, PDPOfferPriceImpl.EXECUTE_SERVICE,
        endTime - startTime);
    return null;
  }

  @Override
  public PDPOfferPriceResponse getOffertPrice(Map<String, Object> requestMap)
      throws ServiceException {
    PDPOfferPriceImpl.LOGGER.info("GetOffertPrice - start");
    final long startTime = System.currentTimeMillis();
    final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
    final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
    final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects,
        cookieNames);
    final String storekey = (String) requestMap.get(Constant.STORE_KEY);
    final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
    final String partnumber = (String) requestMap.get(Constant.PART_NUMBER);
    final String storeId = propertyReaderService.getStoreId(storekey);
    final String domain = propertyReaderService.getCookieDomain(domainKey);
    final Map<String, Object> dataMap = new HashMap<>();
    String endPointUrl = getPDPOfferPriceEndpoint.replaceAll(PDPOfferPriceImpl.STORE_ID_PLACEHOLDER,
        storeId);
    endPointUrl = endPointUrl.replaceAll(PDPOfferPriceImpl.PART_NUMBER, partnumber);
    endPointUrl = addLangIdToRequestUri(requestMap, endPointUrl);
    final long endTime;
    dataMap.put("validCookieNames", cookieNames);
    dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
    dataMap.put("domain", domain);
    dataMap.put(PDPOfferPriceImpl.END_POINT_URL_KEY, endPointUrl);
    endTime = System.currentTimeMillis();
    PDPOfferPriceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
        PDPOfferPriceImpl.GET_OFFERPRICE_SERVICE, endTime - startTime);
    return (PDPOfferPriceResponse) execute(null, dataMap);
  }

  @ObjectClassDefinition(name = "WCS PDPOfferPrice Configurations", description = "PDPOfferPrice Configuration for WCS Vendor")
  public @interface Config {
    @AttributeDefinition(name = "PDPOfferPrice End Point", description = "Please Enter the PDPOfferPrice End point in the format"
        + "http://domain/restendpoint/${storeId}/price?q=byPartNumbers&partNumber=PART_NUMBER&responseFormat=json&updateCookies=true&storeId=STORE_ID")
    String endPoint() default "https://mqa.services.commerce.mattel.com/wcs/resources/store/STORE_ID/price?q=byPartNumbers&partNumber=PART_NUMBER&responseFormat=json&updateCookies=true&storeId=STORE_ID&langId=LANG_ID";
  }

  @Activate
  public void activate(final Config config) {
    getPDPOfferPriceEndpoint = config.endPoint();
  }

}
