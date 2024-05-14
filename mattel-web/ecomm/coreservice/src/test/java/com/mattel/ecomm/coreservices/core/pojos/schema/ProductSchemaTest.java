package com.mattel.ecomm.coreservices.core.pojos.schema;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProductSchemaTest {
  private Offer offer;
  private AggregateRating aggregateRating;
  private ProductSchema impl;

  @Before
  public void setUp() throws Exception {
    offer = new Offer();
    offer.setAvailability("https://schema.org/InStock");
    offer.setItemCondition(SchemaConstants.ITEM_CONDITION);
    offer.setPriceCurrency("USD");
    offer.setPrice("125");
    offer.setUrl("http://testofferurl");
    offer.setType(SchemaConstants.TYPE_OFFER);
    aggregateRating = new AggregateRating();
    aggregateRating.setType(SchemaConstants.TYPE_AGGREGATE_RATING);
    aggregateRating.setBestRating(SchemaConstants.BEST_RATING);
    aggregateRating.setRatingCount("10");
    aggregateRating.setRatingValue("5");
    aggregateRating.setWorstRating(SchemaConstants.WORST_RATING);
    impl = new ProductSchema();
    impl.setType(SchemaConstants.TYPE_PRODUCT);
    impl.setAggregateRating(aggregateRating);
    impl.setOffers(offer);
  }

  @Test
  public void testToString() {
    Assert.assertNotNull(impl.toString());
  }

  @Test
  public void testGetOffers() {
    Assert.assertEquals(aggregateRating, impl.getAggregateRating());
  }

  @Test
  public void testGetAggregateRating() {
    Assert.assertEquals(offer, impl.getOffers());
  }
}
