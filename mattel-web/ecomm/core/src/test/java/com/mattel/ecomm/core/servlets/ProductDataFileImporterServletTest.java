package com.mattel.ecomm.core.servlets;

import com.mattel.ecomm.core.interfaces.ProductDataImporterService;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.DataImporterErrorCode;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.ProductDataImporterResponse;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.request.RequestParameterMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ServletFileUpload.class })
public class ProductDataFileImporterServletTest {
  @Mock
  private ProductDataImporterService productDataImporterService;
  @InjectMocks
  private ProductDataFileImporterServlet productDataFileImporterServlet;

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testDoPost() throws Exception {
    final SlingHttpServletRequest slingHttpServletRequest = Mockito
        .mock(SlingHttpServletRequest.class);
    final SlingHttpServletResponse slingHttpServletResponse = Mockito
        .mock(SlingHttpServletResponse.class);
    final Map<String, RequestParameter[]> parameterMap = new HashMap<String, RequestParameter[]>();
    final RequestParameter requestParameter = Mockito.mock(RequestParameter.class);
    Mockito.when(requestParameter.isFormField()).thenReturn(false);
    Mockito.when(requestParameter.getInputStream()).thenReturn(Mockito.mock(InputStream.class));
    final ProductDataImporterResponse productDataImporterResponse = new ProductDataImporterResponse();
    final StringWriter sw = new StringWriter();
    productDataImporterResponse.setStatus(true);
    productDataImporterResponse.setFilePath("/var/tmp/ag/en/product.json");

    parameterMap.put("file", new RequestParameter[] { requestParameter });
    final RequestParameterMap parameters = generateParamMap(parameterMap);

    Mockito.when(slingHttpServletRequest.getMethod()).thenReturn(HttpConstants.METHOD_POST);
    Mockito.when(slingHttpServletRequest.getMethod())
        .thenReturn(FileUploadBase.MULTIPART_FORM_DATA);
    PowerMockito.mockStatic(ServletFileUpload.class);
    PowerMockito.when(ServletFileUpload.isMultipartContent(slingHttpServletRequest))
        .thenReturn(true);
    Mockito.doNothing().when(slingHttpServletResponse)
        .setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
    Mockito.when(slingHttpServletResponse.getWriter()).thenReturn(new PrintWriter(sw));
    Mockito.when(slingHttpServletRequest.getRequestParameterMap()).thenReturn(parameters);
    Mockito.when(productDataImporterService.readInputData(ArgumentMatchers.any(InputStream.class),
        ArgumentMatchers.anyMap())).thenReturn(productDataImporterResponse);
    productDataFileImporterServlet.doPost(slingHttpServletRequest, slingHttpServletResponse);
    final String resp = sw.toString();

    Assert.assertTrue(resp.contains(productDataImporterResponse.getFilePath()));
    Assert.assertTrue(resp.contains(productDataImporterResponse.getStatus().toString()));
  }

  @Test
  public void testDoPosst1() throws Exception {
    final SlingHttpServletRequest slingHttpServletRequest = Mockito
        .mock(SlingHttpServletRequest.class);
    final SlingHttpServletResponse slingHttpServletResponse = Mockito
        .mock(SlingHttpServletResponse.class);
    final Map<String, RequestParameter[]> parameterMap = new HashMap<String, RequestParameter[]>();
    final RequestParameter requestParameter = Mockito.mock(RequestParameter.class);
    Mockito.when(requestParameter.isFormField()).thenReturn(false);
    Mockito.when(requestParameter.getInputStream()).thenReturn(Mockito.mock(InputStream.class));
    final StringWriter sw = new StringWriter();

    parameterMap.put("image", new RequestParameter[] { requestParameter });
    final RequestParameterMap parameters = generateParamMap(parameterMap);

    Mockito.when(slingHttpServletRequest.getMethod()).thenReturn(HttpConstants.METHOD_POST);
    Mockito.when(slingHttpServletRequest.getMethod())
        .thenReturn(FileUploadBase.MULTIPART_FORM_DATA);
    PowerMockito.mockStatic(ServletFileUpload.class);
    PowerMockito.when(ServletFileUpload.isMultipartContent(slingHttpServletRequest))
        .thenReturn(true);
    Mockito.doNothing().when(slingHttpServletResponse)
        .setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
    Mockito.when(slingHttpServletResponse.getWriter()).thenReturn(new PrintWriter(sw));
    Mockito.when(slingHttpServletRequest.getRequestParameterMap()).thenReturn(parameters);
    productDataFileImporterServlet.doPost(slingHttpServletRequest, slingHttpServletResponse);
    final String resp = sw.toString();

    Assert.assertTrue(resp.contains(String.valueOf(HttpURLConnection.HTTP_BAD_REQUEST)));
  }

