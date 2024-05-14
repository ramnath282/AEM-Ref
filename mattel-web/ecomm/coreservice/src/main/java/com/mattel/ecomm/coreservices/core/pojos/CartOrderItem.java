package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartOrderItem {
  @JsonProperty
  private String orderItemId;
  @JsonProperty
  private String partNumber;
  @JsonProperty
  private String quantity;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("CartOrderItem [orderItemId=");
    builder.append(orderItemId);
    builder.append(", partNumber=");
    builder.append(partNumber);
    builder.append(", quantity=");
    builder.append(quantity);
    builder.append("]");
    return builder.toString();
  }
}
