package com.mattel.ecomm.coreservices.core.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

public class HttpRequestHandlerTest {
  @Test
  public void testGetHttpClientStringListOfStringMapOfStringStringHttpClientContext1()
      throws ClientProtocolException, IOException {
    final String endPointUrl = "http://localhost:9433/test";
    final HttpClient httpClient = Mockito.mock(HttpClient.class);
    final HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
    final ArgumentCaptor<HttpGet> argumentCaptor = ArgumentCaptor.forClass(HttpGet.class);

    Mockito.when(httpClient.execute(ArgumentMatchers.any(HttpGet.class))).thenReturn(httpResponse);
    HttpRequestHandler.get(httpClient, endPointUrl, null, null, null);
    Mockito.verify(httpClient).execute(argumentCaptor.capture());
    Assert.assertEquals(endPointUrl, argumentCaptor.getValue().getURI().toString());
  }

  @Test
  public void testGetHttpClientStringListOfStringMapOfStringStringHttpClientContext2()
      throws ClientProtocolException, IOException {
    final String endPointUrl = "http://localhost:9433/test";
    final List<String> cookies = Arrays.asList("JSESSIONID=0000Dch-UevJAP6hq7Juda6be7F:1b7o43dnq",
        "WC_SESSION_ESTABLISHED=true",
        "WC_PERSISTENT=kA6%2FJwFUGfMUwswLwcortDevqEU%3D%0A%3B2019-03-28+01%3A59%3A38.031_1552563415165-1_10651",
        "WC_AUTHENTICATION_11450726=11450726%2Ciq3xEXbdEdFZIDrGRgy0MgFhKq0%3D",
        "WC_ACTIVEPOINTER=-1%2C10651",
        "WC_USERACTIVITY_11450726=11450726%2C10651%2C0%2Cnull%2C1553763578033%2C1553765378033%2Cnull%2Cnull%2Cnull%2Cnull%2CWy4MaAIrtdL1bVl2fh%2FCkuOgn");
    final Map<String, String> requestHeaders = new HashMap<>();
    final HttpClient httpClient = Mockito.mock(HttpClient.class);
    final HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
    final ArgumentCaptor<HttpGet> argumentCaptor1 = ArgumentCaptor.forClass(HttpGet.class);
    final ArgumentCaptor<HttpClientContext> argumentCaptor2 = ArgumentCaptor
        .forClass(HttpClientContext.class);
    final HttpClientContext httpClientContext = Mockito.mock(HttpClientContext.class);
    final HttpGet method;
    final Header[] cookieHeaders;

    requestHeaders.put(HttpHeaders.CONTENT_TYPE, "application/json");
    Mockito.when(httpClient.execute(ArgumentMatchers.any(HttpGet.class),
        ArgumentMatchers.eq(httpClientContext))).thenReturn(httpResponse);
    Assert.assertEquals(httpResponse, HttpRequestHandler.get(httpClient, endPointUrl, cookies,
        requestHeaders, httpClientContext));
    Mockito.verify(httpClient).execute(argumentCaptor1.capture(), argumentCaptor2.capture());

    method = argumentCaptor1.getValue();
    Assert.assertEquals(endPointUrl, method.getURI().toString());
    Assert.assertEquals(httpClientContext, argumentCaptor2.getValue());
    Assert.assertTrue(method.containsHeader(HttpHeaders.CONTENT_TYPE));

    cookieHeaders = method.getHeaders("Cookie");

    for (final Header cookie : cookieHeaders) {
      Assert.assertTrue(cookies.contains(cookie.getValue()));
    }
  }

