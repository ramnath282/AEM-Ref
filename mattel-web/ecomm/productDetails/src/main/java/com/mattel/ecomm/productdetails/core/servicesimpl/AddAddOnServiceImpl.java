package com.mattel.ecomm.productdetails.core.servicesimpl;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.AddAddOnService;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.productdetailspojos.AddAddOnServiceRequest;
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

@Component(service = AddAddOnService.class)
@Designate(ocd = AddAddOnServiceImpl.Config.class)
public class AddAddOnServiceImpl implements AddAddOnService {
  private static final Logger LOGGER = LoggerFactory.getLogger(AddAddOnServiceImpl.class);
  private static final ObjectReader REQ_READER = ResourceMapper
      .getReaderInstance(AddAddOnServiceRequest.class);
  private static final ObjectReader RESP_READER = ResourceMapper
      .getReaderInstance(AddAddOnServiceResponse.class);
  @Reference
  PropertyReaderService propertyReaderService;
  private String addOnEndPoint;
  private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
  private static final String END_POINT_URL_KEY = "endPointUrl";

  @Override
  public AddAddOnServiceResponse addAddOnService(Map<String, Object> requestMap)
      throws ServiceException {
    AddAddOnServiceImpl.LOGGER.info("Start of add Add on service");
    final long startTime = System.currentTimeMillis();
    try {
      final AddAddOnServiceRequest addAddOnServiceRequest = AddAddOnServiceImpl.REQ_READER
          .readValue(requestMap.get(Constant.REQUEST_BODY).toString());
      AddAddOnServiceImpl.LOGGER.debug("Add On service Request is {}", addAddOnServiceRequest);
      final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
      final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
      final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects,
          cookieNames);
      final String storekey = (String) requestMap.get(Constant.STORE_KEY);
      final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
      final String storeId = propertyReaderService.getStoreId(storekey);
      final String domain = propertyReaderService.getCookieDomain(domainKey);
      final String endPointUrl = addOnEndPoint.replaceAll(AddAddOnServiceImpl.STORE_ID_PLACEHOLDER,
          storeId);
      final Map<String, Object> dataMap = new HashMap<>();
      dataMap.put("validCookieNames", cookieNames);
      dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
      dataMap.put("domain", domain);
      dataMap.put(AddAddOnServiceImpl.END_POINT_URL_KEY, endPointUrl);
      dataMap.put(Constant.DEF_CONNECT_TIMEOUT,
          getDefaultConnectTimeout(propertyReaderService, storekey));
      final long endTime = System.currentTimeMillis();
      AddAddOnServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "Add on service",
          endTime - startTime);
      return (AddAddOnServiceResponse) execute(addAddOnServiceRequest, dataMap);
    } catch (final IOException e) {
      final long endTime = System.currentTimeMillis();
      AddAddOnServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "Add on service",
          endTime - startTime);
      AddAddOnServiceImpl.LOGGER.error("Error Encountered {}", e);
      throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
    }

  }

  @SuppressWarnings("unchecked")
  @Override
  public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap)
      throws ServiceException {
    final long startTime = System.currentTimeMillis();
    try (CloseableHttpClient client = createCustomClient(dataMap)) {
      final AddAddOnServiceRequest addAddOnServiceRequest = (AddAddOnServiceRequest) baseRequest;
      final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
      final Map<String, String> requestHeaders = new HashMap<>();
      final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
      final HttpResponse httpResponse;
      final int status;
      requestHeaders.put("Accept", "application/json");
      requestHeaders.put("Content-type", "application/json");
      httpResponse = HttpRequestHandler.post(client,
          dataMap.get(AddAddOnServiceImpl.END_POINT_URL_KEY).toString(), requestCookies,
          requestHeaders, addAddOnServiceRequest, httpClientContext);
      status = httpResponse.getStatusLine().getStatusCode();
      AddAddOnServiceImpl.LOGGER.debug("Status is {}", status);
      if (!isError(status)) {
        final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");

        AddAddOnServiceImpl.LOGGER.debug("Response Headers: {}", cookieResheader);

        try (InputStream inputStream = httpResponse.getEntity().getContent()) {
          final AddAddOnServiceResponse addAddOnServiceResponse = AddAddOnServiceImpl.RESP_READER
              .readValue(inputStream);
          AddAddOnServiceImpl.LOGGER.debug("Add On service Response {}", addAddOnServiceResponse);
          addAddOnServiceResponse.setCookieList(fetchResponsCookies(dataMap, httpClientContext));
          logServiceErrors(addAddOnServiceResponse);
          return addAddOnServiceResponse;
        }
      } else {
        AddAddOnServiceImpl.LOGGER.error("Recieved response with error status: {} in {}", status,
            Constant.EXECUTE_SERVICE);
        AddAddOnServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
            System.currentTimeMillis() - startTime);
        generalExceptionHandling(status, httpResponse.getEntity());
      }
    } catch (final IOException e) {
      final long endTime = System.currentTimeMillis();

      AddAddOnServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
          endTime - startTime);
      AddAddOnServiceImpl.LOGGER.error("Encountered error:", e);
      throw new ServiceException(Constant.IO_ERROR_KEY_ADD_ON, "IO Exception Occured");
    }
    return null;
  }

  @ObjectClassDefinition(name = "Add AddOn Service Configurations")
  public @interface Config {
    @AttributeDefinition(name = "Addon End Point")
    String endPoint() default "https://mdev.services.commerce.mattel.com/wcs/resources/store/STORE_ID/xupdatecart/addaos?responseFormat=json&updateCookies=true&storeId=STORE_ID";
  }

  @Activate
  public void activate(Config config) {
    addOnEndPoint = config.endPoint();
  }
}