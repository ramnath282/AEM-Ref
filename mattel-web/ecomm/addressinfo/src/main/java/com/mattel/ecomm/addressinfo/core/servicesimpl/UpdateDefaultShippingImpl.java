package com.mattel.ecomm.addressinfo.core.servicesimpl;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.UpdateDefaultShippingMethod;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.UpdateDefaultShippingRequest;
import com.mattel.ecomm.coreservices.core.pojos.UpdateDefaultShippingResponse;
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

@Component(service = UpdateDefaultShippingMethod.class)
@Designate(ocd = UpdateDefaultShippingImpl.Config.class)
public class UpdateDefaultShippingImpl implements UpdateDefaultShippingMethod {

    private static final String END_POINT_URL_KEY = "endPointUrl";

    private static final String STORE_ID_PLACEHOLDER = "STORE_ID";

    private static final String UPDATE_CHILD_INFORMATION_SERVICE = "updateDefaultShipping";

    private static final String EXECUTE_SERVICE = "execute";

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateDefaultShippingImpl.class);
    private static final ObjectReader REQ_READER = ResourceMapper.getReaderInstance(UpdateDefaultShippingRequest.class);
    private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(UpdateDefaultShippingResponse.class);

    @Reference
    PropertyReaderService propertyReaderService;

    private String updateDefaultShipEndPoint;

    @SuppressWarnings("unchecked")
    @Override
    public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap) throws ServiceException {

        final long startTime = System.currentTimeMillis();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            final UpdateDefaultShippingRequest updateDefaultShippingRequest = (UpdateDefaultShippingRequest) baseRequest;
            final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
            final Map<String, String> requestHeaders = new HashMap<>();
            final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
            final HttpResponse httpResponse;
            final int status;

            requestHeaders.put("Accept", "application/json");
            requestHeaders.put("Content-type", "application/json");
            httpResponse = HttpRequestHandler.put(httpClient,
                    dataMap.get(UpdateDefaultShippingImpl.END_POINT_URL_KEY).toString(), requestCookies, requestHeaders,
                    updateDefaultShippingRequest, httpClientContext);
            status = httpResponse.getStatusLine().getStatusCode();

            UpdateDefaultShippingImpl.LOGGER.debug("Response status is {}", status);
            UpdateDefaultShippingResponse updateDefaultShippingResponse = null;
            if (null != httpResponse.getEntity() && !isError(status)) {
                try (final InputStream inputStream = httpResponse.getEntity().getContent()) {
                    final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");

                    if (null != cookieResheader && cookieResheader.length > 0) {
                        final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
                        final String domain = (String) dataMap.get("domain");
                        final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain, httpClientContext.getCookieStore().getCookies());

                        updateDefaultShippingResponse = RESP_READER.readValue(inputStream);
                        updateDefaultShippingResponse.setCookieList(cookieList);
                    }

                    UpdateDefaultShippingImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, UpdateDefaultShippingImpl.EXECUTE_SERVICE,
                            (System.currentTimeMillis() - startTime));
                    return updateDefaultShippingResponse;
                }
            } else {
                final long endTime = System.currentTimeMillis();

                UpdateDefaultShippingImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, UpdateDefaultShippingImpl.EXECUTE_SERVICE,
                        endTime - startTime);
                generalExceptionHandling(status);
            }
        } catch (final IOException io) {
            final long endTime = System.currentTimeMillis();

            UpdateDefaultShippingImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, UpdateDefaultShippingImpl.EXECUTE_SERVICE,
                    endTime - startTime);
            UpdateDefaultShippingImpl.LOGGER.error("Encountered error:", io);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
        }

        final long endTime = System.currentTimeMillis();
        UpdateDefaultShippingImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, UpdateDefaultShippingImpl.EXECUTE_SERVICE,
                endTime - startTime);

        return null;
    }

    @Override
    public BaseResponse updateDefaultShipping(Map<String, Object> requestMap) throws ServiceException {

        final long startTime = System.currentTimeMillis();

        try {

            final UpdateDefaultShippingRequest updateDefaultShippingRequest = REQ_READER.readValue(
                    requestMap.get(Constant.REQUEST_BODY).toString());

            final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
            final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
            final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects, cookieNames);
            final String storekey = (String) requestMap.get(Constant.STORE_KEY);
            final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
            final String storeId = propertyReaderService.getStoreId(storekey);
            final String domain = propertyReaderService.getCookieDomain(domainKey);

            final Map<String, Object> dataMap = new HashMap<>();
            final String endPointUrl = updateDefaultShipEndPoint.replaceAll(UpdateDefaultShippingImpl.STORE_ID_PLACEHOLDER,
                    storeId);
            final long endTime;

            dataMap.put("validCookieNames", cookieNames);
            dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
            dataMap.put("domain", domain);
            dataMap.put(UpdateDefaultShippingImpl.END_POINT_URL_KEY, endPointUrl);
            endTime = System.currentTimeMillis();

            UpdateDefaultShippingImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                    UpdateDefaultShippingImpl.UPDATE_CHILD_INFORMATION_SERVICE, endTime - startTime);
            return execute(updateDefaultShippingRequest, dataMap);
        } catch (final IOException e) {
            final long endTime = System.currentTimeMillis();

            UpdateDefaultShippingImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                    UpdateDefaultShippingImpl.UPDATE_CHILD_INFORMATION_SERVICE, endTime - startTime);
            UpdateDefaultShippingImpl.LOGGER.error("Error encountered: {}", e);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
        }

    }

    @Activate
    public void activate(final UpdateDefaultShippingImpl.Config config) {
        updateDefaultShipEndPoint = config.endPoint();
    }

    public PropertyReaderService getPropertyReaderService() {
        return propertyReaderService;
    }

    public void setPropertyReaderService(PropertyReaderService propertyReaderService) {
        this.propertyReaderService = propertyReaderService;
    }

    @ObjectClassDefinition(name = "WCS UpdateDefaultShippingAddress Configurations", description = "UpdateDefaultShippingAddress Configuration for WCS Vendor")
    public @interface Config {
        @AttributeDefinition(name = "UpdateDefaultShippingAddress End Point", description = "Please enter the UpdateDefaultShippingAddress end point in the format"
                + "http://domain/restendpoint/${storeId}/loginservice")
        String endPoint() default "https://mdev.services.commerce.mattel.com/wcs/resources/store/STORE_ID/person/@self/checkoutProfile?storeId=STORE_ID&responseFormat=json&updateCookies=true";
    }

}

