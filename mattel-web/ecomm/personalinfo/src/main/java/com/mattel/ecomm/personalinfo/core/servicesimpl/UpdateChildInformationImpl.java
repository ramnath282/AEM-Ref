package com.mattel.ecomm.personalinfo.core.servicesimpl;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.UpdateChildInfo;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.ChildInformationRequest;
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

@Component(service = UpdateChildInfo.class)
@Designate(ocd = UpdateChildInformationImpl.Config.class)
public class UpdateChildInformationImpl implements UpdateChildInfo {

  private static final String END_POINT_URL_KEY = "endPointUrl";
  private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
  private static final String UPDATE_CHILD_INFORMATION_SERVICE = "updateChildInfoService";
  private static final String EXECUTE_SERVICE = "execute";
  private static final Logger LOGGER = LoggerFactory.getLogger(UpdateChildInformationImpl.class);
  private static final ObjectReader RESP_READER = ResourceMapper
      .getReaderInstance(BaseResponse.class);
  private static final ObjectReader REQ_READER = ResourceMapper
      .getReaderInstance(ChildInformationRequest.class);
  @Reference
  PropertyReaderService propertyReaderService;
  private String updateChildInfoEndPoint;

  @SuppressWarnings("unchecked")
  @Override
  public BaseResponse execute(final BaseRequest baseRequest, final Map<String, Object> dataMap)
      throws ServiceException {
    final long startTime = System.currentTimeMillis();

    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
      final ChildInformationRequest childInformationRequest = (ChildInformationRequest) baseRequest;
      final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
      final Map<String, String> requestHeaders = new HashMap<>();
      final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
      final HttpResponse httpResponse;
      final int status;

      requestHeaders.put("Accept", "application/json");
      requestHeaders.put("Content-type", "application/json");
      httpResponse = HttpRequestHandler.put(httpClient,
          dataMap.get(UpdateChildInformationImpl.END_POINT_URL_KEY).toString(), requestCookies,
          requestHeaders, childInformationRequest, httpClientContext);
      status = httpResponse.getStatusLine().getStatusCode();

      UpdateChildInformationImpl.LOGGER.debug("Response status is {}", status);
      BaseResponse baseResponse = null;
      if (!isError(status)) {
        try (final InputStream inputStream = httpResponse.getEntity().getContent()) {
          final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");

          baseResponse = UpdateChildInformationImpl.RESP_READER.readValue(inputStream);

          if (null != cookieResheader && cookieResheader.length > 0) {
            final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
            final String domain = (String) dataMap.get("domain");
            final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain,
                httpClientContext.getCookieStore().getCookies());

            baseResponse.setCookieList(cookieList);
          }

          UpdateChildInformationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
              UpdateChildInformationImpl.EXECUTE_SERVICE, (System.currentTimeMillis() - startTime));
          return baseResponse;
        }
      } else {
        final long endTime = System.currentTimeMillis();

        UpdateChildInformationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
            UpdateChildInformationImpl.EXECUTE_SERVICE, endTime - startTime);
        generalExceptionHandling(status);
      }
    } catch (final IOException io) {
      final long endTime = System.currentTimeMillis();

      UpdateChildInformationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
          UpdateChildInformationImpl.EXECUTE_SERVICE, endTime - startTime);
      UpdateChildInformationImpl.LOGGER.error("Encountered error:", io);
      throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
    }

    final long endTime = System.currentTimeMillis();
    UpdateChildInformationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
        UpdateChildInformationImpl.EXECUTE_SERVICE, endTime - startTime);
    return null;
  }

  @Override
  public BaseResponse updateChildInfo(final Map<String, Object> requestMap)
      throws ServiceException {
    final long startTime = System.currentTimeMillis();

    try {
      final ChildInformationRequest updateChildInformationRequest = UpdateChildInformationImpl.REQ_READER
          .readValue(requestMap.get(Constant.REQUEST_BODY).toString());
      final Map<String, Object> dataMap = new HashMap<>();
      final String storekey = (String) requestMap.get(Constant.STORE_KEY);
      final String storeId = propertyReaderService.getStoreId(storekey);
      final String endPointUrl = updateChildInfoEndPoint
          .replaceAll(UpdateChildInformationImpl.STORE_ID_PLACEHOLDER, storeId);
      final long endTime;

      mapCommonRequestVariables(requestMap, dataMap,
          propertyReaderService.getCookieDomain((String) requestMap.get(Constant.DOMAIN_KEY)));
      dataMap.put(UpdateChildInformationImpl.END_POINT_URL_KEY, endPointUrl);
      endTime = System.currentTimeMillis();

      UpdateChildInformationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
          UpdateChildInformationImpl.UPDATE_CHILD_INFORMATION_SERVICE, endTime - startTime);
      return execute(updateChildInformationRequest, dataMap);
    } catch (final IOException e) {
      final long endTime = System.currentTimeMillis();

      UpdateChildInformationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
          UpdateChildInformationImpl.UPDATE_CHILD_INFORMATION_SERVICE, endTime - startTime);
      UpdateChildInformationImpl.LOGGER.error("Error encountered: {}", e);
      throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
    }
  }

  @Activate
  public void activate(final Config config) {
    updateChildInfoEndPoint = config.endPoint();
  }

  @Override
  public boolean isError(int status) {
    boolean flag = true;
    if (status == 200 || status == 201 || status == 204) {
      flag = false;
    }
    return flag;
  }

  @ObjectClassDefinition(name = "WCS UpdateChildInformation Configurations", description = "UpdateChildInformation Configuration for WCS Vendor")
  public @interface Config {
    @AttributeDefinition(name = "UpdateChildInformation End Point", description = "Please enter the UpdateChildInformation end point in the format"
        + "http://domain/restendpoint/${storeId}/loginservice")
    String endPoint() default "https://mdev.services.commerce.mattel.com/wcs/resources/store/STORE_ID/xupdatechild/update?storeId=STORE_ID&responseFormat=json&updateCookies=true";
  }
}
