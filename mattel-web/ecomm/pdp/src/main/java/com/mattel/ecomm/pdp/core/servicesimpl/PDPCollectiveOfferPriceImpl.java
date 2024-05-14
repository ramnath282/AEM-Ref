package com.mattel.ecomm.pdp.core.servicesimpl;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.PDPCollectiveOfferPrice;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.PDPCollectiveOfferPriceRequest;
import com.mattel.ecomm.coreservices.core.pojos.PDPCollectiveOfferPriceResponse;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.http.HttpResponse;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CTS
 *
 */
@Component(service = PDPCollectiveOfferPrice.class)
@Designate(ocd = PDPCollectiveOfferPriceImpl.Config.class)
public class PDPCollectiveOfferPriceImpl implements PDPCollectiveOfferPrice {
  private static final Logger LOGGER = LoggerFactory.getLogger(PDPCollectiveOfferPriceImpl.class);

  private static final ObjectReader REQ_READER = ResourceMapper
      .getReaderInstance(PDPCollectiveOfferPriceRequest.class);

  private static final ObjectReader RESP_READER = ResourceMapper
      .getReaderInstance(PDPCollectiveOfferPriceResponse.class);

  @Reference
  PropertyReaderService propertyReaderService;

  private String collectiveOfferPriceEndPoint;
  private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
  private static final String END_POINT_URL_KEY = "endPointUrl";
  private static final String EXECUTE_SERVICE = "Execute";

