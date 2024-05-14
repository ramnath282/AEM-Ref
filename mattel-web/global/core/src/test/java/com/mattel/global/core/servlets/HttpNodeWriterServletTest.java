package com.mattel.global.core.servlets;

import java.net.HttpURLConnection;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.mattel.global.core.services.HttpXmlNodeDataService;

@RunWith(MockitoJUnitRunner.class)
public class HttpNodeWriterServletTest {
  @Mock
  private HttpXmlNodeDataService xmlNodeDataService;
  @InjectMocks
  private HttpNodeWriterServlet httpNodeWriterServlet;
  private SlingHttpServletRequest request;
  private SlingHttpServletResponse response;
  private ResourceResolver resolver;
  
  @Before
  public void setup() {
    request = Mockito.mock(SlingHttpServletRequest.class);
    response = Mockito.mock(SlingHttpServletResponse.class);
    resolver = Mockito.mock(ResourceResolver.class);
  }

  @Test
  public void testDoGetValidSiteMap() throws Exception {
    Mockito.when(request.getParameter("appName")).thenReturn("corporate");
    Mockito.when(request.getParameter("type")).thenReturn("xml");
    Mockito.when(request.getParameter("locale")).thenReturn(null);
    Mockito.when(request.getResourceResolver()).thenReturn(resolver);
    Mockito.doNothing().when(xmlNodeDataService).write("corporate", null, resolver, response);
    httpNodeWriterServlet.doGet(request, response);
  }

  @Test
  public void testDoGetValidSiteMap2() throws Exception {
    Mockito.when(request.getParameter("appName")).thenReturn("corporate");
    Mockito.when(request.getParameter("type")).thenReturn("json");
    Mockito.when(request.getParameter("locale")).thenReturn(null);
    Mockito.when(request.getResourceResolver()).thenReturn(resolver);
    Mockito.doNothing().when(xmlNodeDataService).write("corporate", null, resolver, response);
    httpNodeWriterServlet.doGet(request, response);
  }
  
  @Test
  public void testDoGetNoAppName() throws Exception {
    Mockito.when(request.getParameter("appName")).thenReturn(null);
    Mockito.when(request.getParameter("type")).thenReturn("xml");
    Mockito.when(request.getParameter("locale")).thenReturn(null);
    Mockito.when(request.getResourceResolver()).thenReturn(resolver);
    Mockito.doNothing().when(response).sendError(HttpURLConnection.HTTP_NOT_FOUND);
    httpNodeWriterServlet.doGet(request, response);
  }
  
  @Test
  public void testDoGetNoContentType() throws Exception {
    Mockito.when(request.getParameter("appName")).thenReturn("corporate");
    Mockito.when(request.getParameter("type")).thenReturn(null);
    Mockito.when(request.getParameter("locale")).thenReturn(null);
    Mockito.when(request.getResourceResolver()).thenReturn(resolver);
    Mockito.doNothing().when(response).sendError(HttpURLConnection.HTTP_NOT_FOUND);
    httpNodeWriterServlet.doGet(request, response);
  }
  
  @Test
  public void testDoGetNotSupportedContentType() throws Exception {
    Mockito.when(request.getParameter("appName")).thenReturn("corporate");
    Mockito.when(request.getParameter("type")).thenReturn("csv");
    Mockito.when(request.getParameter("locale")).thenReturn(null);
    Mockito.doNothing().when(response).sendError(HttpURLConnection.HTTP_NOT_FOUND);
    Mockito.when(request.getResourceResolver()).thenReturn(resolver);
    httpNodeWriterServlet.doGet(request, response);
  }
}
