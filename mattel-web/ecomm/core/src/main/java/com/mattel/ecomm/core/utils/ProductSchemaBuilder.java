package com.mattel.ecomm.core.utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mattel.ecomm.core.pojos.PDPProductUIResponse;
import com.mattel.ecomm.core.pojos.shopify.ComponentUIResponse;
import com.mattel.ecomm.core.pojos.shopify.ProductUIResponse;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.pojos.schema.AggregateRating;
import com.mattel.ecomm.coreservices.core.pojos.schema.Offer;
import com.mattel.ecomm.coreservices.core.pojos.schema.ProductSchema;
import com.mattel.ecomm.coreservices.core.pojos.schema.SchemaConstants;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Core;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Variant;
import com.mattel.ecomm.coreservices.core.pojos.shopify.VariantCore;

/**
 * Utility class to build SEO schema objects.
 */
public class ProductSchemaBuilder {
  private static final String AVAILABLE = "Available";
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductSchemaBuilder.class);
  private static final ObjectWriter WRITER = new ObjectMapper().writer().withDefaultPrettyPrinter();
  private static final String UNAVAILABLE = "Unavailable";
  private static final String NO_LONGER_AVAILABLE = "noLongerAvailable";
  private static final String SOLD_OUT = "Sold Out";

  /**
   * Builds simple {@link AggregateRating} object.
   *
   * @param productUIResponse
   *          Product details object.
   * @checkfornull
   * @return {@link AggregateRating} object, if ratingCount is positive else
   *         <code>null</code>.
   */
  public static AggregateRating buildAggregateRating(final PDPProductUIResponse productUIResponse) {
    if (StringUtils.isNotBlank(productUIResponse.getReviewRating())
        && StringUtils.isNotBlank(productUIResponse.getProductReviewCount())) {
      try {
        final Integer reviewCount = Integer.parseInt(productUIResponse.getProductReviewCount());

        ProductSchemaBuilder.LOGGER.debug("Value of product reviewCount: {}", reviewCount);

        if (reviewCount > SchemaConstants.SCHEMA_RATING_COUNT_MIN_VALUE) {
          final AggregateRating aggregateRating = new AggregateRating();

          aggregateRating.setType(SchemaConstants.TYPE_AGGREGATE_RATING);
          aggregateRating.setBestRating(SchemaConstants.BEST_RATING);
          aggregateRating.setWorstRating(SchemaConstants.WORST_RATING);
          aggregateRating.setRatingValue(productUIResponse.getReviewRating());
          aggregateRating.setRatingCount(productUIResponse.getProductReviewCount());
          ProductSchemaBuilder.LOGGER.debug("Generated aggregate rating seo schema object: {}", aggregateRating);
          return aggregateRating;
        }
      } catch (final NumberFormatException e) {
        ProductSchemaBuilder.LOGGER
            .error("Invalid rating value in product, unable to generate aggregate rating schema object", e);
      }
    }

    return null;
  }

  /**
   * Builds composite {@link ProductSchema} object, containing
   * {@link AggregateRating rating} and {@link Offer offer} details.
   *
   * @param productUIResponse
   *          Product details object.
   * @return {@link ProductSchema} object.
   */
  public static String buildCompositeProductSchema(final PDPProductUIResponse productUIResponse)
      throws JsonProcessingException {
    ProductSchemaBuilder.LOGGER.debug("Building product seo schema object for product: {}", productUIResponse);
    final ProductSchema productSchema = ProductSchemaBuilder.buildProduct(productUIResponse);
    final Offer offer = ProductSchemaBuilder.buildOffer(productUIResponse);
    final AggregateRating aggregateRating = ProductSchemaBuilder.buildAggregateRating(productUIResponse);

    productSchema.setOffers(offer);
    productSchema.setAggregateRating(aggregateRating);
    ProductSchemaBuilder.LOGGER.debug("Composite product seo schema object: {}", productSchema);
    return ProductSchemaBuilder.WRITER.writeValueAsString(productSchema);
  }

  /**
   * Builds simple {@link Offer} object.
   *
   * @param productUIResponse
   *          Product details object.
   * @checkfornull
   * @return {@link Offer} object, if price is positive else <code>null</code>.
   */
  public static Offer buildOffer(final PDPProductUIResponse productUIResponse) {
    if (Objects.nonNull(productUIResponse.getListPrice()) && StringUtils.isNotBlank(productUIResponse.getOfferPrice())
        && StringUtils.isNotBlank(productUIResponse.getListPrice().getCurrency())) {
      try {
        final Double price = Double.parseDouble(productUIResponse.getOfferPrice());

        ProductSchemaBuilder.LOGGER.debug("Value of product offer price: {}", price);

        if (price > SchemaConstants.SCHEMA_PRICE_MIN_VALUE) {
          final Offer offer = new Offer();

          offer.setType(SchemaConstants.TYPE_OFFER);
          offer.setUrl(productUIResponse.getCanonicalUrl());
          offer.setPriceCurrency(productUIResponse.getListPrice().getCurrency());
          offer.setPrice(productUIResponse.getOfferPrice());
          offer.setItemCondition(SchemaConstants.ITEM_CONDITION);
          offer.setAvailability(productUIResponse.getAvailabilityStatus());
          ProductSchemaBuilder.LOGGER.debug("Generated offer seo schema object: {}", offer);
          return offer;
        }
      } catch (final NumberFormatException | NullPointerException e) {
        ProductSchemaBuilder.LOGGER.error("Invalid price value in product, unable to generate offer schema object", e);
      }
    }

    return null;
  }

  /**
   * Builds simple {@link ProductSchema} object.
   *
   * @param productUIResponse
   *          Product details object.
   * @return {@link ProductSchema} object.
   */
  public static ProductSchema buildProduct(final PDPProductUIResponse productUIResponse) {
    ProductSchemaBuilder.LOGGER.info("buildProductSchema - start");
    final ProductSchema productSchema = new ProductSchema();

    productSchema.setType(SchemaConstants.TYPE_PRODUCT);
    productSchema.setContext(SchemaConstants.CONTEXT);
    productSchema.setName(productUIResponse.getSkuName());
    productSchema.setImage(productUIResponse.getImageLink());
    // Escape the characters in a String using HTML entities to avoid JSON
    // parsing errors.
    productSchema.setDescription(StringEscapeUtils.escapeHtml(productUIResponse.getSeoMetaDescription()));
    productSchema.setBrand(SchemaConstants.BRAND);
    productSchema.setSku(productUIResponse.getPartNumber());
    productSchema.setGtin13(productUIResponse.getProductGTIN());
    ProductSchemaBuilder.LOGGER.debug("Generated product seo schema object: {}", productSchema);
    ProductSchemaBuilder.LOGGER.info("buildProductSchema - end");
    return productSchema;
  }

  /**
   * Default constructor.
   */
  private ProductSchemaBuilder() {
    // no-op
  }

  /**
   * Builds composite {@link ProductSchema} object, containing
   * {@link AggregateRating rating} and {@link Offer offer} details.
   *
   * @param productUIResponse
   *          Product details object.
   * @return {@link ProductSchema} object.
   */
  public static String buildCompositeProductSchema(ProductUIResponse productUIResponse) throws IOException {
    ProductSchemaBuilder.LOGGER.debug("Building product seo schema object for product: {}", productUIResponse);
    final ProductSchema productSchema = ProductSchemaBuilder.buildProduct(productUIResponse);
    final Offer offer = ProductSchemaBuilder.buildOffer(productUIResponse);
    final AggregateRating aggregateRating = ProductSchemaBuilder.productUIResponse(productUIResponse);

    productSchema.setOffers(offer);
    productSchema.setAggregateRating(aggregateRating);
    ProductSchemaBuilder.LOGGER.debug("Composite product seo schema object: {}", productSchema);
    return ProductSchemaBuilder.WRITER.writeValueAsString(productSchema);
  }

  /**
   * Builds simple {@link AggregateRating} object.
   *
   * @param productUIResponse
   *          Product details object.
   * @checkfornull
   * @return {@link AggregateRating} object, if ratingCount is positive else
   *         <code>null</code>.
   */
  private static AggregateRating productUIResponse(ProductUIResponse productUIResponse) {
    ProductSchemaBuilder.LOGGER.info("buildAggregateRating - start");
    final Core core = productUIResponse.getCore();

    if (StringUtils.isNotBlank(core.getProduct_reviewRatingOriginal())
        && StringUtils.isNotBlank(core.getProduct_reviewCount())) {
      try {
        final Integer reviewCount = Integer.parseInt(core.getProduct_reviewCount());

        ProductSchemaBuilder.LOGGER.debug("Value of product reviewCount: {}", reviewCount);

        if (reviewCount > SchemaConstants.SCHEMA_RATING_COUNT_MIN_VALUE) {
          final AggregateRating aggregateRating = new AggregateRating();

          aggregateRating.setType(SchemaConstants.TYPE_AGGREGATE_RATING);
          aggregateRating.setBestRating(SchemaConstants.BEST_RATING);
          aggregateRating.setWorstRating(SchemaConstants.WORST_RATING);
          aggregateRating.setRatingValue(core.getProduct_reviewRatingOriginal());
          aggregateRating.setRatingCount(core.getProduct_reviewCount());
          ProductSchemaBuilder.LOGGER.debug("Generated aggregate rating seo schema object: {}", aggregateRating);
          return aggregateRating;
        }
      } catch (final NumberFormatException e) {
        ProductSchemaBuilder.LOGGER
            .error("Invalid rating value in product, unable to generate aggregate rating schema object", e);
      }
    }

    ProductSchemaBuilder.LOGGER.info("buildAggregateRating - end");
    return null;
  }

  /**
   * Builds simple {@link Offer} object.
   *
   * @param productUIResponse
   *          Product details object.
   * @checkfornull
   * @return {@link Offer} object, if price is positive else <code>null</code>.
   */
  private static Offer buildOffer(ProductUIResponse productUIResponse) {
    ProductSchemaBuilder.LOGGER.info("buildOffer - start");
    final List<Variant> variants = productUIResponse.getVariants();
    final Variant variant = Optional.ofNullable(variants).filter(v -> !v.isEmpty()).map(v -> v.get(0)).orElse(null);
    final String inventoryStatus = ProductSchemaBuilder
        .setProductAvailabilityStatus(ProductSchemaBuilder.getInventoryStatus(productUIResponse));
    Double price = 0.0;
    final Core core = productUIResponse.getCore();

    if (Objects.nonNull(core) && Constant.GIFT_CARD.equals(core.getProduct_type())) {
      return null;
    }

    if (Objects.nonNull(core) && Objects.nonNull(productUIResponse.getComponents())
        && Constant.PRODUCT_TYPE_BUNDLE_BEAN.equals(core.getProduct_type())) {
      try {
        price = productUIResponse.getComponents().stream().filter(c -> StringUtils.isNotEmpty(c.getPrice()))
            .map(ComponentUIResponse::getPrice).map(Double::parseDouble).reduce(0d, Double::sum);
      } catch (final Exception e) {
        ProductSchemaBuilder.LOGGER
            .error("Invalid price value in bundlebean product, unable to generate offer schema object", e);
      }
    } else if (Objects.nonNull(variant) && Objects.nonNull(variant.getPricing())
        && StringUtils.isNotBlank(variant.getPricing().getPrice())) {
      try {
        price = Double.parseDouble(variant.getPricing().getPrice());
      } catch (final NumberFormatException | NullPointerException e) {
        ProductSchemaBuilder.LOGGER.error("Invalid price value in product, unable to generate offer schema object", e);
      }
    }

    productUIResponse.setAvailabilityStatus(inventoryStatus);
    ProductSchemaBuilder.LOGGER.debug("Inventory status:{}, Value of product offer price: {}", inventoryStatus, price);

    if (price > SchemaConstants.SCHEMA_PRICE_MIN_VALUE) {
      final Offer offer = new Offer();

      productUIResponse.setPriceForRecommendations(price);
      offer.setType(SchemaConstants.TYPE_OFFER);
      offer.setUrl(productUIResponse.getCanonicalUrl());
      offer.setPriceCurrency("USD");
      offer.setPrice(Double.toString(price));
      offer.setItemCondition(SchemaConstants.ITEM_CONDITION);
      offer.setAvailability(inventoryStatus);
      ProductSchemaBuilder.LOGGER.debug("Generated offer seo schema object: {}", offer);
      return offer;
    }

    ProductSchemaBuilder.LOGGER.info("buildOffer - end");
    return null;
  }

  /**
   * Returns the inventory status of product based on product type.
   *
   * @param productUIResponse
   *          Product details object.
   * @return The inventory status of the product.
   */
  private static String getInventoryStatus(ProductUIResponse productUIResponse) {
    final Core core = productUIResponse.getCore();
    final List<Variant> variants = productUIResponse.getVariants();
    final String productType = core.getProduct_type();

    switch (productType) {
      case Constant.PRODUCT_TYPE_PACKAGE_BEAN:
      case Constant.ITEM_BEAN_PRODUCT_TYPE:
        return variants.get(0).getCore().getVariant_inventorystatus();
      case Constant.PRODUCT_TYPE_PRODUCT_BEAN:
        return ProductSchemaBuilder.getSwatchInventory(variants);
      case Constant.PRODUCT_TYPE_BUNDLE_BEAN:
        return ProductSchemaBuilder.getComponentInventory(productUIResponse.getComponents());
    }

    return ProductSchemaBuilder.AVAILABLE;
  }

  /**
   * Returns inventory status of product with multiple components.
   *
   * @param components
   *          The product components.
   * @return Inventory status of whole product.
   */
  private static String getComponentInventory(List<ComponentUIResponse> components) {
    final Map<String, List<ComponentUIResponse>> statusGroup = components.stream()
        .filter(c -> Objects.nonNull(c.getVariants())).collect(Collectors.groupingBy(c -> {
          if (Constant.PRODUCT_TYPE_PRODUCT_BEAN.equals(c.getProduct_type())) {
            return ProductSchemaBuilder.getSwatchInventory(c.getVariants());
          }

          return Optional.ofNullable(c.getVariants()).filter(v -> !v.isEmpty()).map(v -> v.get(0))
              .filter(x -> Objects.nonNull(x.getCore())).map(Variant::getCore)
              .map(VariantCore::getVariant_inventorystatus).orElse(ProductSchemaBuilder.AVAILABLE);
        }));

    if (Objects.nonNull(statusGroup.get(ProductSchemaBuilder.AVAILABLE))) {
      return ProductSchemaBuilder.AVAILABLE;
    } else if (Objects.nonNull(statusGroup.get(Constant.AVAILABILITY_LIMITED))
        || Objects.nonNull(statusGroup.get(Constant.AVAILABILITY_LIMITED_QTY))) {
      return Constant.AVAILABILITY_LIMITED;
    } else if (Objects.nonNull(statusGroup.get(Constant.AVAILABILITY_BACKORDERABLE))
        || Objects.nonNull(statusGroup.get(Constant.AVAILABILITY_BACKORDERED))) {
      return Constant.AVAILABILITY_BACKORDERED;
    }

    return ProductSchemaBuilder.UNAVAILABLE;
  }

  /**
   * Returns the inventory of product with multiple variants.
   *
   * @param variants
   *          The product variants.
   * @return The Inventory status of whole product.
   */
  private static String getSwatchInventory(List<Variant> variants) {
    final Map<String, List<VariantCore>> statusGroup = variants.stream().filter(v -> Objects.nonNull(v.getCore()))
        .map(Variant::getCore).collect(Collectors.groupingBy(vc -> {
          if (StringUtils.isNotEmpty(vc.getVariant_inventorystatus())) {
            return vc.getVariant_inventorystatus();
          }

          return ProductSchemaBuilder.AVAILABLE;
        }));

    if (Objects.nonNull(statusGroup.get(ProductSchemaBuilder.AVAILABLE))) {
      return ProductSchemaBuilder.AVAILABLE;
    } else if (Objects.nonNull(statusGroup.get(Constant.AVAILABILITY_LIMITED))
        || Objects.nonNull(statusGroup.get(Constant.AVAILABILITY_LIMITED_QTY))) {
      return Constant.AVAILABILITY_LIMITED;
    } else if (Objects.nonNull(statusGroup.get(Constant.AVAILABILITY_BACKORDERABLE))
        || Objects.nonNull(statusGroup.get(Constant.AVAILABILITY_BACKORDERED))) {
      return Constant.AVAILABILITY_BACKORDERED;
    }

    return ProductSchemaBuilder.UNAVAILABLE;
  }

  /**
   *
   * @param availabilityStatus
   * @return
   */
  private static String setProductAvailabilityStatus(final String availabilityStatus) {
    if (StringUtils.isNotEmpty(availabilityStatus)) {
      if (ProductSchemaBuilder.UNAVAILABLE.equalsIgnoreCase(availabilityStatus)
          || ProductSchemaBuilder.NO_LONGER_AVAILABLE.equalsIgnoreCase(availabilityStatus)
          || ProductSchemaBuilder.SOLD_OUT.equalsIgnoreCase(availabilityStatus)) {
        return Constant.SEO_OUT_OF_STOCK;
      } else if (Constant.AVAILABILITY_BACKORDERABLE.equalsIgnoreCase(availabilityStatus)
          || Constant.AVAILABILITY_BACKORDERED.equalsIgnoreCase(availabilityStatus)) {
        return Constant.SEO_PRE_ORDER;
      } else if (Constant.AVAILABILITY_LIMITED.equalsIgnoreCase(availabilityStatus)
          || Constant.AVAILABILITY_LIMITED_QTY.equalsIgnoreCase(availabilityStatus)) {
        return Constant.SEO_LIMITED;
      }
    }

    return Constant.SEO_IN_STOCK;
  }

  /**
   * Builds simple {@link ProductSchema} object.
   *
   * @param productUIResponse
   *          Product details object.
   * @return {@link ProductSchema} object.
   */
  private static ProductSchema buildProduct(ProductUIResponse productUIResponse) {
    final ProductSchema productSchema = new ProductSchema();
    final Core core = productUIResponse.getCore();

    productSchema.setType(SchemaConstants.TYPE_PRODUCT);
    productSchema.setContext(SchemaConstants.CONTEXT);
    productSchema.setName(core.getTitle());
    productSchema.setImage(core.getProduct_imagelink());
    productSchema.setBrand(SchemaConstants.BRAND);
    productSchema.setSku(productUIResponse.getPartnumber());
    productSchema.setGtin13(core.getProduct_productGTIN());

    if (StringUtils.isNotEmpty(core.getGlobal_description_tag())) {
      // Escape the characters in a String using HTML entities to avoid JSON
      // parsing errors.
      productSchema.setDescription(StringEscapeUtils.escapeHtml(core.getGlobal_description_tag()));
    }

    ProductSchemaBuilder.LOGGER.debug("Generated product seo schema object: {}", productSchema);
    return productSchema;
  }
}
