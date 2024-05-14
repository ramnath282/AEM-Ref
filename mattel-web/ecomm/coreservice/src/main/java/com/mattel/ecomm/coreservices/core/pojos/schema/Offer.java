package com.mattel.ecomm.coreservices.core.pojos.schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

/**
 * Product SEO schema class.
 *
 * {@link https://schema.org/Product}
 * {@link https://developers.google.com/search/docs/data-types/product}
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "@type", "url", "priceCurrency", "price", "availability", "itemCondition" })
@Getter
@Setter
public class Offer extends BaseSchema {
  private String url;
  private String priceCurrency;
  private String price;
  private String availability;
  private String itemCondition;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Offer [url=");
    builder.append(url);
    builder.append(", priceCurrency=");
    builder.append(priceCurrency);
    builder.append(", price=");
    builder.append(price);
    builder.append(", availability=");
    builder.append(availability);
    builder.append(", itemCondition=");
    builder.append(itemCondition);
    builder.append(", getType()=");
    builder.append(getType());
    builder.append("]");
    return builder.toString();
  }
}
