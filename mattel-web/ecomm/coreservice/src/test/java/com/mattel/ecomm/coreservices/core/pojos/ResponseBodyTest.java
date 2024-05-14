package com.mattel.ecomm.coreservices.core.pojos;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
//import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.ecomm.coreservices.core.constants.Constant;

@RunWith(MockitoJUnitRunner.class)
public class ResponseBodyTest {

  private ResponseBody responseBody;

  @Before
  public void setup() {
    responseBody = new ResponseBody();
    responseBody.setContent("content_here");
    responseBody.setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
  }

  @Test
  public void test_toString() {
    String strings = "ResponseBody " + "[content=" + responseBody.getContent() + ", contentType="
        + responseBody.getContentType() + "]";
    assertEquals(strings, responseBody.toString());
  }
}