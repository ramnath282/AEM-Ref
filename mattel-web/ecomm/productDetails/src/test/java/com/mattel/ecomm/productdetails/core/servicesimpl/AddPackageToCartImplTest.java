package com.mattel.ecomm.productdetails.core.servicesimpl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
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
import com.mattel.ecomm.coreservices.core.pojos.productdetailspojos.AddAddOnServiceResponse;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(MockitoJUnitRunner.class)
public class AddPackageToCartImplTest {
  private final String endPointUrl = "http://localhost:PORT_NUMBER/wcs/resources/store/STORE_ID/xuser/register?storeId=STORE_ID&responseFormat=json";
  private MockWebServer mockWebServer;
  @Mock
  private PropertyReaderService propertyReaderService;
  @InjectMocks
  private AddPackageToCartImpl addPackageToCart;

  @Test
  public void testAddPackageToCartServiceSuccessScenerio() throws ServiceException, IOException {
    try (
        InputStream is1 = getClass()
            .getResourceAsStream("add_package_to_cart_service_request.json");
        InputStream is2 = getClass().getResourceAsStream("add_addOn_service_response.json")) {
      final AddPackageToCartImpl.Config config = Mockito.mock(AddPackageToCartImpl.Config.class);
      final MockResponse mockResponse = new MockResponse();
      final Map<String, Object> requestMap = new HashMap<>();
      AddAddOnServiceResponse addAddOnServiceResponse;

      mockWebServer = new MockWebServer();
      mockResponse.setBody(IOUtils.toString(is2, StandardCharsets.UTF_8));
      addCookies(mockResponse);
      mockWebServer.enqueue(mockResponse);
      mockWebServer.start();
      requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(is1, StandardCharsets.UTF_8));
      requestMap.put(Constant.STORE_KEY, "AG");
      requestMap.put(Constant.DOMAIN_KEY, "AG");
      Mockito.when(config.endPoint())
          .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
      Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
      Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
      addPackageToCart.activate(config);
      addAddOnServiceResponse = addPackageToCart.addPackageService(requestMap);

      Assert.assertNotNull(addAddOnServiceResponse);

      Assert.assertNotNull(addAddOnServiceResponse.getOrderId());
      Assert.assertEquals(1, addAddOnServiceResponse.getOrderId().length);

      Assert.assertEquals("1403695001", addAddOnServiceResponse.getOrderId()[0]);
      Assert.assertEquals("1416681002", addAddOnServiceResponse.getOrderItemId()[0]);
    } finally {
      if (mockWebServer != null)
        mockWebServer.shutdown();
    }
  }

  @Test(expected = ServiceException.class)
  public void testAddPackageToCartServiceWCSErrorScenerio() throws IOException, ServiceException {
    try (InputStream is = getClass()
        .getResourceAsStream("add_package_to_cart_service_request.json")) {
      final AddPackageToCartImpl.Config config = Mockito.mock(AddPackageToCartImpl.Config.class);
      final MockResponse mockResponse = new MockResponse();
      final Map<String, Object> requestMap = new HashMap<>();

      mockWebServer = new MockWebServer();
      mockResponse.setResponseCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
      mockWebServer.enqueue(mockResponse);
      mockWebServer.start();
      requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(is, StandardCharsets.UTF_8));
      requestMap.put(Constant.STORE_KEY, "AG");
      requestMap.put(Constant.DOMAIN_KEY, "AG");
      Mockito.when(config.endPoint())
          .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
      Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
      Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
      addPackageToCart.activate(config);
      addPackageToCart.addPackageService(requestMap);
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
  public void testAddPackageToCartServiceWCSDownScenerio() throws IOException, ServiceException {
    try (InputStream is = getClass()
        .getResourceAsStream("add_package_to_cart_service_request.json")) {
      final AddPackageToCartImpl.Config config = Mockito.mock(AddPackageToCartImpl.Config.class);
      final Map<String, Object> requestMap = new HashMap<>();

      requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(is, StandardCharsets.UTF_8));
      requestMap.put(Constant.STORE_KEY, "AG");
      requestMap.put(Constant.DOMAIN_KEY, "AG");
      Mockito.when(config.endPoint())
          .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(8080)));
      Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
      Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
      addPackageToCart.activate(config);
      addPackageToCart.addPackageService(requestMap);
    } catch (final ServiceException serviceException) {
      Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
      Assert.assertEquals("IO Exception Occured", serviceException.getErrorMessage());
      throw serviceException;
    }
  }

  private void addCookies(final MockResponse mockResponse) {
    mockResponse.addHeader("Set-Cookie",
        "JSESSIONID=0000Ai-NPQF6j4iYMwCVf_brFbR:1b7o43dnq; path=/; domain=localhost; HttpOnly; Expires=Tue, 19 Jan 2038 03:14:07 GMT;");
    mockResponse.addHeader("Set-Cookie",
        "WC_SESSION_ESTABLISHED=true; path=/; domain=localhost; Expires=Tue, 19 Jan 2038 03:14:07 GMT;");
  }
}
