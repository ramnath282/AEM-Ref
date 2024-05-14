package com.mattel.ecomm.productdetails.core.servicesimpl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.GiftCardMessageValidity;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(MockitoJUnitRunner.class)
public class CheckGiftCardMessageValidityImplTest {

  private final String endPointUrl = "http://localhost:PORT_NUMBER/wcs/resources/store/STORE_ID/xchecknames/checkmsg?responseFormat=json&updateCookies=true&storeId=STORE_ID";

  private MockWebServer mockWebServer;

  private GiftCardMessageValidity serviceResponse;

  @Mock
  private PropertyReaderService propertyReaderService;

  @InjectMocks
  private CheckGiftCardMessageValidityImpl impl;

  @Test
  public void testverify() throws IOException, ServiceException {
    try (InputStream is1 = getClass().getResourceAsStream("check_giftCard_request.json");
        InputStream is2 = getClass().getResourceAsStream("check_giftCard_response.json")) {
      final CheckGiftCardMessageValidityImpl.Config config = Mockito
          .mock(CheckGiftCardMessageValidityImpl.Config.class);
      final MockResponse mockResponse = new MockResponse();
      final Map<String, Object> requestMap = new HashMap<>();
      mockWebServer = new MockWebServer();
      mockResponse.setResponseCode(HttpStatus.SC_OK);
      mockResponse.setBody(IOUtils.toString(is2, StandardCharsets.UTF_8));
      mockWebServer.enqueue(mockResponse);
      mockWebServer.start();
      requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(is1, StandardCharsets.UTF_8));
      requestMap.put(Constant.STORE_KEY, "AG");
      requestMap.put(Constant.DOMAIN_KEY, "AG");
      Mockito.when(config.endPoint())
          .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
      Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
      impl.activate(config);
      serviceResponse = impl.verify(requestMap);
      Assert.assertNotNull(serviceResponse);
    } finally {
      if (mockWebServer != null)
        mockWebServer.shutdown();
    }
  }

  @Test(expected = ServiceException.class)
  public void testverify_withWCSError() throws IOException, ServiceException {
    try (InputStream is1 = getClass().getResourceAsStream("check_giftCard_request.json")) {
      final CheckGiftCardMessageValidityImpl.Config config = Mockito
          .mock(CheckGiftCardMessageValidityImpl.Config.class);
      final MockResponse mockResponse = new MockResponse();
      final Map<String, Object> requestMap = new HashMap<>();
      mockWebServer = new MockWebServer();
      mockResponse.setResponseCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
      mockWebServer.enqueue(mockResponse);
      mockWebServer.start();
      requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(is1, StandardCharsets.UTF_8));
      requestMap.put(Constant.STORE_KEY, "AG");
      requestMap.put(Constant.DOMAIN_KEY, "AG");
      Mockito.when(config.endPoint())
          .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
      Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
      impl.activate(config);
      impl.verify(requestMap);

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

  @Test(expected = ServiceException.class)
  public void testverify_withWCSDown() throws IOException, ServiceException {
    try (InputStream is1 = getClass().getResourceAsStream("check_giftCard_request.json")) {
      final CheckGiftCardMessageValidityImpl.Config config = Mockito
          .mock(CheckGiftCardMessageValidityImpl.Config.class);
      final MockResponse mockResponse = new MockResponse();
      final Map<String, Object> requestMap = new HashMap<>();
      mockWebServer = new MockWebServer();
      mockWebServer.enqueue(mockResponse);
      mockWebServer.start();
      requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(is1, StandardCharsets.UTF_8));
      requestMap.put(Constant.STORE_KEY, "AG");
      requestMap.put(Constant.DOMAIN_KEY, "AG");
      Mockito.when(config.endPoint())
          .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
      Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
      impl.activate(config);
      impl.verify(requestMap);

    } catch (final ServiceException serviceException) {
      Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
      Assert.assertEquals("IO Exception Occured", serviceException.getErrorMessage());
      throw serviceException;
    } finally {
      if (mockWebServer != null)
        mockWebServer.shutdown();
    }
  }

  @Test(expected = ServiceException.class)
  public void testverify_withNullPayLoad() throws IOException, ServiceException {
    try {
      final CheckGiftCardMessageValidityImpl.Config config = Mockito
          .mock(CheckGiftCardMessageValidityImpl.Config.class);
      final MockResponse mockResponse = new MockResponse();
      final Map<String, Object> requestMap = new HashMap<>();
      mockWebServer = new MockWebServer();
      mockWebServer.enqueue(mockResponse);
      mockWebServer.start();
      requestMap.put(Constant.REQUEST_BODY, "[]");
      requestMap.put(Constant.STORE_KEY, "AG");
      requestMap.put(Constant.DOMAIN_KEY, "AG");
      Mockito.when(config.endPoint())
          .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
      impl.activate(config);
      impl.verify(requestMap);

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
