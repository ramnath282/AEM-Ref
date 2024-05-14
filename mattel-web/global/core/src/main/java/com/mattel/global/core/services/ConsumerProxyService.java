package com.mattel.global.core.services;

import com.mattel.global.core.constants.Constant;
import com.mattel.global.core.exceptions.ServiceException;
import com.mattel.global.core.interfaces.BaseService;
import com.mattel.global.core.interfaces.ConsumerPreferenceService;
import com.mattel.global.core.pojo.BaseResponse;
import com.mattel.global.core.utils.ResponseFormatGetter;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.models.annotations.Optional;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Component (service = ConsumerProxyService.class)
@SuppressWarnings("PMD.UnusedPrivateMethod")
public class ConsumerProxyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerProxyService.class);
    private static final String NO_RESOURCE_FOUND_ERROR = "No resource found: {}, HTTP method type: {}";

    @Reference
    @Optional
    ConsumerPreferenceService consumerPreferenceService;

    /**
     * This method initiates handlemodule services method
     * @param requestMap
     * @param selector
     */
    public Map<String, Object> makeServiceCalls(Map<String, Object> requestMap, String selector) {
        ConsumerProxyService.LOGGER.info("start of makeServiceCalls method");
        long startTime = System.currentTimeMillis();
        Map<String, Object> responseMap = new HashMap<>();
        handleModuleServices(requestMap, responseMap, selector);
        ConsumerProxyService.LOGGER.debug("Final Response Value Map is {}", responseMap);
        long endTime = System.currentTimeMillis();
        ConsumerProxyService.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "makeServiceCalls", endTime - startTime);
        return responseMap;
    }

    /**
     * This method checks method type and initiates get or post call services method
     * @param requestMap
     * @param responseMap
     * @param serviceName
     */
    private void handleModuleServices(Map<String, Object> requestMap, Map<String, Object> responseMap,
        String serviceName) {
        ConsumerProxyService.LOGGER.info("Handle module service is called");
        long startTime = System.currentTimeMillis();
        JSONObject response = null;
        String methodType = String.valueOf(requestMap.get(Constant.METHOD_TYPE));
        ConsumerProxyService.LOGGER.debug("Method type in Handle Module Service is {}", methodType);
        try {
            switch (methodType) {
                case HttpConstants.METHOD_GET:
                    response = handleGetServices(requestMap, serviceName);
                    break;
                case HttpConstants.METHOD_POST:
                    response = handlePostServices(requestMap, serviceName);
                    break;
                default:
                    ConsumerProxyService.LOGGER.error("Invalid Method Type: {}", methodType);
                    break;
            }
        } catch (ServiceException se) {
            long endTime = System.currentTimeMillis();
            boolean isPropagateError = false;
            LOGGER.debug(Constant.EXECUTION_TIME_LOG, "ServiceException in handelModuleServices method",
                endTime - startTime);
            if (se.isPropagateError() && StringUtils.isNumeric(se.getErrorKey())) {
                LOGGER.error(String.format("Consumer API service failure, serviceName: %s, http error code: %s", serviceName, se.getErrorKey()), se);
                isPropagateError = true;
                responseMap.put(Constant.SERVICE_ERROR_MESSAGE, se.getErrorMessage());
                responseMap.put(Constant.ERROR_HTTP_STATUS, se.getErrorKey());
                responseMap.put(Constant.SERVICE_ERROR_BODY, se.getResponseBody());
            } else {
                response = ResponseFormatGetter.getErrorJson(se);
            }
            responseMap.put(Constant.PROPAGATE_SERVICE_ERRORS, isPropagateError);
        }
        if(null != response) {
            responseMap.put(Constant.RESPONSE_BODY, response.toString());
        }
        long endTime = System.currentTimeMillis();
        ConsumerProxyService.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "handleModuleServices", endTime - startTime);
    }

    /**
     * This method initiates service call for get method
     * @param requestMap
     * @param serviceName
     */
    private JSONObject handleGetServices (Map<String, Object> requestMap,
        String serviceName) throws ServiceException {
        ConsumerProxyService.LOGGER.info("start - handle get services method");
        JSONObject response = null;
        if (Constant.CONSUMER_PREFERENCE_SERVICE.equals(serviceName)) {
            logServiceCall(consumerPreferenceService);
            response = buildConsumerResponse(
                consumerPreferenceService.fetchEmailPreference(requestMap),
                consumerPreferenceService);
        } else {
            ConsumerProxyService.LOGGER
                .error(NO_RESOURCE_FOUND_ERROR, serviceName, HttpConstants.METHOD_GET);
        }
    return response;
    }

    /**
     * This method initiates service call for post method
     * @param requestMap
     * @param serviceName
     */
    private JSONObject handlePostServices (Map<String, Object> requestMap,
        String serviceName) throws ServiceException {
        ConsumerProxyService.LOGGER.info("start - handle post services method");
        JSONObject response = null;
        if (Constant.CONSUMER_PREFERENCE_SERVICE.equals(serviceName)) {
            logServiceCall(consumerPreferenceService);
            response = buildConsumerResponse(
                consumerPreferenceService.updateEmailPreference(requestMap),
                consumerPreferenceService);
        } else {
            ConsumerProxyService.LOGGER
                .error(NO_RESOURCE_FOUND_ERROR, serviceName, HttpConstants.METHOD_POST);
        }
        return response;
    }

    /**
     * Build {@link JSONObject} response.
     *
     * @param serviceResponse
     * @param service
     * @throws ServiceException
     */
    private JSONObject buildConsumerResponse(final BaseResponse serviceResponse, final BaseService service) throws ServiceException {
        ConsumerProxyService.LOGGER.debug("Service : {}, Response : {}", new Object [] {
            service.getClass().getSimpleName(), serviceResponse});
        return service.getResponseValueAsJson(serviceResponse);
    }

    /**
     * Log the service calls.
     *
     * @param service
     */
    private void logServiceCall(final BaseService service) {
        ConsumerProxyService.LOGGER.debug("Invoking service: {}",
            service.getClass().getSimpleName());
    }

}
