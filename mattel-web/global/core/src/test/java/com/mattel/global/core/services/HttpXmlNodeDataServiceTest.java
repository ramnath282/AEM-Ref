package com.mattel.global.core.services;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.mattel.global.core.exceptions.NodeWriterException;
import com.mattel.global.core.utils.PropertyReaderUtils;

@RunWith(MockitoJUnitRunner.class)
public class HttpXmlNodeDataServiceTest {
  @Mock
  private CorporateXmlNodeWriterService corporateXmlNodeWriterService;
  @Mock
  private PropertyReaderUtils propertyReaderUtils;
  @InjectMocks
  private HttpXmlNodeDataService httpXmlNodeDataService;
  private SlingHttpServletResponse response;
  private ResourceResolver resolver;

  @Before
  public void setUp() throws Exception {
    response = Mockito.mock(SlingHttpServletResponse.class);
    resolver = Mockito.mock(ResourceResolver.class);
  }

  @Test
  public void testWriteForValid() throws Exception {
    final PrintWriter pw = Mockito.mock(PrintWriter.class);

    Mockito.when(propertyReaderUtils.getNodeDataPath("corporate"))
        .thenReturn("/content/dam/mattel/corporate-mattel");
    Mockito.when(response.getWriter()).thenReturn(pw);
    //Mockito.when(resolver.getResource("/content/dam/mattel/corporate-mattel")).thenReturn(Mockito.mock(Resource.class));
    Mockito.doNothing().when(response).setStatus(HttpURLConnection.HTTP_OK);
    Mockito.doNothing().when(response).setContentType("application/xhtml+xml");
    Mockito.doNothing().when(response).setCharacterEncoding(StandardCharsets.UTF_8.toString());
    //Mockito.doNothing().when(corporateXmlNodeWriterService)
    //    .readNode("/content/dam/mattel/corporate-mattel","/content/mattel/mattel-corporate/us/en-us/home/news",resolver, pw);
    httpXmlNodeDataService.write("corporate", null, resolver, response);
  }
  
  @Test
  public void testWriteForInValidAppName() throws Exception {
    Mockito.doNothing().when(response).sendError(HttpURLConnection.HTTP_NOT_FOUND);
    httpXmlNodeDataService.write("fisher-price", null, resolver, response);
  }
  
  @Test
  public void testWriteForInValidPath() throws Exception {
    Mockito.when(propertyReaderUtils.getNodeDataPath("corp"))
    .thenReturn(null);
    Mockito.doNothing().when(response).sendError(HttpURLConnection.HTTP_NOT_FOUND);
    httpXmlNodeDataService.write("corp", null, resolver, response);
  }
  
  @Test
  public void testWriteForInValidResource() throws Exception {
    Mockito.when(propertyReaderUtils.getNodeDataPath("corp"))
    .thenReturn("/content/dam/mattel/corporate-mattel-new");
    //Mockito.when(resolver.getResource("/content/dam/mattel/corporate-mattel-new")).thenReturn(null);
    //Mockito.doNothing().when(response).sendError(HttpURLConnection.HTTP_NOT_FOUND);
    httpXmlNodeDataService.write("corp", null, resolver, response);
  }
  
  @Test
  public void testWriteForInternalError() throws Exception {
    final PrintWriter pw = Mockito.mock(PrintWriter.class);

    Mockito.when(propertyReaderUtils.getNodeDataPath("corp"))
        .thenReturn("/content/dam/mattel/corporate-mattel");
    Mockito.when(propertyReaderUtils.getRootPagePath("corp"))
    .thenReturn("/content/mattel/mattel-corporate/us/en-us/home/news");
    Mockito.when(response.getWriter()).thenReturn(pw);
    //Mockito.when(resolver.getResource("/content/dam/mattel/corporate-mattel")).thenReturn(Mockito.mock(Resource.class));
    //Mockito.when(resolver.getResource("/content/mattel/mattel-corporate/us/en-us/home/news")).thenReturn(Mockito.mock(Resource.class));
    Mockito.doNothing().when(response).setStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
    Mockito.doThrow(new NodeWriterException("Unable to generate node data xml")).when(corporateXmlNodeWriterService)
        .readNode("/content/dam/mattel/corporate-mattel","/content/mattel/mattel-corporate/us/en-us/home/news" ,resolver, pw);
    httpXmlNodeDataService.write("corp", null, resolver, response);
  }
}
