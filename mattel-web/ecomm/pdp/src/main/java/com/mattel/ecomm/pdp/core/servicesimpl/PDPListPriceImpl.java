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
import com.mattel.ecomm.coreservices.core.interfaces.PDPListPrice;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.PDPListPriceResponse;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@Component(service = PDPListPrice.class)
@Designate(ocd = PDPListPriceImpl.Config.class)
public class PDPListPriceImpl implements PDPListPrice {

  private static final String END_POINT_URL_KEY = "endPointUrl";
  private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
  private static final String PART_NUMBER = "PART_NUMBER";
  private static final String GET_LISTPRICE_SERVICE = "getPDPListPrice";
  private static final String EXECUTE_SERVICE = "execute";
  private static final Logger LOGGER = LoggerFactory.getLogger(PDPListPriceImpl.class);
  private static final ObjectReader RESP_READER = ResourceMapper
      .getReaderInstance(PDPListPriceResponse.class);
  @Reference
  PropertyReaderService propertyReaderService;
  private String getPDPListPriceEndpoint;

  @SuppressWarnings("unchecked")
  @Override
  public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap)
      throws ServiceException {
    PDPListPriceImpl.LOGGER.info("{} - start", PDPListPriceImpl.EXECUTE_SERVICE);
    final long startTime = System.currentTimeMillis();

    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
      final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
      final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
      final HttpResponse httpResponse = HttpRequestHandler.get(httpClient,
          dataMap.get(PDPListPriceImpl.END_POINT_URL_KEY).toString(), requestCookies, null,
          httpClientContext);
      final int status = httpResponse.getStatusLine().getStatusCode();
      PDPListPriceImpl.LOGGER.debug("Response status is {}", status);
      if (null != httpResponse.getEntity() && !isError(status)) {
        try (InputStream inputStream = httpResponse.getEntity().getContent()) {
          final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");
          PDPListPriceResponse pdpListPriceResponse;
          pdpListPriceResponse = PDPListPriceImpl.RESP_READER.readValue(inputStream);
          if (null != cookieResheader && cookieResheader.length > 0) {
            final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
            final String domain = (String) dataMap.get("domain");
            final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain,
                httpClientContext.getCookieStore().getCookies());
            pdpListPriceResponse.setCookieList(cookieList);
          }
          PDPListPriceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
              PDPListPriceImpl.EXECUTE_SERVICE, System.currentTimeMillis() - startTime);
          return pdpListPriceResponse;
        }
      } else {
        final long endTime = System.currentTimeMillis();
        PDPListPriceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, PDPListPriceImpl.EXECUTE_SERVICE,
            endTime - startTime);
        generalExceptionHandling(status);
      }
    } catch (final IOException io) {
      final long endTime = System.currentTimeMillis();
      PDPListPriceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, PDPListPriceImpl.EXECUTE_SERVICE,
          endTime - startTime);
      PDPListPriceImpl.LOGGER.error("Encountered error:", io);
      throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
    }
    final long endTime = System.currentTimeMillis();
    PDPListPriceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, PDPListPriceImpl.EXECUTE_SERVICE,
        endTime - startTime);
    return null;
  }

  @Override
  public PDPListPriceResponse getListPrice(Map<String, Object> requestMap) throws ServiceException {
    PDPListPriceImpl.LOGGER.info("GetListPrice - start");
    final long startTime = System.currentTimeMillis();
    final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
    final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
    final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects,
        cookieNames);
    final String storekey = (String) requestMap.get(Constant.STORE_KEY);
    final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
    final String partNumber = (String) requestMap.get(Constant.PART_NUMBER);
    final String storeId = propertyReaderService.getStoreId(storekey);
    final String domain = propertyReaderService.getCookieDomain(domainKey);
    final Map<String, Object> dataMap = new HashMap<>();
    String endPointUrl = getPDPListPriceEndpoint.replaceAll(PDPListPriceImpl.STORE_ID_PLACEHOLDER,
        storeId);
    endPointUrl = endPointUrl.replaceAll(PDPListPriceImpl.PART_NUMBER, partNumber);
    endPointUrl = addLangIdToRequestUri(requestMap, endPointUrl);
    final long endTime;
    dataMap.put("validCookieNames", cookieNames);
    dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
    dataMap.put("domain", domain);
    dataMap.put(PDPListPriceImpl.END_POINT_URL_KEY, endPointUrl);
    endTime = System.currentTimeMillis();
    PDPListPriceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
        PDPListPriceImpl.GET_LISTPRICE_SERVICE, endTime - startTime);
    return (PDPListPriceResponse) execute(null, dataMap);
  }

  @ObjectClassDefinition(name = "WCS PDPListPrice Configurations", 
      description = "PDPListPrice Configuration for WCS Vendor")
  public @interface Config {
    @AttributeDefinition(name = "PDPListPrice End Point", 
        description = "Please Enter the PDPListPrice End point in the format"
        + "http://domain/restendpoint/${storeId}/display_price?q=byPartNumbersAndPriceRuleName&partNumber=PART_NUMBER&responseFormat=json&updateCookies=true&storeId=STORE_ID&priceRuleName=List price rule")
    String endPoint() default "https://mqa.services.commerce.mattel.com/wcs/resources/store/STORE_ID/display_price?q=byPartNumbersAndPriceRuleName&partNumber=PART_NUMBER&responseFormat=json&updateCookies=true&storeId=STORE_ID&langId=LANG_ID&priceRuleName=List%20price%20rule";
  }

  @Activate
  public void activate(final Config config) {
    getPDPListPriceEndpoint = config.endPoint();
  }

}
