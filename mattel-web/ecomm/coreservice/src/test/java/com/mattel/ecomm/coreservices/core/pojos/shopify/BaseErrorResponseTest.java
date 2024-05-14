package com.mattel.ecomm.coreservices.core.pojos.shopify;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BaseErrorResponseTest {
  BaseErrorResponse impl;
  
  @Before
  public void setUp() throws Exception {
    impl = new BaseErrorResponse();
    impl.setError("Could not find product with part number fgd39");
  }

  @Test
  public void testToString() throws Exception {
    impl.toString();
  }

  @Test
  public void testGetError() throws Exception {
   Assert.assertEquals("Could not find product with part number fgd39", impl.getError());
  }
}
