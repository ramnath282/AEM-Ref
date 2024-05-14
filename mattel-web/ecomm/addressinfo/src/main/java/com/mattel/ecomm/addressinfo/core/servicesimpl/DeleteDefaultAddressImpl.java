package com.mattel.ecomm.addressinfo.core.servicesimpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

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
import com.mattel.ecomm.coreservices.core.interfaces.DeleteDefaultAddress;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.DeleteChildRequest;
import com.mattel.ecomm.coreservices.core.pojos.DeleteDefaultAddressResponse;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@Component(service = DeleteDefaultAddress.class)
@Designate(ocd = DeleteDefaultAddressImpl.Config.class)
public class DeleteDefaultAddressImpl implements DeleteDefaultAddress {
    private static final String END_POINT_URL_KEY = "endPointUrl";
    private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
    private static final String NICK_NAME_PLACEHOLDER = "NICK_NAME";
    private static final String DELETE_DEFAULT_ADDRESS_SERVICE = "deleteDefaultAddress";
    private static final String EXECUTE_SERVICE = "execute";
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteDefaultAddressImpl.class);
    private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(DeleteDefaultAddressResponse.class);
    private static final ObjectReader REQ_READER = ResourceMapper.getReaderInstance(DeleteChildRequest.class);
    private String deleteDefaultAddressEndpoint;
    @Reference
    PropertyReaderService propertyReaderService;

    @SuppressWarnings("unchecked")
    @Override
    public BaseResponse execute(final BaseRequest baseRequest, final Map<String, Object> dataMap)
            throws ServiceException {
        final long startTime = System.currentTimeMillis();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
            final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
            final HttpResponse httpResponse = HttpRequestHandler.delete(httpClient,
                    dataMap.get(DeleteDefaultAddressImpl.END_POINT_URL_KEY).toString(), requestCookies, null, httpClientContext);
            final int status = httpResponse.getStatusLine().getStatusCode();

            DeleteDefaultAddressImpl.LOGGER.debug("Response status is {}", status);

            if (null != httpResponse.getEntity().getContent() && !isError(status)) {
                try (final InputStream inputStream = httpResponse.getEntity().getContent()) {
                    DeleteDefaultAddressResponse addressResponse = DeleteDefaultAddressImpl.RESP_READER.readValue(inputStream);
                    addressResponse.setCookieList(CookieUtils.constructCookieList((String[]) dataMap.get("validCookieNames"), (String) dataMap.get("domain"), httpClientContext.getCookieStore().getCookies()));
                    DeleteDefaultAddressImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                            DeleteDefaultAddressImpl.EXECUTE_SERVICE, (System.currentTimeMillis() - startTime));
                    return addressResponse;
                }
            } else {
                final long endTime = System.currentTimeMillis();

                DeleteDefaultAddressImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                        DeleteDefaultAddressImpl.EXECUTE_SERVICE, endTime - startTime);
                generalExceptionHandling(status);
            }
        } catch (final IOException io) {
            final long endTime = System.currentTimeMillis();

            DeleteDefaultAddressImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, DeleteDefaultAddressImpl.EXECUTE_SERVICE,
                    endTime - startTime);
            DeleteDefaultAddressImpl.LOGGER.error("Encountered error:", io);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
        }

        final long endTime = System.currentTimeMillis();
        DeleteDefaultAddressImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, DeleteDefaultAddressImpl.EXECUTE_SERVICE,
                endTime - startTime);
        return null;
    }

    @Override
    public BaseResponse delete(final Map<String, Object> requestMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();

        try {
            final DeleteChildRequest deleteRequest = DeleteDefaultAddressImpl.REQ_READER
                    .readValue(requestMap.get(Constant.REQUEST_BODY).toString());
            final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
            final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
            final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects, cookieNames);
            final String storekey = (String) requestMap.get(Constant.STORE_KEY);
            final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
            final String storeId = propertyReaderService.getStoreId(storekey);
            final String domain = propertyReaderService.getCookieDomain(domainKey);
            final Map<String, Object> dataMap = new HashMap<>();
            String endPointUrl = deleteDefaultAddressEndpoint.replaceAll(DeleteDefaultAddressImpl.STORE_ID_PLACEHOLDER,
                    storeId);
            endPointUrl = endPointUrl.replace(DeleteDefaultAddressImpl.NICK_NAME_PLACEHOLDER,
                    deleteRequest.getNickName());
            final long endTime;

            dataMap.put("validCookieNames", cookieNames);
            dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
            dataMap.put("domain", domain);
            dataMap.put(DeleteDefaultAddressImpl.END_POINT_URL_KEY, endPointUrl);
            endTime = System.currentTimeMillis();

            DeleteDefaultAddressImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                    DeleteDefaultAddressImpl.DELETE_DEFAULT_ADDRESS_SERVICE, endTime - startTime);
            return execute(null, dataMap);
        } catch (final IOException e) {
            final long endTime = System.currentTimeMillis();

            DeleteDefaultAddressImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                    DeleteDefaultAddressImpl.DELETE_DEFAULT_ADDRESS_SERVICE, endTime - startTime);
            DeleteDefaultAddressImpl.LOGGER.error("Error encountered:", e);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
        }
    }

    @ObjectClassDefinition(name = "WCS DeleteDefaultAddress Configurations", description = "DeleteDefaultAddress Configuration for WCS Vendor")
    public @interface Config {
        @AttributeDefinition(name = "DeleteDefaultAddress End Point", description = "Please Enter the DeleteDefaultAddress End point in the format"
                + "http://domain/restendpoint/${storeId}/loginservice")
        String endPoint() default "https://mdev.services.commerce.mattel.com/wcs/resources/store/STORE_ID/person/@self/contact/NICK_NAME?storeId=STORE_ID&updateCookies=true";
    }

    @Activate
    public void activate(final Config config) {
        deleteDefaultAddressEndpoint = config.endPoint();
    }
}
