package com.mattel.ecomm.login.core.servicesimpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
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
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.ResetPassword;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.ResetPasswordRequest;
import com.mattel.ecomm.coreservices.core.pojos.ResetPasswordResponse;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@Component(service = ResetPassword.class)
@Designate(ocd = ResetPasswordImpl.Config.class)
public class ResetPasswordImpl implements ResetPassword {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResetPasswordImpl.class);
    private static final String END_POINT_URL_KEY = "endPointUrl";
    private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
    private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(ResetPasswordResponse.class);
    private static final ObjectReader REQ_READER = ResourceMapper.getReaderInstance(ResetPasswordRequest.class);
    @Reference
    private PropertyReaderService propertyReaderService;
    private String resetPasswordEndPoint;

    @Override
    public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();
        final String methodName = "execute";
        try (CloseableHttpClient httpClient = HttpClients.createDefault();) {
          ResetPasswordImpl.LOGGER.info("Start of Login Execute Method.");
            final ResetPasswordRequest resetPasswordRequest = (ResetPasswordRequest) baseRequest;
            final Map<String, String> requestHeaders = new HashMap<>();
            ResetPasswordResponse resetPasswordResponse;
            requestHeaders.put("Accept", "application/json");
            requestHeaders.put("Content-type", "application/json");
            final long wcsExecutionStartTime = System.currentTimeMillis();
            final HttpResponse httpResponse = HttpRequestHandler.post(httpClient,
                    dataMap.get(ResetPasswordImpl.END_POINT_URL_KEY).toString(), null, requestHeaders,
                    resetPasswordRequest, null);
            final long wcsExecutionEndTime = System.currentTimeMillis();
            ResetPasswordImpl.LOGGER.debug("WCS Execution Time for Login is {}", wcsExecutionEndTime - wcsExecutionStartTime);
            final int status = httpResponse.getStatusLine().getStatusCode();
            ResetPasswordImpl.LOGGER.debug("Status from Login is {}", status);
            final InputStream inputStream = httpResponse.getEntity().getContent();
            final boolean isError = isError(status);
            if (!isError) {
                if (status == 200) {
                    resetPasswordResponse = new ResetPasswordResponse();
                    resetPasswordResponse.setPasswordChangeStatus(true);
                } else {
                    resetPasswordResponse = ResetPasswordImpl.RESP_READER.readValue(inputStream);
                }

                ResetPasswordImpl.LOGGER.debug("Login Response Object is {}", resetPasswordResponse);
                final long endTime = System.currentTimeMillis();
                ResetPasswordImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
                ResetPasswordImpl.LOGGER.debug("Login Response Object is {}", resetPasswordResponse);
                return resetPasswordResponse;
            } else {
                final long endTime = System.currentTimeMillis();
                ResetPasswordImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
                generalExceptionHandling(status);
            }

        } catch (final IOException e) {
            final long endTime = System.currentTimeMillis();
            ResetPasswordImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
            ResetPasswordImpl.LOGGER.error("IO Exception occured:", e);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
        }
        final long endTime = System.currentTimeMillis();
        ResetPasswordImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
        return null;
    }

    @Override
    public ResetPasswordResponse resetPassword(Map<String, Object> requestHeader) throws ServiceException {
        final long startTime = System.currentTimeMillis();
        final String methodName = "RestPassword";
        ResetPasswordImpl.LOGGER.info("Login Response Starts");
        try {
          ResetPasswordImpl.LOGGER.debug("End from Config is {}", resetPasswordEndPoint);
            final ResetPasswordRequest resetPasswordRequest = ResetPasswordImpl.REQ_READER
                    .readValue(requestHeader.get(Constant.REQUEST_BODY).toString());
            final String storeKey = (String) requestHeader.get(Constant.STORE_KEY);
            final String domainKey = (String) requestHeader.get(Constant.DOMAIN_KEY);
            final String storeId = propertyReaderService.getStoreId(storeKey);
            final String endPointUrl = resetPasswordEndPoint.replaceAll(ResetPasswordImpl.STORE_ID_PLACEHOLDER,
                    storeId);
            final String domain = propertyReaderService.getCookieDomain(domainKey);
            ResetPasswordImpl.LOGGER.debug("Connection End Point is {}", endPointUrl);
            final Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("domain", domain);
            dataMap.put(ResetPasswordImpl.END_POINT_URL_KEY, endPointUrl);
            final long endTime = System.currentTimeMillis();
            ResetPasswordImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
            return (ResetPasswordResponse) execute(resetPasswordRequest, dataMap);
        } catch (final IOException e) {
            final long endTime = System.currentTimeMillis();
            ResetPasswordImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
            ResetPasswordImpl.LOGGER.error("Io Exception occured {}", e);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
        }
    }

    @ObjectClassDefinition(name = "WCS ResetPassword Configurations", description = "ResetPassword Configuration for WCS Vendor")
    public @interface Config {

        @AttributeDefinition(name = "ResetPassword End Point", description = "Please Enter the ResetPassword End point in the format"
                + "http://domain/restendpoint/STORE_ID/serviceendpoint")
        String endPoint() default "https://mdev.americangirl.com/wcs/resources/store/STORE_ID/xResetPassword/xUpdatePassword?&responseFormat=json&updateCookies=true";

    }

    @Activate
    public void activate(final Config config) {
        resetPasswordEndPoint = config.endPoint();
    }
}
