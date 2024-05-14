package com.mattel.global.core.services;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.global.core.constants.Constant;
import com.mattel.global.core.enums.ResourceMapper;
import com.mattel.global.core.exceptions.ServiceException;
import com.mattel.global.core.interfaces.ConsumerPreferenceService;
import com.mattel.global.core.pojo.BaseRequest;
import com.mattel.global.core.pojo.ConsumerPreferenceRequest;
import com.mattel.global.core.pojo.ConsumerPreferenceResponse;
import com.mattel.global.core.pojo.UpdateConsumerPrefRequest;
import com.mattel.global.core.utils.CryptoSupportUtils;
import com.mattel.global.core.utils.HttpRequestHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Component (service = ConsumerPreferenceService.class)
@Designate(ocd = ConsumerPreferenceServiceImpl.Config.class)
/**
 * Consumer Email Preferences API to fetch and update email preferences of the Consumer.
 */
public class ConsumerPreferenceServiceImpl implements ConsumerPreferenceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerPreferenceServiceImpl.class);

    private String apiEndPoint;
    private String apiKey;
    private static final String END_POINT_URL = "endPointUrl";
    private static final ObjectReader RESPONSE_READER = ResourceMapper
        .getReaderInstance(ConsumerPreferenceResponse.class);
    private static final ObjectReader REQUEST_READER = ResourceMapper
        .getReaderInstance(UpdateConsumerPrefRequest.class);


    /**
     * Consumer Email Preferences - Update preferences execute method.
     */
    public ConsumerPreferenceResponse executeUpdate(UpdateConsumerPrefRequest updateConsumerPrefRequest,
        Map<String, Object> dataMap) throws ServiceException {
        ConsumerPreferenceServiceImpl.LOGGER.info("Start of executeUpdate method of ConsumerPreferenceServiceImpl");
        final long startTime = System.currentTimeMillis();
        final String methodName = "executeUpdate";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
           if(StringUtils.isNotEmpty(updateConsumerPrefRequest.getEmailAddress())) {
               updateConsumerPrefRequest.setEmailAddress(CryptoSupportUtils.decryptString(updateConsumerPrefRequest.getEmailAddress()));
           }
           else {
               updateConsumerPrefRequest.setEmailAddress("");
           }
            ConsumerPreferenceServiceImpl.LOGGER.info("Decrypted consumer ID {} in req", updateConsumerPrefRequest.getEmailAddress());
            ConsumerPreferenceResponse consumerPreferenceResponse;
            final Map<String, String> requestHeaders = new HashMap<>();
            requestHeaders.put("Accept", Constant.CONTENT_TYPE_APPLICATION_JSON);
            requestHeaders.put("Content-type", Constant.CONTENT_TYPE_APPLICATION_JSON);
            requestHeaders.put(Constant.FROM_API_KEY, apiKey);
            final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
            final HttpResponse httpResponse = HttpRequestHandler.post(client, dataMap.get(ConsumerPreferenceServiceImpl.END_POINT_URL).toString(),
                requestHeaders, updateConsumerPrefRequest, httpClientContext);
            final int status = httpResponse.getStatusLine().getStatusCode();
            ConsumerPreferenceServiceImpl.LOGGER.info("Update consumer preference API Status is {}", status);
            if(null != httpResponse.getEntity() && !isError(status)) {
                final InputStream inputStream = httpResponse.getEntity().getContent();
                consumerPreferenceResponse = ConsumerPreferenceServiceImpl.RESPONSE_READER.readValue(inputStream);
                ConsumerPreferenceServiceImpl.LOGGER.info("Update Consumer Preference Response Object is {}",consumerPreferenceResponse);
                final long endTime = System.currentTimeMillis();
                ConsumerPreferenceServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
                return consumerPreferenceResponse;
            } else {
                final long endTime = System.currentTimeMillis();
                ConsumerPreferenceServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
                generalExceptionHandling(status,httpResponse.getEntity());
            }
        } catch (final IOException e) {
            final long endTime = System.currentTimeMillis();
            ConsumerPreferenceServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
            ConsumerPreferenceServiceImpl.LOGGER.error("Encountered error", e);
        }
        final long endTime = System.currentTimeMillis();
        ConsumerPreferenceServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
        return null;
    }

    /**
     * Consumer Email Preferences - Update preferences.
     */
    @Override
    public ConsumerPreferenceResponse updateEmailPreference(Map<String, Object> requestHeader)
    throws ServiceException {
        ConsumerPreferenceServiceImpl.LOGGER.info("Update Customer Preferences Response Starts");
        final long startTime = System.currentTimeMillis();
        final String methodName = "updateEmailPreferences";

        try {
            ConsumerPreferenceServiceImpl.LOGGER.info("End Point Config is {}", apiEndPoint);
            final UpdateConsumerPrefRequest updateConsumerPrefRequest = ConsumerPreferenceServiceImpl
                .REQUEST_READER.readValue(requestHeader.get(Constant.REQUEST_BODY).toString());
            final Map<String, Object> dataMap = new HashMap<>();
            dataMap.put(Constant.FROM_API_KEY, apiKey);
            dataMap.put(ConsumerPreferenceServiceImpl.END_POINT_URL, apiEndPoint);
            dataMap.put(Constant.DEF_CONNECT_TIMEOUT, 2000);
            ConsumerPreferenceServiceImpl.LOGGER.debug("dataMap is built {}", dataMap);
            final long endTime = System.currentTimeMillis();
            ConsumerPreferenceServiceImpl.LOGGER.info(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
            return executeUpdate(updateConsumerPrefRequest,dataMap);
        } catch (final IOException e) {
            final long endTime = System.currentTimeMillis();
            ConsumerPreferenceServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
            ConsumerPreferenceServiceImpl.LOGGER.error("IO Exception occured", e);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
        }
    }

    /**
     * Consumer Email Preferences - Get preferences execute method.
     */
    @SuppressWarnings("unchecked")
    @Override
    public ConsumerPreferenceResponse execute(BaseRequest baseRequest, Map<String, Object> requestMap)
        throws ServiceException {
        ConsumerPreferenceServiceImpl.LOGGER.info("start - execute method");
        final long startTime = System.currentTimeMillis();
        final String methodName = "executeFetch";
        try(CloseableHttpClient httpClient = createCustomClient(requestMap)) {
            final Map<String, String> requestHeaders = new HashMap<>();
            requestHeaders.put(Constant.EMAIL_ADDRESS,
                requestMap.get(Constant.EMAIL_ADDRESS).toString());
            requestHeaders.put(Constant.FROM_API_KEY, requestMap.get(Constant.FROM_API_KEY).toString());
            ConsumerPreferenceServiceImpl.LOGGER.debug("request headers in executeFetch {}", requestHeaders);
            final HttpResponse httpResponse = HttpRequestHandler.get(httpClient,
                requestMap.get(ConsumerPreferenceServiceImpl.END_POINT_URL).toString(), requestHeaders,
                null, null);
            final int status = httpResponse.getStatusLine().getStatusCode();
            ConsumerPreferenceServiceImpl.LOGGER.info("Fetch response status code is {}", status);
            if(null != httpResponse.getEntity() && !isError(status)) {
                try (InputStream inputStream = httpResponse.getEntity().getContent()) {
                    final ConsumerPreferenceResponse consumerPreferenceResponse = ConsumerPreferenceServiceImpl
                        .RESPONSE_READER.readValue(inputStream);
                    ConsumerPreferenceServiceImpl.LOGGER.info("consumerPreferenceResponse object is {}", consumerPreferenceResponse);
                    return consumerPreferenceResponse;
                }
            }
            else {
                final long endTime = System.currentTimeMillis();
                ConsumerPreferenceServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
                generalExceptionHandling(status, httpResponse.getEntity());
            }
        } catch(IOException | URISyntaxException e) {
            final long endTime = System.currentTimeMillis();
            ConsumerPreferenceServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
            ConsumerPreferenceServiceImpl.LOGGER.error("Exception in execute of ConsumerPreferenceServiceImpl", e);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception");
        }
        ConsumerPreferenceServiceImpl.LOGGER.info("Execute of ConsumerPreferenceServiceImpl - end");
        final long endTime = System.currentTimeMillis();
        ConsumerPreferenceServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
        return null;
    }

    /**
     * Consumer Email Preferences - Get preferences.
     */
    @Override
    public ConsumerPreferenceResponse fetchEmailPreference(Map<String, Object> requestMap)
        throws ServiceException {
        ConsumerPreferenceServiceImpl.LOGGER.info("fetchEmailPreference - start {}", requestMap);
        final long startTime = System.currentTimeMillis();
        ConsumerPreferenceRequest consumerPreferenceRequest = new ConsumerPreferenceRequest();
        final String emailAddress = String.valueOf(requestMap.get(Constant.EMAIL_ADDRESS));
        ConsumerPreferenceServiceImpl.LOGGER.debug("Encrypted email address from request map {}",emailAddress);
        final Map<String, Object> dataMap = new HashMap<>();
        if(StringUtils.isNotEmpty(emailAddress)) {            
            dataMap.put(Constant.EMAIL_ADDRESS, CryptoSupportUtils.decryptString(emailAddress));
        }
        else {            
            dataMap.put(Constant.EMAIL_ADDRESS, "");
        }
        dataMap.put(Constant.FROM_API_KEY, apiKey);
        dataMap.put(ConsumerPreferenceServiceImpl.END_POINT_URL, apiEndPoint);
        dataMap.put(Constant.DEF_CONNECT_TIMEOUT, 2000);
        ConsumerPreferenceServiceImpl.LOGGER.debug("data map in fetchEmail method {}", dataMap);
        final long endTime = System.currentTimeMillis();
        ConsumerPreferenceServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "ConsumerPreferences - fecth",
            endTime - startTime);
        return execute(consumerPreferenceRequest, dataMap);
    }

    /**
     * OGI Configuration for Consumer Email Preferences API.
     */
    @ObjectClassDefinition(name = "Consumer Email Preference API Configurations", description = "Consumer Email Preference API Configurations")
    public @interface Config {
        @AttributeDefinition(name = "Consumer Email Preference End point", description = "Please Enter the Consumer "
            + "Email Preference End point") String endPoint() default "https://api.dev.mattel.com/preprod/consumerpreferences/filteredresponse";
        @AttributeDefinition(name = "Consumer Email Preference API Key", description = "Please Enter the Consumer "
            + "Email Preference API Key") String apiKey() default "4csmwmp8ff55ur2aanfa568x";
    }

    @Activate public void activate(final Config config) {
        apiEndPoint = config.endPoint();
        apiKey = config.apiKey();
    }

}