package com.mattel.ecomm.personalinfo.core.servicesimpl;

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
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.UpdatePassword;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.UpdatePasswordRequest;
import com.mattel.ecomm.coreservices.core.pojos.UpdatePasswordResponse;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;


@Component(service = UpdatePassword.class)
@Designate(ocd = UpdatePasswordImpl.Config.class)
public class UpdatePasswordImpl implements UpdatePassword {
    private static final String END_POINT_URL_KEY = "endPointUrl";

    private static final String STORE_ID_PLACEHOLDER = "STORE_ID";

    private static final String UPDATE_CREDENTIAL_SERVICE = "updatepassword";

    private static final String EXECUTE_SERVICE = "execute";

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdatePasswordImpl.class);
    private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(UpdatePasswordResponse.class);
    private static final ObjectReader REQ_READER = ResourceMapper.getReaderInstance(UpdatePasswordRequest.class);

    @Reference
    PropertyReaderService propertyReaderService;

    private String updatePasswordEndpoint;

    @SuppressWarnings("unchecked")
    @Override
    public BaseResponse execute(final BaseRequest baseRequest,
                                final Map<String, Object> dataMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            final UpdatePasswordRequest updatePasswordRequest = (UpdatePasswordRequest) baseRequest;
            final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
            final Map<String, String> requestHeaders = new HashMap<>();
            final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
            final HttpResponse httpResponse;
            final int status;
            UpdatePasswordResponse updatePasswordResponse = null;
            
            requestHeaders.put("Accept", "application/json");
            requestHeaders.put("Content-type", "application/json");
            httpResponse = HttpRequestHandler.post(httpClient,
                    dataMap.get(UpdatePasswordImpl.END_POINT_URL_KEY).toString(), requestCookies,
                    requestHeaders, updatePasswordRequest, httpClientContext);
            status = httpResponse.getStatusLine().getStatusCode();

            UpdatePasswordImpl.LOGGER.debug("Response status is {}", status);
            InputStream inputStream = httpResponse.getEntity().getContent();
            if (!isError(status)) {
                final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");

                if (null != cookieResheader && cookieResheader.length > 0) {
                    final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
                    final String domain = (String) dataMap.get("domain");
                    final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain, httpClientContext.getCookieStore().getCookies());

                    updatePasswordResponse = RESP_READER.readValue(inputStream);

                    updatePasswordResponse.setCookieList(cookieList);
                }

                UpdatePasswordImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                        UpdatePasswordImpl.EXECUTE_SERVICE, (System.currentTimeMillis() - startTime));
                return updatePasswordResponse;
            } else {
                final long endTime = System.currentTimeMillis();

                UpdatePasswordImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                        UpdatePasswordImpl.EXECUTE_SERVICE, endTime - startTime);
                generalExceptionHandling(status);
            }
        } catch (final IOException io) {
            final long endTime = System.currentTimeMillis();

            UpdatePasswordImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                    UpdatePasswordImpl.EXECUTE_SERVICE, endTime - startTime);
            UpdatePasswordImpl.LOGGER.error("Encountered error:", io);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
        }

        UpdatePasswordImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, UpdatePasswordImpl.EXECUTE_SERVICE,
                (System.currentTimeMillis() - startTime));
        return null;
    }

    @Override
    public UpdatePasswordResponse updatePassword(final Map<String, Object> requestMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();

        try {
            final UpdatePasswordRequest updatePasswordRequest = REQ_READER.readValue(
                    requestMap.get(Constant.REQUEST_BODY).toString());
            final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
            final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
            final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects, cookieNames);
            final String storekey = (String) requestMap.get(Constant.STORE_KEY);
            final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
            final String storeId = propertyReaderService.getStoreId(storekey);
            final String domain = propertyReaderService.getCookieDomain(domainKey);
            final Map<String, Object> dataMap = new HashMap<>();
            final String endPointUrl = updatePasswordEndpoint.replaceAll(UpdatePasswordImpl.STORE_ID_PLACEHOLDER,
                    storeId);
            final long endTime;

            dataMap.put("validCookieNames", cookieNames);
            dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
            dataMap.put("domain", domain);
            dataMap.put(UpdatePasswordImpl.END_POINT_URL_KEY, endPointUrl);
            endTime = System.currentTimeMillis();

            UpdatePasswordImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                    UpdatePasswordImpl.UPDATE_CREDENTIAL_SERVICE, endTime - startTime);
            return (UpdatePasswordResponse) execute(updatePasswordRequest, dataMap);
        } catch (final IOException e) {
            final long endTime = System.currentTimeMillis();

            UpdatePasswordImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                    UpdatePasswordImpl.UPDATE_CREDENTIAL_SERVICE, endTime - startTime);
            UpdatePasswordImpl.LOGGER.error("Error encountered: {}", e);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
        }
    }

    @ObjectClassDefinition(name = "WCS UpdatePassword Configurations", description = "UpdatePassword Configuration for WCS Vendor")
    public @interface Config {
        @AttributeDefinition(name = "UpdatePassword End Point", description = "Please enter the UpdatePassword end point in the format"
                + "http://domain/restendpoint/${storeId}/loginservice")
        String endPoint() default "https://mdev.services.commerce.mattel.com/wcs/resources/store/STORE_ID/xpassword/update?responseFormat=json&updateCookies=true?storeId=STORE_ID";
    }

    @Activate
    public void activate(final Config config) {
        updatePasswordEndpoint = config.endPoint();
    }
}
