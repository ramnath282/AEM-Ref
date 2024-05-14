package com.mattel.ecomm.core.utils.shopify;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.interfaces.ProductAvailability;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Product;
import com.mattel.ecomm.coreservices.core.pojos.shopify.ProductServiceResponse;

@RunWith(PowerMockRunner.class)
public class GTSummaryPageUIAdapterTest {

  @InjectMocks
  GTSummaryPageUIAdapter gtSummaryPageUIAdapter;

  @Mock
  ProductAvailability productAvailability;

  Map<String, Object> requestMap = null;
  Product product = null;

  public ProductServiceResponse getPDPInstance() throws Exception {
    try (InputStream is = getClass().getResourceAsStream("gtc011.json")) {
      final ObjectReader reader = ResourceMapper.getReaderInstance(ProductServiceResponse.class);
      final ProductServiceResponse response = reader.readValue(is);
      return response;
    }
  }

  @Before
  public void setup() throws Exception {
    ProductServiceResponse productServieResponse = getPDPInstance();
    requestMap = new HashMap<>();
    product = productServieResponse.getProduct();
    requestMap.put(Constant.STORE_KEY, "ag_en");
    requestMap.put(Constant.DOMAIN_KEY, "ag_en");
    requestMap.put(Constant.LANG_ID, "-1");
    requestMap.put(Constant.PART_NUMBER, "gvf40");
  }

  @Test
  public void testTransformProductToSignleSKU() {
    gtSummaryPageUIAdapter.transformProductToSingleSKU(product, productAvailability, requestMap);
  }

}
