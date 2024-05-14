package com.mattel.ecomm.coreservices.core.pojos.schema;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BaseSchemaTest {
  private BaseSchema impl;

  @Before
  public void setUp() throws Exception {
    impl = new BaseSchema();
    impl.setBrand(SchemaConstants.BRAND);
    impl.setContext(SchemaConstants.CONTEXT);
    impl.setDescription(
        "The 18&quot; Blaire doll has bright green eyes that open and close, and curly red hair. She has a cloth body, and movable head and limbs made of smooth vinyl.");
    impl.setGtin13("887961717327");
    impl.setGtin8("12345678");
    impl.setImage("https://mattel.scene7.com/is/image/Mattel/GBL30_Viewer");
    impl.setName("Blaire Doll & Book");
    impl.setSku("GBL30");
    impl.setType("ItemBean");
  }

  @Test
  public void testGetBrand() {
    Assert.assertEquals(SchemaConstants.BRAND, impl.getBrand());
  }

  @Test
  public void testGetContext() {
    Assert.assertEquals(SchemaConstants.CONTEXT, impl.getContext());
  }

  @Test
  public void testGetDescription() {
    Assert.assertEquals(
        "The 18&quot; Blaire doll has bright green eyes that open and close, and curly red hair. She has a cloth body, and movable head and limbs made of smooth vinyl.",
        impl.getDescription());
  }

  @Test
  public void testGetGtin13() {
    Assert.assertEquals("887961717327", impl.getGtin13());
  }

  @Test
  public void testGetGtin8() {
    Assert.assertEquals("12345678", impl.getGtin8());
  }

  @Test
  public void testGetImage() {
    Assert.assertEquals("https://mattel.scene7.com/is/image/Mattel/GBL30_Viewer", impl.getImage());
  }

  @Test
  public void testGetName() {
    Assert.assertEquals("Blaire Doll & Book", impl.getName());
  }

  @Test
  public void testGetSku() {
    Assert.assertEquals("GBL30", impl.getSku());
  }

  @Test
  public void testGetType() {
    Assert.assertEquals("ItemBean", impl.getType());
  }

  @Test
  public void testToString() {
    Assert.assertNotNull(impl.toString());
  }
}
