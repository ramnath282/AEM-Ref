package com.mattel.ecomm.orderhistory.core.servicesimpl;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringUtils;
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
import com.mattel.ecomm.coreservices.core.interfaces.RecentOrderHistory;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.RecentOrderHistoryResponse;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@Component(service = RecentOrderHistory.class)
@Designate(ocd = RecentOrderHistoryImpl.Config.class)
public class RecentOrderHistoryImpl implements RecentOrderHistory {
  private static final Logger LOGGER = LoggerFactory.getLogger(RecentOrderHistoryImpl.class);
    private static final String END_POINT_URL_KEY = "endPointUrl";
    private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
    private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(RecentOrderHistoryResponse.class);
    private String recentOrderHistoryEndPoint;
    @Reference
    PropertyReaderService propertyReaderService;
    @Reference
    private MetricsService metricsService;
    private Timer recentOrderHistoryWcsTimer;
    private Timer recentOrderHistoryAemTimer;

    @Override
    public RecentOrderHistoryResponse getRecentOrderHistory(Map<String, Object> requestMap) throws ServiceException {
        final Timer.Context aemContext = recentOrderHistoryAemTimer.time();

        try {
        final long startTime = System.currentTimeMillis();
        RecentOrderHistoryImpl.LOGGER.info("getOrderHistory Starts");
        String userId = null;
        final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
        for (final Cookie cookie : requestCookieObjects) {
            if (StringUtils.startsWith(cookie.getName(), "WC_USERACTIVITY_")) {
                final String[] cookiewithUserId = cookie.getName().split("_");
                userId = cookiewithUserId[2];
            }

        }
        final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
        final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects, cookieNames);
        final String storekey = (String) requestMap.get(Constant.STORE_KEY);
        final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
        final String storeId = propertyReaderService.getStoreId(storekey);
        final String domain = propertyReaderService.getCookieDomain(domainKey);
        String endPointUrl = recentOrderHistoryEndPoint.replaceAll(RecentOrderHistoryImpl.STORE_ID_PLACEHOLDER,
                storeId);
        endPointUrl = endPointUrl.replaceAll("USER_ID", userId);
        RecentOrderHistoryImpl.LOGGER.debug("End Point Url for recent order history is {}", endPointUrl);
        final Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("validCookieNames", cookieNames);
        dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
        dataMap.put("domain", domain);
        dataMap.put(RecentOrderHistoryImpl.END_POINT_URL_KEY, endPointUrl);
        final long endTime = System.currentTimeMillis();
        RecentOrderHistoryImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "getOrderHistory", endTime - startTime);
        return (RecentOrderHistoryResponse) execute(null, dataMap);
        } finally {
            aemContext.stop();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();
        try (CloseableHttpClient httpClient = HttpClients.createDefault();) {
            final List<String> reqCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
            final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
            final Timer.Context wcsContext = recentOrderHistoryWcsTimer.time();
            final HttpResponse httpResponse;

            try {
            final long wcsExecutionStartTime = System.currentTimeMillis();
            httpResponse = HttpRequestHandler.get(httpClient,
                    dataMap.get(RecentOrderHistoryImpl.END_POINT_URL_KEY).toString(), reqCookies, null,
                    httpClientContext);
            final long wcsExecutionEndTime = System.currentTimeMillis();
            RecentOrderHistoryImpl.LOGGER.debug("WCS Execution Time for Recent Order history is {}",
                    wcsExecutionEndTime - wcsExecutionStartTime);
            } finally {
                wcsContext.stop();
            }

            final int status = httpResponse.getStatusLine().getStatusCode();
            RecentOrderHistoryImpl.LOGGER.debug("Status is {}", status);
            final InputStream inputStream = httpResponse.getEntity().getContent();
            RecentOrderHistoryResponse recentOrderHistoryResponse = null;
            final boolean isError = isError(status);
            if (!isError) {
                final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");
                final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
                final String domain = (String) dataMap.get("domain");
                RecentOrderHistoryImpl.LOGGER.debug("Cookies are {}", cookieResheader);
                final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain,
                        httpClientContext.getCookieStore().getCookies());
                recentOrderHistoryResponse = RecentOrderHistoryImpl.RESP_READER.readValue(inputStream);
                recentOrderHistoryResponse.setCookieList(cookieList);
                RecentOrderHistoryImpl.LOGGER.debug("Recent Order History Response is {}", recentOrderHistoryResponse);
            } else {
                final long endTime = System.currentTimeMillis();
                RecentOrderHistoryImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "Product Interest execute", endTime - startTime);
                generalExceptionHandling(status);
            }
            final long endTime = System.currentTimeMillis();
            RecentOrderHistoryImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "Execute of Order History", endTime - startTime);
            return recentOrderHistoryResponse;
        } catch (final IOException io) {
            final long endTime = System.currentTimeMillis();
            RecentOrderHistoryImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "Execute of Order History", endTime - startTime);
            RecentOrderHistoryImpl.LOGGER.error("IO Exception {}", io);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
        }
    }

    @Override
    public boolean isError(int statusCode) {
        return HttpURLConnection.HTTP_OK != statusCode;
    }

    @Activate
    public void activate(final Config config) {
        recentOrderHistoryEndPoint = config.endPoint();
        long systemCurrentTimeInMillis = System.currentTimeMillis();
        recentOrderHistoryAemTimer = metricsService.timer("recentorderhistory-aem-" + systemCurrentTimeInMillis);
        recentOrderHistoryWcsTimer = metricsService.timer("recentorderhistory-wcs-" + systemCurrentTimeInMillis);
    }

    @Deactivate
    public void deactivate () {
        recentOrderHistoryAemTimer = null;
        recentOrderHistoryWcsTimer = null;
    }

    @ObjectClassDefinition(name = "WCS Recent Order History Configurations", description = "Recent Order History Configurations for WCS Vendor")
    public @interface Config {

        @AttributeDefinition(name = "Recent Order History Endpoint", description = "Please Enter the Product Interest End point in the format"
                + "http://domain/restendpoint/${storeId}/loginservice")
        String endPoint() default "https://mdev.services.commerce.mattel.com/wcs/resources/store/STORE_ID/mattelOrderHistoryDataBean/getOrderHistory?storeId=STORE_ID&responseFormat=json&updateCookies=true&userId=USER_ID";

    }
}
