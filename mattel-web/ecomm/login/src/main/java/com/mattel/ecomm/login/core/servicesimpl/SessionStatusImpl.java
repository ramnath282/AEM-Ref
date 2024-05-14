package com.mattel.ecomm.login.core.servicesimpl;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.SessionStatus;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.SessionStatusResponse;
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
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(service = SessionStatus.class)
@Designate(ocd = SessionStatusImpl.Config.class)
public class SessionStatusImpl implements SessionStatus {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionStatusImpl.class);
    private static final String SESSION_STATUS_SERVICE = "sessionStatusService";
    private static final String REQUEST_PARAM_GET_USER_TYPE = "getUserType";
    private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(SessionStatusResponse.class);

    @Reference
    PropertyReaderService propertyReaderService;
    private String sessionStatusEndpoint;

    @SuppressWarnings("unchecked")
    @Override
    public SessionStatusResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            final Map<String, String> requestParameters = new HashMap<>();
            final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
            final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
            final HttpResponse httpResponse;
            final int status;

            requestParameters.put(SessionStatusImpl.REQUEST_PARAM_GET_USER_TYPE,
                    dataMap.get(SessionStatusImpl.REQUEST_PARAM_GET_USER_TYPE).toString());
            httpResponse = HttpRequestHandler.get(httpClient, dataMap.get(Constant.END_POINT_URL_KEY).toString(),
                    requestCookies, null, requestParameters, httpClientContext);
            status = httpResponse.getStatusLine().getStatusCode();

            SessionStatusImpl.LOGGER.debug("Response status is {}", status);

            if (!isError(status)) {
                try (final InputStream inputStream = httpResponse.getEntity().getContent()) {
                    final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");
                    final SessionStatusResponse sessionStatusResponse = RESP_READER.readValue(inputStream);
                    if (null != cookieResheader && cookieResheader.length > 0) {
                        final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
                        final String domain = (String) dataMap.get(Constant.DOMAIN);
                        final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain, httpClientContext.getCookieStore().getCookies());
                        sessionStatusResponse.setCookieList(cookieList);
                    }

                    SessionStatusImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                            SessionStatusImpl.SESSION_STATUS_SERVICE, System.currentTimeMillis() - startTime);
                    return sessionStatusResponse;
                }
            } else {
                SessionStatusImpl.LOGGER.error("Recieved response with error status: {} in {}", status,
                        SessionStatusImpl.SESSION_STATUS_SERVICE);
                SessionStatusImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, SessionStatusImpl.SESSION_STATUS_SERVICE,
                        System.currentTimeMillis() - startTime);
                generalExceptionHandling(status);
            }
        } catch (IOException | URISyntaxException e) {
            SessionStatusImpl.LOGGER
                    .error(String.format("Encountered error in %s:", SessionStatusImpl.SESSION_STATUS_SERVICE), e);
            SessionStatusImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, SessionStatusImpl.SESSION_STATUS_SERVICE,
                    System.currentTimeMillis() - startTime);
            throw new ServiceException(Constant.IO_ERROR_KEY, "Exception Occured");
        }

        return null;
    }

    @Override
    public SessionStatusResponse get(Map<String, Object> requestMap) throws ServiceException {
        final String storeId = (String) requestMap.get(Constant.STORE_KEY);
        final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
        final String userType = (String) requestMap.get(SessionStatusImpl.REQUEST_PARAM_GET_USER_TYPE);
        final String domain = propertyReaderService.getCookieDomain(domainKey);
        final String endPointUrl = sessionStatusEndpoint.replaceAll(Constant.STORE_ID_PLACEHOLDER, storeId);
        final Map<String, Object> dataMap = new HashMap<>();
        final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
        final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
        final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects, cookieNames);

        dataMap.put("validCookieNames", cookieNames);
        dataMap.put(Constant.DOMAIN, domain);
        dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
        dataMap.put(Constant.END_POINT_URL_KEY, endPointUrl);
        dataMap.put(SessionStatusImpl.REQUEST_PARAM_GET_USER_TYPE, userType);
        return execute(null, dataMap);
    }

    @ObjectClassDefinition(name = "WCS Session Status Service Configurations", description = "Session Status Service Configuration for WCS Vendor")
    public @interface Config {
        @AttributeDefinition(name = "Session Status Service End Point", description = "Please Enter the Session Status Service End point in the format"
                + "http://domain/restendpoint/${storeId}/loginservice")
        String endPoint() default "https://mdev.services.commerce.mattel.com/wcs/resources/store/STORE_ID/session/status";
    }

    @Activate
    public void activate(final Config config) {
        sessionStatusEndpoint = config.endPoint();
    }
}