  @Test
  public void testGetHttpClientStringListOfStringMapOfStringStringMapOfStringStringHttpClientContext1()
      throws ClientProtocolException, IOException, URISyntaxException {
    final String endPointUrl = "http://localhost:9433/test";
    final List<String> cookies = Arrays.asList();
    final Map<String, String> urlParams = new HashMap<>();
    final Map<String, String> requestHeaders = new HashMap<>();
    final HttpClient httpClient = Mockito.mock(HttpClient.class);
    final HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
    final ArgumentCaptor<HttpGet> argumentCaptor1 = ArgumentCaptor.forClass(HttpGet.class);
    final ArgumentCaptor<HttpClientContext> argumentCaptor2 = ArgumentCaptor
        .forClass(HttpClientContext.class);
    final HttpClientContext httpClientContext = Mockito.mock(HttpClientContext.class);
    final HttpGet method;

    urlParams.put("param1", "value1");
    urlParams.put("param2", "value2");
    Mockito.when(httpClient.execute(ArgumentMatchers.any(HttpGet.class),
        ArgumentMatchers.eq(httpClientContext))).thenReturn(httpResponse);
    Assert.assertEquals(httpResponse, HttpRequestHandler.get(httpClient, endPointUrl, cookies,
        requestHeaders, urlParams, httpClientContext));
    Mockito.verify(httpClient).execute(argumentCaptor1.capture(), argumentCaptor2.capture());

    method = argumentCaptor1.getValue();
    Assert.assertEquals(
        new StringBuilder(endPointUrl).append("?").append("param1").append("=").append("value1")
            .append("&").append("param2").append("=").append("value2").toString(),
        method.getURI().toString());
    Assert.assertEquals(httpClientContext, argumentCaptor2.getValue());
  }

  @Test
  public void testGetHttpClientStringListOfStringMapOfStringStringMapOfStringStringHttpClientContext2()
      throws ClientProtocolException, IOException, URISyntaxException {
    final String endPointUrl = "http://localhost:9433/test";
    final List<String> cookies = Arrays.asList();
    final Map<String, String> urlParams = new HashMap<>();
    final Map<String, String> requestHeaders = new HashMap<>();
    final HttpClient httpClient = Mockito.mock(HttpClient.class);
    final HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
    final ArgumentCaptor<HttpGet> argumentCaptor1 = ArgumentCaptor.forClass(HttpGet.class);
    final ArgumentCaptor<HttpClientContext> argumentCaptor2 = ArgumentCaptor
        .forClass(HttpClientContext.class);
    final HttpClientContext httpClientContext = Mockito.mock(HttpClientContext.class);
    final HttpGet method;

    Mockito.when(httpClient.execute(ArgumentMatchers.any(HttpGet.class),
        ArgumentMatchers.eq(httpClientContext))).thenReturn(httpResponse);
    Assert.assertEquals(httpResponse, HttpRequestHandler.get(httpClient, endPointUrl, cookies,
        requestHeaders, urlParams, httpClientContext));
    Mockito.verify(httpClient).execute(argumentCaptor1.capture(), argumentCaptor2.capture());

    method = argumentCaptor1.getValue();
    Assert.assertEquals(endPointUrl, method.getURI().toString());
    Assert.assertEquals(httpClientContext, argumentCaptor2.getValue());
  }

  @Test
  public void testGetHttpClientStringListOfStringMapOfStringStringMapOfStringStringHttpClientContext3()
      throws ClientProtocolException, IOException, URISyntaxException {
    final String endPointUrl = "http://localhost:9433/test";
    final List<String> cookies = Arrays.asList();
    final Map<String, String> requestHeaders = new HashMap<>();
    final HttpClient httpClient = Mockito.mock(HttpClient.class);
    final HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
    final ArgumentCaptor<HttpGet> argumentCaptor1 = ArgumentCaptor.forClass(HttpGet.class);
    final ArgumentCaptor<HttpClientContext> argumentCaptor2 = ArgumentCaptor
        .forClass(HttpClientContext.class);
    final HttpClientContext httpClientContext = Mockito.mock(HttpClientContext.class);
    final HttpGet method;

    Mockito.when(httpClient.execute(ArgumentMatchers.any(HttpGet.class),
        ArgumentMatchers.eq(httpClientContext))).thenReturn(httpResponse);
    Assert.assertEquals(httpResponse, HttpRequestHandler.get(httpClient, endPointUrl, cookies,
        requestHeaders, null, httpClientContext));
    Mockito.verify(httpClient).execute(argumentCaptor1.capture(), argumentCaptor2.capture());

    method = argumentCaptor1.getValue();
    Assert.assertEquals(endPointUrl, method.getURI().toString());
    Assert.assertEquals(httpClientContext, argumentCaptor2.getValue());
  }

