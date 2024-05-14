package com.mattel.ecomm.coreservices.core.pojos.schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * AggregateRating SEO schema class.
 * </p>
 *
 * {@link https://schema.org/AggregateRating}
 * {@link https://developers.google.com/search/docs/data-types/review-snippet}
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "@type", "ratingValue", "bestRating", "worstRating", "ratingCount" })
@Getter
@Setter
public class AggregateRating extends BaseSchema {
  private String ratingValue;
  private String bestRating;
  private String worstRating;
  private String ratingCount;

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("AggregateRating [ratingValue=");
    builder.append(ratingValue);
    builder.append(", bestRating=");
    builder.append(bestRating);
    builder.append(", worstRating=");
    builder.append(worstRating);
    builder.append(", ratingCount=");
    builder.append(ratingCount);
    builder.append(", getType()=");
    builder.append(getType());
    builder.append("]");
    return builder.toString();
  }
}
