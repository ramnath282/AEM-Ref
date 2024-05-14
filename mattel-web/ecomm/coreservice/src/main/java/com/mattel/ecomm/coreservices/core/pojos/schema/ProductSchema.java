package com.mattel.ecomm.coreservices.core.pojos.schema;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Product SEO schema class.
 *
 * {@link https://schema.org/Product}
 * {@link https://developers.google.com/search/docs/data-types/product}
 */
@Getter
@Setter
public class ProductSchema extends BaseSchema {
  @JsonProperty("offers")
  private Offer offers;
  @JsonProperty("aggregateRating")
  private AggregateRating aggregateRating;

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("ProductSchema [offers=");
    builder.append(offers);
    builder.append(", aggregateRating=");
    builder.append(aggregateRating);
    builder.append(", getContext()=");
    builder.append(getContext());
    builder.append(", getType()=");
    builder.append(getType());
    builder.append(", getName()=");
    builder.append(getName());
    builder.append(", getImage()=");
    builder.append(getImage());
    builder.append(", getDescription()=");
    builder.append(getDescription());
    builder.append(", getBrand()=");
    builder.append(getBrand());
    builder.append(", getSku()=");
    builder.append(getSku());
    builder.append(", getGtin8()=");
    builder.append(getGtin8());
    builder.append(", getGtin13()=");
    builder.append(getGtin13());
    builder.append("]");
    return builder.toString();
  }
}