  @Test
  public void testPostHttpClientStringListOfStringMapOfStringStringStringHttpClientContext()
      throws ClientProtocolException, IOException {
    final String endPointUrl = "http://localhost:9433/test";
    final List<String> cookies = Arrays.asList("JSESSIONID=0000Dch-UevJAP6hq7Juda6be7F:1b7o43dnq",
        "WC_SESSION_ESTABLISHED=true",
        "WC_PERSISTENT=kA6%2FJwFUGfMUwswLwcortDevqEU%3D%0A%3B2019-03-28+01%3A59%3A38.031_1552563415165-1_10651",
        "WC_AUTHENTICATION_11450726=11450726%2Ciq3xEXbdEdFZIDrGRgy0MgFhKq0%3D",
        "WC_ACTIVEPOINTER=-1%2C10651",
        "WC_USERACTIVITY_11450726=11450726%2C10651%2C0%2Cnull%2C1553763578033%2C1553765378033%2Cnull%2Cnull%2Cnull%2Cnull%2CWy4MaAIrtdL1bVl2fh%2FCkuOgn");
    final Map<String, String> requestHeaders = new HashMap<>();
    final HttpClient httpClient = Mockito.mock(HttpClient.class);
    final HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
    final ArgumentCaptor<HttpPost> argumentCaptor1 = ArgumentCaptor.forClass(HttpPost.class);
    final ArgumentCaptor<HttpClientContext> argumentCaptor2 = ArgumentCaptor
        .forClass(HttpClientContext.class);
    final HttpClientContext httpClientContext = Mockito.mock(HttpClientContext.class);
    final BaseResponse requestEntity = new BaseResponse();
    final HttpPost method;
    final Header[] cookieHeaders;

    requestHeaders.put(HttpHeaders.CONTENT_TYPE, "application/json");
    Mockito.when(httpClient.execute(ArgumentMatchers.any(HttpPost.class),
        ArgumentMatchers.eq(httpClientContext))).thenReturn(httpResponse);
    Assert.assertEquals(httpResponse, HttpRequestHandler.post(httpClient, endPointUrl, cookies,
        requestHeaders, requestEntity, httpClientContext));
    Mockito.verify(httpClient).execute(argumentCaptor1.capture(), argumentCaptor2.capture());

    method = argumentCaptor1.getValue();
    Assert.assertEquals(endPointUrl, method.getURI().toString());
    Assert.assertEquals(httpClientContext, argumentCaptor2.getValue());
    Assert.assertTrue(method.containsHeader(HttpHeaders.CONTENT_TYPE));
    Assert.assertNotNull(method.getEntity().getContent());

    cookieHeaders = method.getHeaders("Cookie");

    for (final Header cookie : cookieHeaders) {
      Assert.assertTrue(cookies.contains(cookie.getValue()));
    }
  }

  @Test
  public void testPostHttpClientStringListOfStringMapOfStringStringObjectHttpClientContext()
      throws ClientProtocolException, IOException {
    final String endPointUrl = "http://localhost:9433/test";
    final Map<String, String> requestHeaders = new HashMap<>();
    final HttpClient httpClient = Mockito.mock(HttpClient.class);
    final HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
    final ArgumentCaptor<HttpPost> argumentCaptor1 = ArgumentCaptor.forClass(HttpPost.class);
    final BaseResponse requestEntity = new BaseResponse();
    final ObjectMapper mapper = new ObjectMapper();
    final HttpPost method;

    requestHeaders.put(HttpHeaders.CONTENT_TYPE, "application/json");
    Mockito.when(httpClient.execute(ArgumentMatchers.any(HttpPost.class))).thenReturn(httpResponse);
    Assert.assertEquals(httpResponse, HttpRequestHandler.post(httpClient, endPointUrl, null, null,
        mapper.writeValueAsString(requestEntity), null));
    Mockito.verify(httpClient).execute(argumentCaptor1.capture());

    method = argumentCaptor1.getValue();
    Assert.assertEquals(endPointUrl, method.getURI().toString());
    Assert.assertNotNull(method.getEntity().getContent());
  }

