package com.mattel.ecomm.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class RecommendedProductsTest {

  @InjectMocks
  RecommendedProducts recommendedProducts;

  @Mock
  SlingHttpServletRequest request;

  @Before
  public void setUp() throws Exception {
    MemberModifier.field(RecommendedProducts.class, "componentId").set(recommendedProducts, "1");
    MemberModifier.field(RecommendedProducts.class, "request").set(recommendedProducts, request);
  }

  @Test
  public void testWithNonNullID() {
    Mockito.when(request.getAttribute("componentId")).thenReturn("123");
    recommendedProducts.postConstruct();
  }

  @Test
  public void testWithNullID() {
    Mockito.when(request.getAttribute("componentId")).thenReturn(null);
    recommendedProducts.postConstruct();
  }

  @Test
  public void testGetters() {
    Assert.assertEquals("1", recommendedProducts.getComponentId());
  }

}
