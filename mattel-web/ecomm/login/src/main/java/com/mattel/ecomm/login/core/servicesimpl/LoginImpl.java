package com.mattel.ecomm.login.core.servicesimpl;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.Login;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.LoginRequest;
import com.mattel.ecomm.coreservices.core.pojos.LoginResponse;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.Cookie;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.sling.commons.metrics.MetricsService;
import org.apache.sling.commons.metrics.Timer;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
@author CTS.
 */
@Component(service = Login.class)
@Designate(ocd = LoginImpl.Config.class)
public class LoginImpl implements Login {
  private static final String END_POINT_URL_KEY = "endPointUrl";
  private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
  private static final ObjectReader RESP_READER = ResourceMapper
      .getReaderInstance(LoginResponse.class);
  private static final ObjectReader REQ_READER = ResourceMapper
      .getReaderInstance(LoginRequest.class);
  @Reference
  private PropertyReaderService propertyReaderService;
  @Reference
  private MetricsService metricsService;
  private Timer loginWcsTimer;
  private Timer loginAemTimer;
  private String loginEndPoint;

  private static final Logger LOGGER = LoggerFactory.getLogger(LoginImpl.class);

  /**
   * Method takes header parameter and returns Login Response.
   * 
   * @param requestHeader
   * @return LoginResponse
   * @throws ServiceException
   */
  @Override
  public LoginResponse login(Map<String, Object> requestHeader) throws ServiceException {
    final Timer.Context aemContext = loginAemTimer.time();
    final long startTime = System.currentTimeMillis();
    final String methodName = "login";
    LoginImpl.LOGGER.info("Login Response Starts");
    try {
      LoginImpl.LOGGER.debug("End from Config is {}", loginEndPoint);
      final LoginRequest loginRequest = LoginImpl.REQ_READER
          .readValue(requestHeader.get(Constant.REQUEST_BODY).toString());
      final String storeKey = (String) requestHeader.get(Constant.STORE_KEY);
      final String domainKey = (String) requestHeader.get(Constant.DOMAIN_KEY);
      final String storeId = propertyReaderService.getStoreId(storeKey);
      final String endPointUrl = loginEndPoint.replaceAll(LoginImpl.STORE_ID_PLACEHOLDER, storeId);
      final String domain = propertyReaderService.getCookieDomain(domainKey);
      final Cookie[] requestCookieObjects = (Cookie[]) requestHeader
          .get(Constant.REQUEST_COOKIES_KEY);
      String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
      cookieNames = ArrayUtils.addAll(cookieNames, ServiceCookieMapping.MINICART.getCookieNames());
      cookieNames = ArrayUtils.addAll(cookieNames, ServiceCookieMapping.SHOPIFY_FLOW.getCookieNames());
      LoginImpl.LOGGER.debug("Invoking login service for user: {}, with endpoint: {}",
          loginRequest.getLogonId(), endPointUrl);
      if (!StringUtils.isEmpty(loginRequest.getLogonId())) {
        loginRequest.setLogonId(
            new StringBuilder(storeId).append("|").append(loginRequest.getLogonId()).toString());
      }
      final Map<String, Object> dataMap = new HashMap<>();
      dataMap.put("validCookieNames", cookieNames);
      dataMap.put(LoginImpl.END_POINT_URL_KEY, endPointUrl);
      dataMap.put("requestCookie", requestCookieObjects);
      dataMap.put("domain", domain);
      dataMap.put("validCookies", cookieNames);
      dataMap.put(Constant.DEF_CONNECT_TIMEOUT,
          getDefaultConnectTimeout(propertyReaderService, storeKey));
      final long endTime = System.currentTimeMillis();
      LoginImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
      return (LoginResponse) execute(loginRequest, dataMap);
    } catch (final IOException e) {
      final long endTime = System.currentTimeMillis();
      LoginImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
      LoginImpl.LOGGER.error("Io Exception occured {}", e);
      throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
    } finally {
      aemContext.stop();
    }
  }

  /**
   * This method executes http request and returns response from WCS.
   * 
   * @param baseRequest
   * @return BaseResponse.
   * @throws ServiceException
   */

