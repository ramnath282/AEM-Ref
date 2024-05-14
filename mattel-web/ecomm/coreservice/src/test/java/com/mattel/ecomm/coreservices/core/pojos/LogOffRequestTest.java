package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LogOffRequestTest {

  private LogOffRequest logOffRequest;

  @Before
  public void setUp() throws Exception {
    logOffRequest = new LogOffRequest();
    logOffRequest.setLangId("en");
  }

  @Test
  public void testLogOffRequest() {
    Assert.assertNotNull(logOffRequest.getLangId());
    Assert.assertEquals("en", logOffRequest.getLangId());
  }

}
