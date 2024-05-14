package com.mattel.ecomm.registration.core.servicesimpl;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.Registration;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.RegistrationRequest;
import com.mattel.ecomm.coreservices.core.pojos.RegistrationResponse;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;
import org.apache.commons.lang3.StringUtils;
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

@Component(service = Registration.class)
@Designate(ocd = RegistrationImpl.Config.class)
public class RegistrationImpl implements Registration {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationImpl.class);
    private static final String REG_SERVICE = "userRegistrationService";
    private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(RegistrationResponse.class);
    private static final ObjectReader REQ_READER = ResourceMapper.getReaderInstance(RegistrationRequest.class);

    @Reference
    PropertyReaderService propertyReaderService;
    private String userRegistrationEndpoint;

    @Override
    public RegistrationResponse makeRegistration(final Map<String, Object> requestMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();

        try {
            final RegistrationRequest registrationRequest = REQ_READER.readValue(
                    requestMap.get(Constant.REQUEST_BODY).toString());
            final String storekey = (String) requestMap.get(Constant.STORE_KEY);
            final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
            final String storeId = propertyReaderService.getStoreId(storekey);
            final String domain = propertyReaderService.getCookieDomain(domainKey);
            final Map<String, Object> dataMap = new HashMap<>();
            final String endPointUrl = userRegistrationEndpoint.replaceAll(Constant.STORE_ID_PLACEHOLDER, storeId);
            final long endTime;
            String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
            dataMap.put("validCookieNames", cookieNames);
            dataMap.put("domain", domain);
            dataMap.put(Constant.END_POINT_URL_KEY, endPointUrl);
            registrationRequest.setStoreId(storeId);

            if (!StringUtils.isEmpty(registrationRequest.getLogonId())) {
                registrationRequest.setLogonId(
                        new StringBuilder(storeId).append("|").append(registrationRequest.getLogonId()).toString());
            }

            if (!StringUtils.isEmpty(registrationRequest.getLogonIdVerify())) {
                registrationRequest.setLogonIdVerify(new StringBuilder(storeId).append("|")
                        .append(registrationRequest.getLogonIdVerify()).toString());
            }

            endTime = System.currentTimeMillis();

            RegistrationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, RegistrationImpl.REG_SERVICE,
                    endTime - startTime);
            return execute(registrationRequest, dataMap);
        } catch (final IOException e) {
            final long endTime = System.currentTimeMillis();

            RegistrationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, RegistrationImpl.REG_SERVICE,
                    endTime - startTime);
            RegistrationImpl.LOGGER.error("Error encountered: ", e);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
        }
    }

    @Override
    public RegistrationResponse execute(final BaseRequest baseRequest, final Map<String, Object> dataMap)
            throws ServiceException {
        final long startTime = System.currentTimeMillis();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            final RegistrationRequest registrationRequest = (RegistrationRequest) baseRequest;
            final Map<String, String> requestHeaders = new HashMap<>();
            final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
            final HttpResponse httpResponse;
            final int status;

            requestHeaders.put("Accept", "application/json");
            requestHeaders.put("Content-type", "application/json");
            httpResponse = HttpRequestHandler.post(httpClient, dataMap.get(Constant.END_POINT_URL_KEY).toString(), null,
                    requestHeaders, registrationRequest, httpClientContext);
            status = httpResponse.getStatusLine().getStatusCode();

            RegistrationImpl.LOGGER.debug("Response status is {}", status);

            if (!isError(status)) {
                String[] cookieNames = (String[]) dataMap.get("validCookieNames");
                String domain = (String) dataMap.get("domain");
                List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain, httpClientContext.getCookieStore().getCookies());
                try (InputStream inputStream = httpResponse.getEntity().getContent()) {
                    final RegistrationResponse registrationResponse = RESP_READER.readValue(inputStream);
                    registrationResponse.setCookieList(cookieList);
                    RegistrationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
                            System.currentTimeMillis() - startTime);
                    return registrationResponse;
                }
            } else {
                final long endTime = System.currentTimeMillis();

                RegistrationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
                        endTime - startTime);
                generalExceptionHandling(status);
            }
        } catch (final IOException io) {
            final long endTime = System.currentTimeMillis();

            RegistrationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE, endTime - startTime);
            RegistrationImpl.LOGGER.error("Encountered error:", io);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
        }

        RegistrationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
                System.currentTimeMillis() - startTime);
        return null;
    }

    @ObjectClassDefinition(name = "WCS Registration Service Configurations", description = "Registration Service Configuration for WCS Vendor")
    public @interface Config {
        @AttributeDefinition(name = "Registration Service End Point", description = "Please Enter the Registration Service End point in the format"
                + "http://domain/restendpoint/${storeId}/loginservice")
        String endPoint() default "https://mdev.services.commerce.mattel.com/wcs/resources/store/STORE_ID/xuser/register?storeId=STORE_ID&responseFormat=json&updateCookies=true";
    }

    @Activate
    public void activate(final Config config) {
        userRegistrationEndpoint = config.endPoint();
    }
}
