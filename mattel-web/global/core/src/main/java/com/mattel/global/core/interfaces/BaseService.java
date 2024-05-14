package com.mattel.global.core.interfaces;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattel.global.core.constants.Constant;
import com.mattel.global.core.exceptions.ServiceException;
import com.mattel.global.core.pojo.BaseRequest;
import com.mattel.global.core.pojo.BaseResponse;
import com.mattel.global.core.pojo.ResponseBody;
import com.mattel.global.core.utils.HttpRequestHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@FunctionalInterface
public interface BaseService {
    BaseResponse execute(BaseRequest request, Map<String, Object> dataMap)
        throws ServiceException;

    Logger LOGGER = LoggerFactory.getLogger(BaseService.class);

    default JSONObject getResponseValueAsJson(Object pojoClass) throws ServiceException {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            final String json = objectMapper.writeValueAsString(pojoClass);
            return new JSONObject(json);
        } catch (final JsonParseException je) {
            LOGGER.error(Constant.SERVICE_RESP_ERROR, je);
            throw new ServiceException("1002", "Unable to parse Response JSON");
        } catch (final JsonMappingException jme) {
            LOGGER.error(Constant.SERVICE_RESP_ERROR, jme);
            throw new ServiceException("1003", "Mapping Invalid of JSON response");
        } catch (final IOException io) {
            LOGGER.error(Constant.SERVICE_RESP_ERROR, io);
            throw new ServiceException("1001", "IO Exception");
        } catch (final JSONException je) {
            LOGGER.error(Constant.SERVICE_RESP_ERROR, je);
            throw new ServiceException("1004", "JSON Exception");
        }
    }

    /**
     * Determine based on response status whether there is an error.
     *
     * @param status
     *          The Response status.
     * @return True if response status is non 2xx error status.
     */
    default boolean isError(int status) {
        switch (status) {
            case 200:
            case 201:
            case 204:
                return false;
            default:
                return true;
        }
    }

    /**
     * Returns a custom instance of {@link CloseableHttpClient}
     *
     * @param dataMap
     *          The request data map containing the timeout value.
     * @return The {@link CloseableHttpClient} instance.
     */
    default CloseableHttpClient createCustomClient(Map<String, Object> dataMap) {
        return HttpRequestHandler.createCustom((Integer) dataMap.get(Constant.DEF_CONNECT_TIMEOUT));
    }

    /**
     * Throws {@link ServiceException} encapsulating the response status.
     *
     * @param status
     *          The Response status.
     * @throws ServiceException
     *           Throws {@link ServiceException} encapsulating the response status.
     */
    default void generalExceptionHandling(int status) throws ServiceException {
        LOGGER.error("Generic error occurred in Services: {}", status);
        throw new ServiceException(Integer.toString(status), "Generic Error Occurred", true);
    }

    /**
     * Throws {@link ServiceException} encapsulating the response status.
     *
     * @param status
     *          The Response status.
     * @param response
     *          The Response object.
     * @throws ServiceException
     *           Throws {@link ServiceException} encapsulating the response status.
     */
    default void generalExceptionHandling(int status, BaseResponse response) throws ServiceException {
        LOGGER.error("Generic error occurred in Services: {}, response : {}", status,
            response);
        throw new ServiceException(Integer.toString(status), "Generic Error Occurred", true);
    }

    /**
     * Throws {@link ServiceException} encapsulating the response status.
     *
     * @param status
     *          The Response status.
     * @param entity
     *          The response entity containing the error response body.
     * @throws ServiceException
     *          Throws {@link ServiceException} encapsulating the response status.
     */
    default void generalExceptionHandling(int status, HttpEntity entity) throws ServiceException {
        LOGGER.error("External API Service call, generic error occurred: {}", status);

        if (Objects.nonNull(entity)) {
            String content = null;
            ContentType contentType = null;

            try {
                content = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                contentType = ContentType.getOrDefault(entity);
            } catch (final Exception e) {
                LOGGER.error("Unable to parse response body for service error handling", e);
            }

            if (StringUtils.isNotBlank(content)) {
                final ResponseBody responseBody = new ResponseBody();

                responseBody.setContent(content);
                Optional.ofNullable(contentType).map(ContentType::getMimeType).ifPresent(responseBody::setContentType);
                throw new ServiceException(Integer.toString(status), responseBody, true);
            }
        }

        generalExceptionHandling(status);
    }

}
