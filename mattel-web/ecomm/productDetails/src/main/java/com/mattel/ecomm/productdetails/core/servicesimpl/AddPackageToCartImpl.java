package com.mattel.ecomm.productdetails.core.servicesimpl;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.AddPackageToCart;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.productdetailspojos.AddAddOnServiceResponse;
import com.mattel.ecomm.coreservices.core.pojos.productdetailspojos.AddPackageToCartServiceRequest;
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
 * For adding products of type PackageBean to cart.
 */
@Component(service = AddPackageToCart.class)
@Designate(ocd = AddPackageToCartImpl.Config.class)
public class AddPackageToCartImpl implements AddPackageToCart {

  private static final Logger LOGGER = LoggerFactory.getLogger(AddPackageToCartImpl.class);
  private static final ObjectReader REQ_READER = ResourceMapper
      .getReaderInstance(AddPackageToCartServiceRequest.class);
  private static final ObjectReader RESP_READER = ResourceMapper
      .getReaderInstance(AddAddOnServiceResponse.class);
  @Reference
  PropertyReaderService propertyReaderService;
  private String addPackageEndPoint;
  private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
  private static final String END_POINT_URL_KEY = "endPointUrl";

  @Override
  public AddAddOnServiceResponse addPackageService(Map<String, Object> requestMap)
      throws ServiceException {
    AddPackageToCartImpl.LOGGER.info("Start of add Package to Cart service");
    final long startTime = System.currentTimeMillis();
    try {
      final AddPackageToCartServiceRequest addPackageToCartServiceRequest = AddPackageToCartImpl.REQ_READER
          .readValue(requestMap.get(Constant.REQUEST_BODY).toString());
      AddPackageToCartImpl.LOGGER.debug("Add Package to cart service Request is {}",
          addPackageToCartServiceRequest);
      final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
      final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
      final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects,
          cookieNames);
      final String storekey = (String) requestMap.get(Constant.STORE_KEY);
      final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
      final String storeId = propertyReaderService.getStoreId(storekey);
      final String domain = propertyReaderService.getCookieDomain(domainKey);
      final String endPointUrl = addPackageEndPoint
          .replaceAll(AddPackageToCartImpl.STORE_ID_PLACEHOLDER, storeId);
      final Map<String, Object> dataMap = new HashMap<>();
      dataMap.put("validCookieNames", cookieNames);
      dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
      dataMap.put("domain", domain);
      dataMap.put(AddPackageToCartImpl.END_POINT_URL_KEY, endPointUrl);
      dataMap.put(Constant.DEF_CONNECT_TIMEOUT,
          getDefaultConnectTimeout(propertyReaderService, storekey));
      final long endTime = System.currentTimeMillis();
      AddPackageToCartImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "Add package to cart service",
          endTime - startTime);
      return (AddAddOnServiceResponse) execute(addPackageToCartServiceRequest, dataMap);
    } catch (final IOException e) {
      final long endTime = System.currentTimeMillis();
      AddPackageToCartImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "Add package to cart service",
          endTime - startTime);
      AddPackageToCartImpl.LOGGER.error("Error Encountered {}", e);
      throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap)
      throws ServiceException {
    final long startTime = System.currentTimeMillis();
    try (CloseableHttpClient client = createCustomClient(dataMap)) {
      final AddPackageToCartServiceRequest addPackageToCartServiceRequest = (AddPackageToCartServiceRequest) baseRequest;
      final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
      final Map<String, String> requestHeaders = new HashMap<>();
      final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
      final HttpResponse httpResponse;
      final int status;
      requestHeaders.put("Accept", "application/json");
      requestHeaders.put("Content-type", "application/json");
      httpResponse = HttpRequestHandler.post(client,
          dataMap.get(AddPackageToCartImpl.END_POINT_URL_KEY).toString(), requestCookies,
          requestHeaders, addPackageToCartServiceRequest, httpClientContext);
      status = httpResponse.getStatusLine().getStatusCode();
      AddPackageToCartImpl.LOGGER.debug("Status is {}", status);
      if (!isError(status)) {
        final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");

        AddPackageToCartImpl.LOGGER.debug("Response Headers: {}", cookieResheader);

        try (InputStream inputStream = httpResponse.getEntity().getContent()) {
          final AddAddOnServiceResponse addPackageToCartResponse = AddPackageToCartImpl.RESP_READER
              .readValue(inputStream);
          AddPackageToCartImpl.LOGGER.debug("Add Package to Cart service Response {}",
              addPackageToCartResponse);
          addPackageToCartResponse.setCookieList(fetchResponsCookies(dataMap, httpClientContext));
          logServiceErrors(addPackageToCartResponse);
          return addPackageToCartResponse;
        }
      } else {
        final long endTime = System.currentTimeMillis();

        AddPackageToCartImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
            endTime - startTime);
        generalExceptionHandling(status, httpResponse.getEntity());
      }
    } catch (final IOException e) {
      AddPackageToCartImpl.LOGGER.error("IO Exception Occured:", e);
      throw new ServiceException(Constant.IO_ERROR_KEY_PACKAGE, "IO Exception Occured");
    }
    return null;
  }

  @ObjectClassDefinition(name = "Add Package to Cart Service Configurations")
  public @interface Config {
    @AttributeDefinition(name = "Add Package to cart End Point")
    String endPoint() default "https://mdev3.americangirl.corp.mattel.com/wcs/resources/store/10651/xupdatecart/addcollection?responseFormat=json&updateCookies=true&storeId=STORE_ID";

  }

  @Activate
  public void activate(Config config) {
    addPackageEndPoint = config.endPoint();
  }

}