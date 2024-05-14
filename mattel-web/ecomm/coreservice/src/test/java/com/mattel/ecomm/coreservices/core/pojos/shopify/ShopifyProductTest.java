package com.mattel.ecomm.coreservices.core.pojos.shopify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ShopifyProductTest {
  private Product shopifyProduct;

  @Before
  public void setUp() throws Exception {
    shopifyProduct = new Product();
  }

  @Test
  public void testGetterSetter() {
    List<Association> associations = new ArrayList<>();
    Map<String, Object> attributes = new HashMap<>();
    Core core = new Core();
    List<Map<String, Object>> images = new ArrayList<>();
    List<Option> options = new ArrayList<>();
    List<Variant> variants = new ArrayList<>();

    shopifyProduct.setAssociations(associations);
    shopifyProduct.setAttributes(attributes);
    shopifyProduct.setCore(core);
    shopifyProduct.setId(1234);
    shopifyProduct.setImages(images);
    shopifyProduct.setOptions(options);
    shopifyProduct.setProduct_id(123456);
    shopifyProduct.setVariants(variants);

    Assert.assertEquals(associations, shopifyProduct.getAssociations());
    Assert.assertEquals(attributes, shopifyProduct.getAttributes());
    Assert.assertEquals(core, shopifyProduct.getCore());
    Assert.assertEquals(1234, shopifyProduct.getId());
    Assert.assertEquals(images, shopifyProduct.getImages());
    Assert.assertEquals(options, shopifyProduct.getOptions());
    Assert.assertEquals(123456, shopifyProduct.getProduct_id());
    Assert.assertEquals(variants, shopifyProduct.getVariants());

    shopifyProduct.toString();
  }
}
