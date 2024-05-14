package com.mattel.ecomm.pdp.core.servicesimpl;

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
import com.mattel.ecomm.coreservices.core.pojos.shopify.Core;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Product;
import com.mattel.ecomm.coreservices.core.pojos.shopify.ProductServiceResponse;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Variant;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(MockitoJUnitRunner.class)
public class ProductAvailabilityImplTest {
  private final String endPointUrl = "http://localhost:PORT_NUMBER/a/product/PART_NUMBER.json";

  private MockWebServer mockWebServer;

  @Mock
  private PropertyReaderService propertyReaderService;
  @InjectMocks
  private ProductAvailabilityImpl productAvailabilityImpl;

  @Test
  public void testFetch() throws Exception {
    try (InputStream is = getClass().getResourceAsStream("fgd39.json")) {
      final ProductAvailabilityImpl.Config config = Mockito.mock(ProductAvailabilityImpl.Config.class);
      final MockResponse mockResponse = new MockResponse();
      final Map<String, Object> requestMap = new HashMap<>();
      final ProductServiceResponse productServiceResponse;
      final Product product;
      final Core core;
      final Variant variant;
      mockWebServer = new MockWebServer();

      mockResponse.setResponseCode(HttpStatus.SC_OK);
      mockResponse.setBody(IOUtils.toString(is, StandardCharsets.UTF_8));
      mockWebServer.enqueue(mockResponse);
      mockWebServer.start();
      requestMap.put(Constant.STORE_KEY, "AG");
      requestMap.put(Constant.DOMAIN_KEY, "AG");
      requestMap.put(Constant.PART_NUMBER, "fgd39");
      Mockito.when(config.endPoint())
          .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
      Mockito.when(propertyReaderService.getHttpServiceDefConnectTimeout("AG")).thenReturn(15000);
      productAvailabilityImpl.activate(config);
      productServiceResponse = productAvailabilityImpl.fetch(requestMap);
      Assert.assertNotNull(productServiceResponse);
      product = productServiceResponse.getProduct();
      Assert.assertNotNull(product);
      Assert.assertEquals(769737883736l, product.getProduct_id());
      core = product.getCore();
      Assert.assertNotNull(core);
      Assert.assertEquals("Willa&#8482; Doll", core.getTitle());
      Assert.assertEquals(
          "Willa has a love for animals. This 14.5 inch (36.8 cm) doll is sized just right for younger girls. She has hazel eyes and strawberry-blond hair in pigtails that can be brushed and styled.",
          core.getBody_html());
      Assert.assertEquals("ItemBean", core.getProduct_type());
      Assert.assertEquals("willa-doll-fgd39", core.getHandle());
      Assert.assertEquals("Willa Doll | WellieWishers | American Girl", core.getGlobal_title_tag());
      Assert.assertEquals("FGD39", core.getProduct_partnumber());
      Assert.assertEquals("https://mattel.scene7.com/is/image/Mattel/DNJ70_Viewer", core.getProduct_imagelink());
      Assert.assertEquals("DNJ70_Viewer", core.getProduct_fullimage());
      Assert.assertEquals("ALL", core.getProduct_custsegexcl());
      Assert.assertEquals("Y", core.getProduct_isretailinventorycheckenabled());
      Assert.assertNotNull(product.getImages());
      Assert.assertEquals(1, product.getImages().size());
      Assert.assertNotNull(product.getVariants());
      Assert.assertEquals(1, product.getVariants().size());
      variant = product.getVariants().get(0);
      Assert.assertEquals(34360234704940l, variant.getId());
      Assert.assertEquals(0l, variant.getProduct_id());
      Assert.assertEquals("FGD39", variant.getCore().getSku());
      Assert.assertNotNull(variant.getPricing());
      Assert.assertEquals("60.00", variant.getPricing().getCompare_at_price());
      Assert.assertEquals("60.00", variant.getPricing().getPrice());
    }
  }

  @Test(expected=ServiceException.class)
  public void testFetchProductNotFound() throws Exception {
    try (InputStream is = getClass().getResourceAsStream("product-availability-404.json")) {
      final ProductAvailabilityImpl.Config config = Mockito.mock(ProductAvailabilityImpl.Config.class);
      final MockResponse mockResponse = new MockResponse();
      final Map<String, Object> requestMap = new HashMap<>();
      mockWebServer = new MockWebServer();

      mockResponse.setResponseCode(HttpStatus.SC_NOT_FOUND);
      mockResponse.setBody(IOUtils.toString(is, StandardCharsets.UTF_8));
      mockWebServer.enqueue(mockResponse);
      mockWebServer.start();
      requestMap.put(Constant.STORE_KEY, "AG");
      requestMap.put(Constant.DOMAIN_KEY, "AG");
      requestMap.put(Constant.PART_NUMBER, "06BUN200");
      Mockito.when(config.endPoint())
          .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
      Mockito.when(propertyReaderService.getHttpServiceDefConnectTimeout("AG")).thenReturn(15000);
      productAvailabilityImpl.activate(config);
      productAvailabilityImpl.fetch(requestMap);
    }
  }

  @Test(expected=ServiceException.class)
  public void testFetchInvalidPartNumber() throws Exception {
      final ProductAvailabilityImpl.Config config = Mockito.mock(ProductAvailabilityImpl.Config.class);
      final Map<String, Object> requestMap = new HashMap<>();

      requestMap.put(Constant.STORE_KEY, "AG");
      requestMap.put(Constant.DOMAIN_KEY, "AG");
      requestMap.put(Constant.PART_NUMBER, null);
      Mockito.when(config.endPoint())
          .thenReturn(endPointUrl);
      productAvailabilityImpl.activate(config);
      productAvailabilityImpl.fetch(requestMap);
  }
}
