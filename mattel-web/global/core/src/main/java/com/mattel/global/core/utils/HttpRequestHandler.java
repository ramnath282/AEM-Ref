package com.mattel.global.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Generic request handler for GET, POST, PUT, DELETE operations
 */
public class HttpRequestHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestHandler.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private HttpRequestHandler() {
    }

    /**
     *
     * @param endPoint
     * @param requestHeaders
     * @return
     * @throws IOException
     */
    public static HttpResponse get(final HttpClient httpClient, final String endPoint,
        final Map<String, String> requestHeaders, final HttpClientContext httpClientContext)
        throws IOException {
        final HttpGet getMethod = new HttpGet(endPoint.trim());
        HttpResponse httpResponse;

        HttpRequestHandler.LOGGER.debug("Invoking GET service: {}", endPoint);

        if (null != requestHeaders && !requestHeaders.isEmpty()) {
            requestHeaders.entrySet().stream()
                .forEach(entry -> getMethod.addHeader(entry.getKey(), entry.getValue()));
        }

        final long wcsExecutionStartTime = System.currentTimeMillis();

        HttpRequestHandler.LOGGER.debug("Get request execution start time: {}", wcsExecutionStartTime);

        if (null != httpClientContext) {
            httpResponse = httpClient.execute(getMethod, httpClientContext);
        } else {
            httpResponse = httpClient.execute(getMethod);
        }

        final long wcsExecutionEndTime = System.currentTimeMillis();
        HttpRequestHandler.LOGGER.debug("Get request execution end time: {}", wcsExecutionEndTime);
        HttpRequestHandler.LOGGER.debug("Get request execution time: {}",
            wcsExecutionEndTime - wcsExecutionStartTime);
        return httpResponse;
    }

    /**
     * Overloaded method.
     *
     * @param endPoint
     * @param requestHeaders
     * @param requestParameters
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static HttpResponse get(final HttpClient httpClient, final String endPoint,
        final Map<String, String> requestHeaders, final Map<String, String> requestParameters,
        final HttpClientContext httpClientContext) throws IOException, URISyntaxException {
        final URIBuilder builder = new URIBuilder(endPoint.trim());

        if (null != requestParameters && !requestParameters.isEmpty()) {
            requestParameters.entrySet().stream()
                .forEach(entry -> builder.setParameter(entry.getKey(), entry.getValue()));
        }

        return HttpRequestHandler.get(httpClient, builder.build().toString(),
            requestHeaders, httpClientContext);
    }

    /**
     *
     * @param endPoint
     * @param requestHeaders
     * @param requestEntity
     * @return
     * @throws IOException
     */
    public static HttpResponse post(final HttpClient httpClient, final String endPoint,
        final Map<String, String> requestHeaders, final String requestEntity,
        final HttpClientContext httpClientContext) throws IOException {
        final HttpPost postMethod = new HttpPost(endPoint.trim());
        HttpResponse httpResponse;

        HttpRequestHandler.LOGGER.debug("Invoking POST service: {}", endPoint);

        if (null != requestHeaders && !requestHeaders.isEmpty()) {
            requestHeaders.entrySet().stream()
                .forEach(entry -> postMethod.addHeader(entry.getKey(), entry.getValue()));
        }

        if (null != requestEntity && !requestEntity.isEmpty()) {
            postMethod.setEntity(new StringEntity(requestEntity, "UTF-8"));
        }

        final long wcsExecutionStartTime = System.currentTimeMillis();

        if (null != httpClientContext) {
            httpResponse = httpClient.execute(postMethod, httpClientContext);
        } else {
            httpResponse = httpClient.execute(postMethod);
        }

        final long wcsExecutionEndTime = System.currentTimeMillis();
        HttpRequestHandler.LOGGER.debug("Post request execution time: {}",
            wcsExecutionEndTime - wcsExecutionStartTime);
        return httpResponse;
    }

    /**
     *
     * @param endPoint
     * @param requestHeaders
     * @param requestEntity
     * @return
     * @throws IOException
     */
    public static HttpResponse post(final HttpClient httpClient, final String endPoint,
        final Map<String, String> requestHeaders, final Object requestEntity,
        final HttpClientContext httpClientContext) throws IOException {
        return HttpRequestHandler.post(httpClient, endPoint, requestHeaders,
            HttpRequestHandler.MAPPER.writeValueAsString(requestEntity), httpClientContext);
    }

    /**
     * Build {@link HttpClientContext}
     *
     * @return The {@link HttpClientContext} instance.
     */
    public static HttpClientContext buildHttpContext() {
        final HttpClientContext context = HttpClientContext.create();
        return context;
    }

    /**
     * To create custom instance of {@link CloseableHttpClient} object. Here we have disabled
     * automatic request recovery and re-execution.
     *
     * @param connectionTimeout
     *          Determines the timeout in milliseconds until a connection is established. A timeout
     *          value of zero is interpreted as an infinite timeout.
     *          <p>
     *          A timeout value of zero is interpreted as an infinite timeout. A negative value is
     *          interpreted as undefined (system default).
     *          </p>
     *          <p>
     *          Default: {@code -1}
     *          </p>
     * @return The {@link CloseableHttpClient} instance
     */
    public static CloseableHttpClient createCustom(int connectionTimeout) {
        final RequestConfig config = RequestConfig.custom().setConnectTimeout(connectionTimeout)
            .build();

        return HttpClientBuilder.create().setDefaultRequestConfig(config).disableAutomaticRetries()
            .build();
    }
}