  @Test
  public void testPostHttpClientStringListOfStringMapOfStringStringObjectHttpClientContext2()
      throws ClientProtocolException, IOException {
    final String endPointUrl = "http://localhost:9433/test";
    final Map<String, String> requestHeaders = new HashMap<>();
    final HttpClient httpClient = Mockito.mock(HttpClient.class);
    final HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
    final ArgumentCaptor<HttpPost> argumentCaptor1 = ArgumentCaptor.forClass(HttpPost.class);
    final HttpPost method;

    requestHeaders.put(HttpHeaders.CONTENT_TYPE, "application/json");
    Mockito.when(httpClient.execute(ArgumentMatchers.any(HttpPost.class))).thenReturn(httpResponse);
    Assert.assertEquals(httpResponse, HttpRequestHandler.post(httpClient, endPointUrl,
        Arrays.asList(), new HashMap<>(), null, null));
    Mockito.verify(httpClient).execute(argumentCaptor1.capture());

    method = argumentCaptor1.getValue();
    Assert.assertEquals(endPointUrl, method.getURI().toString());
  }

  @Test
  public void testPostHttpClientStringListOfStringMapOfStringStringObjectHttpClientContext3()
      throws ClientProtocolException, IOException {
    final String endPointUrl = "http://localhost:9433/test";
    final Map<String, String> requestHeaders = new HashMap<>();
    final HttpClient httpClient = Mockito.mock(HttpClient.class);
    final HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
    final ArgumentCaptor<HttpPost> argumentCaptor1 = ArgumentCaptor.forClass(HttpPost.class);
    final HttpPost method;

    requestHeaders.put(HttpHeaders.CONTENT_TYPE, "application/json");
    Mockito.when(httpClient.execute(ArgumentMatchers.any(HttpPost.class))).thenReturn(httpResponse);
    Assert.assertEquals(httpResponse, HttpRequestHandler.post(httpClient, endPointUrl,
        Arrays.asList(), new HashMap<>(), "", null));
    Mockito.verify(httpClient).execute(argumentCaptor1.capture());

    method = argumentCaptor1.getValue();
    Assert.assertEquals(endPointUrl, method.getURI().toString());
  }

  @Test
  public void testPutHttpClientStringListOfStringMapOfStringStringStringHttpClientContext()
      throws ClientProtocolException, IOException {
    final String endPointUrl = "http://localhost:9433/test";
    final List<String> cookies = Arrays.asList("JSESSIONID=0000Dch-UevJAP6hq7Juda6be7F:1b7o43dnq",
        "WC_SESSION_ESTABLISHED=true",
        "WC_PERSISTENT=kA6%2FJwFUGfMUwswLwcortDevqEU%3D%0A%3B2019-03-28+01%3A59%3A38.031_1552563415165-1_10651",
        "WC_AUTHENTICATION_11450726=11450726%2Ciq3xEXbdEdFZIDrGRgy0MgFhKq0%3D",
        "WC_ACTIVEPOINTER=-1%2C10651",
        "WC_USERACTIVITY_11450726=11450726%2C10651%2C0%2Cnull%2C1553763578033%2C1553765378033%2Cnull%2Cnull%2Cnull%2Cnull%2CWy4MaAIrtdL1bVl2fh%2FCkuOgn");
    final Map<String, String> requestHeaders = new HashMap<>();
    final HttpClient httpClient = Mockito.mock(HttpClient.class);
    final HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
    final ArgumentCaptor<HttpPut> argumentCaptor1 = ArgumentCaptor.forClass(HttpPut.class);
    final ArgumentCaptor<HttpClientContext> argumentCaptor2 = ArgumentCaptor
        .forClass(HttpClientContext.class);
    final HttpClientContext httpClientContext = Mockito.mock(HttpClientContext.class);
    final BaseResponse requestEntity = new BaseResponse();
    final HttpPut method;
    final Header[] cookieHeaders;

    requestHeaders.put(HttpHeaders.CONTENT_TYPE, "application/json");
    Mockito.when(httpClient.execute(ArgumentMatchers.any(HttpPut.class),
        ArgumentMatchers.eq(httpClientContext))).thenReturn(httpResponse);
    Assert.assertEquals(httpResponse, HttpRequestHandler.put(httpClient, endPointUrl, cookies,
        requestHeaders, requestEntity, httpClientContext));
    Mockito.verify(httpClient).execute(argumentCaptor1.capture(), argumentCaptor2.capture());

    method = argumentCaptor1.getValue();
    Assert.assertEquals(endPointUrl, method.getURI().toString());
    Assert.assertEquals(httpClientContext, argumentCaptor2.getValue());
    Assert.assertTrue(method.containsHeader(HttpHeaders.CONTENT_TYPE));
    Assert.assertNotNull(method.getEntity().getContent());

    cookieHeaders = method.getHeaders("Cookie");

    for (final Header cookie : cookieHeaders) {
      Assert.assertTrue(cookies.contains(cookie.getValue()));
    }
  }

