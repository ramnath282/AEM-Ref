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
import org.apache.http.impl.client.HttpClients;
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
import com.mattel.ecomm.coreservices.core.interfaces.RemoveReward;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.RemoveRewardRequest;
import com.mattel.ecomm.coreservices.core.pojos.RemoveRewardResponse;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@Component(service = RemoveReward.class)
@Designate(ocd = RemoveRewardImpl.Config.class)
public class RemoveRewardImpl implements RemoveReward {
    private static final String END_POINT_URL_KEY = "endPointUrl";

    private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
    private static final String PROMO_ID = "PROMO_ID";
    private static final String REMOVE_PROMO_SERVICE = "removepromoservice";

    private static final String EXECUTE_SERVICE = "execute";

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoveRewardImpl.class);
    private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(RemoveRewardResponse.class);
    private static final ObjectReader REQ_READER = ResourceMapper
    	      .getReaderInstance(RemoveRewardRequest.class);

    @Reference
    PropertyReaderService propertyReaderService;

    private String removeRewardEndpoint;

    @SuppressWarnings("unchecked")
    @Override
    public RemoveRewardResponse execute(final BaseRequest baseRequest,
                                final Map<String, Object> dataMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
            final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
            final HttpResponse httpResponse = HttpRequestHandler.delete(httpClient,
                    dataMap.get(RemoveRewardImpl.END_POINT_URL_KEY).toString(), requestCookies, null, httpClientContext);
            final int status = httpResponse.getStatusLine().getStatusCode();

            RemoveRewardImpl.LOGGER.debug("Response status is {}", status);
            RemoveRewardResponse removeRewardResponse = null;
            if (null != httpResponse.getEntity() && !isError(status)) {
                try (final InputStream inputStream = httpResponse.getEntity().getContent()) {
                    final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");
                    removeRewardResponse = RESP_READER.readValue(inputStream);
                    RemoveRewardImpl.LOGGER.debug("Remove Rewards Response Object is {}", removeRewardResponse);
                    if (null != cookieResheader && cookieResheader.length > 0) {
                    	final List<Cookie> cookieList = fetchResponsCookies(dataMap,httpClientContext);
                    	removeRewardResponse.setCookieList(cookieList);
                    	logServiceErrors(removeRewardResponse);
                    }
                    RemoveRewardImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, RemoveRewardImpl.EXECUTE_SERVICE,
                            (System.currentTimeMillis() - startTime));
                    return removeRewardResponse;
                }
            } else {
                final long endTime = System.currentTimeMillis();

                RemoveRewardImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, RemoveRewardImpl.EXECUTE_SERVICE,
                        endTime - startTime);
                generalExceptionHandling(status, httpResponse.getEntity());
            }
        } catch (final IOException io) {
            final long endTime = System.currentTimeMillis();

            RemoveRewardImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, RemoveRewardImpl.EXECUTE_SERVICE,
                    endTime - startTime);
            RemoveRewardImpl.LOGGER.error("Encountered error:", io);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
        }

        final long endTime = System.currentTimeMillis();
        RemoveRewardImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, RemoveRewardImpl.EXECUTE_SERVICE,
                endTime - startTime);
        return null;
    }

    @Override
    public RemoveRewardResponse remove(final Map<String, Object> requestMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();

        try {
            final RemoveRewardRequest removeRewardRequest = RemoveRewardImpl.REQ_READER.readValue(
                    requestMap.get(Constant.REQUEST_BODY).toString());
            final String storekey = (String) requestMap.get(Constant.STORE_KEY);
            final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
            final String promoCode = removeRewardRequest.getPromoCode();
            final String storeId = propertyReaderService.getStoreId(storekey);
            final String domain = propertyReaderService.getCookieDomain(domainKey);
            final Map<String, Object> dataMap = new HashMap<>();
            String endPointUrl = removeRewardEndpoint.replaceAll(RemoveRewardImpl.STORE_ID_PLACEHOLDER,
                    storeId);
            endPointUrl = endPointUrl.replaceAll(RemoveRewardImpl.PROMO_ID, promoCode);
            RemoveRewardImpl.LOGGER.debug("Invoking RemoveRewards service with endpoint: {}", endPointUrl);
            final long endTime;
            mapCommonRequestVariables(requestMap,dataMap,domain);
            dataMap.put(RemoveRewardImpl.END_POINT_URL_KEY, endPointUrl);
            endTime = System.currentTimeMillis();
            RemoveRewardImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                    RemoveRewardImpl.REMOVE_PROMO_SERVICE, endTime - startTime);
            return execute(removeRewardRequest, dataMap);
        } catch (final IOException e) {
            final long endTime = System.currentTimeMillis();

            RemoveRewardImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                    RemoveRewardImpl.REMOVE_PROMO_SERVICE, endTime - startTime);
            RemoveRewardImpl.LOGGER.error("Error encountered: {}", e);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
        }
    }

    @Activate
    public void activate(final Config config) {
    	removeRewardEndpoint = config.endPoint();
    }


    @ObjectClassDefinition(name = "WCS Remove Reward Configurations", description = "Remove Reward Configuration for WCS Vendor")
    public @interface Config {
        @AttributeDefinition(name = "Remove Reward End Point", description = "Please enter the Remove Reward end point in the format"
                + "http://domain/restendpoint/${storeId}/removerewardservice")
        String endPoint() default "https://mqa.services.commerce.mattel.com/wcs/resources/store/STORE_ID/cart/@self/assigned_promotion_code/PROMO_ID?responseFormat=json&updateCookies=true";
    }
}