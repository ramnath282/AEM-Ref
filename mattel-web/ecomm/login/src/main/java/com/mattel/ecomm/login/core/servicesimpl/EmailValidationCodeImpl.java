package com.mattel.ecomm.login.core.servicesimpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import com.mattel.ecomm.coreservices.core.interfaces.EmailValidationCode;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.EmailValidationCodeRequest;
import com.mattel.ecomm.coreservices.core.pojos.EmailValidationCodeResponse;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@Component(service = EmailValidationCode.class)
@Designate(ocd = EmailValidationCodeImpl.Config.class)
public class EmailValidationCodeImpl implements EmailValidationCode {
  private static final Logger LOGGER = LoggerFactory.getLogger(EmailValidationCodeImpl.class);
    private static final String END_POINT_URL_KEY = "endPointUrl";
    private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
    @Reference
    private PropertyReaderService propertyReaderService;
    private String emailValidationCodeEndPoint;
    private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(EmailValidationCodeResponse.class);
    private static final ObjectReader REQ_READER = ResourceMapper.getReaderInstance(EmailValidationCodeRequest.class);

    public PropertyReaderService getPropertyReaderService() {
        return propertyReaderService;
    }

    public void setPropertyReaderService(PropertyReaderService propertyReaderService) {
        this.propertyReaderService = propertyReaderService;
    }

    public String getEmailValidationCodeEndPoint() {
        return emailValidationCodeEndPoint;
    }

    public void setEmailValidationCodeEndPoint(String emailValidationCodeEndPoint) {
        this.emailValidationCodeEndPoint = emailValidationCodeEndPoint;
    }

    @Override
    public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();
        final String methodName = "execute";
        try (CloseableHttpClient httpClient = HttpClients.createDefault();) {
            EmailValidationCodeImpl.LOGGER.info("Start of emailValidationCode Execute Method.");
            final EmailValidationCodeRequest emailValidationCodeRequest = (EmailValidationCodeRequest) baseRequest;
            final Map<String, String> requestHeaders = new HashMap<>();
            EmailValidationCodeResponse emailValidationCodeResponse;
            requestHeaders.put("Accept", "application/json");
            requestHeaders.put("Content-type", "application/json");
            final long wcsExecutionStartTime = System.currentTimeMillis();
            final HttpResponse httpResponse = HttpRequestHandler.post(httpClient,
                    dataMap.get(EmailValidationCodeImpl.END_POINT_URL_KEY).toString(), null, requestHeaders,
                    emailValidationCodeRequest, null);
            final long wcsExecutionEndTime = System.currentTimeMillis();
            EmailValidationCodeImpl.LOGGER.debug("WCS Execution Time for emailValidationCode is {}",
                    wcsExecutionEndTime - wcsExecutionStartTime);
            final int status = httpResponse.getStatusLine().getStatusCode();
            EmailValidationCodeImpl.LOGGER.debug("Status from emailValidationCode is {}", status);
            final InputStream inputStream = httpResponse.getEntity().getContent();
            final boolean isError = isError(status);
            if (!isError) {
                if (status == 200) {
                    emailValidationCodeResponse = EmailValidationCodeImpl.RESP_READER.readValue(inputStream);
                    emailValidationCodeResponse.setEmailValidationProcessStatus(true);
                } else {
                    emailValidationCodeResponse = EmailValidationCodeImpl.RESP_READER.readValue(inputStream);
                }
                EmailValidationCodeImpl.LOGGER.debug("emailValidationCode Response Object is {}",
                        emailValidationCodeResponse);
                final long endTime = System.currentTimeMillis();
                EmailValidationCodeImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
                EmailValidationCodeImpl.LOGGER.debug("emailValidationCode Response Object is {}",
                        emailValidationCodeResponse);
                return emailValidationCodeResponse;
            } else {
                final long endTime = System.currentTimeMillis();
                EmailValidationCodeImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
                generalExceptionHandling(status);
            }

        } catch (final IOException e) {
            final long endTime = System.currentTimeMillis();
            EmailValidationCodeImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
            EmailValidationCodeImpl.LOGGER.error("IO Exception occured:", e);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
        }
        final long endTime = System.currentTimeMillis();
        EmailValidationCodeImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
        return null;
    }

    @Override
    public EmailValidationCodeResponse emailValidationCode(Map<String, Object> requestHeader) throws ServiceException {
        final long startTime = System.currentTimeMillis();
        final String methodName = "emailValidationCode";
        EmailValidationCodeImpl.LOGGER.info("emailValidationCode Response Starts");
        try {
            EmailValidationCodeImpl.LOGGER.debug("End from Config is {}", emailValidationCodeEndPoint);
            final EmailValidationCodeRequest emailValidationCodeRequest = EmailValidationCodeImpl.REQ_READER
                    .readValue(requestHeader.get(Constant.REQUEST_BODY).toString());
            final String storeKey = (String) requestHeader.get(Constant.STORE_KEY);
            final String domainKey = (String) requestHeader.get(Constant.DOMAIN_KEY);
            final String storeId = propertyReaderService.getStoreId(storeKey);
            final String endPointUrl = emailValidationCodeEndPoint
                    .replaceAll(EmailValidationCodeImpl.STORE_ID_PLACEHOLDER, storeId);
            final String domain = propertyReaderService.getCookieDomain(domainKey);
            emailValidationCodeRequest.setStoreId(storeId);
            if (!StringUtils.isEmpty(emailValidationCodeRequest.getUserName())) {
                emailValidationCodeRequest.setUserName(new StringBuilder(storeId).append("|")
                        .append(emailValidationCodeRequest.getUserName()).toString());
            }

            EmailValidationCodeImpl.LOGGER.debug("Connection End Point is {}", endPointUrl);
            final Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("domain", domain);
            dataMap.put(EmailValidationCodeImpl.END_POINT_URL_KEY, endPointUrl);
            final long endTime = System.currentTimeMillis();
            EmailValidationCodeImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
            return (EmailValidationCodeResponse) execute(emailValidationCodeRequest, dataMap);
        } catch (final IOException e) {
            final long endTime = System.currentTimeMillis();
            EmailValidationCodeImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
            EmailValidationCodeImpl.LOGGER.error("IO Exception occured {}", e);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
        }
    }

    @ObjectClassDefinition(name = "WCS EmailValidationCode Configurations", description = "EmailValidationCode Configuration for WCS Vendor")
    public @interface Config {

        @AttributeDefinition(name = "EmailValidationCode End Point", description = "Please Enter the EmailValidationCode End point in the format"
                + "http://domain/restendpoint/STORE_ID/serviceendpoint")
        String endPoint() default "https://mdev.americangirl.com/wcs/resources/store/STORE_ID/xResetPassword/xSendValidationCode?responseFormat=json&updateCookies=true";

    }

    @Activate
    public void activate(final Config config) {
        emailValidationCodeEndPoint = config.endPoint();
    }

}
