package com.mattel.ecomm.coreservices.core.pojos;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProductKeywordSuggestionResponseTest {
  ProductKeywordSuggestionResponse productKeywordSuggestionResponse;

  @Before
  public void setUp() {
    productKeywordSuggestionResponse = new ProductKeywordSuggestionResponse();
    ProductKeywordSuggestion productKeywordSuggestion = new ProductKeywordSuggestion();
    List<ProductKeywordSuggestion> suggestionView = new ArrayList<>();
    suggestionView.add(productKeywordSuggestion);
    productKeywordSuggestionResponse.setSuggestionView(suggestionView);
  }

  @Test
  public void testProductSuggestionResponse() {
    Assert.assertNotNull(productKeywordSuggestionResponse.getSuggestionView());
    Assert.assertEquals(1, productKeywordSuggestionResponse.getSuggestionView().size());
    Assert.assertNotNull(productKeywordSuggestionResponse.getSuggestionView().get(0));
    Assert.assertNotNull(productKeywordSuggestionResponse.toString());
  }

}
