package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInventoryData {
  @JsonProperty
  private String availableQuantity;
  @JsonProperty
  private String unitOfMeasure;
  @JsonProperty
  private String productId;
  @JsonProperty
  private String inventoryStatus;

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("ProductInventoryData [availableQuantity=");
    builder.append(availableQuantity);
    builder.append(", unitOfMeasure=");
    builder.append(unitOfMeasure);
    builder.append(", productId=");
    builder.append(productId);
    builder.append(", inventoryStatus=");
    builder.append(inventoryStatus);
    builder.append("]");
    return builder.toString();
  }
}
