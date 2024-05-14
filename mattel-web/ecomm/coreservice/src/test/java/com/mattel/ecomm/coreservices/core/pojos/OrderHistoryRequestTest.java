package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OrderHistoryRequestTest {

  private OrderHistoryRequest orderHistoryRequest;

  @Before
  public void setUp() throws Exception {
    orderHistoryRequest = new OrderHistoryRequest();
    orderHistoryRequest.setLangId("en");
  }

  @Test
  public void testOrderHistoryRequest() {
    Assert.assertEquals("en", orderHistoryRequest.getLangId());
  }

}
