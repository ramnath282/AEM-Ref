package com.mattel.ecomm.coreservices.core.pojos.schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

/**
 * Base schema class containing base SEO schema parameters.
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "@context", "@type", "name", "image", "description", "brand", "sku", "gtin8",
    "gtin13" })
@Getter
@Setter
public class BaseSchema {
  @JsonProperty("@context")
  private String context;
  @JsonProperty("@type")
  private String type;
  private String name;
  private String image;
  private String description;
  private String brand;
  private String sku;
  private String gtin8;
  private String gtin13;

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("BaseSchema [context=");
    builder.append(context);
    builder.append(", type=");
    builder.append(type);
    builder.append(", name=");
    builder.append(name);
    builder.append(", image=");
    builder.append(image);
    builder.append(", description=");
    builder.append(description);
    builder.append(", brand=");
    builder.append(brand);
    builder.append(", sku=");
    builder.append(sku);
    builder.append(", gtin8=");
    builder.append(gtin8);
    builder.append(", gtin13=");
    builder.append(gtin13);
    builder.append("]");
    return builder.toString();
  }
}
