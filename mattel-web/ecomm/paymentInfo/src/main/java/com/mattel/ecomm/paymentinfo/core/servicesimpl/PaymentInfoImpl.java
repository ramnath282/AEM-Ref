package com.mattel.ecomm.paymentinfo.core.servicesimpl;

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
import com.mattel.ecomm.coreservices.core.interfaces.PaymentInfo;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.PaymentInfoResponse;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

/**
 * Service implementation to fetch card details.
 */
@Component(service = PaymentInfo.class)
@Designate(ocd = PaymentInfoImpl.Config.class)
public class PaymentInfoImpl implements PaymentInfo {
    private static final String END_POINT_URL_KEY = "endPointUrl";
    private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
    private static final String GET_PAYMENT_INFORMATION_SERVICE = "getPaymentInformation";
    private static final String EXECUTE_SERVICE = "execute";
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentInfoImpl.class);
    private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(PaymentInfoResponse.class);
    private String paymentInfoEndPoint;
    @Reference
    PropertyReaderService propertyReaderService;
    @Reference
    private MetricsService metricsService;
    private Timer wcsTimer;
    private Timer aemTimer;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.mattel.ecomm.coreservices.core.interfaces.BaseService#execute(com.
     * mattel.ecomm.coreservices.core.pojos.BaseRequest,
     * com.mattel.ecomm.coreservices.core.pojos.BaseResponse, java.util.Map)
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseResponse execute(final BaseRequest baseRequest, final Map<String, Object> dataMap)
            throws ServiceException {
        final long startTime = System.currentTimeMillis();

        try (final CloseableHttpClient httpClient = HttpClients.createDefault()) {
            final List<String> reqCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
            final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
            final HttpResponse httpResponse;
            final int status;
            final Timer.Context wcsContext = wcsTimer.time();

            try {
            final long wcsExecutionStartTime = System.currentTimeMillis();
            httpResponse = HttpRequestHandler.get(httpClient, dataMap.get(PaymentInfoImpl.END_POINT_URL_KEY).toString(),
                    reqCookies, null, httpClientContext);
            final long wcsExecutionEndTime = System.currentTimeMillis();
            PaymentInfoImpl.LOGGER.debug("WCS Execution Time for PaymentInfo is {}",
                    wcsExecutionEndTime - wcsExecutionStartTime);
            } finally {
            wcsContext.stop();
            }

            status = httpResponse.getStatusLine().getStatusCode();
            PaymentInfoImpl.LOGGER.debug("Response status is {}", status);
            final boolean isError = isError(status);
            PaymentInfoResponse paymentInfoResponse = null;

            if (null != httpResponse.getEntity() && !isError) {
                try (final InputStream inputStream = httpResponse.getEntity().getContent()) {
                    final long endTime;

                    final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");
                    final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
                    final String domain = (String) dataMap.get("domain");
                    PaymentInfoImpl.LOGGER.debug("Cookies are {}", cookieResheader);
                    final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain,
                            httpClientContext.getCookieStore().getCookies());
                    paymentInfoResponse = PaymentInfoImpl.RESP_READER.readValue(inputStream);
                    paymentInfoResponse.setCookieList(cookieList);

                    endTime = System.currentTimeMillis();
                    PaymentInfoImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, PaymentInfoImpl.EXECUTE_SERVICE,
                            endTime - startTime);
                    return paymentInfoResponse;
                }
            } else {
                final long endTime = System.currentTimeMillis();
                PaymentInfoImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, PaymentInfoImpl.EXECUTE_SERVICE,
                        endTime - startTime);
                generalExceptionHandling(status);
            }

            final long endTime = System.currentTimeMillis();
            PaymentInfoImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, PaymentInfoImpl.EXECUTE_SERVICE,
                    endTime - startTime);
            return paymentInfoResponse;
        } catch (final IOException io) {
            final long endTime = System.currentTimeMillis();
            PaymentInfoImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, PaymentInfoImpl.EXECUTE_SERVICE,
                    endTime - startTime);
            PaymentInfoImpl.LOGGER.error("Encountered error:", io);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.mattel.ecomm.coreservices.core.interfaces.PaymentInfo#
     * getPaymentInformation(java.util.Map)
     */
    @Override
    public PaymentInfoResponse getPaymentInformation(final Map<String, Object> requestMap) throws ServiceException {
        final Timer.Context aemContext = aemTimer.time();
        
        try {
        final long startTime = System.currentTimeMillis();
        final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
        final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
        final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects, cookieNames);
        final String storekey = (String) requestMap.get(Constant.STORE_KEY);
        final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
        final String storeId = propertyReaderService.getStoreId(storekey);
        final String domain = propertyReaderService.getCookieDomain(domainKey);
        final Map<String, Object> dataMap = new HashMap<>();
        final String endPointUrl = paymentInfoEndPoint.replaceAll(PaymentInfoImpl.STORE_ID_PLACEHOLDER, storeId);
        final long endTime;

        dataMap.put("validCookieNames", cookieNames);
        dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
        dataMap.put("domain", domain);
        dataMap.put(PaymentInfoImpl.END_POINT_URL_KEY, endPointUrl);
        endTime = System.currentTimeMillis();

        PaymentInfoImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, PaymentInfoImpl.GET_PAYMENT_INFORMATION_SERVICE,
                endTime - startTime);
        return (PaymentInfoResponse) execute(null, dataMap);
        } finally {
            aemContext.stop();
        }
    }

    @ObjectClassDefinition(name = "WCS PaymentInfo Configurations", description = "PaymentInfo Configuration for WCS Vendor")
    public @interface Config {
        @AttributeDefinition(name = "PaymentInfo End Point", description = "Please Enter the PaymentInfo End point in the format"
                + "http://domain/restendpoint/${storeId}/loginservice")
        String endPoint() default "https://mdev.americangirl.com/wcs/resources/store/STORE_ID/xcreditcard/getPaymentInfo?updateCookies=true&storeId=STORE_ID&responseFormat=json";
    }

    @Activate
    public void activate(final Config config) {
        paymentInfoEndPoint = config.endPoint();
        long systemCurrentTimeInMillis = System.currentTimeMillis();
        aemTimer = metricsService.timer("paymentinfo-aem-" + systemCurrentTimeInMillis);
        wcsTimer = metricsService.timer("paymentinfo-wcs-" + systemCurrentTimeInMillis);
    }

    @Deactivate
    public void deactivate () {
        aemTimer = null;
        wcsTimer = null;
    }
}
