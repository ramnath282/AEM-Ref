package com.mattel.ecomm.loyaltyrewards.core.servicesimpl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.CustomerResponse;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {
  private final String endPointUrl = "https://api.dev.mattel.com/qa/consumer/fetchloyaltydetails";

  private MockWebServer mockWebServer;

  private CustomerResponse customerResponse;

  @InjectMocks
  private CustomerServiceImpl customerServiceImpl;
  @Mock
  PropertyReaderService propertyReaderService;

  @Ignore
  @Test
  public void testCustomerService() throws IOException, ServiceException {

    try (InputStream is1 = getClass()
        .getResourceAsStream("customer_response.json");) {
      final CustomerServiceImpl.Config config = Mockito
          .mock(CustomerServiceImpl.Config.class);
      final MockResponse mockResponse = new MockResponse();
      final Map<String, Object> requestMap = new HashMap<>();
      mockWebServer = new MockWebServer();
      mockResponse.setResponseCode(HttpStatus.SC_OK);
      mockResponse.setBody(IOUtils.toString(is1, StandardCharsets.UTF_8));
      mockWebServer.enqueue(mockResponse);
      mockWebServer.start();
      requestMap.put(Constant.STORE_KEY, "AG");
      Mockito.when(config.endPoint())
          .thenReturn(endPointUrl);
      Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
      Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
      customerServiceImpl.activate(config);
      customerResponse = customerServiceImpl.fetch(requestMap);
      Assert.assertNotNull(customerResponse);

    } finally {
      if (mockWebServer != null)
        mockWebServer.shutdown();
    }
  }
  
  @Ignore
  @Test(expected = ServiceException.class)
  public void testCustomerServiceWithEAIError() throws IOException, ServiceException {
    try (InputStream is1 = getClass()
        .getResourceAsStream("customer_request.json")) {
      final CustomerServiceImpl.Config config = Mockito
          .mock(CustomerServiceImpl.Config.class);
      final MockResponse mockResponse = new MockResponse();
      final Map<String, Object> requestMap = new HashMap<>();
      mockWebServer = new MockWebServer();
      mockResponse.setResponseCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
      mockWebServer.enqueue(mockResponse);
      mockWebServer.start();
      requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(is1, StandardCharsets.UTF_8));
      requestMap.put(Constant.STORE_KEY, "AG");
      Mockito.when(config.endPoint())
          .thenReturn(endPointUrl);
      Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
      Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
      customerServiceImpl.activate(config);
      customerServiceImpl.fetch(requestMap);

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
  public void testCustomerServiceWithEAIDown() throws IOException, ServiceException {
    try (InputStream is1 = getClass()
        .getResourceAsStream("counsumer_loyalty_rewards_request.json")) {
      final CustomerServiceImpl.Config config = Mockito
          .mock(CustomerServiceImpl.Config.class);
      final MockResponse mockResponse = new MockResponse();
      final Map<String, Object> requestMap = new HashMap<>();
      mockWebServer = new MockWebServer();
      mockWebServer.enqueue(mockResponse);
      mockWebServer.start();
      requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(is1, StandardCharsets.UTF_8));
      requestMap.put(Constant.STORE_KEY, "AG");
      Mockito.when(config.endPoint())
          .thenReturn(endPointUrl);
      Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
      Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
      customerServiceImpl.activate(config);
      customerServiceImpl.fetch(requestMap);

    } catch (final ServiceException serviceException) {
      Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
      Assert.assertEquals("IO Exception Occured", serviceException.getErrorMessage());
      throw serviceException;
    } finally {
      if (mockWebServer != null)
        mockWebServer.shutdown();
    }
  }
}