  @Test
  public void testPutHttpClientStringListOfStringMapOfStringStringObjectHttpClientContext()
      throws ClientProtocolException, IOException {
    final String endPointUrl = "http://localhost:9433/test";
    final Map<String, String> requestHeaders = new HashMap<>();
    final HttpClient httpClient = Mockito.mock(HttpClient.class);
    final HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
    final ArgumentCaptor<HttpPut> argumentCaptor1 = ArgumentCaptor.forClass(HttpPut.class);
    final BaseResponse requestEntity = new BaseResponse();
    final ObjectMapper mapper = new ObjectMapper();
    final HttpPut method;

    requestHeaders.put(HttpHeaders.CONTENT_TYPE, "application/json");
    Mockito.when(httpClient.execute(ArgumentMatchers.any(HttpPut.class))).thenReturn(httpResponse);
    Assert.assertEquals(httpResponse, HttpRequestHandler.put(httpClient, endPointUrl, null, null,
        mapper.writeValueAsString(requestEntity), null));
    Mockito.verify(httpClient).execute(argumentCaptor1.capture());

    method = argumentCaptor1.getValue();
    Assert.assertEquals(endPointUrl, method.getURI().toString());
    Assert.assertNotNull(method.getEntity().getContent());
  }

  @Test
  public void testPutHttpClientStringListOfStringMapOfStringStringObjectHttpClientContext2()
      throws ClientProtocolException, IOException {
    final String endPointUrl = "http://localhost:9433/test";
    final Map<String, String> requestHeaders = new HashMap<>();
    final HttpClient httpClient = Mockito.mock(HttpClient.class);
    final HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
    final ArgumentCaptor<HttpPut> argumentCaptor1 = ArgumentCaptor.forClass(HttpPut.class);
    final HttpPut method;

    requestHeaders.put(HttpHeaders.CONTENT_TYPE, "application/json");
    Mockito.when(httpClient.execute(ArgumentMatchers.any(HttpPut.class))).thenReturn(httpResponse);
    Assert.assertEquals(httpResponse, HttpRequestHandler.put(httpClient, endPointUrl,
        Arrays.asList(), new HashMap<>(), null, null));
    Mockito.verify(httpClient).execute(argumentCaptor1.capture());

    method = argumentCaptor1.getValue();
    Assert.assertEquals(endPointUrl, method.getURI().toString());
  }

  @Test
  public void testPutHttpClientStringListOfStringMapOfStringStringObjectHttpClientContext3()
      throws ClientProtocolException, IOException {
    final String endPointUrl = "http://localhost:9433/test";
    final Map<String, String> requestHeaders = new HashMap<>();
    final HttpClient httpClient = Mockito.mock(HttpClient.class);
    final HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
    final ArgumentCaptor<HttpPut> argumentCaptor1 = ArgumentCaptor.forClass(HttpPut.class);
    final HttpPut method;

    requestHeaders.put(HttpHeaders.CONTENT_TYPE, "application/json");
    Mockito.when(httpClient.execute(ArgumentMatchers.any(HttpPut.class))).thenReturn(httpResponse);
    Assert.assertEquals(httpResponse, HttpRequestHandler.put(httpClient, endPointUrl,
        Arrays.asList(), new HashMap<>(), "", null));
    Mockito.verify(httpClient).execute(argumentCaptor1.capture());

    method = argumentCaptor1.getValue();
    Assert.assertEquals(endPointUrl, method.getURI().toString());
  }

  @Test
  public void testDeleteHttpClientStringListOfStringMapOfStringStringHttpClientContext1()
      throws ClientProtocolException, IOException {
    final String endPointUrl = "http://localhost:9433/test";
    final HttpClient httpClient = Mockito.mock(HttpClient.class);
    final HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
    final ArgumentCaptor<HttpDelete> argumentCaptor = ArgumentCaptor.forClass(HttpDelete.class);

    Mockito.when(httpClient.execute(ArgumentMatchers.any(HttpDelete.class)))
        .thenReturn(httpResponse);
    HttpRequestHandler.delete(httpClient, endPointUrl, null, null, null);
    Mockito.verify(httpClient).execute(argumentCaptor.capture());
    Assert.assertEquals(endPointUrl, argumentCaptor.getValue().getURI().toString());
  }

