package com.mattel.ecomm.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProductTypeTemplateConfigPojoTest {

  private ProductTypeTemplateConfigPojo productTypeTemplateConfigPojo = null;

  @Before
  public void setUp() throws Exception {
    productTypeTemplateConfigPojo = new ProductTypeTemplateConfigPojo();
    productTypeTemplateConfigPojo.setPagePath("pagePath");
    productTypeTemplateConfigPojo.setProductType("productType");
  }

  @Test
  public void testGetPagePath() {
    Assert.assertEquals("pagePath", productTypeTemplateConfigPojo.getPagePath());
  }

  @Test
  public void testGetProductType() {
    Assert.assertEquals("productType", productTypeTemplateConfigPojo.getProductType());
  }

  @Test
  public void testToString() {
    productTypeTemplateConfigPojo.toString();
  }

}
