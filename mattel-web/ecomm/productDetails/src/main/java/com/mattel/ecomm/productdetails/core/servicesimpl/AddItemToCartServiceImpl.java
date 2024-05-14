package com.mattel.ecomm.productdetails.core.servicesimpl;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.AddItemToCartService;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.productdetailspojos.AddItemToCartRequest;
import com.mattel.ecomm.coreservices.core.pojos.productdetailspojos.ItemCartResponse;
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
 * For adding products of type ItemBean, BundleBean and ProductBean to cart.
 */
@Component(service = AddItemToCartService.class)
@Designate(ocd = AddItemToCartServiceImpl.Config.class)
public class AddItemToCartServiceImpl implements AddItemToCartService {
  private static final Logger LOGGER = LoggerFactory.getLogger(AddItemToCartServiceImpl.class);
  private static final ObjectReader REQ_READER = ResourceMapper
      .getReaderInstance(AddItemToCartRequest.class);
  private static final ObjectReader RESP_READER = ResourceMapper
      .getReaderInstance(ItemCartResponse.class);
  @Reference
  PropertyReaderService propertyReaderService;
  private String addItemToCartServiceEndpoint;

  @SuppressWarnings("unchecked")
  @Override
  public ItemCartResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap)
      throws ServiceException {
    final long startTime = System.currentTimeMillis();

    try (CloseableHttpClient httpClient = createCustomClient(dataMap)) {
      final AddItemToCartRequest addItemToCartRequest = (AddItemToCartRequest) baseRequest;
      final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
      final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
      final Map<String, String> requestHeaders = new HashMap<>();
      final HttpResponse httpResponse;
      final int status;

      requestHeaders.put("Accept", Constant.CONTENT_TYPE_APPLICATION_JSON);
      requestHeaders.put("Content-type", Constant.CONTENT_TYPE_APPLICATION_JSON);
      httpResponse = HttpRequestHandler.post(httpClient,
          dataMap.get(Constant.END_POINT_URL_KEY).toString(), requestCookies, requestHeaders,
          addItemToCartRequest, httpClientContext);
      status = httpResponse.getStatusLine().getStatusCode();

      AddItemToCartServiceImpl.LOGGER.debug("Response status is {}", status);

      if (!isError(status)) {
        try (final InputStream inputStream = httpResponse.getEntity().getContent()) {
          final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");
          final ItemCartResponse itemCartResponse = AddItemToCartServiceImpl.RESP_READER
              .readValue(inputStream);

          if (null != cookieResheader && cookieResheader.length > 0) {
            final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
            final String domain = (String) dataMap.get("domain");
            final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain,
                httpClientContext.getCookieStore().getCookies());

            itemCartResponse.setCookieList(cookieList);
          }

          AddItemToCartServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
              Constant.EXECUTE_SERVICE, System.currentTimeMillis() - startTime);
          logServiceErrors(itemCartResponse);
          return itemCartResponse;
        }
      } else {
        AddItemToCartServiceImpl.LOGGER.error("Recieved response with error status: {} in {}",
            status, Constant.EXECUTE_SERVICE);
        AddItemToCartServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
            System.currentTimeMillis() - startTime);
        generalExceptionHandling(status, httpResponse.getEntity());
      }
    } catch (final IOException e) {
      final long endTime = System.currentTimeMillis();

      AddItemToCartServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
          endTime - startTime);
      AddItemToCartServiceImpl.LOGGER.error("Encountered error:", e);
      throw new ServiceException(Constant.IO_ERROR_KEY_ITEM, "IO Exception Occured");
    }

    return null;
  }

  @Override
  public ItemCartResponse addToCart(Map<String, Object> requestMap) throws ServiceException {
    final long startTime = System.currentTimeMillis();

    AddItemToCartServiceImpl.LOGGER.info("Start of add {}", getClass().getSimpleName());

    try {
      final AddItemToCartRequest addItemToCartRequest = AddItemToCartServiceImpl.REQ_READER
          .readValue(requestMap.get(Constant.REQUEST_BODY).toString());
      final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
      final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
      final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects,
          cookieNames);
      final String storekey = (String) requestMap.get(Constant.STORE_KEY);
      final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
      final String storeId = propertyReaderService.getStoreId(storekey);
      final String domain = propertyReaderService.getCookieDomain(domainKey);
      final String endPointUrl = addItemToCartServiceEndpoint
          .replaceAll(Constant.STORE_ID_PLACEHOLDER, storeId);
      final Map<String, Object> dataMap = new HashMap<>();
      final long endTime;

      dataMap.put("validCookieNames", cookieNames);
      dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
      dataMap.put(Constant.DOMAIN, domain);
      dataMap.put(Constant.END_POINT_URL_KEY, endPointUrl);
      dataMap.put(Constant.DEF_CONNECT_TIMEOUT, getDefaultConnectTimeout(propertyReaderService, storekey));
      endTime = System.currentTimeMillis();

      AddItemToCartServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, getClass().getSimpleName(),
          endTime - startTime);
      return execute(addItemToCartRequest, dataMap);
    } catch (final IOException e) {
      final long endTime = System.currentTimeMillis();

      AddItemToCartServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, getClass().getSimpleName(),
          endTime - startTime);
      AddItemToCartServiceImpl.LOGGER.error("IO Error Encountered {}", e);
      throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
    }
  }

  @ObjectClassDefinition(name = "WCS Add Item to Cart Service Configurations", description = "Add Item to Cart Service Configuration for WCS Vendor")
  public @interface Config {
    @AttributeDefinition(name = "Add Item to Cart Service End Point", description = "Please Enter the Add Item to Cart Service End point in the format"
        + "http://domain/restendpoint/${storeId}/additem")
    String endPoint() default "https://mqa.services.commerce.mattel.com/wcs/resources/store/STORE_ID/xupdatecart/additem?storeId=STORE_ID&responseFormat=json&updateCookies=true";
  }

  @Activate
  public void activate(final Config config) {
    addItemToCartServiceEndpoint = config.endPoint();
  }
}
