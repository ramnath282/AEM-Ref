package com.mattel.ecomm.agrewards.core.servicesimpl;

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

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.AgRewards;
import com.mattel.ecomm.coreservices.core.pojos.AgRewardsRequest;
import com.mattel.ecomm.coreservices.core.pojos.AgRewardsResponse;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@Component(service = AgRewards.class)
@Designate(ocd = AgRewardsImpl.Config.class)
public class AgRewardsImpl implements AgRewards {
    private static final String END_POINT_URL_KEY = "endPointUrl";
    private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
    private static final Logger LOGGER = LoggerFactory.getLogger(AgRewardsImpl.class);
    private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(AgRewardsResponse.class);

    @Reference
    PropertyReaderService propertyReaderService;
    @Reference
    private MetricsService metricsService;
    private Timer wcsTimer;
    private Timer aemTimer;

    private String agRewardsEndPoint;

    /*
     * method execute HTTP method with respect to the calling service.
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();
        AgRewardsImpl.LOGGER.info("Ag rewards Execute Start");
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            final List<String> reqCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
            final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
            final Timer.Context wcsContext = wcsTimer.time();
            final HttpResponse httpResponse;

            try {
            final long wcsExecutionStartTime = System.currentTimeMillis();
            httpResponse = HttpRequestHandler.get(httpClient,
                    dataMap.get(AgRewardsImpl.END_POINT_URL_KEY).toString(), reqCookies, null, httpClientContext);
            final long wcsExecutionEndTime = System.currentTimeMillis();
            AgRewardsImpl.LOGGER.debug("WCS Execution Time for AgRewards is {}",
                    wcsExecutionEndTime - wcsExecutionStartTime);
            } finally {
            wcsContext.stop();
            }

            final int status = httpResponse.getStatusLine().getStatusCode();
            AgRewardsImpl.LOGGER.debug("Status is {}", status);

            final InputStream inputStream = httpResponse.getEntity().getContent();
            AgRewardsResponse agRewardsResponse = null;
            final boolean isError = isError(status);
            if (!isError) {
                final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");
                final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
                final String domain = (String) dataMap.get("domain");
                AgRewardsImpl.LOGGER.debug("Cookies are {}", cookieResheader);
                final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain,
                        httpClientContext.getCookieStore().getCookies());
                agRewardsResponse = AgRewardsImpl.RESP_READER.readValue(inputStream);
                agRewardsResponse.setCookieList(cookieList);
            } else {
                final long endTime = System.currentTimeMillis();
                AgRewardsImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "AgRewards execute", endTime - startTime);
                generalExceptionHandling(status);
            }
            final long endTime = System.currentTimeMillis();
            AgRewardsImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "execute", endTime - startTime);
            return agRewardsResponse;
        } catch (final IOException io) {
            final long endTime = System.currentTimeMillis();
            AgRewardsImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "execute IOException", endTime - startTime);
            AgRewardsImpl.LOGGER.error("IO Exception Occured:", io);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
        }
    }

    /*
     * Method prepares request and response object specific to the service and
     * internally call execute method passing created request and response
     * 
     */
    @Override
    public AgRewardsResponse getAgReward(Map<String, Object> requestMap) throws ServiceException {
        final Timer.Context aemContext = aemTimer.time();
        
        try {
        final long startTime = System.currentTimeMillis();
        AgRewardsImpl.LOGGER.info("getAgReward Starts");

        final AgRewardsRequest agRewardsRequest = new AgRewardsRequest();

        final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
        final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
        final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects, cookieNames);
        final String storekey = (String) requestMap.get(Constant.STORE_KEY);
        final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
        final String storeId = propertyReaderService.getStoreId(storekey);
        final String domain = propertyReaderService.getCookieDomain(domainKey);
        final String endPointUrl = agRewardsEndPoint.replaceAll(AgRewardsImpl.STORE_ID_PLACEHOLDER, storeId);
        final Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("validCookieNames", cookieNames);
        dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
        dataMap.put("domain", domain);
        dataMap.put(AgRewardsImpl.END_POINT_URL_KEY, endPointUrl);

        final long endTime = System.currentTimeMillis();
        AgRewardsImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "getAgReward", endTime - startTime);
        return (AgRewardsResponse) execute(agRewardsRequest, dataMap);
        } finally {
            aemContext.stop();
        }
    }

    @ObjectClassDefinition(name = "WCS AgRewards Configurations", description = "AgRewards Configuration for WCS Vendor")
    public @interface Config {
        @AttributeDefinition(name = "AgRewards End Point", description = "Please Enter the AgRewards End point in the format"
                + "http://domain/restendpoint/${storeId}/loginservice")
        String endPoint() default "http://mdev.americangirl.com/wcs/resources/store/STORE_ID/xloyalty/xloyaltyRewards?storeId=STORE_ID&responseFormat=json";
    }

    @Activate
    public void activate(final Config config) {
        agRewardsEndPoint = config.endPoint();
        long systemCurrentTimeInMillis = System.currentTimeMillis();
        aemTimer = metricsService.timer("agrewards-aem-" + systemCurrentTimeInMillis);
        wcsTimer = metricsService.timer("agrewards-wcs-" + systemCurrentTimeInMillis);
    }

    @Deactivate
    public void deactivate () {
        aemTimer = null;
        wcsTimer = null;
    }

    public void setAgRewardsEndPoint(String agRewardsEndPoint) {
        this.agRewardsEndPoint = agRewardsEndPoint;
    }

    public void setPropertyReaderService(PropertyReaderService propertyReaderService) {
        this.propertyReaderService = propertyReaderService;
    }

}
