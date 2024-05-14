package com.mattel.ecomm.coreservices.core.pojos.schema;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OfferTest {
  private Offer offer;

  @Before
  public void setUp() throws Exception {
    offer = new Offer();
    offer.setAvailability("https://schema.org/InStock");
    offer.setItemCondition(SchemaConstants.ITEM_CONDITION);
    offer.setPriceCurrency("USD");
    offer.setPrice("125");
    offer.setUrl("http://testofferurl");
    offer.setType(SchemaConstants.TYPE_OFFER);
  }

  @Test
  public void testToString() {
    Assert.assertNotNull(offer.toString());
  }

  @Test
  public void testGetUrl() {
    Assert.assertEquals("http://testofferurl", offer.getUrl());
  }

  @Test
  public void testGetPriceCurrency() {
    Assert.assertEquals("USD", offer.getPriceCurrency());
  }

  @Test
  public void testGetPrice() {
    Assert.assertEquals("125", offer.getPrice());
  }

  @Test
  public void testGetAvailability() {
    Assert.assertEquals("https://schema.org/InStock", offer.getAvailability());
  }

  @Test
  public void testGetItemCondition() {
    Assert.assertEquals(SchemaConstants.ITEM_CONDITION, offer.getItemCondition());
  }

  @Test
  public void testGetType() {
    Assert.assertEquals(SchemaConstants.TYPE_OFFER, offer.getType());
  }
}
