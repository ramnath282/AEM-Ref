package com.mattel.ecomm.paymentinfo.core.servicesimpl;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.UpdatePaymentInfo;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.PaymentInfoRequest;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;
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

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(service = UpdatePaymentInfo.class)
@Designate(ocd = UpdatePaymentInfoImpl.Config.class)
public class UpdatePaymentInfoImpl implements UpdatePaymentInfo {
    private static final String END_POINT_URL_KEY = "endPointUrl";
    private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
    private static final String GET_PAYMENT_INFORMATION_SERVICE = "updatePaymentInfo";
    private static final String EXECUTE_SERVICE = "execute";
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdatePaymentInfoImpl.class);
    private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(BaseResponse.class);
    private static final ObjectReader REQ_READER = ResourceMapper.getReaderInstance(PaymentInfoRequest.class);
    @Reference
    PropertyReaderService propertyReaderService;
    private String updatePaymentInfoEndpoint;

    @SuppressWarnings("unchecked")
    @Override
    public BaseResponse execute(final BaseRequest baseRequest, final Map<String, Object> dataMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            final PaymentInfoRequest paymentInfoRequest = (PaymentInfoRequest) baseRequest;
            BaseResponse baseResponse = new BaseResponse();
            final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
            final Map<String, String> requestHeaders = new HashMap<>();
            final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
            final HttpResponse httpResponse;
            final int status;

            requestHeaders.put("Accept", "application/json");
            requestHeaders.put("Content-type", "application/json");
            httpResponse = HttpRequestHandler.post(httpClient,
                    dataMap.get(UpdatePaymentInfoImpl.END_POINT_URL_KEY).toString(), requestCookies, requestHeaders,
                    paymentInfoRequest, httpClientContext);
            status = httpResponse.getStatusLine().getStatusCode();

            UpdatePaymentInfoImpl.LOGGER.debug("Response status is {}", status);
            InputStream inputStream = httpResponse.getEntity().getContent();

            if (!isError(status)) {
                final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");

                if (null != cookieResheader && cookieResheader.length > 0) {
                    final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
                    final String domain = (String) dataMap.get("domain");
                    final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain, httpClientContext.getCookieStore().getCookies());
                    baseResponse = RESP_READER.readValue(inputStream);
                    baseResponse.setCookieList(cookieList);
                }

                UpdatePaymentInfoImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, UpdatePaymentInfoImpl.EXECUTE_SERVICE,
                        (System.currentTimeMillis() - startTime));
                return baseResponse;
            } else {
                final long endTime = System.currentTimeMillis();

                UpdatePaymentInfoImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, UpdatePaymentInfoImpl.EXECUTE_SERVICE,
                        endTime - startTime);
                generalExceptionHandling(status);
            }
        } catch (final IOException io) {
            final long endTime = System.currentTimeMillis();

            UpdatePaymentInfoImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, UpdatePaymentInfoImpl.EXECUTE_SERVICE,
                    endTime - startTime);
            UpdatePaymentInfoImpl.LOGGER.error("Encountered error:", io);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
        }

        final long endTime = System.currentTimeMillis();
        UpdatePaymentInfoImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, UpdatePaymentInfoImpl.EXECUTE_SERVICE,
                endTime - startTime);
        return null;
    }

    @Override
    public BaseResponse updateCardDetails(final Map<String, Object> requestMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();

        try {

            final PaymentInfoRequest paymentInfoRequest = REQ_READER.readValue(
                    requestMap.get(Constant.REQUEST_BODY).toString());
            final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
            final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
            final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects, cookieNames);
            final String storekey = (String) requestMap.get(Constant.STORE_KEY);
            final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
            final String storeId = propertyReaderService.getStoreId(storekey);
            final String domain = propertyReaderService.getCookieDomain(domainKey);
            final Map<String, Object> dataMap = new HashMap<>();
            final String endPointUrl = updatePaymentInfoEndpoint.replaceAll(UpdatePaymentInfoImpl.STORE_ID_PLACEHOLDER,
                    storeId);
            final long endTime;

            dataMap.put("validCookieNames", cookieNames);
            dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
            dataMap.put("domain", domain);
            dataMap.put(UpdatePaymentInfoImpl.END_POINT_URL_KEY, endPointUrl);
            endTime = System.currentTimeMillis();

            UpdatePaymentInfoImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                    UpdatePaymentInfoImpl.GET_PAYMENT_INFORMATION_SERVICE, endTime - startTime);
            return execute(paymentInfoRequest, dataMap);
        } catch (final IOException e) {
            final long endTime = System.currentTimeMillis();

            UpdatePaymentInfoImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                    UpdatePaymentInfoImpl.GET_PAYMENT_INFORMATION_SERVICE, endTime - startTime);
            UpdatePaymentInfoImpl.LOGGER.error("Error encountered: {}", e);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
        }
    }

    @ObjectClassDefinition(name = "WCS UpdatePaymentInfo Configurations", description = "UpdatePaymentInfo Configuration for WCS Vendor")
    public @interface Config {
        @AttributeDefinition(name = "UpdatePaymentInfo End Point", description = "Please Enter the UpdatePaymentInfo End point in the format"
                + "http://domain/restendpoint/${storeId}/loginservice")
        String endPoint() default "https://mdev.americangirl.com/wcs/resources/store/STORE_ID/xcreditcard/updateCardDetails?storeId=STORE_ID&responseFormat=json&updateCookies=true";
    }

    @Activate
    public void activate(final Config config) {
        updatePaymentInfoEndpoint = config.endPoint();
    }

}