  /*
   * (non-Javadoc)
   *
   * @see com.mattel.ecomm.coreservices.core.interfaces.BaseService#execute(com.
   * mattel.ecomm.coreservices.core.pojos.BaseRequest, java.util.Map)
   */
  @Override
  public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap)
      throws ServiceException {

    PDPCollectiveOfferPriceImpl.LOGGER.info("Start of PDPCollectiveOfferPrice execute service");
    final long startTime = System.currentTimeMillis();
    try (CloseableHttpClient client = createCustomClient(dataMap)) {
      final PDPCollectiveOfferPriceRequest pdpCollectiveOfferPriceRequest = (PDPCollectiveOfferPriceRequest) baseRequest;
      @SuppressWarnings("unchecked")
      final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
      final Map<String, String> requestHeaders = new HashMap<>();
      final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
      final HttpResponse httpResponse;
      final int status;
      requestHeaders.put("Accept", "application/json");
      requestHeaders.put("Content-type", "application/json");
      httpResponse = HttpRequestHandler.post(client,
          dataMap.get(PDPCollectiveOfferPriceImpl.END_POINT_URL_KEY).toString(), requestCookies,
          requestHeaders, pdpCollectiveOfferPriceRequest, httpClientContext);
      status = httpResponse.getStatusLine().getStatusCode();
      PDPCollectiveOfferPriceImpl.LOGGER.debug("Status is {}", status);
      if (null != httpResponse.getEntity() && !isError(status)) {
        final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
        final String domain = (String) dataMap.get("domain");
        final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain,
            httpClientContext.getCookieStore().getCookies());
        try (InputStream inputStream = httpResponse.getEntity().getContent()) {
          final PDPCollectiveOfferPriceResponse pdpCollectiveOfferPriceResponse = PDPCollectiveOfferPriceImpl.RESP_READER
              .readValue(inputStream);
          PDPCollectiveOfferPriceImpl.LOGGER.debug("PDPCollectiveOfferPrice service Response {}",
              pdpCollectiveOfferPriceResponse);
          pdpCollectiveOfferPriceResponse.setCookieList(cookieList);
          final long endTime = System.currentTimeMillis();
          PDPCollectiveOfferPriceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
              PDPCollectiveOfferPriceImpl.EXECUTE_SERVICE, endTime - startTime);
          logServiceErrors(pdpCollectiveOfferPriceResponse);
          return pdpCollectiveOfferPriceResponse;
        }
      } else {
        final long endTime = System.currentTimeMillis();
        PDPCollectiveOfferPriceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
            PDPCollectiveOfferPriceImpl.EXECUTE_SERVICE, endTime - startTime);
        generalExceptionHandling(status, httpResponse.getEntity());
      }
    } catch (final IOException e) {
      PDPCollectiveOfferPriceImpl.LOGGER.error("Error Encountered:", e);
      final long endTime = System.currentTimeMillis();
      PDPCollectiveOfferPriceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
          PDPCollectiveOfferPriceImpl.EXECUTE_SERVICE, endTime - startTime);
      throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
    }
    final long endTime = System.currentTimeMillis();
    PDPCollectiveOfferPriceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
        PDPCollectiveOfferPriceImpl.EXECUTE_SERVICE, endTime - startTime);
    return null;

  }

  /*
   * (non-Javadoc)
   *
   * @see com.mattel.ecomm.coreservices.core.interfaces.PDPCollectiveOfferPrice#
   * getOfferPrice(java.util.Map)
   */
  @Override
  public PDPCollectiveOfferPriceResponse getOfferPrice(Map<String, Object> requestMap)
      throws ServiceException {
    PDPCollectiveOfferPriceImpl.LOGGER.info("Start of CollectiveOfferPrice GetOfferPrice service");
    final long startTime = System.currentTimeMillis();
    try {
      final PDPCollectiveOfferPriceRequest pdpCollectiveOfferPriceRequest = PDPCollectiveOfferPriceImpl.REQ_READER
          .readValue(requestMap.get(Constant.REQUEST_BODY).toString());
      PDPCollectiveOfferPriceImpl.LOGGER.debug("CollectiveOfferPrice Request is {}",
          pdpCollectiveOfferPriceRequest);
      final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
      final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
      final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects,
          cookieNames);
      final String storekey = (String) requestMap.get(Constant.STORE_KEY);
      final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
      final String storeId = propertyReaderService.getStoreId(storekey);
      final String domain = propertyReaderService.getCookieDomain(domainKey);
      final String endPointUrl = collectiveOfferPriceEndPoint
          .replaceAll(PDPCollectiveOfferPriceImpl.STORE_ID_PLACEHOLDER, storeId);
      final Map<String, Object> dataMap = new HashMap<>();
      dataMap.put("validCookieNames", cookieNames);
      dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
      dataMap.put("domain", domain);
      dataMap.put(PDPCollectiveOfferPriceImpl.END_POINT_URL_KEY, endPointUrl);
      dataMap.put(Constant.DEF_CONNECT_TIMEOUT,
          getDefaultConnectTimeout(propertyReaderService, storekey));
      final long endTime = System.currentTimeMillis();
      PDPCollectiveOfferPriceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "GetStoreAvailability",
          endTime - startTime);
      return (PDPCollectiveOfferPriceResponse) execute(pdpCollectiveOfferPriceRequest, dataMap);
    } catch (final IOException e) {
      final long endTime = System.currentTimeMillis();
      PDPCollectiveOfferPriceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
          "CollectiveOfferPrice Exception", endTime - startTime);
      PDPCollectiveOfferPriceImpl.LOGGER.error("Error Encountered {}", e);
      throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
    }
  }

  @ObjectClassDefinition(name = "WCS Collective Offer Price Service Configurations", description = "Collective Offer Price Configuration for WCS Vendor")
  public @interface Config {
    @AttributeDefinition(name = "Collective Offer Price End Point", description = "Please Enter the Collective Offer Price End Point in the format"
        + "http://domain/restendpoint/${storeId}/mattelProductPrice/getProductPrice")
    String endPoint() default "https://mqa.services.commerce.mattel.com/wcs/resources/store/STORE_ID/mattelProductPrice/getProductPrice?responseFormat=json&updateCookies=true&storeId=STORE_ID";
  }

  @Activate
  public void activate(Config config) {
    collectiveOfferPriceEndPoint = config.endPoint();
  }

}
