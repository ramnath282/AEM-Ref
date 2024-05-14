package com.mattel.global.core.servlets;

import com.mattel.global.core.constants.Constant;
import com.mattel.global.core.pojo.ResponseBody;
import com.mattel.global.core.services.ConsumerProxyService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component(service = Servlet.class, immediate = true, property = {
    Constants.SERVICE_DESCRIPTION + "=Consumer API Servlet",
    "sling.servlet.methods=" + HttpConstants.METHOD_POST,
    "sling.servlet.paths=" + "/bin/consumerinfohandler" })
public class ConsumerInfoHandler extends SlingAllMethodsServlet {
    private static final String ORIGIN_HEADER = "Origin";
    private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerInfoHandler.class);
    @Reference
    private transient ConsumerProxyService consumerProxyService;

    /**
     * This method initiates the service call for GET method
     * @param request
     *          The {@link SlingHttpServletRequest request} object.
     * @param response
     *          The {@link SlingHttpServletResponse} instance.
     */

    @Override
    protected void doGet(final SlingHttpServletRequest request,
        final SlingHttpServletResponse response) throws IOException {
        ConsumerInfoHandler.LOGGER.info("Get Method Called");

        final String selectorString = request.getRequestPathInfo().getSelectorString();
        ConsumerInfoHandler.LOGGER.debug("Selector String is {}",selectorString);
        final Map<String, Object> requestMap = new HashMap<>();
       	ConsumerInfoHandler.LOGGER.debug("email address in request header {}", request.getHeader(Constant.EMAIL_ADDRESS));
        final String emailAddress = request.getHeader(Constant.EMAIL_ADDRESS);
        if(StringUtils.isNotEmpty(emailAddress)) {
            requestMap.put(Constant.EMAIL_ADDRESS, emailAddress);
        }
        else {
            requestMap.put(Constant.EMAIL_ADDRESS, "");
        }        
		requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_GET);
        ConsumerInfoHandler.LOGGER.debug("Request map in handler {} ", requestMap);
        final Map<String, Object> responseMap = consumerProxyService.makeServiceCalls(requestMap, selectorString);
        ConsumerInfoHandler.LOGGER.debug("final response Map is {}", responseMap);
        setOriginHeader(request, response);
        writeResponse(responseMap, response);
    }

    /**
     * This method initiates the service call for POST method
     * @param request
     *          The {@link SlingHttpServletRequest request} object.
     * @param response
     *          The {@link SlingHttpServletResponse} instance.
     */

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
        throws IOException {
        ConsumerInfoHandler.LOGGER.info("Post Method called");
        final String selectorString = request.getRequestPathInfo().getSelectorString();
        ConsumerInfoHandler.LOGGER.debug("Selector String is {}",selectorString);
        final long startTime = System.currentTimeMillis();

        final Map<String, Object> requestMap = new HashMap<>();
        final BufferedReader bufferedReader = request.getReader();
        final String requestBody = IOUtils.toString(bufferedReader);
        requestMap.put(Constant.REQUEST_BODY, requestBody);
        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_POST);

        final Map<String, Object> responseMap = consumerProxyService.makeServiceCalls(requestMap, selectorString);
        setOriginHeader(request, response);
        writeResponse(responseMap, response);
        final long endTime = System.currentTimeMillis();
        ConsumerInfoHandler.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "ConsumerInfoHandler",
            endTime-startTime);

    }

    /**
     * This method sets the origin headers
     * @param request
     *          The {@link SlingHttpServletRequest request} object.
     * @param response
     *          The {@link SlingHttpServletResponse} instance.
     */
    private void setOriginHeader(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        ConsumerInfoHandler.LOGGER.debug("Http request origin header: {}, setting in response headers",
            request.getHeader(ConsumerInfoHandler.ORIGIN_HEADER));

        if (Objects.nonNull(request.getHeader(ConsumerInfoHandler.ORIGIN_HEADER))) {
            response.setHeader(ConsumerInfoHandler.ACCESS_CONTROL_ALLOW_ORIGIN,
                request.getHeader(ConsumerInfoHandler.ORIGIN_HEADER));
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
                response.setContentType("text/plain");
                response.setStatus(httpStatus);
                response.getWriter().write(Optional.ofNullable(responseMap.get(Constant.SERVICE_ERROR_MESSAGE)).map(Object::toString)
                    .orElse("Generic Error Occurred"));
            }
        } else {
            response.setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
            final Object respBody = responseMap.get(Constant.RESPONSE_BODY);

            if (responseMap.containsKey(Constant.RESPONSE_BODY) && null != respBody) {
                ConsumerInfoHandler.LOGGER.debug("Final Servlet response {}", respBody);
                response.getWriter().write(respBody.toString());
            }
        }
    }

}
