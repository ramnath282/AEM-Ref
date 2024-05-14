package com.mattel.ecomm.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;

import javax.servlet.ServletInputStream;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.mattel.ecomm.core.interfaces.ProductDataImporterService;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.DataImporterErrorCode;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.ProductDataImporterResponse;

@RunWith(MockitoJUnitRunner.class)
public class ProductDataImporterServletTest {
  @Mock
  private ProductDataImporterService productDataImporterService;
  @InjectMocks
  private ProductDataImporterServlet productDataImporterServlet;

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testDoPostForSuccess() throws Exception {
    final SlingHttpServletRequest request = Mockito.mock(SlingHttpServletRequest.class);
    final SlingHttpServletResponse response = Mockito.mock(SlingHttpServletResponse.class);
    final ServletInputStream inputStream = Mockito.mock(ServletInputStream.class);
    final StringWriter stringWriter = new StringWriter();
    final ProductDataImporterResponse productDataImporterResponse = new ProductDataImporterResponse();
    final PrintWriter printWriter = new PrintWriter(stringWriter);

    productDataImporterResponse.setStatus(true);
    final String generatedFilePath = "/content/dam/ag/productfeed/productdata_20190912074047.json";
    productDataImporterResponse.setFilePath(generatedFilePath);
    Mockito.when(request.getContentLength()).thenReturn(10000);
    Mockito.when(request.getInputStream()).thenReturn(inputStream);
    Mockito.doNothing().when(response).setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
    Mockito.when(response.getWriter()).thenReturn(printWriter);
    Mockito.when(
        productDataImporterService.readInputData(ArgumentMatchers.any(), ArgumentMatchers.any()))
        .thenReturn(productDataImporterResponse);
    productDataImporterServlet.doPost(request, response);
    Assert.assertTrue(stringWriter.toString().contains(generatedFilePath));
  }

  @Test
  public void testDoPostForRequestBodyEmpty() throws Exception {
    final SlingHttpServletRequest request = Mockito.mock(SlingHttpServletRequest.class);
    final SlingHttpServletResponse response = Mockito.mock(SlingHttpServletResponse.class);
    final ServletInputStream inputStream = Mockito.mock(ServletInputStream.class);
    final StringWriter stringWriter = new StringWriter();
    final PrintWriter printWriter = new PrintWriter(stringWriter);

    Mockito.when(request.getContentLength()).thenReturn(10000);
    Mockito.when(request.getInputStream()).thenReturn(inputStream);
    Mockito.doNothing().when(response).setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
    Mockito.when(response.getWriter()).thenReturn(printWriter);
    Mockito
        .when(productDataImporterService.readInputData(ArgumentMatchers.any(),
            ArgumentMatchers.any()))
        .thenThrow(new ServiceException(DataImporterErrorCode.REQUEST_BODY_EMPTY.toString(),
            "Request Body is empty"));
    productDataImporterServlet.doPost(request, response);
    Assert.assertTrue(stringWriter.toString()
        .contains(DataImporterErrorCode.REQUEST_BODY_EMPTY.getErrorMessage()));
  }

  @Test
  public void testDoPostForRequestForException() throws Exception {
    final SlingHttpServletRequest request = Mockito.mock(SlingHttpServletRequest.class);
    final SlingHttpServletResponse response = Mockito.mock(SlingHttpServletResponse.class);
    final StringWriter stringWriter = new StringWriter();
    final PrintWriter printWriter = new PrintWriter(stringWriter);

    Mockito.when(request.getContentLength()).thenReturn(10000);
    Mockito.when(request.getInputStream()).thenThrow(new IOException("Request already read"));
    Mockito.doNothing().when(response).setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
    Mockito.when(response.getWriter()).thenReturn(printWriter);
    productDataImporterServlet.doPost(request, response);
    Assert.assertTrue(stringWriter.toString()
        .contains(String.valueOf(HttpURLConnection.HTTP_INTERNAL_ERROR)));
  }

