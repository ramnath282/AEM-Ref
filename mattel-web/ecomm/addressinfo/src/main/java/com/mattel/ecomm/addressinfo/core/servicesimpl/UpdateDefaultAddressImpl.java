package com.mattel.ecomm.addressinfo.core.servicesimpl;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.UpdateDefaultAddress;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.UpdateDefaultAddressRequest;
import com.mattel.ecomm.coreservices.core.pojos.UpdateDefaultAddressResponse;
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

@Component(service = UpdateDefaultAddress.class)
@Designate(ocd = UpdateDefaultAddressImpl.Config.class)
public class UpdateDefaultAddressImpl implements UpdateDefaultAddress {

  @Reference
  PropertyReaderService propertyReaderService;

  public PropertyReaderService getPropertyReaderService() {
    return propertyReaderService;
  }

  public void setPropertyReaderService(PropertyReaderService propertyReaderService) {
    this.propertyReaderService = propertyReaderService;
  }

  public String getUpdateDefaultAddressEndpoint() {
    return updateDefaultAddressEndpoint;
  }

  public void setUpdateDefaultAddressEndpoint(String updateDefaultAddressEndpoint) {
    this.updateDefaultAddressEndpoint = updateDefaultAddressEndpoint;
  }

  private String updateDefaultAddressEndpoint;
  private static final String END_POINT_URL_KEY = "endPointUrl";
  private static final ObjectReader REQ_READER = ResourceMapper
      .getReaderInstance(UpdateDefaultAddressRequest.class);
  private static final ObjectReader RESP_READER = ResourceMapper
      .getReaderInstance(UpdateDefaultAddressResponse.class);

  private static final Logger LOGGER = LoggerFactory.getLogger(UpdateDefaultAddressImpl.class);

  @SuppressWarnings("unchecked")
  @Override
  public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap)
      throws ServiceException {
    final long startTime = System.currentTimeMillis();
    UpdateDefaultAddressImpl.LOGGER.info("execute start");

    try (CloseableHttpClient client = HttpClients.createDefault()) {
      final UpdateDefaultAddressRequest updateDefaultAddressRequest = (UpdateDefaultAddressRequest) baseRequest;
      UpdateDefaultAddressResponse updateDefaultAddressResponse = null;
      final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
      final Map<String, String> requestHeaders = new HashMap<>();
      final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
      final HttpResponse httpResponse;
      requestHeaders.put("Accept", "application/json");
      requestHeaders.put("Content-type", "application/json");
      httpResponse = HttpRequestHandler.put(client,
          dataMap.get(UpdateDefaultAddressImpl.END_POINT_URL_KEY).toString(), requestCookies,
          requestHeaders, updateDefaultAddressRequest, httpClientContext);
      final int status = httpResponse.getStatusLine().getStatusCode();
      UpdateDefaultAddressImpl.LOGGER.debug("Status from Login is {}", status);
      final InputStream inputStream = httpResponse.getEntity().getContent();
      final boolean isError = isError(status);
      if (!isError) {
        final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");
        UpdateDefaultAddressImpl.LOGGER.debug("Cookies are {}", cookieResheader);
        final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
        final String domain = (String) dataMap.get("domain");
        final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain,
            httpClientContext.getCookieStore().getCookies());
        updateDefaultAddressResponse = UpdateDefaultAddressImpl.RESP_READER.readValue(inputStream);
        updateDefaultAddressResponse.setCookieList(cookieList);
      } else {
        final long endTime = System.currentTimeMillis();
        UpdateDefaultAddressImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
            "Update Default Address Execute", endTime - startTime);
        generalExceptionHandling(status);
      }
      final long endTime = System.currentTimeMillis();
      UpdateDefaultAddressImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "execute",
          endTime - startTime);
      return updateDefaultAddressResponse;

    } catch (final IOException e) {
      final long endTime = System.currentTimeMillis();
      UpdateDefaultAddressImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "IOException",
          endTime - startTime);
      UpdateDefaultAddressImpl.LOGGER.error("Io Exception occured:", e);
      throw new ServiceException(Constant.IO_ERROR_KEY, "Io Exception Occured");
    }
  }

  @Override
  public UpdateDefaultAddressResponse updateDefaultAddress(Map<String, Object> requestMap)
      throws ServiceException {
    final long startTime = System.currentTimeMillis();
    UpdateDefaultAddressImpl.LOGGER.info("updateDefaultAddress start");
    try {
      final UpdateDefaultAddressRequest updateDefaultAddressRequest = UpdateDefaultAddressImpl.REQ_READER
          .readValue(requestMap.get(Constant.REQUEST_BODY).toString());
      final String storekey = (String) requestMap.get(Constant.STORE_KEY);
      final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
      final String storeId = propertyReaderService.getStoreId(storekey);
      final String domain = propertyReaderService.getCookieDomain(domainKey);
      final String updateDefaultAddress = updateDefaultAddressEndpoint.replaceAll("STORE_ID",
          storeId);
      final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
      final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
      final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects,
          cookieNames);
      final Map<String, Object> dataMap = new HashMap<>();
      dataMap.put("validCookieNames", cookieNames);
      dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
      dataMap.put("domain", domain);
      dataMap.put(UpdateDefaultAddressImpl.END_POINT_URL_KEY, updateDefaultAddress);
      final long endTime = System.currentTimeMillis();
      UpdateDefaultAddressImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "updateDefaultAddress",
          endTime - startTime);
      return (UpdateDefaultAddressResponse) execute(updateDefaultAddressRequest, dataMap);

    } catch (final IOException e) {
      final long endTime = System.currentTimeMillis();
      UpdateDefaultAddressImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "updateDefaultAddress",
          endTime - startTime);
      UpdateDefaultAddressImpl.LOGGER.error("Io Exception occured {}", e);
      throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
    }
  }

  @ObjectClassDefinition(name = "WCS UpdateDefaultAddress Configurations", description = "UpdateDefaultAddress Configuration for WCS Vendor")
  public @interface Config {
    @AttributeDefinition(name = "UpdateDefaultAddress End Point", description = "Please Enter the UpdateDefaultAddress End point in the format"
        + "http://domain/restendpoint/${storeId}/loginservice")
    String endPoint() default "https://mdev3.americangirl.corp.mattel.com/wcs/resources/store/STORE_ID/xperson/updateperson?storeId=10651&updateCookies=true&responseFormat=json";
  }

  @Activate
  public void activate(final Config config) {
    updateDefaultAddressEndpoint = config.endPoint();
  }
}
