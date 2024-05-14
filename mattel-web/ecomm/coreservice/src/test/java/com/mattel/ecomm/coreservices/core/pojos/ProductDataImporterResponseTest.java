package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProductDataImporterResponseTest {
  private ProductDataImporterResponse productDataImporterResponse;
  
  @Before
  public void setUp(){
    productDataImporterResponse = new ProductDataImporterResponse();
    productDataImporterResponse.setFilePath("https://mattel.com/image");
    productDataImporterResponse.setStatus(true);
  }

  @Test
  public void testDataImporterResponse() {
    Assert.assertEquals("https://mattel.com/image", productDataImporterResponse.getFilePath());
    Assert.assertTrue(productDataImporterResponse.getStatus());
  }

}
