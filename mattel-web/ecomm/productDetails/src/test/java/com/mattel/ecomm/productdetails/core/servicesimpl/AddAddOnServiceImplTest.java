package com.mattel.ecomm.productdetails.core.servicesimpl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.productdetailspojos.AddAddOnServiceResponse;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(MockitoJUnitRunner.class)
public class AddAddOnServiceImplTest {
  private final String endPointUrl = "http://localhost:PORT_NUMBER/wcs/resources/store/10651/xupdatecart/addaos?responseFormat=json&updateCookies=true&storeId=STORE_ID";
  private MockWebServer mockWebServer;
  @Mock
  private PropertyReaderService propertyReaderService;

  @InjectMocks
  private AddAddOnServiceImpl impl;

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testAddAddOnService() throws ServiceException, IOException {
    try (InputStream is1 = getClass().getResourceAsStream("add_addOn_service_request.json");
        InputStream is2 = getClass().getResourceAsStream("add_addOn_service_response.json")) {
      final AddAddOnServiceImpl.Config config = Mockito.mock(AddAddOnServiceImpl.Config.class);
      final MockResponse mockResponse = new MockResponse();
      mockResponse.setResponseCode(HttpStatus.SC_OK);
      mockResponse.setBody(IOUtils.toString(is2, StandardCharsets.UTF_8));
      final Map<String, Object> requestHeader = new HashMap<>();
      final Cookie cookie = new Cookie("JSESSIONID", "213123132");
      final Cookie[] cookieObj = new Cookie[1];
      mockWebServer = new MockWebServer();
      mockWebServer.enqueue(mockResponse);
      mockWebServer.start();
      cookie.setComment("HttpOnly");
      cookie.setDomain("pattern");
      cookie.setMaxAge(2);
      cookie.setPath("uri");
      cookie.setSecure(true);
      cookie.setValue("newValue");
      cookie.setVersion(1);
      cookieObj[0] = cookie;
      requestHeader.put(Constant.STORE_KEY, "AG");
      requestHeader.put(Constant.DOMAIN_KEY, "AG");
      requestHeader.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
      requestHeader.put(Constant.REQUEST_BODY, IOUtils.toString(is1, StandardCharsets.UTF_8));
      Mockito.when(config.endPoint())
          .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
      Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
      Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
      impl.activate(config);
      AddAddOnServiceResponse serviceResponse = impl.addAddOnService(requestHeader);
      Assert.assertNotNull(serviceResponse);
      Assert.assertEquals("1", serviceResponse.getParentQuantity());
      Assert.assertEquals("1403695001", serviceResponse.getOrderId()[0]);
    } finally {
      if (mockWebServer != null)
        mockWebServer.shutdown();
    }
  }

