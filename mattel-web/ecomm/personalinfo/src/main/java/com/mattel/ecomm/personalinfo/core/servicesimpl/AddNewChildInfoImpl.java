package com.mattel.ecomm.personalinfo.core.servicesimpl;


import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.AddChildInformation;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.ChildInformationRequest;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component(service = AddChildInformation.class)
@Designate(ocd = AddNewChildInfoImpl.Config.class)
public class AddNewChildInfoImpl implements AddChildInformation {

    private static final String END_POINT_URL_KEY = "endPointUrl";

    private static final String STORE_ID_PLACEHOLDER = "STORE_ID";

    private static final String ADD_CHILD_SERVICE = "addChildInformation";

    private static final String EXECUTE_SERVICE = "execute";
    private static final ObjectReader REQ_READER = ResourceMapper.getReaderInstance(ChildInformationRequest.class);

    private static final Logger LOGGER = LoggerFactory.getLogger(AddNewChildInfoImpl.class);

    @Reference
    PropertyReaderService propertyReaderService;

    private String addChildInfoEndpoint;

    @SuppressWarnings("unchecked")
    @Override
    public BaseResponse execute(final BaseRequest baseRequest, final Map<String, Object> dataMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            final ChildInformationRequest addChildInformation = (ChildInformationRequest) baseRequest;
            final BaseResponse baseResponse = new BaseResponse();
            final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
            final Map<String, String> requestHeaders = new HashMap<>();
            final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
            final HttpResponse httpResponse;
            final int status;

            requestHeaders.put("Accept", "application/json");
            requestHeaders.put("Content-type", "application/json");
            httpResponse = HttpRequestHandler.post(httpClient,
                    dataMap.get(AddNewChildInfoImpl.END_POINT_URL_KEY).toString(), requestCookies,
                    requestHeaders, addChildInformation, httpClientContext);
            status = httpResponse.getStatusLine().getStatusCode();

            AddNewChildInfoImpl.LOGGER.debug("Response status is {}", status);

            if (!isError(status)) {
                final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");

                if (null != cookieResheader && cookieResheader.length > 0) {
                    final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
                    final String domain = (String) dataMap.get("domain");
                    final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain, httpClientContext.getCookieStore().getCookies());

                    baseResponse.setCookieList(cookieList);
                }

                AddNewChildInfoImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                        AddNewChildInfoImpl.EXECUTE_SERVICE, (System.currentTimeMillis() - startTime));
                return baseResponse;
            } else {
                final long endTime = System.currentTimeMillis();

                AddNewChildInfoImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                        AddNewChildInfoImpl.EXECUTE_SERVICE, endTime - startTime);
                generalExceptionHandling(status);
            }
        } catch (final IOException io) {
            final long endTime = System.currentTimeMillis();

            AddNewChildInfoImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                    AddNewChildInfoImpl.EXECUTE_SERVICE, endTime - startTime);
            AddNewChildInfoImpl.LOGGER.error("Encountered error:", io);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
        }

        AddNewChildInfoImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, AddNewChildInfoImpl.EXECUTE_SERVICE,
                (System.currentTimeMillis() - startTime));
        return null;
    }

    @Override
    public BaseResponse addChildInfo(final Map<String, Object> requestMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();

        try {

            final ChildInformationRequest addChildInformationRequest = REQ_READER.readValue(
                    requestMap.get(Constant.REQUEST_BODY).toString());
            final String storekey = (String) requestMap.get(Constant.STORE_KEY);
            final String storeId = propertyReaderService.getStoreId(storekey);
            final Map<String, Object> dataMap = new HashMap<>();
            final String endPointUrl = addChildInfoEndpoint.replaceAll(AddNewChildInfoImpl.STORE_ID_PLACEHOLDER,
                    storeId);
            final long endTime;

            mapCommonRequestVariables(requestMap, dataMap,
                propertyReaderService.getCookieDomain((String) requestMap.get(Constant.DOMAIN_KEY)));
            dataMap.put(AddNewChildInfoImpl.END_POINT_URL_KEY, endPointUrl);
            endTime = System.currentTimeMillis();

            AddNewChildInfoImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                    AddNewChildInfoImpl.LOGGER, endTime - startTime);
            return execute(addChildInformationRequest, dataMap);
        } catch (final IOException e) {
            final long endTime = System.currentTimeMillis();

            AddNewChildInfoImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                    AddNewChildInfoImpl.ADD_CHILD_SERVICE, endTime - startTime);
            AddNewChildInfoImpl.LOGGER.error("Error encountered: {}", e);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
        }
    }

    @Activate
    public void activate(final Config config) {
        addChildInfoEndpoint = config.endPoint();
    }

    @ObjectClassDefinition(name = "WCS AddChildInformation Configurations", description = "AddChildInformation Configuration for WCS Vendor")
    public @interface Config {
        @AttributeDefinition(name = "AddChildInformation End Point", description = "Please enter the AddChildInformation end point in the format"
                + "http://domain/restendpoint/${storeId}/loginservice")
        String endPoint() default "https://mdev.services.commerce.mattel.com/wcs/resources/store/STORE_ID/xupdatechild/update?storeId=STORE_ID&responseFormat=json&updateCookies=true";
    }
}