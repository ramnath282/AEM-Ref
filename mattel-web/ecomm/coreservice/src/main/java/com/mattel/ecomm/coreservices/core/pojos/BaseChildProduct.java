package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseChildProduct {
  @JsonProperty
  private String buyable;
  @JsonProperty
  private String imageLink;
  @JsonProperty
  private String thumbnail;
  @JsonProperty(value="product_type")
  private String productType;
  @JsonProperty
  private String fullimage;
  @JsonProperty
  private String partNumber;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("BaseChildProduct [buyable=");
    builder.append(buyable);
    builder.append(", imageLink=");
    builder.append(imageLink);
    builder.append(", thumbnail=");
    builder.append(thumbnail);
    builder.append(", product_type=");
    builder.append(productType);
    builder.append(", fullimage=");
    builder.append(fullimage);
    builder.append(", partNumber=");
    builder.append(partNumber);
    builder.append("]");
    return builder.toString();
  }
}
