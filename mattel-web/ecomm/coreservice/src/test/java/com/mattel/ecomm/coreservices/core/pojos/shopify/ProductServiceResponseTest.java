package com.mattel.ecomm.coreservices.core.pojos.shopify;

import org.junit.Before;
import org.junit.Test;

public class ProductServiceResponseTest {
  private ProductServiceResponse productServiceResponse;

  @Before
  public void setUp() throws Exception {
    productServiceResponse = new ProductServiceResponse();
  }

  @Test
  public void testGetterSetter() {
    Product product = new Product();
    productServiceResponse.setProduct(product);
    productServiceResponse.toString();
  }
}
