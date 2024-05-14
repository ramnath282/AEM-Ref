package com.mattel.ecomm.productdetails.core.servicesimpl;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.AddGiftCardService;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.GiftCard;
import com.mattel.ecomm.coreservices.core.pojos.productdetailspojos.AddAddOnServiceResponse;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

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
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * For adding products of type GiftCard to cart.
 */
@Component(service = AddGiftCardService.class)
@Designate(ocd = AddGiftCardServiceImpl.Config.class)
public class AddGiftCardServiceImpl implements AddGiftCardService {
  private static final Logger LOGGER = LoggerFactory.getLogger(AddGiftCardServiceImpl.class);
  private static final Object ADD_TO_CART_SERVICE = "addGiftCardToCartService";
  private static final ObjectReader REQ_READER = ResourceMapper.getReaderInstance(GiftCard.class);
  /** Reusing AddAddOnServiceResponse */
  private static final ObjectReader RESP_READER = ResourceMapper
      .getReaderInstance(AddAddOnServiceResponse.class);
  @Reference
  PropertyReaderService propertyReaderService;
  private String addGiftCardToCartServiceEndpoint;

  @SuppressWarnings("unchecked")
  @Override
  public AddAddOnServiceResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap)
      throws ServiceException {
    final long startTime = System.currentTimeMillis();

    try (CloseableHttpClient httpClient = createCustomClient(dataMap)) {
      final GiftCard addGiftCardRequest = (GiftCard) baseRequest;
      final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
      final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
      final Map<String, String> requestHeaders = new HashMap<>();
      final HttpResponse httpResponse;
      final int status;

      requestHeaders.put("Accept", Constant.CONTENT_TYPE_APPLICATION_JSON);
      requestHeaders.put("Content-type", Constant.CONTENT_TYPE_APPLICATION_JSON);
      httpResponse = HttpRequestHandler.post(httpClient,
          dataMap.get(Constant.END_POINT_URL_KEY).toString(), requestCookies, requestHeaders,
          addGiftCardRequest, httpClientContext);
      status = httpResponse.getStatusLine().getStatusCode();

      AddGiftCardServiceImpl.LOGGER.debug("Response status is {}", status);

      if (!isError(status)) {
        try (final InputStream inputStream = httpResponse.getEntity().getContent()) {
          final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");
          final AddAddOnServiceResponse serviceResponse = AddGiftCardServiceImpl.RESP_READER
              .readValue(inputStream);

          if (null != cookieResheader && cookieResheader.length > 0) {
            serviceResponse.setCookieList(fetchResponsCookies(dataMap, httpClientContext));
          }

          AddGiftCardServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
              System.currentTimeMillis() - startTime);
          logServiceErrors(serviceResponse);
          return serviceResponse;
        }
      } else {
        AddGiftCardServiceImpl.LOGGER.error("Recieved response with error status: {} in {}", status,
            Constant.EXECUTE_SERVICE);
        AddGiftCardServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
            System.currentTimeMillis() - startTime);
        generalExceptionHandling(status, httpResponse.getEntity());
      }
    } catch (final IOException e) {
      final long endTime = System.currentTimeMillis();

      AddGiftCardServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
          endTime - startTime);
      AddGiftCardServiceImpl.LOGGER.error("Encountered error:", e);
      throw new ServiceException(Constant.IO_ERROR_KEY_GIFT_CARD, "IO Exception Occured");
    }

    return null;
  }

  @Override
  public AddAddOnServiceResponse addToCart(Map<String, Object> requestMap) throws ServiceException {
    final long startTime = System.currentTimeMillis();

    try {
      final GiftCard giftCard = AddGiftCardServiceImpl.REQ_READER
          .readValue(requestMap.get(Constant.REQUEST_BODY).toString());
      final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
      final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
      final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects,
          cookieNames);
      final String storekey = (String) requestMap.get(Constant.STORE_KEY);
      final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
      final String storeId = propertyReaderService.getStoreId(storekey);
      final String domain = propertyReaderService.getCookieDomain(domainKey);
      final String endPointUrl = addGiftCardToCartServiceEndpoint
          .replaceAll(Constant.STORE_ID_PLACEHOLDER, storeId);
      final Map<String, Object> dataMap = new HashMap<>();
      final long endTime;

      dataMap.put("validCookieNames", cookieNames);
      dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
      dataMap.put("domain", domain);
      dataMap.put(Constant.END_POINT_URL_KEY, endPointUrl);
      dataMap.put(Constant.DEF_CONNECT_TIMEOUT,
          getDefaultConnectTimeout(propertyReaderService, storekey));
      endTime = System.currentTimeMillis();

      AddGiftCardServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
          AddGiftCardServiceImpl.ADD_TO_CART_SERVICE, endTime - startTime);
      return execute(giftCard, dataMap);
    } catch (final IOException e) {
      final long endTime = System.currentTimeMillis();

      AddGiftCardServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
          AddGiftCardServiceImpl.ADD_TO_CART_SERVICE, endTime - startTime);
      AddGiftCardServiceImpl.LOGGER.error("Error encountered: ", e);
      throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
    }
  }

  @ObjectClassDefinition(name = "AddGiftCardService Configurations")
  public @interface Config {
    @AttributeDefinition(name = "AddGiftCardService End Point")
    String endPoint() default "https://mqa.services.commerce.mattel.com/wcs/resources/store/STORE_ID/xupdatecart/addgiftcard?responseFormat=json&updateCookies=true&storeId=STORE_ID";
  }

  @Activate
  public void activate(Config config) {
    addGiftCardToCartServiceEndpoint = config.endPoint();
  }
}