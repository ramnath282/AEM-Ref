package com.mattel.ecomm.core.servlets;

import com.mattel.ecomm.core.services.ProxyService;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.pojos.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.Servlet;
import javax.servlet.http.Cookie;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = Servlet.class, immediate = true, property = {
    Constants.SERVICE_DESCRIPTION + "=Wcs Api Servlet",
    "sling.servlet.methods=" + HttpConstants.METHOD_POST,
    "sling.servlet.paths=" + "/bin/requesthandler" })
public class EcommRequestHandler extends SlingAllMethodsServlet {
  private static final String ORIGIN_HEADER = "Origin";
  private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(EcommRequestHandler.class);
  @Reference
  private transient ProxyService proxyService;

  @SuppressWarnings("unchecked")
  @Override
  protected void doGet(final SlingHttpServletRequest request,
      final SlingHttpServletResponse response) throws IOException {
    EcommRequestHandler.LOGGER.info("Get MEthod called");
    final String[] selectors = request.getRequestPathInfo().getSelectors();
    EcommRequestHandler.LOGGER.debug("WEB Selector executed");
    final Map<String, Object> requestMap = new HashMap<>();
    final String storeKey = request.getParameter(Constant.REQUEST_STOREID);
    final String domainKey = request.getParameter(Constant.REQUEST_DOMAIN_ID);
    final Cookie[] cookies = request.getCookies();
    requestMap.put(Constant.REQUEST_COOKIES_KEY, cookies);
    requestMap.put(Constant.STORE_KEY, storeKey);
    requestMap.put(Constant.DOMAIN_KEY, domainKey);
    requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_GET);
    if (request.getParameterMap().containsKey(Constant.PART_NUMBER)) {
      requestMap.put(Constant.PART_NUMBER, request.getParameter(Constant.PART_NUMBER));
    }
    requestMap.putAll(buildGetRequestMap(request));
    final Map<String, Object> responseMap = proxyService.makeServiceCalls(requestMap, selectors);
    final List<Cookie> cookieList = (List<Cookie>) responseMap.get(Constant.RESPONSE_COOKIES_KEY);

    setOriginHeader(request, response);

    if (null != cookieList && !cookieList.isEmpty()) {
      cookieList.forEach(response::addCookie);
    }

    writeResponse(responseMap, response);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void doPost(final SlingHttpServletRequest request,
      final SlingHttpServletResponse response) throws IOException {
    EcommRequestHandler.LOGGER.info("POST Method Called");
    final String[] selectors = request.getRequestPathInfo().getSelectors();
    final long startTime = System.currentTimeMillis();
    Map<String, Object> responseMap = null;
    final BufferedReader bufferedReader = request.getReader();
    final String storeKey = request.getParameter(Constant.REQUEST_STOREID);
    EcommRequestHandler.LOGGER.debug("Store key Id from UI is {}", storeKey);
    final String domainKey = request.getParameter(Constant.REQUEST_DOMAIN_ID);
    final String requestBody = IOUtils.toString(bufferedReader);
    final Map<String, Object> requestMap = buildRequestMap(request, storeKey, domainKey,
        requestBody);
    requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_POST);
    responseMap = proxyService.makeServiceCalls(requestMap, selectors);
    final List<Cookie> cookieList = (List<Cookie>) responseMap.get(Constant.RESPONSE_COOKIES_KEY);

    setOriginHeader(request, response);

    if (null != cookieList && !cookieList.isEmpty()) {
      cookieList.forEach(response::addCookie);
    }
    
    final Map<String,String> responseHeaders = (Map<String, String>) responseMap.get(Constant.RESPONSE_HEADERS);
    if(Objects.nonNull(responseHeaders) && !responseHeaders.isEmpty()){
    	 setServiceResponseHeaders(responseHeaders,response);
    }
    
    writeResponse(responseMap, response);

