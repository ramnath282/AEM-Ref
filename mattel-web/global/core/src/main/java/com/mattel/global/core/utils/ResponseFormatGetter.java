package com.mattel.global.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattel.global.core.constants.Constant;
import com.mattel.global.core.exceptions.ServiceException;
import com.mattel.global.core.pojo.ErrorResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseFormatGetter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseFormatGetter.class);

    private ResponseFormatGetter() {
    }

    /**
     *
     * @param se
     * @return
     */
    public static JSONObject getErrorJson(ServiceException se) {
    	long startTime = System.currentTimeMillis();
		String methodname = "getErrorJson";
        try {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setErrorKey(se.getErrorKey());
            errorResponse.setErrorCode(se.getErrorKey());
            errorResponse.setErrorMessage(se.getErrorMessage());
            ObjectMapper objectMapper = new ObjectMapper();
            JSONObject errorJson = new JSONObject(objectMapper.writeValueAsString(errorResponse));
            LOGGER.debug("Error JSON String is {}", errorJson);
            LOGGER.info("End of get error json method");
            long endTime = System.currentTimeMillis();
			LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodname, endTime - startTime);
            return errorJson;
        } catch (JSONException | JsonProcessingException je) {
        	long endTime = System.currentTimeMillis();
			LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodname, endTime - startTime);
            LOGGER.error("JSON Exception {}", je);
        }
        long endTime = System.currentTimeMillis();
		LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodname, endTime - startTime);
        return null;
    }

}
