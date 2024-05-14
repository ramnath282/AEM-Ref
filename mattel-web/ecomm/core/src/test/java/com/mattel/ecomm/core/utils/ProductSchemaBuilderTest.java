package com.mattel.ecomm.core.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattel.ecomm.core.pojos.PDPProductUIResponse;
import com.mattel.ecomm.core.pojos.shopify.ProductUIResponse;
import com.mattel.ecomm.core.utils.shopify.ProductUIAdapter;
import com.mattel.ecomm.coreservices.core.pojos.Price;
import com.mattel.ecomm.coreservices.core.pojos.schema.AggregateRating;
import com.mattel.ecomm.coreservices.core.pojos.schema.Offer;
import com.mattel.ecomm.coreservices.core.pojos.schema.ProductSchema;
import com.mattel.ecomm.coreservices.core.pojos.schema.SchemaConstants;
import com.mattel.ecomm.coreservices.core.pojos.shopify.ProductServiceResponse;

public class ProductSchemaBuilderTest {
  private PDPProductUIResponse pdpProductUIResponse;

  @Before
  public void setUp() throws Exception {
    final Price listPrice = new Price();
    pdpProductUIResponse = new PDPProductUIResponse();
    listPrice.setCurrency("USD");
    listPrice.setValue("115");
    pdpProductUIResponse.setAvailabilityStatus("https://schema.org/InStock");
    pdpProductUIResponse.setCanonicalUrl("http://testcanonicalurl");
    pdpProductUIResponse.setImageLink("http://testimagelink");
    pdpProductUIResponse.setOfferPrice("115");
    pdpProductUIResponse.setListPrice(listPrice);
    pdpProductUIResponse.setSkuName("Blaire Doll & Book");
    pdpProductUIResponse.setPartNumber("GBL30");
    pdpProductUIResponse.setProductGTIN("887961717327");
    pdpProductUIResponse.setProductReviewCount("10");
    pdpProductUIResponse.setReviewRating("5");
    pdpProductUIResponse.setProductType("ItemBean");
    pdpProductUIResponse.setSeoMetaDescription(
        "The 18\" Blaire doll has bright green eyes that open and close, and curly red hair. She has a cloth body, and movable head and limbs made of smooth vinyl.");
  }

  @Test
  public void testBuildAggregateRating() {
    final AggregateRating aggregateRating = ProductSchemaBuilder
        .buildAggregateRating(pdpProductUIResponse);

    Assert.assertNotNull(aggregateRating);
    Assert.assertEquals(SchemaConstants.TYPE_AGGREGATE_RATING, aggregateRating.getType());
    Assert.assertEquals(pdpProductUIResponse.getProductReviewCount(),
        aggregateRating.getRatingCount());
    Assert.assertEquals(pdpProductUIResponse.getReviewRating(), aggregateRating.getRatingValue());
    Assert.assertEquals(SchemaConstants.BEST_RATING, aggregateRating.getBestRating());
    Assert.assertEquals(SchemaConstants.WORST_RATING, aggregateRating.getWorstRating());
  }

  @Test
  public void testBuildAggregateRatingForNoRatings() {
    pdpProductUIResponse.setProductReviewCount("0");
    final AggregateRating aggregateRating = ProductSchemaBuilder
        .buildAggregateRating(pdpProductUIResponse);

    Assert.assertNull(aggregateRating);
  }

  @Test
  public void testBuildCompositeProductSchema() throws JsonProcessingException {
    Assert.assertTrue(StringUtils
        .isNotBlank(ProductSchemaBuilder.buildCompositeProductSchema(pdpProductUIResponse)));
  }

  @Test
  public void testBuildOffer() {
    final Offer offer = ProductSchemaBuilder.buildOffer(pdpProductUIResponse);

    Assert.assertNotNull(offer);
    Assert.assertEquals(SchemaConstants.TYPE_OFFER, offer.getType());
    Assert.assertEquals(pdpProductUIResponse.getAvailabilityStatus(), offer.getAvailability());
    Assert.assertEquals(pdpProductUIResponse.getOfferPrice(), offer.getPrice());
    Assert.assertEquals(pdpProductUIResponse.getListPrice().getCurrency(),
        offer.getPriceCurrency());
    Assert.assertEquals(SchemaConstants.ITEM_CONDITION, offer.getItemCondition());
    Assert.assertEquals(pdpProductUIResponse.getCanonicalUrl(), offer.getUrl());
   
  }

  @Test
  public void testBuildOfferForNoPrice() {
    pdpProductUIResponse.setOfferPrice("0.00");
    final Offer offer = ProductSchemaBuilder.buildOffer(pdpProductUIResponse);

    Assert.assertNull(offer);
  }

  @Test
  public void testBuildProduct() {
    final ProductSchema productSchema = ProductSchemaBuilder.buildProduct(pdpProductUIResponse);

    Assert.assertNotNull(productSchema);
    Assert.assertEquals(
        "The 18&quot; Blaire doll has bright green eyes that open and close, and curly red hair. She has a cloth body, and movable head and limbs made of smooth vinyl.",
        productSchema.getDescription());
    Assert.assertEquals(pdpProductUIResponse.getImageLink(), productSchema.getImage());
  }

  @Test
  public void testNewProductSchema() throws IOException {
    try (InputStream is = getClass().getResourceAsStream("16bun11.json")) {
        ProductServiceResponse productServiceResponse = new ObjectMapper().readValue(is, ProductServiceResponse.class);
        ProductUIResponse productUIResponse = ProductUIAdapter.transformProduct(productServiceResponse.getProduct(), null);
        Assert.assertNotNull(ProductSchemaBuilder.buildCompositeProductSchema(productUIResponse));
    }
  }
}
