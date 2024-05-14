package com.mattel.ecomm.coreservices.core.interfaces;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattel.ecomm.coreservices.core.constants.BaseServiceLogger;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.ResponseBody;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@FunctionalInterface
public interface BaseService {
  BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap)
      throws ServiceException;

  default JSONObject getResponseValueAsJson(Object pojoClass) throws ServiceException {
    final ObjectMapper objectMapper = new ObjectMapper();
    try {
      final String json = objectMapper.writeValueAsString(pojoClass);
      return new JSONObject(json);
    } catch (final JsonParseException je) {
      BaseServiceLogger.LOG.error(Constant.SERVICE_RESP_ERROR, je);
      throw new ServiceException("1002", "Unable to parse Response JSON");
    } catch (final JsonMappingException jme) {
      BaseServiceLogger.LOG.error(Constant.SERVICE_RESP_ERROR, jme);
      throw new ServiceException("1003", "Mapping Invalid of JSON response");
    } catch (final IOException io) {
      BaseServiceLogger.LOG.error(Constant.SERVICE_RESP_ERROR, io);
      throw new ServiceException("1001", "IO Exception");
    } catch (final JSONException je) {
      BaseServiceLogger.LOG.error(Constant.SERVICE_RESP_ERROR, je);
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
   * Throws {@link ServiceException} encapsulating the response status.
   *
   * @param status
   *          The Response status.
   * @throws ServiceException
   *           Throws {@link ServiceException} encapsulating the response status.
   */
  default void generalExceptionHandling(int status) throws ServiceException {
    BaseServiceLogger.LOG.error("WCS Services, generic error occurred: {}", status);
    throw new ServiceException(Integer.toString(status), "Generic Error Ocuured", true);
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
    BaseServiceLogger.LOG.error("External API Service call, generic error occurred: {}", status);

    if (Objects.nonNull(entity)) {
      String content = null;
      ContentType contentType = null;
      
      try {
        content = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        contentType = ContentType.getOrDefault(entity);
      } catch (final Exception e) {
        BaseServiceLogger.LOG.error("Unable to parse response body for service error handling", e);
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
    BaseServiceLogger.LOG.error("WCS Services, generic error occurred: {}, response : {}", status,
        response);
    throw new ServiceException(Integer.toString(status), "Generic Error Ocuured", true);
  }

  /**
   * Method to add langId to request url.
   *
   * @param requestMap
   *          {@link Map} containing langId import parameters.
   * @param endPointUrl
   *          Request url to append to.
   * @return The updated request url.
   */
  default String addLangIdToRequestUri(final Map<String, Object> requestMap,
      final String endPointUrl) {
    String langId = (String) requestMap.get(Constant.LANG_ID);

    if (StringUtils.isBlank(langId)) {
      langId = Constant.LANG_ID_DEFAULT_VAL;
    }

    return endPointUrl.replaceAll(Constant.LANG_ID_PLACEHOLDER, langId);
  }

  /**
   * Fetch default connection timeout setting from property service.
   *
   * @param pr
   *          The {@link PropertyReaderService}.
   * @param clientkey
   *          The client specific unique key.
   * @return The default connection timeout value.
   */
  default Integer getDefaultConnectTimeout(final PropertyReaderService pr, final String clientkey) {
    return pr.getHttpServiceDefConnectTimeout(clientkey);
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
   * To check and log service errors.
   *
   * @param br
   *          The service response.
   */
  default void logServiceErrors(BaseResponse br) {
    Optional.ofNullable(br).filter(x -> Objects.nonNull(x.getErrors())).map(BaseResponse::getErrors)
        .filter(y -> !y.isEmpty()).ifPresent(e -> BaseServiceLogger.LOG
            .error("Encountered service failures, error response json: {}", e));
  }
  
  /**
   * Check for service errors.
   *
   * @param br
   *          The service response.
   * @return True, if there is a service error.
   */
  default boolean isServiceError(BaseResponse br) {
    return Optional.ofNullable(br).filter(x -> Objects.nonNull(x.getErrors()))
        .filter(x -> !x.getErrors().isEmpty()).isPresent();
  }

  /**
   * @param requestMap
   * @param dataMap
   */
  default void mapDomain(final Map<String, Object> dataMap, final String domain) {
    dataMap.put("domain", domain);
  }

  /**
   * @param requestMap
   * @param dataMap
   */
  default void mapRequestCookies(final Map<String, Object> requestMap,
      final Map<String, Object> dataMap) {
    final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
    final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
    final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects,
        cookieNames);

    dataMap.put(Constant.VALID_COOKIE_NAMES_KEY, cookieNames);
    dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
  }

  default void mapCommonRequestVariables(final Map<String, Object> requestMap,
      final Map<String, Object> dataMap, String domain) {
    mapDomain(dataMap, domain);
    mapRequestCookies(requestMap, dataMap);
  }

  /**
   * @param dataMap
   * @param httpClientContext
   * @return
   */
  default List<Cookie> fetchResponsCookies(Map<String, Object> dataMap,
      final HttpClientContext httpClientContext) {
    final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
    final String domain = (String) dataMap.get("domain");
    return CookieUtils.constructCookieList(cookieNames, domain,
        httpClientContext.getCookieStore().getCookies());
  }
}