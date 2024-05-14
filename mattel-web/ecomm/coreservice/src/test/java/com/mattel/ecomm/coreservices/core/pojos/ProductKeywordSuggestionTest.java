package com.mattel.ecomm.coreservices.core.pojos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProductKeywordSuggestionTest {

  ProductKeywordSuggestion productKeywordSuggestion;

  @Before
  public void setUp() {
    List<Map<String, Object>> entry = new ArrayList<>();
    Map<String, Object> map = new HashMap<>();
    map.put("1", "value1");
    map.put("2", "value2");
    entry.add(map);
    productKeywordSuggestion = new ProductKeywordSuggestion();
    productKeywordSuggestion.setIdentifier("identifier");
    productKeywordSuggestion.setEntry(entry);
  }

  @Test
  public void test() {
    Assert.assertEquals("identifier", productKeywordSuggestion.getIdentifier());
    Assert.assertEquals(1, productKeywordSuggestion.getEntry().size());
    Assert.assertNotNull(productKeywordSuggestion.toString());
  }

}