  @Override
  public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap)
      throws ServiceException {
    final long startTime = System.currentTimeMillis();
    final String methodName = "execute";
    try (CloseableHttpClient client = createCustomClient(dataMap)) {
      LoginImpl.LOGGER.info("Start of Login Execute Method.");
      final LoginRequest loginRequest = (LoginRequest) baseRequest;
      LoginResponse loginResponse;
      final List<Cookie> finalCookieList = new ArrayList<>();
      final Cookie[] requestCookies = (Cookie[]) dataMap.get("requestCookie");
      final String[] validCookieName = (String[]) dataMap.get("validCookies");
      final String domain = (String) dataMap.get("domain");

      if (Objects.nonNull(requestCookies)) {
        for (final Cookie cookie : requestCookies) {
          if (CookieUtils.checkValidServiceCookie(cookie.getName(), validCookieName) && !"SHOPIFY_FLOW".equals(cookie.getName())) {
            cookie.setMaxAge(0);
            cookie.setPath("/");
            cookie.setDomain(domain);
            finalCookieList.add(cookie);
          }
        }
      }

      final StringEntity requestEntity = new StringEntity(loginRequest.toString());
      final HttpPost postMethod = new HttpPost(dataMap.get(LoginImpl.END_POINT_URL_KEY).toString());
      postMethod.setEntity(requestEntity);
      postMethod.setHeader("Accept", "application/json");
      postMethod.setHeader("Content-type", "application/json");
      
      if(Objects.nonNull(requestCookies) && Arrays.asList(requestCookies).stream().anyMatch(cookie -> "SHOPIFY_FLOW".equals(cookie.getName()))){
        populateShopifyFlowCookie(requestCookies,postMethod);
      }
      
      final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
      final Timer.Context wcsContext = loginWcsTimer.time();
      HttpResponse httpResponse;

      try {
        final long wcsExecutionStartTime = System.currentTimeMillis();
        httpResponse = client.execute(postMethod, httpClientContext);
        final long wcsExecutionEndTime = System.currentTimeMillis();
        LoginImpl.LOGGER.debug("WCS Execution Time for Login is {}",
            wcsExecutionEndTime - wcsExecutionStartTime);
      } finally {
        wcsContext.stop();
      }

      final int status = httpResponse.getStatusLine().getStatusCode();
      LoginImpl.LOGGER.debug("Status from Login is {}", status);
      final InputStream inputStream = httpResponse.getEntity().getContent();
      final boolean isError = isError(status);
      if (!isError) {
        final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");
        LoginImpl.LOGGER.debug("Cookies are {}", cookieResheader);
        final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
        final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain,
            httpClientContext.getCookieStore().getCookies());
        for (final Cookie cookie : cookieList) {
          finalCookieList.add(cookie);
        }
        loginResponse = LoginImpl.RESP_READER.readValue(inputStream);
        LoginImpl.LOGGER.debug("Login Response Object is {}", loginResponse);
        final long endTime = System.currentTimeMillis();
        LoginImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
        LoginImpl.LOGGER.debug("Login Response Object is {}", loginResponse);
        loginResponse.setCookieList(finalCookieList);
        logServiceErrors(loginResponse);
        return loginResponse;
      } else {
        final long endTime = System.currentTimeMillis();
        LoginImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
        generalExceptionHandling(status, httpResponse.getEntity());
      }

    } catch (final IOException e) {
      final long endTime = System.currentTimeMillis();
      LoginImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
      LoginImpl.LOGGER.error("IO Exception Occured:", e);
      throw new ServiceException(Constant.IO_ERROR_KEY, "Io Exception Occured");
    }
    final long endTime = System.currentTimeMillis();
    LoginImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
    return null;
  }
  
  /**
   * Populate Shopify flow cookies in login request.
   *
   * @param requestCookies
   *          The incoming guest user cookies.
   * @param postMethod
   *          The request method.
   */
  private void populateShopifyFlowCookie(Cookie[] requestCookies, HttpPost postMethod) {
	  LoginImpl.LOGGER.info("Start of populateShopifyFlowCookie method");
	  if (Objects.nonNull(requestCookies)) {
	      LOGGER.debug("Shopify flow cookie present, populating Shopify flow cookie in login request");
	      final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookies,
	          ServiceCookieMapping.SHOPIFY_FLOW.getCookieNames());

	      if (!reqCookies.isEmpty()) {
	        reqCookies.forEach(reqCookie -> {
	          postMethod.addHeader("Cookie", reqCookie);
	          LoginImpl.LOGGER
	              .debug("String value of Cookie object added in request header is {}", reqCookie);
	        });
	      }
	    }
	  LoginImpl.LOGGER.info("End of populateShopifyFlowCookie method");
  }

/**
   * Populate guest user cookies in login request. Used for cart merging.
   *
   * @param requestCookies
   *          The incoming guest user cookies.
   * @param isGuestUser
   *          Flag indicating if given user is a guest user.
   * @param postMethod
   *          The request method.
   */
  protected void populateGuestUserCookies(final Cookie[] requestCookies, boolean isGuestUser,
      final HttpPost postMethod) {
    if (Objects.nonNull(requestCookies) && isGuestUser) {
      LOGGER.debug("Guest user detected, populating guest user cookies in login request");
      final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookies,
          ServiceCookieMapping.DEFAULT.getCookieNames());

      if (!reqCookies.isEmpty()) {
        reqCookies.forEach(reqCookie -> {
          postMethod.addHeader("Cookie", reqCookie);
          LoginImpl.LOGGER
              .debug("String value of Cookie object added in request header is {}", reqCookie);
        });
      }
    }
  }

  @ObjectClassDefinition(name = "WCS Login Configurations", description = "Login Configuration for WCS Vendor")
  public @interface Config {

    @AttributeDefinition(name = "Login End Point", description = "Please Enter the Login End point in the format"
        + "http://domain/restendpoint/STORE_ID/serviceendpoint")
    String endPoint() default "https://mdev.services.commerce.mattel.com/wcs/resources/store/STORE_ID/loginidentity?responseFormat=json&updateCookies=true";

  }

  @Activate
  public void activate(final Config config) {
    loginEndPoint = config.endPoint();
    final long systemCurrentTimeInMillis = System.currentTimeMillis();
    loginAemTimer = metricsService.timer("login-aem-" + systemCurrentTimeInMillis);
    loginWcsTimer = metricsService.timer("login-wcs-" + systemCurrentTimeInMillis);
  }

  @Deactivate
  public void deactivate() {
    loginAemTimer = null;
    loginWcsTimer = null;
  }
}
