package com.mattel.ecomm.coreservices.core.pojos.shopify;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class VariantTest {
  private Variant variant;

  @Before
  public void setUp() throws Exception {
    variant = new Variant();
  }

  @Test
  public void testGetterSetter() {
    VariantCore core = Mockito.mock(VariantCore.class);
    Inventory inventory = Mockito.mock(Inventory.class);
    Pricing pricing = Mockito.mock(Pricing.class);

    variant.setCore(core);
    variant.setId(1234);
    variant.setInventory(inventory);
    variant.setPricing(pricing);
    variant.setProduct_id(123456);
    variant.toString();

    Assert.assertEquals(1234, variant.getId());
    Assert.assertEquals(123456, variant.getProduct_id());
    Assert.assertEquals(core, variant.getCore());
    Assert.assertEquals(inventory, variant.getInventory());
    Assert.assertEquals(pricing, variant.getPricing());
  }
}
