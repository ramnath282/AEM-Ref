package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LogOffResponseTest {
  private LogOffResponse logOffResponse;

  @Before
  public void setUp() throws Exception {
    logOffResponse = new LogOffResponse();
    logOffResponse.setStatusCode(200);
  }

  @Test
  public void LogOffResponse() {
    Assert.assertNotNull(logOffResponse.getStatusCode());
    Assert.assertEquals(200, logOffResponse.getStatusCode());
  }

}
