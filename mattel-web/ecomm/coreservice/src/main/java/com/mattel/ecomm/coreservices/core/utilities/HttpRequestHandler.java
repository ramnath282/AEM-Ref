package com.mattel.ecomm.coreservices.core.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generic request handler for GET, POST, PUT, DELETE operations
 */
public class HttpRequestHandler {
  private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestHandler.class);
  private static final String COOKIE_HEADER_NAME = "Cookie";
  private static final ObjectMapper MAPPER = new ObjectMapper();

  private HttpRequestHandler() {
  }

  /**
   *
   * @param endPoint
   * @param requestCookies
   * @param requestHeaders
   * @return
   * @throws IOException
   */
  public static HttpResponse get(final HttpClient httpClient, final String endPoint,
      final List<String> requestCookies, final Map<String, String> requestHeaders,
      final HttpClientContext httpClientContext) throws IOException {
    final HttpGet getMethod = new HttpGet(endPoint.trim());
    HttpResponse httpResponse;

    HttpRequestHandler.LOGGER.debug("Invoking GET service: {}", endPoint);

    if (null != requestCookies && !requestCookies.isEmpty()) {
      requestCookies.forEach(reqCookie -> {
        getMethod.addHeader(HttpRequestHandler.COOKIE_HEADER_NAME, reqCookie);
        HttpRequestHandler.LOGGER
            .debug("String value of Cookie object added in request header is {}", reqCookie);
      });
    }

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
   * @param requestCookies
   * @param requestHeaders
   * @param requestParameters
   * @return
   * @throws IOException
   * @throws URISyntaxException
   */
  public static HttpResponse get(final HttpClient httpClient, final String endPoint,
      final List<String> requestCookies, final Map<String, String> requestHeaders,
      final Map<String, String> requestParameters, final HttpClientContext httpClientContext)
      throws IOException, URISyntaxException {
    final URIBuilder builder = new URIBuilder(endPoint.trim());

    if (null != requestParameters && !requestParameters.isEmpty()) {
      requestParameters.entrySet().stream()
          .forEach(entry -> builder.setParameter(entry.getKey(), entry.getValue()));
    }

    return HttpRequestHandler.get(httpClient, builder.build().toString(), requestCookies,
        requestHeaders, httpClientContext);
  }

  /**
   *
   * @param endPoint
   * @param requestCookies
   * @param requestHeaders
   * @param requestEntity
   * @return
   * @throws IOException
   */
  public static HttpResponse post(final HttpClient httpClient, final String endPoint,
      final List<String> requestCookies, final Map<String, String> requestHeaders,
      final String requestEntity, final HttpClientContext httpClientContext) throws IOException {
    final HttpPost postMethod = new HttpPost(endPoint.trim());
    HttpResponse httpResponse;

    HttpRequestHandler.LOGGER.debug("Invoking POST service: {}", endPoint);

    if (null != requestCookies && !requestCookies.isEmpty()) {
      requestCookies.forEach(reqCookie -> {
        postMethod.addHeader(HttpRequestHandler.COOKIE_HEADER_NAME, reqCookie);
        HttpRequestHandler.LOGGER
            .debug("String value of Cookie object added in request header is: {}", reqCookie);
      });
    }

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
   * @param requestCookies
   * @param requestHeaders
   * @param requestEntity
   * @return
   * @throws IOException
   */
  public static HttpResponse post(final HttpClient httpClient, final String endPoint,
      final List<String> requestCookies, final Map<String, String> requestHeaders,
      final Object requestEntity, final HttpClientContext httpClientContext) throws IOException {
    return HttpRequestHandler.post(httpClient, endPoint, requestCookies, requestHeaders,
        HttpRequestHandler.MAPPER.writeValueAsString(requestEntity), httpClientContext);
  }

  /**
   *
   * @param endPoint
   * @param requestCookies
   * @param requestHeaders
   * @param requestEntity
   * @return
   * @throws IOException
   */
  public static HttpResponse put(final HttpClient httpClient, final String endPoint,
      final List<String> requestCookies, final Map<String, String> requestHeaders,
      final String requestEntity, final HttpClientContext httpClientContext) throws IOException {
    final HttpPut putMethod = new HttpPut(endPoint.trim());
    HttpResponse httpResponse;

    HttpRequestHandler.LOGGER.debug("Invoking PUT service: {}", endPoint);

    if (null != requestCookies && !requestCookies.isEmpty()) {
      requestCookies.forEach(reqCookie -> {
        putMethod.addHeader(HttpRequestHandler.COOKIE_HEADER_NAME, reqCookie);
        HttpRequestHandler.LOGGER
            .debug("String value of Cookie object added in request header is: {}", reqCookie);
      });
    }

    if (null != requestHeaders && !requestHeaders.isEmpty()) {
      requestHeaders.entrySet().stream()
          .forEach(entry -> putMethod.addHeader(entry.getKey(), entry.getValue()));
    }

    if (null != requestEntity && !requestEntity.isEmpty()) {
      putMethod.setEntity(new StringEntity(requestEntity));
    }

    final long wcsExecutionStartTime = System.currentTimeMillis();

    if (null != httpClientContext) {
      httpResponse = httpClient.execute(putMethod, httpClientContext);
    } else {
      httpResponse = httpClient.execute(putMethod);
    }

    final long wcsExecutionEndTime = System.currentTimeMillis();
    HttpRequestHandler.LOGGER.debug("Put request execution time: {}",
        wcsExecutionEndTime - wcsExecutionStartTime);
    return httpResponse;
  }

  /**
   *
   * @param endPoint
   * @param requestCookies
   * @param requestHeaders
   * @param requestEntity
   * @return
   * @throws IOException
   */
  public static HttpResponse put(final HttpClient httpClient, final String endPoint,
      final List<String> requestCookies, final Map<String, String> requestHeaders,
      final Object requestEntity, final HttpClientContext httpClientContext) throws IOException {
    return HttpRequestHandler.put(httpClient, endPoint, requestCookies, requestHeaders,
        HttpRequestHandler.MAPPER.writeValueAsString(requestEntity), httpClientContext);
  }

  /**
   *
   * @param httpClient
   * @param endPoint
   * @param requestCookies
   * @param requestHeaders
   * @return
   * @throws IOException
   */
  public static HttpResponse delete(final HttpClient httpClient, final String endPoint,
      final List<String> requestCookies, final Map<String, String> requestHeaders,
      final HttpClientContext httpClientContext) throws IOException {
    final HttpDelete deleteMethod = new HttpDelete(endPoint.trim());
    HttpResponse httpResponse;

    HttpRequestHandler.LOGGER.debug("Invoking DELETE service: {}", endPoint);

    if (null != requestCookies && !requestCookies.isEmpty()) {
      requestCookies.forEach(reqCookie -> {
        deleteMethod.addHeader(HttpRequestHandler.COOKIE_HEADER_NAME, reqCookie);
        HttpRequestHandler.LOGGER
            .debug("String value of Cookie object added in request header is {}", reqCookie);
      });
    }

    if (null != requestHeaders && !requestHeaders.isEmpty()) {
      requestHeaders.entrySet().stream()
          .forEach(entry -> deleteMethod.addHeader(entry.getKey(), entry.getValue()));
    }

    final long wcsExecutionStartTime = System.currentTimeMillis();

    if (null != httpClientContext) {
      httpResponse = httpClient.execute(deleteMethod, httpClientContext);
    } else {
      httpResponse = httpClient.execute(deleteMethod);
    }

    final long wcsExecutionEndTime = System.currentTimeMillis();
    HttpRequestHandler.LOGGER.debug("Delete request execution time: {}",
        wcsExecutionEndTime - wcsExecutionStartTime);
    return httpResponse;
  }

  /**
   *
   * @param httpClient
   * @param endPoint
   * @param requestCookies
   * @param requestHeaders
   * @param requestParameters
   * @return
   * @throws IOException
   * @throws URISyntaxException
   */
  public static HttpResponse delete(final HttpClient httpClient, final String endPoint,
      final List<String> requestCookies, final Map<String, String> requestHeaders,
      final Map<String, String> requestParameters, final HttpClientContext httpClientContext)
      throws IOException, URISyntaxException {
    final URIBuilder builder = new URIBuilder(endPoint.trim());

    if (null != requestParameters && !requestParameters.isEmpty()) {
      requestParameters.entrySet().stream()
          .forEach(entry -> builder.setParameter(entry.getKey(), entry.getValue()));
    }

    return HttpRequestHandler.delete(httpClient, builder.build().toString(), requestCookies,
        requestHeaders, httpClientContext);
  }

  /**
   * Build {@link HttpClientContext} for storing response cookie details.
   *
   * @return The {@link HttpClientContext} instance.
   */
  public static HttpClientContext buildHttpContext() {
    final HttpClientContext context = HttpClientContext.create();
    final BasicCookieStore cookieStore = new BasicCookieStore();

    context.setCookieStore(cookieStore);
    return context;
  }

  /**
   * To create default instance of {@link CloseableHttpClient} object.
   *
   * @return The {@link CloseableHttpClient} instance
   */
  public static CloseableHttpClient createDefault() {
    return HttpClients.createDefault();
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
   * @param readTimeout
   *          Defines the socket/read timeout in milliseconds, which is the timeout for waiting for
   *          data or, put differently, a maximum period inactivity between two consecutive data
   *          packets).
   *          <p>
   *          A timeout value of zero is interpreted as an infinite timeout. A negative value is
   *          interpreted as undefined (system default).
   *          </p>
   *          <p>
   *          Default: {@code -1}
   *          </p>
   * @return The {@link CloseableHttpClient} instance
   */
  public static CloseableHttpClient createCustom(int connectionTimeout, int readTimeout) {
    final RequestConfig config = RequestConfig.custom().setConnectTimeout(connectionTimeout)
        .setSocketTimeout(readTimeout).build();

    return HttpClientBuilder.create().setDefaultRequestConfig(config).disableAutomaticRetries()
        .build();
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