  @Test
  public void testDoPosst2() throws Exception {
    final SlingHttpServletRequest slingHttpServletRequest = Mockito
        .mock(SlingHttpServletRequest.class);
    final SlingHttpServletResponse slingHttpServletResponse = Mockito
        .mock(SlingHttpServletResponse.class);
    final StringWriter sw = new StringWriter();

    Mockito.when(slingHttpServletRequest.getMethod()).thenReturn(HttpConstants.METHOD_POST);
    Mockito.when(slingHttpServletRequest.getMethod())
        .thenReturn(FileUploadBase.MULTIPART_FORM_DATA);
    PowerMockito.mockStatic(ServletFileUpload.class);
    PowerMockito.when(ServletFileUpload.isMultipartContent(slingHttpServletRequest))
        .thenReturn(false);
    Mockito.doNothing().when(slingHttpServletResponse)
        .setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
    Mockito.when(slingHttpServletResponse.getWriter()).thenReturn(new PrintWriter(sw));
    productDataFileImporterServlet.doPost(slingHttpServletRequest, slingHttpServletResponse);
    final String resp = sw.toString();

    Assert.assertTrue(resp.contains(String.valueOf(HttpURLConnection.HTTP_BAD_REQUEST)));
  }

  @Test
  public void testDoPost3() throws Exception {
    final SlingHttpServletRequest slingHttpServletRequest = Mockito
        .mock(SlingHttpServletRequest.class);
    final SlingHttpServletResponse slingHttpServletResponse = Mockito
        .mock(SlingHttpServletResponse.class);
    final Map<String, RequestParameter[]> parameterMap = new HashMap<String, RequestParameter[]>();
    final RequestParameter requestParameter = Mockito.mock(RequestParameter.class);
    Mockito.when(requestParameter.isFormField()).thenReturn(false);
    Mockito.when(requestParameter.getInputStream()).thenReturn(Mockito.mock(InputStream.class));
    final StringWriter sw = new StringWriter();

    parameterMap.put("file", new RequestParameter[] { requestParameter });
    final RequestParameterMap parameters = generateParamMap(parameterMap);

    Mockito.when(slingHttpServletRequest.getMethod()).thenReturn(HttpConstants.METHOD_POST);
    Mockito.when(slingHttpServletRequest.getMethod())
        .thenReturn(FileUploadBase.MULTIPART_FORM_DATA);
    PowerMockito.mockStatic(ServletFileUpload.class);
    PowerMockito.when(ServletFileUpload.isMultipartContent(slingHttpServletRequest))
        .thenReturn(true);
    Mockito.doNothing().when(slingHttpServletResponse)
        .setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
    Mockito.when(slingHttpServletResponse.getWriter()).thenReturn(new PrintWriter(sw));
    Mockito.when(slingHttpServletRequest.getRequestParameterMap()).thenReturn(parameters);
    Mockito
        .when(productDataImporterService.readInputData(ArgumentMatchers.any(InputStream.class),
            ArgumentMatchers.anyMap()))
        .thenThrow(new ServiceException(DataImporterErrorCode.REQUEST_BODY_EMPTY.toString(),
            DataImporterErrorCode.REQUEST_BODY_EMPTY.getErrorMessage()));
    productDataFileImporterServlet.doPost(slingHttpServletRequest, slingHttpServletResponse);
    final String resp = sw.toString();

    Assert.assertTrue(resp.contains(String.valueOf(HttpURLConnection.HTTP_BAD_REQUEST)));
    Assert.assertTrue(resp.contains(DataImporterErrorCode.REQUEST_BODY_EMPTY.getErrorMessage()));
  }

