package com.mattel.ecomm.coreservices.core.pojos;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UnitPrice {
  @JsonProperty
  private Map<String, String> quantity;
  @JsonProperty
  private Map<String, String> price;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("UnitPrice [quantity=");
    builder.append(quantity);
    builder.append(", price=");
    builder.append(price);
    builder.append("]");
    return builder.toString();
  }
}
