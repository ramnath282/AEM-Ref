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
import com.mattel.ecomm.coreservices.core.interfaces.PersonalInfo;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.PersonalInfoResponse;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@Component(service = PersonalInfo.class)
@Designate(ocd = PersonalInformationImpl.Config.class)
public class PersonalInformationImpl implements PersonalInfo {
    private static final String END_POINT_URL_KEY = "endPointUrl";
    private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonalInformationImpl.class);
    private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(PersonalInfoResponse.class);
    @Reference
    PropertyReaderService propertyReaderService;

    private String personalInfoEndPoint;

    @SuppressWarnings("unchecked")
    @Override
    public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();
        final String methodName = "execute";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            final List<String> reqCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
            final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
            final HttpResponse httpResponse = HttpRequestHandler.get(httpClient,
                    dataMap.get(PersonalInformationImpl.END_POINT_URL_KEY).toString(), reqCookies, null,
                    httpClientContext);
            final int status = httpResponse.getStatusLine().getStatusCode();
            PersonalInfoResponse personalInfoResponse = null;
            final boolean isError = isError(status);

            PersonalInformationImpl.LOGGER.debug("Status is {}", status);
            if (null != httpResponse.getEntity() && !isError) {
                try (final InputStream inputStream = httpResponse.getEntity().getContent()) {
                    final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");
                    final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
                    final String domain = (String) dataMap.get("domain");
                    PersonalInformationImpl.LOGGER.debug("Cookies are {}", cookieResheader);
                    final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain,
                            httpClientContext.getCookieStore().getCookies());
                    personalInfoResponse = PersonalInformationImpl.RESP_READER.readValue(inputStream);
                    personalInfoResponse.setCookieList(cookieList);

                    final long endTime = System.currentTimeMillis();
                    PersonalInformationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
                    return personalInfoResponse;
                }
            } else {
                final long endTime = System.currentTimeMillis();
                PersonalInformationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
                generalExceptionHandling(status);
            }

            final long endTime = System.currentTimeMillis();
            PersonalInformationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
            return personalInfoResponse;

        } catch (final IOException io) {
            final long endTime = System.currentTimeMillis();
            PersonalInformationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
            PersonalInformationImpl.LOGGER.error("IO Exception Occured:", io);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
        }
    }

    @Override
    public PersonalInfoResponse getPersonalInfo(Map<String, Object> requestMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();
        final String methodName = "getPersonalInfo";
        final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
        final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
        final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects, cookieNames);
        final String storekey = (String) requestMap.get(Constant.STORE_KEY);
        final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
        final String storeId = propertyReaderService.getStoreId(storekey);
        final String domain = propertyReaderService.getCookieDomain(domainKey);
        final String endPointUrl = personalInfoEndPoint.replaceAll(PersonalInformationImpl.STORE_ID_PLACEHOLDER,
                storeId);
        final Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("validCookieNames", cookieNames);
        dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
        dataMap.put("domain", domain);
        dataMap.put(PersonalInformationImpl.END_POINT_URL_KEY, endPointUrl);
        final long endTime = System.currentTimeMillis();
        PersonalInformationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
        return (PersonalInfoResponse) execute(null, dataMap);
    }

    @ObjectClassDefinition(name = "WCS PersonalInfo Configurations", description = "PersonalInfo Configuration for WCS Vendor")
    public @interface Config {
        @AttributeDefinition(name = "personalInfo End Point", description = "Please Enter the personalInfo End point in the format"
                + "http://domain/restendpoint/${storeId}/loginservice")
        String endPoint() default "http://mdev.americangirl.com/wcs/resources/store/STORE_ID/person/@self?storeId=STORE_ID&updateCookies=true&responseFormat=json";
    }

    @Activate
    public void activate(final Config config) {
        personalInfoEndPoint = config.endPoint();
    }

    public void setPropertyReaderService(PropertyReaderService propertyReaderService) {
        this.propertyReaderService = propertyReaderService;
    }

}