  @Test(expected = ServiceException.class)
  public void testAddAddOnService_withWCSError() throws ServiceException, IOException {
    try (InputStream is1 = getClass().getResourceAsStream("add_addOn_service_request.json")) {
      final AddAddOnServiceImpl.Config config = Mockito.mock(AddAddOnServiceImpl.Config.class);
      final MockResponse mockResponse = new MockResponse();
      mockResponse.setResponseCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
      final Map<String, Object> requestHeader = new HashMap<>();
      final Cookie cookie = new Cookie("JSESSIONID", "213123132");
      final Cookie[] cookieObj = new Cookie[1];
      mockWebServer = new MockWebServer();
      mockWebServer.enqueue(mockResponse);
      mockWebServer.start();
      cookie.setComment("HttpOnly");
      cookie.setDomain("pattern");
      cookie.setMaxAge(2);
      cookie.setPath("uri");
      cookie.setSecure(true);
      cookie.setValue("newValue");
      cookie.setVersion(1);
      cookieObj[0] = cookie;
      requestHeader.put(Constant.STORE_KEY, "AG");
      requestHeader.put(Constant.DOMAIN_KEY, "AG");
      requestHeader.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
      requestHeader.put(Constant.REQUEST_BODY, IOUtils.toString(is1, StandardCharsets.UTF_8));
      Mockito.when(config.endPoint())
          .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
      Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
      Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
      impl.activate(config);
      impl.addAddOnService(requestHeader);
    } catch (final ServiceException serviceException) {
      Assert.assertEquals(String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR),
          serviceException.getErrorKey());
      Assert.assertEquals("Generic Error Ocuured", serviceException.getErrorMessage());
      throw serviceException;
    } finally {
      if (mockWebServer != null)
        mockWebServer.shutdown();
    }
  }

  @Ignore
  @Test(expected = ServiceException.class)
  public void testAddAddOnService_withWCSDown() throws ServiceException, IOException {
    try (InputStream is1 = getClass().getResourceAsStream("add_addOn_service_request.json")) {
      final AddAddOnServiceImpl.Config config = Mockito.mock(AddAddOnServiceImpl.Config.class);
      final MockResponse mockResponse = new MockResponse();
      final Map<String, Object> requestHeader = new HashMap<>();
      final Cookie cookie = new Cookie("JSESSIONID", "213123132");
      final Cookie[] cookieObj = new Cookie[1];
      mockWebServer = new MockWebServer();
      mockWebServer.enqueue(mockResponse);
      mockWebServer.start();
      cookie.setComment("HttpOnly");
      cookie.setDomain("pattern");
      cookie.setMaxAge(2);
      cookie.setPath("uri");
      cookie.setSecure(true);
      cookie.setValue("newValue");
      cookie.setVersion(1);
      cookieObj[0] = cookie;
      requestHeader.put(Constant.STORE_KEY, "AG");
      requestHeader.put(Constant.DOMAIN_KEY, "AG");
      requestHeader.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
      requestHeader.put(Constant.REQUEST_BODY, IOUtils.toString(is1, StandardCharsets.UTF_8));
      Mockito.when(config.endPoint())
          .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
      Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
      Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
      impl.activate(config);
      impl.addAddOnService(requestHeader);
    } catch (final ServiceException serviceException) {
      System.out
          .println(serviceException.getErrorKey() + "   " + serviceException.getErrorMessage());
      Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
      Assert.assertEquals("IO Exception Occured", serviceException.getErrorMessage());
      throw serviceException;
    } finally {
      if (mockWebServer != null)
        mockWebServer.shutdown();
    }
  }

  @Test(expected = ServiceException.class)
  public void testAddAddOnService_withNullRequestPayload() throws ServiceException, IOException {
    try {
      final AddAddOnServiceImpl.Config config = Mockito.mock(AddAddOnServiceImpl.Config.class);
      final MockResponse mockResponse = new MockResponse();
      mockResponse.setResponseCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
      final Map<String, Object> requestHeader = new HashMap<>();
      final Cookie cookie = new Cookie("JSESSIONID", "213123132");
      final Cookie[] cookieObj = new Cookie[1];
      mockWebServer = new MockWebServer();
      mockResponse.setBody("[]");
      mockWebServer.enqueue(mockResponse);
      mockWebServer.start();
      cookie.setComment("HttpOnly");
      cookie.setDomain("pattern");
      cookie.setMaxAge(2);
      cookie.setPath("uri");
      cookie.setSecure(true);
      cookie.setValue("newValue");
      cookie.setVersion(1);
      cookieObj[0] = cookie;
      requestHeader.put(Constant.STORE_KEY, "AG");
      requestHeader.put(Constant.DOMAIN_KEY, "AG");
      requestHeader.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
      requestHeader.put(Constant.REQUEST_BODY, "[]");
      Mockito.when(config.endPoint())
          .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
      impl.activate(config);
      impl.addAddOnService(requestHeader);
    } catch (final ServiceException serviceException) {
      Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
      Assert.assertEquals("IO Exception thrown from message body",
          serviceException.getErrorMessage());
      throw serviceException;
    } finally {
      if (mockWebServer != null)
        mockWebServer.shutdown();
    }
  }

}
