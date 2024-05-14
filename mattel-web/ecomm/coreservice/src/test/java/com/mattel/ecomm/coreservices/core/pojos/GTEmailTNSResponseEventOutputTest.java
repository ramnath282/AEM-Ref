package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GTEmailTNSResponseEventOutputTest {

  private GTEmailTNSResponseEventOutput gTEmailTNSResponseEventOutput;
  @Mock
  GTEmailTNSResponseMessage gTEmailTNSResponseMessage;

  @Before
  public void setUp() throws Exception {
    gTEmailTNSResponseEventOutput = new GTEmailTNSResponseEventOutput();
    gTEmailTNSResponseEventOutput.setCommonResponseMessage(gTEmailTNSResponseMessage);

  }

  @Test
  public void testGetInstructions() {
    Assert.assertNotNull(gTEmailTNSResponseEventOutput.getCommonResponseMessage());
  }

  @Test
  public void testGetCommonResponseMessage() throws IOException {
    final String json = new StringBuilder("{\"CommonResponseMessage\":").append("{").append("\"")
        .append("status").append("\":").append("\"dummy_string\"").append(",").append("\"")
        .append("message").append("\":").append("\"dummy_string\"").append(",").append("\"")
        .append("messageId").append("\":").append("\"dummy_string\"").append(",").append("\"")
        .append("correlationId").append("\":").append("\"dummy_string\"").append(",").append("\"")
        .append("timeStamp").append("\":").append("\"dummy_string\"").append("}").append("}")
        .toString();
    final GTEmailTNSResponseEventOutput responseMessage = new ObjectMapper()
        .readValue(json.getBytes(StandardCharsets.UTF_8), GTEmailTNSResponseEventOutput.class);
    final GTEmailTNSResponseMessage commonResponseMessage;

    Assert.assertNotNull(responseMessage);
    commonResponseMessage = responseMessage.getCommonResponseMessage();
    Assert.assertNotNull(commonResponseMessage);
  }
}
