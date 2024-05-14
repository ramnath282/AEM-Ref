package com.mattel.ecomm.personalinfo.core.servicesimpl;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.UpdatePersonalInfo;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.PersonalInfoRequest;
import com.mattel.ecomm.coreservices.core.pojos.PersonalInfoResponse;
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

/**
 */
@Component(service = UpdatePersonalInfo.class)
@Designate(ocd = UpdatePersonalInfoImpl.Config.class)
public class UpdatePersonalInfoImpl implements UpdatePersonalInfo {
    private static final String END_POINT_URL_KEY = "endPointUrl";
    private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
    private static final String GET_PERSONAL_INFORMATION_SERVICE = "updatePersonalInfo";
    private static final String EXECUTE_SERVICE = "execute";
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdatePersonalInfoImpl.class);
    private static final ObjectReader REQ_READER = ResourceMapper.getReaderInstance(PersonalInfoRequest.class);
    private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(PersonalInfoResponse.class);
    @Reference
    PropertyReaderService propertyReaderService;
    private String updatePersonalInfoEndpoint;

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
    public BaseResponse execute(final BaseRequest baseRequest, final Map<String, Object> dataMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();

        try (final CloseableHttpClient httpClient = HttpClients.createDefault()) {
            final PersonalInfoRequest personalInfoRequest = (PersonalInfoRequest) baseRequest;
            final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
            final Map<String, String> requestHeaders = new HashMap<>();
            final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
            final HttpResponse httpResponse;
            final int status;

            requestHeaders.put("Accept", "application/json");
            requestHeaders.put("Content-type", "application/json");
            httpResponse = HttpRequestHandler.put(httpClient, dataMap.get(UpdatePersonalInfoImpl.END_POINT_URL_KEY).toString(),
                    requestCookies, requestHeaders, personalInfoRequest, httpClientContext);
            status = httpResponse.getStatusLine().getStatusCode();

            UpdatePersonalInfoImpl.LOGGER.debug("Response status is {}", status);

            if (null != httpResponse.getEntity() && !isError(status)) {
                try (final InputStream inputStream = httpResponse.getEntity().getContent()) {
                    final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");
                    PersonalInfoResponse paymentInfoResponse;
                    paymentInfoResponse = RESP_READER.readValue(inputStream);
                    if (null != cookieResheader && cookieResheader.length > 0) {
                        final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
                        final String domain = (String) dataMap.get("domain");
                        final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain, httpClientContext.getCookieStore().getCookies());

                        paymentInfoResponse.setCookieList(cookieList);
                    }

                    UpdatePersonalInfoImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                            UpdatePersonalInfoImpl.EXECUTE_SERVICE, (System.currentTimeMillis() - startTime));
                    return paymentInfoResponse;
                }
            } else {
                final long endTime = System.currentTimeMillis();

                UpdatePersonalInfoImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, UpdatePersonalInfoImpl.EXECUTE_SERVICE,
                        endTime - startTime);
                generalExceptionHandling(status);
            }
        } catch (final IOException io) {
            final long endTime = System.currentTimeMillis();

            UpdatePersonalInfoImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, UpdatePersonalInfoImpl.EXECUTE_SERVICE,
                    endTime - startTime);
            UpdatePersonalInfoImpl.LOGGER.error("Encountered error:", io);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
        }

        final long endTime = System.currentTimeMillis();
        UpdatePersonalInfoImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, UpdatePersonalInfoImpl.EXECUTE_SERVICE,
                endTime - startTime);
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.mattel.ecomm.coreservices.core.interfaces.UpdatePersonalInfo#
     * updatePersonalInfo(java.util.Map)
     */
    @Override
    public PersonalInfoResponse updatePersonalInfo(final Map<String, Object> requestMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();

        try {
            final PersonalInfoRequest personalInfoRequest = REQ_READER.readValue(
                    requestMap.get(Constant.REQUEST_BODY).toString());
            final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
            final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
            final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects, cookieNames);
            final String storekey = (String) requestMap.get(Constant.STORE_KEY);
            final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
            final String storeId = propertyReaderService.getStoreId(storekey);
            final String domain = propertyReaderService.getCookieDomain(domainKey);
            final Map<String, Object> dataMap = new HashMap<>();
            final String endPointUrl = updatePersonalInfoEndpoint
                    .replaceAll(UpdatePersonalInfoImpl.STORE_ID_PLACEHOLDER, storeId);
            final long endTime;

            dataMap.put("validCookieNames", cookieNames);
            dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
            dataMap.put("domain", domain);
            dataMap.put(UpdatePersonalInfoImpl.END_POINT_URL_KEY, endPointUrl);
            endTime = System.currentTimeMillis();

            UpdatePersonalInfoImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                    UpdatePersonalInfoImpl.GET_PERSONAL_INFORMATION_SERVICE, endTime - startTime);
            return (PersonalInfoResponse) execute(personalInfoRequest, dataMap);
        } catch (final IOException e) {
            final long endTime = System.currentTimeMillis();

            UpdatePersonalInfoImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                    UpdatePersonalInfoImpl.GET_PERSONAL_INFORMATION_SERVICE, endTime - startTime);
            UpdatePersonalInfoImpl.LOGGER.error("Error encountered: {}", e);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
        }
    }

    @ObjectClassDefinition(name = "WCS UpdatePersonalInfo Configurations", description = "UpdatePersonalInfo Configuration for WCS Vendor")
    public @interface Config {
        @AttributeDefinition(name = "UpdatePersonalInfo End Point", description = "Please Enter the UpdatePersonalInfo End point in the format"
                + "http://domain/restendpoint/${storeId}/loginservice")
        String endPoint() default "https://mdev3.americangirl.com/wcs/resources/store/STORE_ID/person/@self?storeId=STORE_ID&responseFormat=json&updateCookies=true";
    }

    @Activate
    public void activate(final Config config) {
        updatePersonalInfoEndpoint = config.endPoint();
    }

    public void setPropertyReaderService(final PropertyReaderService propertyReaderService) {
        this.propertyReaderService = propertyReaderService;
    }

    public void setUpdatePersonalInfoEndpoint(final String updatePersonalInfoEndpoint) {
        this.updatePersonalInfoEndpoint = updatePersonalInfoEndpoint;
    }
}