  @Test
  public void testDeleteHttpClientStringListOfStringMapOfStringStringHttpClientContext2()
      throws ClientProtocolException, IOException {
    final String endPointUrl = "http://localhost:9433/test";
    final List<String> cookies = Arrays.asList("JSESSIONID=0000Dch-UevJAP6hq7Juda6be7F:1b7o43dnq",
        "WC_SESSION_ESTABLISHED=true",
        "WC_PERSISTENT=kA6%2FJwFUGfMUwswLwcortDevqEU%3D%0A%3B2019-03-28+01%3A59%3A38.031_1552563415165-1_10651",
        "WC_AUTHENTICATION_11450726=11450726%2Ciq3xEXbdEdFZIDrGRgy0MgFhKq0%3D",
        "WC_ACTIVEPOINTER=-1%2C10651",
        "WC_USERACTIVITY_11450726=11450726%2C10651%2C0%2Cnull%2C1553763578033%2C1553765378033%2Cnull%2Cnull%2Cnull%2Cnull%2CWy4MaAIrtdL1bVl2fh%2FCkuOgn");
    final Map<String, String> requestHeaders = new HashMap<>();
    final HttpClient httpClient = Mockito.mock(HttpClient.class);
    final HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
    final ArgumentCaptor<HttpDelete> argumentCaptor1 = ArgumentCaptor.forClass(HttpDelete.class);
    final ArgumentCaptor<HttpClientContext> argumentCaptor2 = ArgumentCaptor
        .forClass(HttpClientContext.class);
    final HttpClientContext httpClientContext = Mockito.mock(HttpClientContext.class);
    final HttpDelete method;
    final Header[] cookieHeaders;

    requestHeaders.put(HttpHeaders.CONTENT_TYPE, "application/json");
    Mockito.when(httpClient.execute(ArgumentMatchers.any(HttpDelete.class),
        ArgumentMatchers.eq(httpClientContext))).thenReturn(httpResponse);
    Assert.assertEquals(httpResponse, HttpRequestHandler.delete(httpClient, endPointUrl, cookies,
        requestHeaders, httpClientContext));
    Mockito.verify(httpClient).execute(argumentCaptor1.capture(), argumentCaptor2.capture());

    method = argumentCaptor1.getValue();
    Assert.assertEquals(endPointUrl, method.getURI().toString());
    Assert.assertEquals(httpClientContext, argumentCaptor2.getValue());
    Assert.assertTrue(method.containsHeader(HttpHeaders.CONTENT_TYPE));

    cookieHeaders = method.getHeaders("Cookie");

    for (final Header cookie : cookieHeaders) {
      Assert.assertTrue(cookies.contains(cookie.getValue()));
    }
  }

  @Test
  public void testDeleteHttpClientStringListOfStringMapOfStringStringMapOfStringStringHttpClientContext1()
      throws ClientProtocolException, IOException, URISyntaxException {
    final String endPointUrl = "http://localhost:9433/test";
    final List<String> cookies = Arrays.asList();
    final Map<String, String> urlParams = new HashMap<>();
    final Map<String, String> requestHeaders = new HashMap<>();
    final HttpClient httpClient = Mockito.mock(HttpClient.class);
    final HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
    final ArgumentCaptor<HttpDelete> argumentCaptor1 = ArgumentCaptor.forClass(HttpDelete.class);
    final ArgumentCaptor<HttpClientContext> argumentCaptor2 = ArgumentCaptor
        .forClass(HttpClientContext.class);
    final HttpClientContext httpClientContext = Mockito.mock(HttpClientContext.class);
    final HttpDelete method;

    urlParams.put("param1", "value1");
    urlParams.put("param2", "value2");
    Mockito.when(httpClient.execute(ArgumentMatchers.any(HttpDelete.class),
        ArgumentMatchers.eq(httpClientContext))).thenReturn(httpResponse);
    Assert.assertEquals(httpResponse, HttpRequestHandler.delete(httpClient, endPointUrl, cookies,
        requestHeaders, urlParams, httpClientContext));
    Mockito.verify(httpClient).execute(argumentCaptor1.capture(), argumentCaptor2.capture());

    method = argumentCaptor1.getValue();
    Assert.assertEquals(
        new StringBuilder(endPointUrl).append("?").append("param1").append("=").append("value1")
            .append("&").append("param2").append("=").append("value2").toString(),
        method.getURI().toString());
    Assert.assertEquals(httpClientContext, argumentCaptor2.getValue());
  }

