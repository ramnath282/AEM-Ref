package com.mattel.ecomm.core.pojos;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionParameterPojoTest {

  private ConnectionParameterPojo connectionParameterPojo = null;

  @Before
  public void setUp() throws Exception {
    connectionParameterPojo = new ConnectionParameterPojo();
    final JSONObject json = new JSONObject();
    json.append("key", "value");
    connectionParameterPojo.setDomainUrl("domainUrl");
    connectionParameterPojo.setEndPoint("endPoint");
    connectionParameterPojo.setMethodType("methodType");
    connectionParameterPojo.setRequestHeaders(json);
  }

  @Test
  public void testGetRequestHeaders() throws JSONException {
    Assert.assertNotNull(connectionParameterPojo.getRequestHeaders());
  }

  @Test
  public void testGetEndPoint() {
    Assert.assertEquals("endPoint", connectionParameterPojo.getEndPoint());
  }

  @Test
  public void testGetMethodType() {
    Assert.assertEquals("methodType", connectionParameterPojo.getMethodType());
  }

  @Test
  public void testGetDomainUrl() {
    Assert.assertEquals("domainUrl", connectionParameterPojo.getDomainUrl());
  }

}
