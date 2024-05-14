package com.mattel.ecomm.pdp.core.servicesimpl;

import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.pojos.InventoryCheckChildResponse;
import com.mattel.ecomm.coreservices.core.pojos.InventoryCheckResponse;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(MockitoJUnitRunner.class)
public class ProductInventoryCheckServiceImplTest {
  private final String endPointUrl = "http://localhost:PORT_NUMBER/wcs/resources/store/STORE_ID/mattelInventoryAvailability/getProductInventory?updateCookies=true&collate=Y";
  private MockWebServer mockWebServer;
  @Mock
  private PropertyReaderService propertyReaderService;
  @InjectMocks
  private ProductInventoryCheckServiceImpl impl;

  @Test
  public void testFetchForSuccess() throws Exception {
    try (
        InputStream is1 = getClass()
            .getResourceAsStream("inventory_check_request.json");
        InputStream is2 = getClass()
            .getResourceAsStream("inventory_check_response.json")) {
      final ProductInventoryCheckServiceImpl.Config config = Mockito
          .mock(ProductInventoryCheckServiceImpl.Config.class);
      final MockResponse mockResponse = new MockResponse();
      final Map<String, Object> requestMap = new HashMap<>();
      final Cookie cookie = new Cookie("JSESSIONID", "213123132");
      final Cookie[] cookieObj = new Cookie[1];
      mockWebServer = new MockWebServer();
      final InventoryCheckResponse inventoryCheckResponse;
      final InventoryCheckChildResponse inventoryCheckChildResponse;

      mockResponse.setResponseCode(HttpStatus.SC_OK);
      mockResponse.setBody(IOUtils.toString(is2, StandardCharsets.UTF_8));
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
      requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(is1, StandardCharsets.UTF_8));
      requestMap.put(Constant.STORE_KEY, "AG");
      requestMap.put(Constant.DOMAIN_KEY, "AG");
      requestMap.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
      Mockito.when(config.endPoint())
          .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
      Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
      Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
      impl.activate(config);
      inventoryCheckResponse = impl.fetch(requestMap);

      Assert.assertNotNull(inventoryCheckResponse);
      Assert.assertNotNull(inventoryCheckResponse.getResponse());
      Assert.assertEquals(1, inventoryCheckResponse.getResponse().size());
      Assert.assertNotNull(inventoryCheckChildResponse = inventoryCheckResponse.getResponse().get(0));
      Assert.assertNotNull(inventoryCheckChildResponse.getComponentInventory());
      Assert.assertEquals(2, inventoryCheckChildResponse.getComponentInventory().size());
      Assert.assertEquals("DNJ75", inventoryCheckChildResponse.getComponentInventory().get(0).getParentPartNumber());
      Assert.assertEquals(2, inventoryCheckChildResponse.getComponentInventory().get(0).getSkuArr().size());
    } finally {
      if (mockWebServer != null)
        mockWebServer.shutdown();
    }
  }
}
