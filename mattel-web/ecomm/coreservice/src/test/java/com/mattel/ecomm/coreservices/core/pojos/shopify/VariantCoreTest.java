package com.mattel.ecomm.coreservices.core.pojos.shopify;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VariantCoreTest {
  private VariantCore variantCore;

  @Before
  public void setUp() throws Exception {
    variantCore = new VariantCore();
  }

  @Test
  public void testGetterSetter() {
    variantCore.setSku("sku");
    variantCore.setPosition(1);
    variantCore.setOption1("option1");
    variantCore.setOption2("option2");
    variantCore.setOption3("option3");
    variantCore.setVariant_backorderdate("variant_backorderdate");
    variantCore.setVariant_buyable("variant_buyable");
    variantCore.setVariant_fullimage("variant_fullimage");
    variantCore.setVariant_imageLink("variant_imageLink");
    variantCore.setVariant_inventorystatus("variant_inventorystatus");
    variantCore.setVariant_parentpartnumber("variant_parentpartnumber");
    variantCore.setVariant_product_type("variant_product_type");
    variantCore.setVariant_swatch("variant_swatch");
    variantCore.setVariant_thumbnail("variant_thumbnail");
    variantCore.setVariant_itematpreceiptid("1234567890");

    Assert.assertEquals("sku", variantCore.getSku());
    Assert.assertNotNull(variantCore.getPosition());
    Assert.assertEquals("option1", variantCore.getOption1());
    Assert.assertEquals("option2", variantCore.getOption2());
    Assert.assertEquals("option3", variantCore.getOption3());

    Assert.assertEquals("variant_backorderdate", variantCore.getVariant_backorderdate());
    Assert.assertEquals("variant_buyable", variantCore.getVariant_buyable());
    Assert.assertEquals("variant_fullimage", variantCore.getVariant_fullimage());
    Assert.assertEquals("variant_imageLink", variantCore.getVariant_imageLink());
    Assert.assertEquals("variant_inventorystatus", variantCore.getVariant_inventorystatus());
    Assert.assertEquals("variant_product_type", variantCore.getVariant_product_type());
    Assert.assertEquals("variant_swatch", variantCore.getVariant_swatch());
    Assert.assertEquals("variant_thumbnail", variantCore.getVariant_thumbnail());
    Assert.assertEquals("variant_parentpartnumber", variantCore.getVariant_parentpartnumber());
    Assert.assertEquals("1234567890", variantCore.getVariant_itematpreceiptid());
    variantCore.toString();
  }
}
