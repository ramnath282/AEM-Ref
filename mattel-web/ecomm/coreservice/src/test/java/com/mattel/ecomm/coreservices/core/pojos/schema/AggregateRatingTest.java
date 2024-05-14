package com.mattel.ecomm.coreservices.core.pojos.schema;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AggregateRatingTest {
  private AggregateRating aggregateRating;

  @Before
  public void setUp() throws Exception {
    aggregateRating = new AggregateRating();
    aggregateRating.setType(SchemaConstants.TYPE_AGGREGATE_RATING);
    aggregateRating.setBestRating(SchemaConstants.BEST_RATING);
    aggregateRating.setRatingCount("10");
    aggregateRating.setRatingValue("5");
    aggregateRating.setWorstRating(SchemaConstants.WORST_RATING);
  }

  @Test
  public void testToString() {
    Assert.assertNotNull(aggregateRating.toString());
  }

  @Test
  public void testGetRatingValue() {
    Assert.assertEquals("5", aggregateRating.getRatingValue());
  }

  @Test
  public void testGetBestRating() {
    Assert.assertEquals(SchemaConstants.BEST_RATING, aggregateRating.getBestRating());
  }

  @Test
  public void testGetWorstRating() {
    Assert.assertEquals(SchemaConstants.WORST_RATING, aggregateRating.getWorstRating());
  }

  @Test
  public void testGetRatingCount() {
    Assert.assertEquals("10", aggregateRating.getRatingCount());
  }

  @Test
  public void testGetType() {
    Assert.assertEquals(SchemaConstants.TYPE_AGGREGATE_RATING, aggregateRating.getType());
  }
}
