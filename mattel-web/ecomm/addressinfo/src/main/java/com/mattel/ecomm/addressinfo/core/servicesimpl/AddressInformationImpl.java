package com.mattel.ecomm.addressinfo.core.servicesimpl;

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
import com.mattel.ecomm.coreservices.core.interfaces.AddressInfo;
import com.mattel.ecomm.coreservices.core.pojos.AddressInfoRequest;
import com.mattel.ecomm.coreservices.core.pojos.AddressInfoResponse;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@Component(service = AddressInfo.class)
@Designate(ocd = AddressInformationImpl.Config.class)
public class AddressInformationImpl implements AddressInfo {
    private static final String END_POINT_URL_KEY = "endPointUrl";
    private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressInformationImpl.class);
    private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(AddressInfoResponse.class);

    @Reference
    PropertyReaderService propertyReaderService;
    @Reference
    private MetricsService metricsService;
    private Timer wcsTimer;
    private Timer aemTimer;

    public void setAddressInfoEndPoint(String addressInfoEndPoint) {
        this.addressInfoEndPoint = addressInfoEndPoint;
    }

    private String addressInfoEndPoint;

    @SuppressWarnings("unchecked")
    @Override
    public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();
        final String methodName = "execute";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            final List<String> reqCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
            final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
            final Timer.Context wcsContext = wcsTimer.time();
            final HttpResponse httpResponse;

            try {
            final long wcsExecutionStartTime = System.currentTimeMillis();
            httpResponse = HttpRequestHandler.get(httpClient,
                    dataMap.get(AddressInformationImpl.END_POINT_URL_KEY).toString(), reqCookies, null,
                    httpClientContext);
            final long wcsExecutionEndTime = System.currentTimeMillis();
            AddressInformationImpl.LOGGER.debug("WCS Execution Time for AddressInfo is {}",
                    wcsExecutionEndTime - wcsExecutionStartTime);
            } finally {
            wcsContext.stop();
            }

            final int status = httpResponse.getStatusLine().getStatusCode();
            AddressInfoResponse addressInfoResponse = null;
            final boolean isError = isError(status);
            AddressInformationImpl.LOGGER.debug("Status is {}", status);

            if (null != httpResponse.getEntity() && !isError) {
                try (final InputStream inputStream = httpResponse.getEntity().getContent()) {
                    final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");
                    final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
                    final String domain = (String) dataMap.get("domain");
                    AddressInformationImpl.LOGGER.debug("Cookies are {}", cookieResheader);
                    final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain,
                            httpClientContext.getCookieStore().getCookies());

                    addressInfoResponse = AddressInformationImpl.RESP_READER.readValue(inputStream);
                    addressInfoResponse.setCookieList(cookieList);

                    final long endTime = System.currentTimeMillis();
                    AddressInformationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
                    return addressInfoResponse;
                }
            } else {
                final long endTime = System.currentTimeMillis();
                AddressInformationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
                generalExceptionHandling(status);
            }
            final long endTime = System.currentTimeMillis();
            AddressInformationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
            return addressInfoResponse;
        } catch (final IOException io) {
            final long endTime = System.currentTimeMillis();
            AddressInformationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
            AddressInformationImpl.LOGGER.error("IO Exception occured", io);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
        }
    }

    @Override
    public AddressInfoResponse getAddressInfo(Map<String, Object> requestMap) throws ServiceException {
        final Timer.Context aemContext = aemTimer.time();
        
        try {
        final long startTime = System.currentTimeMillis();
        final String methodName = "getAddressInfo";
        final AddressInfoRequest addressInfoRequest = new AddressInfoRequest();
        final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
        final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
        final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects, cookieNames);
        final String storekey = (String) requestMap.get(Constant.STORE_KEY);
        final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
        final String storeId = propertyReaderService.getStoreId(storekey);
        final String domain = propertyReaderService.getCookieDomain(domainKey);
        final String endPointUrl = addressInfoEndPoint.replaceAll(AddressInformationImpl.STORE_ID_PLACEHOLDER, storeId);
        final Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("validCookieNames", cookieNames);
        dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
        dataMap.put("domain", domain);
        dataMap.put(AddressInformationImpl.END_POINT_URL_KEY, endPointUrl);
        final long endTime = System.currentTimeMillis();
        AddressInformationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
        return (AddressInfoResponse) execute(addressInfoRequest, dataMap);
        } finally {
            aemContext.stop();
        }
    }

    @ObjectClassDefinition(name = "WCS AddressInfo Configurations", description = "AddressInfo Configuration for WCS Vendor")
    public @interface Config {
        @AttributeDefinition(name = "addressInfo End Point", description = "Please Enter the addressInfo End point in the format"
                + "http://domain/restendpoint/${storeId}/loginservice")
        String endPoint() default "http://mdev.americangirl.com/wcs/resources/store/STORE_ID/defaultuserprofile/getUserInfo?updateCookies=true&responseFormat=json";
    }

    @Activate
    public void activate(final Config config) {
        addressInfoEndPoint = config.endPoint();
        long systemCurrentTimeInMillis = System.currentTimeMillis();
        aemTimer = metricsService.timer("addressinfo-aem-" + systemCurrentTimeInMillis);
        wcsTimer = metricsService.timer("addressinfo-wcs-" + systemCurrentTimeInMillis);
    }

    @Deactivate
    public void deactivate () {
        aemTimer = null;
        wcsTimer = null;
    }

    public void setPropertyReaderService(PropertyReaderService propertyReaderService) {
        this.propertyReaderService = propertyReaderService;
    }

}