  @Test
  public void testDoPost4() throws Exception {
    final SlingHttpServletRequest slingHttpServletRequest = Mockito
        .mock(SlingHttpServletRequest.class);
    final SlingHttpServletResponse slingHttpServletResponse = Mockito
        .mock(SlingHttpServletResponse.class);
    final Map<String, RequestParameter[]> parameterMap = new HashMap<String, RequestParameter[]>();
    final RequestParameter requestParameter = Mockito.mock(RequestParameter.class);
    Mockito.when(requestParameter.isFormField()).thenReturn(false);
    Mockito.when(requestParameter.getInputStream()).thenReturn(Mockito.mock(InputStream.class));
    final StringWriter sw = new StringWriter();

    parameterMap.put("file", new RequestParameter[] { requestParameter });
    final RequestParameterMap parameters = generateParamMap(parameterMap);

    Mockito.when(slingHttpServletRequest.getMethod()).thenReturn(HttpConstants.METHOD_POST);
    Mockito.when(slingHttpServletRequest.getMethod())
        .thenReturn(FileUploadBase.MULTIPART_FORM_DATA);
    PowerMockito.mockStatic(ServletFileUpload.class);
    PowerMockito.when(ServletFileUpload.isMultipartContent(slingHttpServletRequest))
        .thenReturn(true);
    Mockito.doNothing().when(slingHttpServletResponse)
        .setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
    Mockito.when(slingHttpServletResponse.getWriter()).thenReturn(new PrintWriter(sw));
    Mockito.when(slingHttpServletRequest.getRequestParameterMap()).thenReturn(parameters);
    Mockito
        .when(productDataImporterService.readInputData(ArgumentMatchers.any(InputStream.class),
            ArgumentMatchers.anyMap()))
        .thenThrow(new ServiceException(DataImporterErrorCode.REQUEST_BODY_NULL.toString(),
            DataImporterErrorCode.REQUEST_BODY_NULL.getErrorMessage()));
    productDataFileImporterServlet.doPost(slingHttpServletRequest, slingHttpServletResponse);
    final String resp = sw.toString();

    Assert.assertTrue(resp.contains(String.valueOf(HttpURLConnection.HTTP_BAD_REQUEST)));
    Assert.assertTrue(resp.contains(DataImporterErrorCode.REQUEST_BODY_NULL.getErrorMessage()));
  }

  @Test
  public void testDoPost5() throws Exception {
    final SlingHttpServletRequest slingHttpServletRequest = Mockito
        .mock(SlingHttpServletRequest.class);
    final SlingHttpServletResponse slingHttpServletResponse = Mockito
        .mock(SlingHttpServletResponse.class);
    final Map<String, RequestParameter[]> parameterMap = new HashMap<String, RequestParameter[]>();
    final RequestParameter requestParameter = Mockito.mock(RequestParameter.class);
    Mockito.when(requestParameter.isFormField()).thenReturn(false);
    Mockito.when(requestParameter.getInputStream()).thenReturn(Mockito.mock(InputStream.class));
    final StringWriter sw = new StringWriter();

    parameterMap.put("file", new RequestParameter[] { requestParameter });
    final RequestParameterMap parameters = generateParamMap(parameterMap);

    Mockito.when(slingHttpServletRequest.getMethod()).thenReturn(HttpConstants.METHOD_POST);
    Mockito.when(slingHttpServletRequest.getMethod())
        .thenReturn(FileUploadBase.MULTIPART_FORM_DATA);
    PowerMockito.mockStatic(ServletFileUpload.class);
    PowerMockito.when(ServletFileUpload.isMultipartContent(slingHttpServletRequest))
        .thenReturn(true);
    Mockito.doNothing().when(slingHttpServletResponse)
        .setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
    Mockito.when(slingHttpServletResponse.getWriter()).thenReturn(new PrintWriter(sw));
    Mockito.when(slingHttpServletRequest.getRequestParameterMap()).thenReturn(parameters);
    Mockito
        .when(productDataImporterService.readInputData(ArgumentMatchers.any(InputStream.class),
            ArgumentMatchers.anyMap()))
        .thenThrow(new ServiceException(DataImporterErrorCode.ASSERT_MANAGER_UNAVAILABLE.toString(),
            DataImporterErrorCode.ASSERT_MANAGER_UNAVAILABLE.getErrorMessage()));
    productDataFileImporterServlet.doPost(slingHttpServletRequest, slingHttpServletResponse);
    final String resp = sw.toString();

    Assert.assertTrue(resp.contains(String.valueOf(HttpURLConnection.HTTP_UNAVAILABLE)));
    Assert.assertTrue(resp.contains("Service Unavailable"));
  }