    final long endTime = System.currentTimeMillis();
    EcommRequestHandler.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "RequestHandler",
        endTime - startTime);
  }

  /**
   * This method reads the response headers from response maps sets those
   * values in SlingHttpServletResponse
   * @param responseHeaders map of response headers
   * @param response SlingHttpServletResponse
   */
  private void setServiceResponseHeaders(Map<String, String> responseHeaders, SlingHttpServletResponse response) {
	LOGGER.info("Start of setServiceResponseHeaders method");
	for (Map.Entry<String,String> entry : responseHeaders.entrySet())
		response.setHeader(entry.getKey(), entry.getValue());
	LOGGER.info("End of setServiceResponseHeaders method");
  }

/**
   * To build the requestMap containing request details such as request body.
   *
   * @param request
   *          The {@link SlingHttpServletRequest request} object.
   * @param storeKey
   *          The store key.
   * @param domainKey
   *          The domain key.
   * @param requestBody
   *          The request body encapsulation json.
   * @return The requestMap.
   */
  private Map<String, Object> buildRequestMap(final SlingHttpServletRequest request,
      final String storeKey, final String domainKey, final String requestBody) {
    final Map<String, Object> requestMap = new HashMap<>();
    final Cookie[] cookies = request.getCookies();

    requestMap.put(Constant.REQUEST_BODY, requestBody);
    requestMap.put(Constant.STORE_KEY, storeKey);
    requestMap.put(Constant.DOMAIN_KEY, domainKey);

    requestMap.put(Constant.REQUEST_COOKIES_KEY, cookies);
    return requestMap;
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void doPut(SlingHttpServletRequest request, SlingHttpServletResponse response)
      throws IOException {
    EcommRequestHandler.LOGGER.info("PUT Method Called");
    final long startTime = System.currentTimeMillis();
    final String[] selectors = request.getRequestPathInfo().getSelectors();
    final BufferedReader body = request.getReader();
    final String storeKey = request.getParameter(Constant.REQUEST_STOREID);
    EcommRequestHandler.LOGGER.debug("Store key Id from UI is {}", storeKey);
    final String domainKey = request.getParameter(Constant.REQUEST_DOMAIN_ID);
    final String requestBody = IOUtils.toString(body);
    final Map<String, Object> requestMap = buildRequestMap(request, storeKey, domainKey,
        requestBody);

    requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_PUT);
    final Map<String, Object> responseMap = proxyService.makeServiceCalls(requestMap, selectors);
    final List<Cookie> respCookieList = (List<Cookie>) responseMap
        .get(Constant.RESPONSE_COOKIES_KEY);

    setOriginHeader(request, response);

    if (null != respCookieList) {
      respCookieList.forEach(response::addCookie);
    }
    
    writeResponse(responseMap, response);
    
    final long endTime = System.currentTimeMillis();
    EcommRequestHandler.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "RequestHandler",
        endTime - startTime);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void doDelete(final SlingHttpServletRequest request,
      final SlingHttpServletResponse response) throws IOException {
    EcommRequestHandler.LOGGER.info("Delete Method called");

    final String[] selectors = request.getRequestPathInfo().getSelectors();
    EcommRequestHandler.LOGGER.debug("WEB Selector executed");

    final Map<String, Object> requestMap = new HashMap<>();
    final String storeKey = request.getParameter(Constant.REQUEST_STOREID);
    final String domainKey = request.getParameter(Constant.REQUEST_DOMAIN_ID);
    String orderId = StringUtils.EMPTY;
    final BufferedReader bufferedReader = request.getReader();

    if (null != bufferedReader) {
      requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(bufferedReader));
    }

    requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_DELETE);

    if (request.getParameterMap().containsKey(Constant.REQUEST_ORDERID)) {
      orderId = request.getParameter(Constant.REQUEST_ORDERID);
    }

    final Cookie[] cookies = request.getCookies();
    requestMap.put(Constant.REQUEST_COOKIES_KEY, cookies);
    requestMap.put(Constant.STORE_KEY, storeKey);
    requestMap.put(Constant.DOMAIN_KEY, domainKey);
    requestMap.put(Constant.REQUEST_ORDERID, orderId);

    // Add all the parameters coming in the request
    requestMap.putAll(buildGetRequestMap(request));

    final Map<String, Object> responseMap = proxyService.makeServiceCalls(requestMap, selectors);
    final List<Cookie> cookieList = (List<Cookie>) responseMap.get(Constant.RESPONSE_COOKIES_KEY);

    setOriginHeader(request, response);

    if (null != cookieList && !cookieList.isEmpty()) {
      cookieList.forEach(response::addCookie);
    }

    writeResponse(responseMap, response);
  }

  /**
   * To build {@link Map} containing details of all the
   * {@link SlingHttpServletRequest#getParameterNames() request parameters}.
   *
   * @param request
   *          The {@link SlingHttpServletRequest} object.
   * @return The {@link Map} containing parameter name to parameter value mapping.
   */
  private Map<String, Object> buildGetRequestMap(SlingHttpServletRequest request) {
    final Map<String, Object> requestMap = new HashMap<>();

    Optional.ofNullable(request.getParameterNames()).ifPresent(names -> {
      while (names.hasMoreElements()) {
        final String key = (String) names.nextElement();
        final String val = request.getParameter(key);

        requestMap.put(key, val);
      }
    });
    return requestMap;
  }

  private void setOriginHeader(SlingHttpServletRequest request, SlingHttpServletResponse response) {
    EcommRequestHandler.LOGGER.debug("Http request origin header: {}, setting in response headers",
        request.getHeader(EcommRequestHandler.ORIGIN_HEADER));

    if (Objects.nonNull(request.getHeader(EcommRequestHandler.ORIGIN_HEADER))) {
      response.setHeader(EcommRequestHandler.ACCESS_CONTROL_ALLOW_ORIGIN,
          request.getHeader(EcommRequestHandler.ORIGIN_HEADER));
    }
  }
  

  /**
   * Write response for the incoming request.
   *
   * @param responseMap
   *          The {@link Map} containing response data.
   * @param response
   *          The {@link SlingHttpServletResponse} instance.
   * @throws IOException
   */
  public void writeResponse(final Map<String, Object> responseMap,
      SlingHttpServletResponse response) throws IOException {
    final boolean isPropogateError = (Boolean) responseMap
        .getOrDefault(Constant.PROPAGATE_SERVICE_ERRORS, false);

    if (isPropogateError) {
      final int httpStatus = Integer.parseInt(responseMap.getOrDefault(Constant.ERROR_HTTP_STATUS,
          String.valueOf(HttpURLConnection.HTTP_INTERNAL_ERROR)).toString());
      final Object errorResponseBodyObj =  responseMap
          .get(Constant.SERVICE_ERROR_BODY);

      if (Objects.nonNull(errorResponseBodyObj)) {
        final ResponseBody errorResponseBody = (ResponseBody) errorResponseBodyObj;
        
        response.setContentType(errorResponseBody.getContentType());
        response.setStatus(httpStatus);
        response.getWriter().write(errorResponseBody.getContent());
      } else {
        // Propagate the wcs error code to caller.
        response.setContentType("text/plain");
        response.setStatus(httpStatus);
        response.getWriter().write(Optional.ofNullable(responseMap.get(Constant.SERVICE_ERROR_MESSAGE)).map(Object::toString)
              .orElse("Generic Error Occurred"));
      }
    } else {
      response.setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
      final Object respBody = responseMap.get(Constant.RESPONSE_BODY);

      if (responseMap.containsKey(Constant.RESPONSE_BODY) && null != respBody) {
        EcommRequestHandler.LOGGER.debug("Final Servlet response {}", respBody);
        response.getWriter().write(respBody.toString());
      }
    }
  }
}
