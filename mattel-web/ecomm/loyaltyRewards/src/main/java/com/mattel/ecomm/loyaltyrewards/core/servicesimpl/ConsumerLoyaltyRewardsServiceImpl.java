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
import com.mattel.ecomm.coreservices.core.interfaces.ConsumerLoyaltyRewardsService;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.ConsumerLoyaltyRewardsResponse;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@Component(service = ConsumerLoyaltyRewardsService.class)
@Designate(ocd = ConsumerLoyaltyRewardsServiceImpl.Config.class)
public class ConsumerLoyaltyRewardsServiceImpl implements ConsumerLoyaltyRewardsService {

  private static final String END_POINT_URL_KEY = "endPointUrl";
  private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
  private static final String EXECUTE_SERVICE = "execute";

  private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerLoyaltyRewardsServiceImpl.class);
  private static final ObjectReader RESP_READER = ResourceMapper
      .getReaderInstance(ConsumerLoyaltyRewardsResponse.class);
  @Reference
  PropertyReaderService propertyReaderService;
  private String getConsumerLoyaltyRewardsDataEndpoint;

  @SuppressWarnings("unchecked")
  @Override
  public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap)
      throws ServiceException {
    ConsumerLoyaltyRewardsServiceImpl.LOGGER.info("{} - start", ConsumerLoyaltyRewardsServiceImpl.EXECUTE_SERVICE);
    final long startTime = System.currentTimeMillis();

    try (CloseableHttpClient httpClient = createCustomClient(dataMap)) {
      final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
      final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
      final HttpResponse httpResponse = HttpRequestHandler.get(httpClient,
          dataMap.get(ConsumerLoyaltyRewardsServiceImpl.END_POINT_URL_KEY).toString(), requestCookies, null,
          httpClientContext);
      final int status = httpResponse.getStatusLine().getStatusCode();
      ConsumerLoyaltyRewardsServiceImpl.LOGGER.debug("Response status is {}", status);
      if (null != httpResponse.getEntity() && !isError(status)) {
        try (final InputStream inputStream = httpResponse.getEntity().getContent()) {
          final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");
          ConsumerLoyaltyRewardsResponse consumerLoyaltyRewardsResponse;
          consumerLoyaltyRewardsResponse = ConsumerLoyaltyRewardsServiceImpl.RESP_READER.readValue(inputStream);
          if (null != cookieResheader && cookieResheader.length > 0) {
        	final List<Cookie> cookieList = fetchResponsCookies(dataMap,httpClientContext);
            consumerLoyaltyRewardsResponse.setCookieList(cookieList);
          }
          ConsumerLoyaltyRewardsServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, ConsumerLoyaltyRewardsServiceImpl.EXECUTE_SERVICE,
              (System.currentTimeMillis() - startTime));
          logServiceErrors(consumerLoyaltyRewardsResponse);
          return consumerLoyaltyRewardsResponse;
        }
      } else {
        final long endTime = System.currentTimeMillis();
        ConsumerLoyaltyRewardsServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, ConsumerLoyaltyRewardsServiceImpl.EXECUTE_SERVICE,
            endTime - startTime);
        generalExceptionHandling(status, httpResponse.getEntity());
      }
    } catch (final IOException io) {
      final long endTime = System.currentTimeMillis();
      ConsumerLoyaltyRewardsServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, ConsumerLoyaltyRewardsServiceImpl.EXECUTE_SERVICE,
          endTime - startTime);
      ConsumerLoyaltyRewardsServiceImpl.LOGGER.error("Encountered error:", io);
      throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
    }
    final long endTime = System.currentTimeMillis();
    ConsumerLoyaltyRewardsServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, ConsumerLoyaltyRewardsServiceImpl.EXECUTE_SERVICE,
        endTime - startTime);
    return null;
  }

  @Override
  public ConsumerLoyaltyRewardsResponse fetch(Map<String, Object> requestMap) throws ServiceException {
    ConsumerLoyaltyRewardsServiceImpl.LOGGER.info("fetch - start");
    final long startTime = System.currentTimeMillis();
    final String storekey = (String) requestMap.get(Constant.STORE_KEY);
    final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
    final String storeId = propertyReaderService.getStoreId(storekey);
    final String domain = propertyReaderService.getCookieDomain(domainKey);
    final Map<String, Object> dataMap = new HashMap<>();
    String endPointUrl = getConsumerLoyaltyRewardsDataEndpoint.replaceAll(ConsumerLoyaltyRewardsServiceImpl.STORE_ID_PLACEHOLDER,
        storeId);
    endPointUrl = addLangIdToRequestUri(requestMap, endPointUrl);
    final long endTime;
    mapCommonRequestVariables(requestMap,dataMap,domain);
    dataMap.put(ConsumerLoyaltyRewardsServiceImpl.END_POINT_URL_KEY, endPointUrl);
    dataMap.put(Constant.DEF_CONNECT_TIMEOUT, getDefaultConnectTimeout(propertyReaderService, storekey));
    endTime = System.currentTimeMillis();
    ConsumerLoyaltyRewardsServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "ConsumerLoyaltyRewardsServiceImpl", endTime - startTime);
    return (ConsumerLoyaltyRewardsResponse) execute(null, dataMap);
  }

  @ObjectClassDefinition(name = "WCS Consumer Loyalty And Rewards Configurations", 
      description = "Consumer Loyalty And Rewards Configuration for WCS Vendor")
  public @interface Config {
    @AttributeDefinition(name = "PDPProduct End Point", 
        description = "Please Enter the Consumer Loyalty And Rewards End point in the format"
        + "http://domain/restendpoint/${storeId}/consumerloyaltyrewardsservice")
    String endPoint() default "https://mqa.services.commerce.mattel.com/wcs/resources/store/STORE_ID/xloyalty/xGetConsumerLoyaltyData?storeId=STORE_ID&responseFormat=json&langId=LANG_ID";
  }

  @Activate
  public void activate(final Config config) {
	getConsumerLoyaltyRewardsDataEndpoint = config.endPoint();
  }

}