  @Test
  public void testDoPost6() throws Exception {
    final SlingHttpServletRequest slingHttpServletRequest = Mockito
        .mock(SlingHttpServletRequest.class);
    final SlingHttpServletResponse slingHttpServletResponse = Mockito
        .mock(SlingHttpServletResponse.class);
    final Map<String, RequestParameter[]> parameterMap = new HashMap<String, RequestParameter[]>();
    final RequestParameter requestParameter = Mockito.mock(RequestParameter.class);
    Mockito.when(requestParameter.isFormField()).thenReturn(false);
    Mockito.when(requestParameter.getInputStream()).thenReturn(Mockito.mock(InputStream.class));
    final StringWriter sw = new StringWriter();

    parameterMap.put("file", new RequestParameter[] { requestParameter });
    final RequestParameterMap parameters = generateParamMap(parameterMap);

    Mockito.when(slingHttpServletRequest.getMethod()).thenReturn(HttpConstants.METHOD_POST);
    Mockito.when(slingHttpServletRequest.getMethod())
        .thenReturn(FileUploadBase.MULTIPART_FORM_DATA);
    PowerMockito.mockStatic(ServletFileUpload.class);
    PowerMockito.when(ServletFileUpload.isMultipartContent(slingHttpServletRequest))
        .thenReturn(true);
    Mockito.doNothing().when(slingHttpServletResponse)
        .setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
    Mockito.when(slingHttpServletResponse.getWriter()).thenReturn(new PrintWriter(sw));
    Mockito.when(slingHttpServletRequest.getRequestParameterMap()).thenReturn(parameters);
    Mockito
        .when(productDataImporterService.readInputData(ArgumentMatchers.any(InputStream.class),
            ArgumentMatchers.anyMap()))
        .thenThrow(new ServiceException(DataImporterErrorCode.ASSERT_MANAGER_UNAVAILABLE.toString(),
            DataImporterErrorCode.ASSERT_MANAGER_UNAVAILABLE.getErrorMessage()));
    productDataFileImporterServlet.doPost(slingHttpServletRequest, slingHttpServletResponse);
    final String resp = sw.toString();

    Assert.assertTrue(resp.contains(String.valueOf(HttpURLConnection.HTTP_UNAVAILABLE)));
    Assert.assertTrue(resp.contains("Service Unavailable"));
  }

  @Test
  public void testDoPost7() throws Exception {
    final SlingHttpServletRequest slingHttpServletRequest = Mockito
        .mock(SlingHttpServletRequest.class);
    final SlingHttpServletResponse slingHttpServletResponse = Mockito
        .mock(SlingHttpServletResponse.class);
    final Map<String, RequestParameter[]> parameterMap = new HashMap<String, RequestParameter[]>();
    final RequestParameter requestParameter = Mockito.mock(RequestParameter.class);
    Mockito.when(requestParameter.isFormField()).thenReturn(false);
    Mockito.when(requestParameter.getInputStream()).thenReturn(Mockito.mock(InputStream.class));
    final StringWriter sw = new StringWriter();

    parameterMap.put("file", new RequestParameter[] { requestParameter });
    final RequestParameterMap parameters = generateParamMap(parameterMap);

    Mockito.when(slingHttpServletRequest.getMethod()).thenReturn(HttpConstants.METHOD_POST);
    Mockito.when(slingHttpServletRequest.getMethod())
        .thenReturn(FileUploadBase.MULTIPART_FORM_DATA);
    PowerMockito.mockStatic(ServletFileUpload.class);
    PowerMockito.when(ServletFileUpload.isMultipartContent(slingHttpServletRequest))
        .thenReturn(true);
    Mockito.doNothing().when(slingHttpServletResponse)
        .setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
    Mockito.when(slingHttpServletResponse.getWriter()).thenReturn(new PrintWriter(sw));
    Mockito.when(slingHttpServletRequest.getRequestParameterMap()).thenReturn(parameters);
    Mockito
        .when(productDataImporterService.readInputData(ArgumentMatchers.any(InputStream.class),
            ArgumentMatchers.anyMap()))
        .thenThrow(new ServiceException(DataImporterErrorCode.INVALID_DAM_PATH.toString(),
            DataImporterErrorCode.INVALID_DAM_PATH.getErrorMessage()));
    productDataFileImporterServlet.doPost(slingHttpServletRequest, slingHttpServletResponse);
    final String resp = sw.toString();

    Assert.assertTrue(resp.contains(String.valueOf(HttpURLConnection.HTTP_INTERNAL_ERROR)));
    Assert.assertTrue(resp.contains("Internal Error"));
  }

