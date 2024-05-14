package com.mattel.ecomm.personalinfo.core.servicesimpl;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.ChildInformation;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.ChildInformationResponse;
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

@Component(service = ChildInformation.class)
@Designate(ocd = ChildInformationImpl.Config.class)
public class ChildInformationImpl implements ChildInformation {

    private static final String END_POINT_URL_KEY = "endPointUrl";
    private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
    private static final String GET_CHILDINFO_SERVICE = "getChildInfo";
    private static final String EXECUTE_SERVICE = "execute";
    private static final Logger LOGGER = LoggerFactory.getLogger(ChildInformationImpl.class);
    private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(ChildInformationResponse.class);

    @Reference
    PropertyReaderService propertyReaderService;

    private String childInformationEndPoint;

    public void setChildInformationEndPoint(String childInformationEndPoint) {
        this.childInformationEndPoint = childInformationEndPoint;
    }

    @SuppressWarnings("unchecked")
    @Override
    public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap)
            throws ServiceException {
        final long startTime = System.currentTimeMillis();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
            final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
            final HttpResponse httpResponse = HttpRequestHandler.get(httpClient,
                    dataMap.get(ChildInformationImpl.END_POINT_URL_KEY).toString(), requestCookies, null, httpClientContext);
            final int status = httpResponse.getStatusLine().getStatusCode();

            ChildInformationImpl.LOGGER.debug("Response status is {}", status);

            if (null != httpResponse.getEntity() && !isError(status)) {
                try (final InputStream inputStream = httpResponse.getEntity().getContent()) {
                    final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");
                    ChildInformationResponse childInformationResponse = null;
                    childInformationResponse = RESP_READER.readValue(inputStream);
                    if (null != cookieResheader && cookieResheader.length > 0) {
                        final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
                        final String domain = (String) dataMap.get("domain");
                        final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain, httpClientContext.getCookieStore().getCookies());
                        
                        childInformationResponse.setCookieList(cookieList);
                    }

                    ChildInformationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, ChildInformationImpl.EXECUTE_SERVICE,
                            (System.currentTimeMillis() - startTime));
                    return childInformationResponse;
                }
            } else {
                final long endTime = System.currentTimeMillis();

                ChildInformationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, ChildInformationImpl.EXECUTE_SERVICE,
                        endTime - startTime);
                generalExceptionHandling(status);
            }
        } catch (final IOException io) {
            final long endTime = System.currentTimeMillis();

            ChildInformationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, ChildInformationImpl.EXECUTE_SERVICE,
                    endTime - startTime);
            ChildInformationImpl.LOGGER.error("Encountered error:", io);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
        }

        final long endTime = System.currentTimeMillis();
        ChildInformationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, ChildInformationImpl.EXECUTE_SERVICE,
                endTime - startTime);
        return null;
    }

    @Override
    public ChildInformationResponse getChildrenInfo(Map<String, Object> requestMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();
        final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
        final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
        final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects, cookieNames);
        final String storekey = (String) requestMap.get(Constant.STORE_KEY);
        final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
        final String storeId = propertyReaderService.getStoreId(storekey);
        final String domain = propertyReaderService.getCookieDomain(domainKey);
        final Map<String, Object> dataMap = new HashMap<>();
        final String endPointUrl = childInformationEndPoint.replaceAll(ChildInformationImpl.STORE_ID_PLACEHOLDER, storeId);
        final long endTime;

        dataMap.put("validCookieNames", cookieNames);
        dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
        dataMap.put("domain", domain);
        dataMap.put(ChildInformationImpl.END_POINT_URL_KEY, endPointUrl);
        endTime = System.currentTimeMillis();

        ChildInformationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, ChildInformationImpl.GET_CHILDINFO_SERVICE,
                endTime - startTime);
        return (ChildInformationResponse) execute(null, dataMap);
    }

    @ObjectClassDefinition(name = "WCS ChildInformation Configurations", description = "ChildInformation Configuration for WCS Vendor")
    public @interface Config {
        @AttributeDefinition(name = "ChildInformation End Point", description = "Please enter the children information end point in the format"
                + "http://domain/restendpoint/${storeId}/loginservice")
        String endPoint() default "http://mdev.americangirl.com/wcs/resources/store/STORE_ID/xchild/getchild?storeId=STORE_ID&updateCookies=true&responseFormat=json";
    }

    @Activate
    public void activate(final Config config) {
        childInformationEndPoint = config.endPoint();
    }

    public void setPropertyReaderService(PropertyReaderService propertyReaderService) {
        this.propertyReaderService = propertyReaderService;
    }
}