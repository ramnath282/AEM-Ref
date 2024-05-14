package com.mattel.ecomm.core.services;

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
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.core.interfaces.GTEmailTNSService;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.EmailTNSRequest;
import com.mattel.ecomm.coreservices.core.pojos.EmailTNSResponse;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@Component(service = GTEmailTNSService.class)
public class GTEmailTNSServiceImpl implements GTEmailTNSService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GTEmailTNSServiceImpl.class);
    private static final ObjectReader REQ_READER = ResourceMapper.getReaderInstance(EmailTNSRequest.class);
    private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(EmailTNSResponse.class);
    @Reference
    PropertyReaderService propertyReaderService;
    @Reference
    GTEmailTnsConfigService gTEmailTnsConfigService;

    @SuppressWarnings("unchecked")
    @Override
    public EmailTNSResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            final EmailTNSRequest emailTNSRequest = (EmailTNSRequest) baseRequest;
            final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
            final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
            final Map<String, String> requestHeaders = new HashMap<>();
            final HttpResponse httpResponse;
            final int status;

            requestHeaders.put("Accept", Constant.CONTENT_TYPE_APPLICATION_JSON);
            requestHeaders.put("Content-type", Constant.CONTENT_TYPE_APPLICATION_JSON);
            httpResponse = HttpRequestHandler.post(httpClient, dataMap.get(Constant.END_POINT_URL_KEY).toString(),
                    requestCookies, requestHeaders, emailTNSRequest, httpClientContext);
            status = httpResponse.getStatusLine().getStatusCode();

            GTEmailTNSServiceImpl.LOGGER.debug("Response status is {}", status);

            if (!isError(status)) {
                try (final InputStream inputStream = httpResponse.getEntity().getContent()) {
                    final EmailTNSResponse emailTNSResponse = GTEmailTNSServiceImpl.RESP_READER.readValue(inputStream);
                    GTEmailTNSServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
                            System.currentTimeMillis() - startTime);
                    return emailTNSResponse;
                }
            } else {
                GTEmailTNSServiceImpl.LOGGER.error("Recieved response with error status: {} in {}", status,
                        Constant.EXECUTE_SERVICE);
                GTEmailTNSServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
                        System.currentTimeMillis() - startTime);
                generalExceptionHandling(status);
            }
        } catch (final IOException e) {
            final long endTime = System.currentTimeMillis();

            GTEmailTNSServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
                    endTime - startTime);
            GTEmailTNSServiceImpl.LOGGER.error("Encountered error:", e);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
        }

        return null;
    }

    @Override
    public EmailTNSResponse sendEmailDetails(Map<String, Object> requestMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();
        GTEmailTNSServiceImpl.LOGGER.error("inside class GTEmailTNSServiceImpl");
        try {
            final EmailTNSRequest emailTNSRequest = GTEmailTNSServiceImpl.REQ_READER
                    .readValue(requestMap.get(Constant.REQUEST_BODY).toString());
            final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
            final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
            final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects, cookieNames);
            final Map<String, Object> dataMap = new HashMap<>();
            final long endTime;
            dataMap.put("validCookieNames", cookieNames);
            dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
            dataMap.put(Constant.END_POINT_URL_KEY, gTEmailTnsConfigService.getEndPointURL());
            endTime = System.currentTimeMillis();

            GTEmailTNSServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "Gift Trunk Email service",
                    endTime - startTime);
            return execute(emailTNSRequest, dataMap);
        } catch (final IOException e) {
            final long endTime = System.currentTimeMillis();

            GTEmailTNSServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "Gift Trunk Email service",
                    endTime - startTime);
            GTEmailTNSServiceImpl.LOGGER.error("Error encountered: ", e);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
        }
    }

}