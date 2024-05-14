package com.mattel.ecomm.addressinfo.core.servicesimpl;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.AddDefaultAddress;
import com.mattel.ecomm.coreservices.core.pojos.AddDefaultAddressRequest;
import com.mattel.ecomm.coreservices.core.pojos.AddDefaultAddressResponse;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
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
import org.apache.http.impl.client.HttpClients;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = AddDefaultAddress.class)
@Designate(ocd = AddDefaultAddressImpl.Config.class)
public class AddDefaultAddressImpl implements AddDefaultAddress {

  @Reference
  private PropertyReaderService propertyReaderService;
  private String addDefaultAddressEndPoint;

  public PropertyReaderService getPropertyReaderService() {
    return propertyReaderService;
  }

  public void setPropertyReaderService(PropertyReaderService propertyReaderService) {
    this.propertyReaderService = propertyReaderService;
  }

  public String getAddDefaultAddressEndPoint() {
    return addDefaultAddressEndPoint;
  }

  public void setAddDefaultAddressEndPoint(String addDefaultAddressEndPoint) {
    this.addDefaultAddressEndPoint = addDefaultAddressEndPoint;
  }

  private static final Logger LOGGER = LoggerFactory.getLogger(AddDefaultAddressImpl.class);
  private static final String END_POINT_URL_KEY = "endPointUrl";
  private static final ObjectReader RESP_READER = ResourceMapper
      .getReaderInstance(AddDefaultAddressResponse.class);
  private static final ObjectReader REQ_READER = ResourceMapper
      .getReaderInstance(AddDefaultAddressRequest.class);

  @SuppressWarnings("unchecked")
  @Override
  public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap)
      throws ServiceException {
    final long startTime = System.currentTimeMillis();
    final String methodName = "execute";
    try (CloseableHttpClient client = HttpClients.createDefault();) {
      AddDefaultAddressImpl.LOGGER.info("Start of AddDefaultAddress Execute Method.");
      final AddDefaultAddressRequest addDefaultAddressRequest = (AddDefaultAddressRequest) baseRequest;
      AddDefaultAddressResponse addDefaultAddressResponse;
      final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
      final Map<String, String> requestHeaders = new HashMap<>();
      requestHeaders.put("Accept", "application/json");
      requestHeaders.put("Content-type", "application/json");

      final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
      final HttpResponse httpResponse = HttpRequestHandler.post(client,
          dataMap.get(AddDefaultAddressImpl.END_POINT_URL_KEY).toString(), requestCookies,
          requestHeaders, addDefaultAddressRequest, httpClientContext);
      final int status = httpResponse.getStatusLine().getStatusCode();
      AddDefaultAddressImpl.LOGGER.debug("Status from AddDefaultAddress is {}", status);
      final InputStream inputStream = httpResponse.getEntity().getContent();
      final boolean isError = isError(status);
      if (!isError) {
        final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");
        AddDefaultAddressImpl.LOGGER.debug("Cookies are {}", cookieResheader);
        final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
        final String domain = (String) dataMap.get("domain");
        final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain,
            httpClientContext.getCookieStore().getCookies());
        addDefaultAddressResponse = AddDefaultAddressImpl.RESP_READER.readValue(inputStream);
        AddDefaultAddressImpl.LOGGER.debug("AddDefaultAddress Response Object is {}",
            addDefaultAddressResponse);
        final long endTime = System.currentTimeMillis();
        AddDefaultAddressImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName,
            endTime - startTime);
        addDefaultAddressResponse.setCookieList(cookieList);
        return addDefaultAddressResponse;
      } else {
        final long endTime = System.currentTimeMillis();
        AddDefaultAddressImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName,
            endTime - startTime);
        generalExceptionHandling(status);
      }

    } catch (final IOException e) {
      final long endTime = System.currentTimeMillis();
      AddDefaultAddressImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName,
          endTime - startTime);
      AddDefaultAddressImpl.LOGGER.error("Encountered error:", e);
      throw new ServiceException(Constant.IO_ERROR_KEY, "Io Exception Occured");
    }
    final long endTime = System.currentTimeMillis();
    AddDefaultAddressImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName,
        endTime - startTime);
    return null;
  }

  @Override
  public AddDefaultAddressResponse addDefaultAddress(Map<String, Object> requestHeader)
      throws ServiceException {
    final long startTime = System.currentTimeMillis();
    final String methodName = " AddDefaultAddress";
    AddDefaultAddressImpl.LOGGER.info(" AddDefaultAddress Response Starts");
    try {
      AddDefaultAddressImpl.LOGGER.debug("End point from Config is {}", addDefaultAddressEndPoint);
      final AddDefaultAddressRequest addDefaultAddressRequest = AddDefaultAddressImpl.REQ_READER
          .readValue(requestHeader.get(Constant.REQUEST_BODY).toString());
      final String storeKey = (String) requestHeader.get(Constant.STORE_KEY);
      final String domainKey = (String) requestHeader.get(Constant.DOMAIN_KEY);
      final String storeId = propertyReaderService.getStoreId(storeKey);
      final String addDefaultAddressUrl = addDefaultAddressEndPoint.replaceAll("STORE_ID", storeId);
      final String domain = propertyReaderService.getCookieDomain(domainKey);
      AddDefaultAddressImpl.LOGGER.debug("Connection End Point is {}", addDefaultAddressEndPoint);
      final Cookie[] requestCookieObjects = (Cookie[]) requestHeader
          .get(Constant.REQUEST_COOKIES_KEY);
      final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
      final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects,
          cookieNames);
      final Map<String, Object> dataMap = new HashMap<>();
      dataMap.put("validCookieNames", cookieNames);
      dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
      dataMap.put("domain", domain);
      dataMap.put(AddDefaultAddressImpl.END_POINT_URL_KEY, addDefaultAddressUrl);
      final long endTime = System.currentTimeMillis();
      AddDefaultAddressImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName,
          endTime - startTime);
      return (AddDefaultAddressResponse) execute(addDefaultAddressRequest, dataMap);
    } catch (final IOException e) {
      final long endTime = System.currentTimeMillis();
      AddDefaultAddressImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName,
          endTime - startTime);
      AddDefaultAddressImpl.LOGGER.error("Io Exception occured {}", e);
      throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
    }
  }

  @ObjectClassDefinition(name = "WCS AddDefaultAddress Service Configurations", description = "AddDefaultAddress Service Configuration for WCS Vendor")
  public @interface Config {

    @AttributeDefinition(name = "AddDefaultAddress Service End Point", description = "Please Enter the AddDefaultAddress Service End point in the format"
        + "http://domain/restendpoint/STORE_ID/serviceendpoint")
    String endPoint() default " https://mdev3.americangirl.corp.mattel.com/wcs/resources/store/STORE_ID/xperson/updateperson?storeId=10651&updateCookies=true&responseFormat=json";

  }

  @Activate
  public void activate(final Config config) {
    addDefaultAddressEndPoint = config.endPoint();
  }
}