  @Test
  public void testDeleteHttpClientStringListOfStringMapOfStringStringMapOfStringStringHttpClientContext2()
      throws ClientProtocolException, IOException, URISyntaxException {
    final String endPointUrl = "http://localhost:9433/test";
    final List<String> cookies = Arrays.asList();
    final Map<String, String> requestHeaders = new HashMap<>();
    final HttpClient httpClient = Mockito.mock(HttpClient.class);
    final HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
    final ArgumentCaptor<HttpDelete> argumentCaptor1 = ArgumentCaptor.forClass(HttpDelete.class);
    final ArgumentCaptor<HttpClientContext> argumentCaptor2 = ArgumentCaptor
        .forClass(HttpClientContext.class);
    final HttpClientContext httpClientContext = Mockito.mock(HttpClientContext.class);
    final HttpDelete method;

    Mockito.when(httpClient.execute(ArgumentMatchers.any(HttpDelete.class),
        ArgumentMatchers.eq(httpClientContext))).thenReturn(httpResponse);
    Assert.assertEquals(httpResponse, HttpRequestHandler.delete(httpClient, endPointUrl, cookies,
        requestHeaders, null, httpClientContext));
    Mockito.verify(httpClient).execute(argumentCaptor1.capture(), argumentCaptor2.capture());

    method = argumentCaptor1.getValue();
    Assert.assertEquals(endPointUrl, method.getURI().toString());
    Assert.assertEquals(httpClientContext, argumentCaptor2.getValue());
  }

  @Test
  public void testDeleteHttpClientStringListOfStringMapOfStringStringMapOfStringStringHttpClientContext3()
      throws ClientProtocolException, IOException, URISyntaxException {
    final String endPointUrl = "http://localhost:9433/test";
    final List<String> cookies = Arrays.asList();
    final Map<String, String> urlParams = new HashMap<>();
    final Map<String, String> requestHeaders = new HashMap<>();
    final HttpClient httpClient = Mockito.mock(HttpClient.class);
    final HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
    final ArgumentCaptor<HttpDelete> argumentCaptor1 = ArgumentCaptor.forClass(HttpDelete.class);
    final ArgumentCaptor<HttpClientContext> argumentCaptor2 = ArgumentCaptor
        .forClass(HttpClientContext.class);
    final HttpClientContext httpClientContext = Mockito.mock(HttpClientContext.class);
    final HttpDelete method;

    Mockito.when(httpClient.execute(ArgumentMatchers.any(HttpDelete.class),
        ArgumentMatchers.eq(httpClientContext))).thenReturn(httpResponse);
    Assert.assertEquals(httpResponse, HttpRequestHandler.delete(httpClient, endPointUrl, cookies,
        requestHeaders, urlParams, httpClientContext));
    Mockito.verify(httpClient).execute(argumentCaptor1.capture(), argumentCaptor2.capture());

    method = argumentCaptor1.getValue();
    Assert.assertEquals(endPointUrl, method.getURI().toString());
    Assert.assertEquals(httpClientContext, argumentCaptor2.getValue());
  }

  @Test
  public void testBuildHttpContext() {
    final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();

    Assert.assertNotNull(httpClientContext);
    Assert.assertNotNull(httpClientContext.getCookieStore());
  }

  @Test
  public void testBuildCreateDefault() throws IOException {
    try (final CloseableHttpClient closeableHttpClient = HttpRequestHandler.createDefault()) {

      Assert.assertNotNull(closeableHttpClient);
    }
  }

  @Test
  public void testBuildCreateCustom1() throws IOException {
    try (final CloseableHttpClient closeableHttpClient = HttpRequestHandler.createCustom(15000)) {

      Assert.assertNotNull(closeableHttpClient);
    }
  }

  @Test
  public void testBuildCreateCustom2() throws IOException {
    try (final CloseableHttpClient closeableHttpClient = HttpRequestHandler.createCustom(15000,
        15000)) {

      Assert.assertNotNull(closeableHttpClient);
    }
  }
}
