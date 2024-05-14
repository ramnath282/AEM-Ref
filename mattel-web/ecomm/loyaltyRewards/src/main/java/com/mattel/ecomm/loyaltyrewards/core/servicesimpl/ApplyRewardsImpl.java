package com.mattel.ecomm.loyaltyrewards.core.servicesimpl;

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

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.ApplyRewards;
import com.mattel.ecomm.coreservices.core.pojos.ApplyRewardsRequest;
import com.mattel.ecomm.coreservices.core.pojos.ApplyRewardsResponse;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

/*
@author CTS.
 */
@Component(service = ApplyRewards.class)
@Designate(ocd = ApplyRewardsImpl.Config.class)
public class ApplyRewardsImpl implements ApplyRewards {
  private static final String END_POINT_URL_KEY = "endPointUrl";
  private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
  private static final ObjectReader RESP_READER = ResourceMapper
      .getReaderInstance(ApplyRewardsResponse.class);
  private static final ObjectReader REQ_READER = ResourceMapper
      .getReaderInstance(ApplyRewardsRequest.class);
  @Reference
  private PropertyReaderService propertyReaderService;
  private String applyRewardsEndPoint;

  private static final Logger LOGGER = LoggerFactory.getLogger(ApplyRewardsImpl.class);

  /**
   * Method takes header parameter and returns Login Response.
   * 
   * @param requestHeader
   * @return LoginResponse
   * @throws ServiceException
   */
  @Override
  public ApplyRewardsResponse applyReward(Map<String, Object> requestHeader) throws ServiceException {
    final long startTime = System.currentTimeMillis();
    final String methodName = "applyReward";
    ApplyRewardsImpl.LOGGER.info("Apply Rewards Response Starts");
    try {
      ApplyRewardsImpl.LOGGER.debug("End from Config is {}", applyRewardsEndPoint);
      final ApplyRewardsRequest applyRewardsRequest = ApplyRewardsImpl.REQ_READER
          .readValue(requestHeader.get(Constant.REQUEST_BODY).toString());
      final String storeKey = (String) requestHeader.get(Constant.STORE_KEY);
      final String domainKey = (String) requestHeader.get(Constant.DOMAIN_KEY);
      final String storeId = propertyReaderService.getStoreId(storeKey);
      final String endPointUrl = applyRewardsEndPoint.replaceAll(ApplyRewardsImpl.STORE_ID_PLACEHOLDER, storeId);
      final String domain = propertyReaderService.getCookieDomain(domainKey);
      ApplyRewardsImpl.LOGGER.debug("Invoking ApplyRewards service with endpoint: {}", endPointUrl);
      final Map<String, Object> dataMap = new HashMap<>();
      mapCommonRequestVariables(requestHeader,dataMap,domain);
      dataMap.put(ApplyRewardsImpl.END_POINT_URL_KEY, endPointUrl);
      dataMap.put(Constant.DEF_CONNECT_TIMEOUT, getDefaultConnectTimeout(propertyReaderService, storeKey));
      final long endTime = System.currentTimeMillis();
      ApplyRewardsImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
      return (ApplyRewardsResponse) execute(applyRewardsRequest, dataMap);
    } catch (final IOException e) {
      final long endTime = System.currentTimeMillis();
      ApplyRewardsImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
      ApplyRewardsImpl.LOGGER.error("Io Exception occured {}", e);
      throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
    }
  }

  /**
   * This method executes http request and returns response from WCS.
   * 
   * @param baseRequest
   * @return BaseResponse.
   * @throws ServiceException
   */

  @SuppressWarnings("unchecked")
  @Override
  public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap)
      throws ServiceException {
    final long startTime = System.currentTimeMillis();
    final String methodName = "execute";
    try (CloseableHttpClient client = createCustomClient(dataMap)) {
      ApplyRewardsImpl.LOGGER.info("Start of Login Execute Method.");
      final ApplyRewardsRequest applyRewardsRequest = (ApplyRewardsRequest) baseRequest;
      ApplyRewardsResponse applyRewardsResponse;
      
      final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
      final Map<String, String> requestHeaders = new HashMap<>();
      requestHeaders.put("Accept", "application/json");
      requestHeaders.put("Content-type", "application/json");
      final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
      
      final HttpResponse httpResponse = HttpRequestHandler.post(client,
              dataMap.get(ApplyRewardsImpl.END_POINT_URL_KEY).toString(), requestCookies,
              requestHeaders, applyRewardsRequest, httpClientContext);
      final int status = httpResponse.getStatusLine().getStatusCode();
      ApplyRewardsImpl.LOGGER.debug("Status from ApplyRewards is {}", status);
      final InputStream inputStream = httpResponse.getEntity().getContent();
      final boolean isError = isError(status);
      if (!isError) {
        final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");
        ApplyRewardsImpl.LOGGER.debug("Cookies are {}", cookieResheader);
        applyRewardsResponse = ApplyRewardsImpl.RESP_READER.readValue(inputStream);
        ApplyRewardsImpl.LOGGER.debug("Apply Rewards Response Object is {}", applyRewardsResponse);
        final long endTime = System.currentTimeMillis();
        ApplyRewardsImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
        final List<Cookie> cookieList = fetchResponsCookies(dataMap,httpClientContext);
        applyRewardsResponse.setCookieList(cookieList);
        logServiceErrors(applyRewardsResponse);
        return applyRewardsResponse;
      } else {
        final long endTime = System.currentTimeMillis();
        ApplyRewardsImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
        generalExceptionHandling(status, httpResponse.getEntity());
      }

    } catch (final IOException e) {
      final long endTime = System.currentTimeMillis();
      ApplyRewardsImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
      ApplyRewardsImpl.LOGGER.error("IO Exception Occured:", e);
      throw new ServiceException(Constant.IO_ERROR_KEY, "Io Exception Occured");
    }
    final long endTime = System.currentTimeMillis();
    ApplyRewardsImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
    return null;
  }


  @ObjectClassDefinition(name = "WCS Apply Rewards Configurations", description = "Apply Rewards Configuration for WCS Vendor")
  public @interface Config {

    @AttributeDefinition(name = "Apply Rewards End Point", description = "Please Enter the Apply Rewards End point in the format"
        + "http://domain/restendpoint/STORE_ID/serviceendpoint")
    String endPoint() default "https://mqa.services.commerce.mattel.com/wcs/resources/store/STORE_ID/cart/@self/assigned_promotion_code?responseFormat=json&updateCookies=true";

  }

  @Activate
  public void activate(final Config config) {
	applyRewardsEndPoint = config.endPoint();
  }

}