  @Test
  public void testDoPost8() throws Exception {
    final SlingHttpServletRequest slingHttpServletRequest = Mockito
        .mock(SlingHttpServletRequest.class);
    final SlingHttpServletResponse slingHttpServletResponse = Mockito
        .mock(SlingHttpServletResponse.class);
    final Map<String, RequestParameter[]> parameterMap = new HashMap<String, RequestParameter[]>();
    final RequestParameter requestParameter = Mockito.mock(RequestParameter.class);
    Mockito.when(requestParameter.isFormField()).thenReturn(false);
    Mockito.when(requestParameter.getInputStream()).thenReturn(Mockito.mock(InputStream.class));
    final StringWriter sw = new StringWriter();

    parameterMap.put("file", new RequestParameter[] { requestParameter });
    final RequestParameterMap parameters = generateParamMap(parameterMap);

    Mockito.when(slingHttpServletRequest.getMethod()).thenReturn(HttpConstants.METHOD_POST);
    Mockito.when(slingHttpServletRequest.getMethod())
        .thenReturn(FileUploadBase.MULTIPART_FORM_DATA);
    PowerMockito.mockStatic(ServletFileUpload.class);
    PowerMockito.when(ServletFileUpload.isMultipartContent(slingHttpServletRequest))
        .thenReturn(true);
    Mockito.doNothing().when(slingHttpServletResponse)
        .setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
    Mockito.when(slingHttpServletResponse.getWriter()).thenReturn(new PrintWriter(sw));
    Mockito.when(slingHttpServletRequest.getRequestParameterMap()).thenReturn(parameters);
    Mockito
        .when(productDataImporterService.readInputData(ArgumentMatchers.any(InputStream.class),
            ArgumentMatchers.anyMap()))
        .thenThrow(new RuntimeException("Unknown error"));
    productDataFileImporterServlet.doPost(slingHttpServletRequest, slingHttpServletResponse);
    final String resp = sw.toString();

    Assert.assertTrue(resp.contains(String.valueOf(HttpURLConnection.HTTP_INTERNAL_ERROR)));
    Assert.assertTrue(resp.contains("Internal Error"));
  }

  /**
   * @param parameterMap
   * @return
   */
  private RequestParameterMap generateParamMap(final Map<String, RequestParameter[]> parameterMap) {
    final RequestParameterMap parameters = new RequestParameterMap() {
      @Override
      public int size() {
        return parameterMap.size();
      }

      @Override
      public boolean isEmpty() {
        return parameterMap.isEmpty();
      }

      @Override
      public boolean containsKey(Object key) {
        return parameterMap.containsKey(key);
      }

      @Override
      public boolean containsValue(Object value) {
        return parameterMap.containsValue(value);
      }

      @Override
      public RequestParameter[] get(Object key) {
        return parameterMap.get(key);
      }

      @Override
      public RequestParameter[] put(String key, RequestParameter[] value) {
        return parameterMap.put(key, value);
      }

      @Override
      public RequestParameter[] remove(Object key) {
        return parameterMap.remove(key);
      }

      @Override
      public void putAll(Map<? extends String, ? extends RequestParameter[]> m) {
        parameterMap.putAll(m);
      }

      @Override
      public void clear() {
        parameterMap.clear();
      }

      @Override
      public Set<String> keySet() {
        return parameterMap.keySet();
      }

      @Override
      public Collection<RequestParameter[]> values() {
        return parameterMap.values();
      }

      @Override
      public Set<java.util.Map.Entry<String, RequestParameter[]>> entrySet() {
        return parameterMap.entrySet();
      }

      @Override
      public RequestParameter getValue(String name) {
        return parameterMap.get(name)[0];
      }

      @Override
      public RequestParameter[] getValues(String name) {
        return parameterMap.get(name);
      }
    };
    return parameters;
  }
}
