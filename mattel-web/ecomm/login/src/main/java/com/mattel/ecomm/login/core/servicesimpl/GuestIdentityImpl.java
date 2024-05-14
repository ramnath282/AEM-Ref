/**
 * Post logic class for guest identity service
 */
package com.mattel.ecomm.login.core.servicesimpl;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.GuestIdentityService;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.LoginResponse;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
 * @author
 *
 */
@Component(service = GuestIdentityService.class)
@Designate(ocd = GuestIdentityImpl.Config.class)
public class GuestIdentityImpl implements GuestIdentityService {
  private static final Logger LOGGER = LoggerFactory.getLogger(GuestIdentityImpl.class);
  private static final String END_POINT_URL_KEY = "endPointUrl";
  private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
  private static final ObjectReader RESP_READER = ResourceMapper
      .getReaderInstance(LoginResponse.class);
  private String guestIdentityEndPointUrl;
  @Reference
  private PropertyReaderService propertyReaderService;

  /*
   * (non-Javadoc)
   *
   * @see com.mattel.ecomm.coreservices.core.interfaces.BaseService#execute(com.
   * mattel.ecomm.coreservices.core.pojos.BaseRequest, java.util.Map)
   */
  @Override
  public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap)
      throws ServiceException {
    final long startTime = System.currentTimeMillis();
    final String methodName = "execute";
    try (CloseableHttpClient httpClient = createCustomClient(dataMap)) {
      GuestIdentityImpl.LOGGER.info("Start of Guest Login Execute Method.");
      final Map<String, String> requestHeaders = new HashMap<>();
      final String[] validCookieNames = (String[]) dataMap.get(Constant.VALID_COOKIE_NAMES_KEY);
      final Cookie[] requestCookies = (Cookie[]) dataMap.get(Constant.REQUEST_COOKIES_KEY);
      final String domain = (String) dataMap.get("domain");
      final List<Cookie> finalCookieList = new ArrayList<>();
      final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
      LoginResponse guestIdentityResponse;
      requestHeaders.put("Accept", Constant.CONTENT_TYPE_APPLICATION_JSON);
      requestHeaders.put("Content-type", Constant.CONTENT_TYPE_APPLICATION_JSON);
      final long wcsExecutionStartTime = System.currentTimeMillis();

      if (Objects.nonNull(requestCookies)) {
        for (Cookie cookie: requestCookies) {
            if (CookieUtils.checkValidServiceCookie(cookie.getName(), validCookieNames)){
                cookie.setMaxAge(0);
                cookie.setPath("/");
                cookie.setDomain(domain);
                // Adding request cookie with max age as zero for invalidation.
                finalCookieList.add(cookie);
            }
        }
      }

      final HttpResponse httpResponse = HttpRequestHandler.post(httpClient,
          dataMap.get(GuestIdentityImpl.END_POINT_URL_KEY).toString(), null, requestHeaders, null,
          httpClientContext);
      final long wcsExecutionEndTime = System.currentTimeMillis();
      GuestIdentityImpl.LOGGER.debug("WCS Execution Time for Guest Identity is {}",
          wcsExecutionEndTime - wcsExecutionStartTime);
      final int status = httpResponse.getStatusLine().getStatusCode();
      GuestIdentityImpl.LOGGER.debug("Status from Guest Login is {}", status);
      final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");

      if (Objects.nonNull(httpResponse.getEntity()) && !isError(status)) {
        try (final InputStream inputStream = httpResponse.getEntity().getContent()) {
          guestIdentityResponse = GuestIdentityImpl.RESP_READER.readValue(inputStream);

          if (null != cookieResheader && cookieResheader.length > 0) {
            final List<Cookie> cookieList = CookieUtils.constructCookieList(validCookieNames, domain,
                httpClientContext.getCookieStore().getCookies());

            // Adding the response cookies
            finalCookieList.addAll(cookieList);
          }

          guestIdentityResponse.setCookieList(finalCookieList);
          final long endTime = System.currentTimeMillis();
          GuestIdentityImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
          logServiceErrors(guestIdentityResponse);
          return guestIdentityResponse;
        }
      } else {
        final long endTime = System.currentTimeMillis();
        GuestIdentityImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
        generalExceptionHandling(status, httpResponse.getEntity());
      }
    } catch (final IOException e) {
      final long endTime = System.currentTimeMillis();
      GuestIdentityImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
      GuestIdentityImpl.LOGGER.error("IO Exception occured:", e);
      throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
    }
    final long endTime = System.currentTimeMillis();
    GuestIdentityImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
    return null;
  }

  /*
   * (non-Javadoc)
   *
   * @see com.mattel.ecomm.coreservices.core.interfaces.GuestIdentityservice#
   * guestIdentity(java.util.Map)
   */
  @Override
  public LoginResponse guestIdentity(Map<String, Object> requestMap) throws ServiceException {
    final long startTime = System.currentTimeMillis();
    final String methodName = "GuestIdentity";
    GuestIdentityImpl.LOGGER.info("Guest Identity Response Starts");
    GuestIdentityImpl.LOGGER.debug("End from Config is {}", guestIdentityEndPointUrl);
    final String storeKey = (String) requestMap.get(Constant.STORE_KEY);
    final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
    final String storeId = propertyReaderService.getStoreId(storeKey);
    final String endPointUrl = guestIdentityEndPointUrl
        .replaceAll(GuestIdentityImpl.STORE_ID_PLACEHOLDER, storeId);
    final String domain = propertyReaderService.getCookieDomain(domainKey);
    GuestIdentityImpl.LOGGER.debug("Connection End Point is {}", endPointUrl);
    final Map<String, Object> dataMap = new HashMap<>();
    dataMap.put("domain", domain);
    final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
    final Cookie[] reqCookies = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);

    dataMap.put(Constant.VALID_COOKIE_NAMES_KEY, cookieNames);
    dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
    dataMap.put(GuestIdentityImpl.END_POINT_URL_KEY, endPointUrl);
    dataMap.put(Constant.DEF_CONNECT_TIMEOUT, getDefaultConnectTimeout(propertyReaderService, storeKey));
    final long endTime = System.currentTimeMillis();
    GuestIdentityImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
    return (LoginResponse) execute(null, dataMap);
  }

  /**
   * Guest Identity Service configuration
   *
   * @author
   *
   */
  @ObjectClassDefinition(name = "WCS Guest Identity Configurations", description = "Guest Identity Configuration for WCS Vendor")
  public @interface Config {

    @AttributeDefinition(name = "Guest Identity End Point", description = "Please Enter the Guest Identity End point in the format"
        + "http://domain/restendpoint/STORE_ID/serviceendpoint")
    String endPoint() default "https://mdev.services.commerce.mattel.com/wcs/resources/store/STORE_ID/guestidentity?storeId=STORE_ID&updateCookies=true&responseFormat=json";

  }

  @Activate
  public void activate(final Config config) {
    guestIdentityEndPointUrl = config.endPoint();
  }
}
