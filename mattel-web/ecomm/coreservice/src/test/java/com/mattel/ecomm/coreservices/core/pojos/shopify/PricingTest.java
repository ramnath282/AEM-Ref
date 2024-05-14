package com.mattel.ecomm.coreservices.core.pojos.shopify;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PricingTest {
  private Pricing pricing;

  @Before
  public void setUp() throws Exception {
    pricing = new Pricing();
  }

  @Test
  public void testGetterSetter() {
    pricing.setCompare_at_price("compare_at_price");
    pricing.setPrice("price");
    Assert.assertEquals("compare_at_price", pricing.getCompare_at_price());
    Assert.assertEquals("price", pricing.getPrice());
    pricing.toString();
  }
}
