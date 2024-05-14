package com.mattel.ecomm.coreservices.core.pojos.shopify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AssociatedProductTest {
  private AssociatedProduct impl;
  private Core core;
  private Variant variant;

  @Before
  public void setUp() throws Exception {
    variant = new Variant();
    core = new Core();
    core.setTitle("Ear Piercing Service");
    variant.setId(42342342l);
    impl = new AssociatedProduct();
    impl.setPartnumber("WWEP");
    impl.setId(4234243243l);
    impl.setAttributes(new HashMap<>());
    impl.setCore(core);
    impl.setOptions(null);
    impl.setImages(new ArrayList<>());
    impl.setVariants(Arrays.asList(variant));
  }

  @Test
  public void testGetPartnumber() throws Exception {
    Assert.assertEquals("WWEP", impl.getPartnumber());
  }

  @Test
  public void testGetId() throws Exception {
    Assert.assertEquals(4234243243l, impl.getId());
  }

  @Test
  public void testGetCore() throws Exception {
    Assert.assertEquals(core, impl.getCore());
  }

  @Test
  public void testGetOptions() throws Exception {
    Assert.assertNull(impl.getOptions());
  }

  @Test
  public void testGetVariants() throws Exception {
    Assert.assertNotNull(impl.getVariants());
    Assert.assertEquals(1, impl.getVariants().size());
    Assert.assertEquals(variant, impl.getVariants().get(0));
  }

  @Test
  public void testGetAttributes() throws Exception {
    Assert.assertNotNull(impl.getAttributes());
  }

  @Test
  public void testGetImages() throws Exception {
    Assert.assertNotNull(impl.getImages());
  }

  @Test
  public void testGetAssociations() throws Exception {
    Assert.assertNull(impl.getAssociations());
  }
}
