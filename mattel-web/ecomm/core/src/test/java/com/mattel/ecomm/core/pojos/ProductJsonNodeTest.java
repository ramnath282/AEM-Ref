package com.mattel.ecomm.core.pojos;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class ProductJsonNodeTest {

  private ProductJsonNode impl = null;

  @Before
  public void setUp() throws Exception {
    final String jsonString = "{\"k1\":\"v1\",\"k2\":\"v2\"}";

    final ObjectMapper mapper = new ObjectMapper();
    final JsonNode actualObj = mapper.readTree(jsonString);

    impl = new ProductJsonNode("", actualObj);
    impl.setProperty("value", "10");
    impl.toString();
  }

  @Test
  public void testNullHashCode() throws JsonProcessingException, IOException {
    final String jsonString = "{\"k1\":\"v1\"}";
    final ObjectMapper mapper = new ObjectMapper();
    final JsonNode actualObj = mapper.readTree(jsonString);

    impl = new ProductJsonNode("1", actualObj);
    Assert.assertEquals(80, impl.hashCode());
  }

  @Test
  public void testHashCode() {
    Assert.assertEquals(31, impl.hashCode());
  }

  @Test
  public void testGetPartNumber() {
    Assert.assertEquals("", impl.getPartNumber());
  }

  @Test
  public void testGetJsonNode() {
    Assert.assertNotNull(impl.getJsonNode());
  }

  @Test
  public void testGetProperties() {
    Assert.assertEquals("10", impl.getProperties().get("value"));
  }

  @Test
  public void testEquals() {
    Assert.assertEquals(true, impl.equals(impl));
  }

  @Test
  public void testEqualswithNull() {
    Assert.assertEquals(false, impl.equals(null));
  }

  @Test
  public void testEqualswithClass() {
    final String s = new String("new");
    Assert.assertEquals(false, impl.equals(s));
  }

  @Test
  public void testEqualswithObjNull() throws JsonProcessingException, IOException {
    final ProductJsonNode test = new ProductJsonNode(null, null);
    Assert.assertEquals(false, impl.equals(test));
  }

  @Test
  public void testEqualswithObj() throws JsonProcessingException, IOException {
    final String jsonString = "{\"key1\":\"value1\"}";
    final ObjectMapper mapper = new ObjectMapper();
    final JsonNode actualObj = mapper.readTree(jsonString);
    final ProductJsonNode test = new ProductJsonNode("", actualObj);
    Assert.assertEquals(true, impl.equals(test));
  }

}