  @Test
  public void testDoPostForRequestBodyNull() throws Exception {
    final SlingHttpServletRequest request = Mockito.mock(SlingHttpServletRequest.class);
    final SlingHttpServletResponse response = Mockito.mock(SlingHttpServletResponse.class);
    final ServletInputStream inputStream = Mockito.mock(ServletInputStream.class);
    final StringWriter stringWriter = new StringWriter();
    final PrintWriter printWriter = new PrintWriter(stringWriter);

    Mockito.when(request.getContentLength()).thenReturn(10000);
    Mockito.when(request.getInputStream()).thenReturn(inputStream);
    Mockito.doNothing().when(response).setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
    Mockito.when(response.getWriter()).thenReturn(printWriter);
    Mockito
        .when(productDataImporterService.readInputData(ArgumentMatchers.any(),
            ArgumentMatchers.any()))
        .thenThrow(new ServiceException(DataImporterErrorCode.REQUEST_BODY_NULL.toString(),
            "Request Body null"));
    productDataImporterServlet.doPost(request, response);
    Assert.assertTrue(stringWriter.toString()
        .contains(DataImporterErrorCode.REQUEST_BODY_NULL.getErrorMessage()));
  }
  
  @Test
  public void testDoPostForServiceUnavailable() throws Exception {
    final SlingHttpServletRequest request = Mockito.mock(SlingHttpServletRequest.class);
    final SlingHttpServletResponse response = Mockito.mock(SlingHttpServletResponse.class);
    final ServletInputStream inputStream = Mockito.mock(ServletInputStream.class);
    final StringWriter stringWriter = new StringWriter();
    final PrintWriter printWriter = new PrintWriter(stringWriter);

    Mockito.when(request.getContentLength()).thenReturn(10000);
    Mockito.when(request.getInputStream()).thenReturn(inputStream);
    Mockito.doNothing().when(response).setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
    Mockito.when(response.getWriter()).thenReturn(printWriter);
    Mockito
        .when(productDataImporterService.readInputData(ArgumentMatchers.any(),
            ArgumentMatchers.any()))
        .thenThrow(new ServiceException(DataImporterErrorCode.ASSERT_MANAGER_UNAVAILABLE.toString(),
            "Assert Manager Unvailable"));
    productDataImporterServlet.doPost(request, response);
    Assert.assertTrue(stringWriter.toString()
        .contains(String.valueOf(HttpURLConnection.HTTP_UNAVAILABLE)));
  }

  @Test
  public void testDoPostForInternalError() throws Exception {
    final SlingHttpServletRequest request = Mockito.mock(SlingHttpServletRequest.class);
    final SlingHttpServletResponse response = Mockito.mock(SlingHttpServletResponse.class);
    final ServletInputStream inputStream = Mockito.mock(ServletInputStream.class);
    final StringWriter stringWriter = new StringWriter();
    final PrintWriter printWriter = new PrintWriter(stringWriter);

    Mockito.when(request.getContentLength()).thenReturn(10000);
    Mockito.when(request.getInputStream()).thenReturn(inputStream);
    Mockito.doNothing().when(response).setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
    Mockito.when(response.getWriter()).thenReturn(printWriter);
    Mockito
        .when(productDataImporterService.readInputData(ArgumentMatchers.any(),
            ArgumentMatchers.any()))
        .thenThrow(new ServiceException(DataImporterErrorCode.INTERNAL_ERROR.toString(),
            "Internal Server Error"));
    productDataImporterServlet.doPost(request, response);
    Assert.assertTrue(stringWriter.toString()
        .contains(DataImporterErrorCode.INTERNAL_ERROR.getErrorMessage()));
  }



  @Test
  public void testDoPostForEmptyFeed() throws Exception {
    final SlingHttpServletRequest request = Mockito.mock(SlingHttpServletRequest.class);
    final SlingHttpServletResponse response = Mockito.mock(SlingHttpServletResponse.class);
    final StringWriter stringWriter = new StringWriter();
    final PrintWriter printWriter = new PrintWriter(stringWriter);

    Mockito.when(request.getContentLength()).thenReturn(0);
    Mockito.doNothing().when(response).setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
    Mockito.when(response.getWriter()).thenReturn(printWriter);
    productDataImporterServlet.doPost(request, response);
    Assert.assertTrue(stringWriter.toString()
        .contains(DataImporterErrorCode.REQUEST_BODY_EMPTY.getErrorMessage()));
  }
}
