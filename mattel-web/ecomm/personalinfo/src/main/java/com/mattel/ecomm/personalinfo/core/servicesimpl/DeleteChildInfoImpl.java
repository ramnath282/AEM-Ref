package com.mattel.ecomm.personalinfo.core.servicesimpl;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.DeleteChildInformation;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.DeleteChildRequest;
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

@Component(service = DeleteChildInformation.class)
@Designate(ocd = DeleteChildInfoImpl.Config.class)
public class DeleteChildInfoImpl implements DeleteChildInformation {
    private static final String END_POINT_URL_KEY = "endPointUrl";

    private static final String STORE_ID_PLACEHOLDER = "STORE_ID";

    private static final String ADD_ID = "ADD_ID";

    private static final String DELETE_CHILD_INFORMATION_SERVICE = "deleteChildInformation";

    private static final String EXECUTE_SERVICE = "execute";

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteChildInfoImpl.class);
    private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(BaseResponse.class);
    private static final ObjectReader REQ_READER = ResourceMapper.getReaderInstance(DeleteChildRequest.class);

    @Reference
    PropertyReaderService propertyReaderService;

    private String deleteChildInfoEndpoint;

    @SuppressWarnings("unchecked")
    @Override
    public BaseResponse execute(final BaseRequest baseRequest,
                                final Map<String, Object> dataMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
            final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
            final HttpResponse httpResponse = HttpRequestHandler.delete(httpClient,
                    dataMap.get(DeleteChildInfoImpl.END_POINT_URL_KEY).toString(), requestCookies, null, httpClientContext);
            final int status = httpResponse.getStatusLine().getStatusCode();

            DeleteChildInfoImpl.LOGGER.debug("Response status is {}", status);
            BaseResponse baseResponse = null;
            if (null != httpResponse.getEntity() && !isError(status)) {
                try (final InputStream inputStream = httpResponse.getEntity().getContent()) {
                    final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");
                    baseResponse = RESP_READER.readValue(inputStream);
                    if (null != cookieResheader && cookieResheader.length > 0) {
                        final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
                        final String domain = (String) dataMap.get("domain");
                        final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain, httpClientContext.getCookieStore().getCookies());

                        baseResponse.setCookieList(cookieList);
                    }

                    DeleteChildInfoImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, DeleteChildInfoImpl.EXECUTE_SERVICE,
                            (System.currentTimeMillis() - startTime));
                    return baseResponse;
                }
            } else {
                final long endTime = System.currentTimeMillis();

                DeleteChildInfoImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, DeleteChildInfoImpl.EXECUTE_SERVICE,
                        endTime - startTime);
                generalExceptionHandling(status);
            }
        } catch (final IOException io) {
            final long endTime = System.currentTimeMillis();

            DeleteChildInfoImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, DeleteChildInfoImpl.EXECUTE_SERVICE,
                    endTime - startTime);
            DeleteChildInfoImpl.LOGGER.error("Encountered error:", io);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
        }

        final long endTime = System.currentTimeMillis();
        DeleteChildInfoImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, DeleteChildInfoImpl.EXECUTE_SERVICE,
                endTime - startTime);
        return null;
    }

    @Override
    public BaseResponse deleteChildInfo(final Map<String, Object> requestMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();

        try {
            final DeleteChildRequest deleteChildRequest = REQ_READER.readValue(
                    requestMap.get(Constant.REQUEST_BODY).toString());
            final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
            final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
            final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects, cookieNames);
            final String storekey = (String) requestMap.get(Constant.STORE_KEY);
            final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
            final String nickname = deleteChildRequest.getNickName();

            final String storeId = propertyReaderService.getStoreId(storekey);
            final String domain = propertyReaderService.getCookieDomain(domainKey);
            final Map<String, Object> dataMap = new HashMap<>();
            String endPointUrl = deleteChildInfoEndpoint.replaceAll(DeleteChildInfoImpl.STORE_ID_PLACEHOLDER,
                    storeId);
            endPointUrl = endPointUrl.replaceAll(DeleteChildInfoImpl.ADD_ID, nickname);
            final long endTime;

            dataMap.put("validCookieNames", cookieNames);
            dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
            dataMap.put("domain", domain);
            dataMap.put(DeleteChildInfoImpl.END_POINT_URL_KEY, endPointUrl);
            endTime = System.currentTimeMillis();

            DeleteChildInfoImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                    DeleteChildInfoImpl.DELETE_CHILD_INFORMATION_SERVICE, endTime - startTime);
            return execute(deleteChildRequest, dataMap);
        } catch (final IOException e) {
            final long endTime = System.currentTimeMillis();

            DeleteChildInfoImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                    DeleteChildInfoImpl.DELETE_CHILD_INFORMATION_SERVICE, endTime - startTime);
            DeleteChildInfoImpl.LOGGER.error("Error encountered: {}", e);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
        }
    }

    @Activate
    public void activate(final Config config) {
        deleteChildInfoEndpoint = config.endPoint();
    }


    @ObjectClassDefinition(name = "WCS DeleteChildInformation Configurations", description = "DeleteChildInformation Configuration for WCS Vendor")
    public @interface Config {
        @AttributeDefinition(name = "DeleteChildInformation End Point", description = "Please enter the DeleteChildInformation end point in the format"
                + "http://domain/restendpoint/${storeId}/loginservice")
        String endPoint() default "https://mdev.services.commerce.mattel.com/wcs/resources/store/STORE_ID/person/@self/contact/ADD_ID?storeId=STORE_ID&responseFormat=json&updateCookies=true";
    }
}
