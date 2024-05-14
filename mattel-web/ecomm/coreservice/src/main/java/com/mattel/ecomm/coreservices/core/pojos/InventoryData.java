package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InventoryData {
  private Integer lowInvThreshold;
  private String availableQuantity;
  private Integer productId;
  private String unitOfMeasure;
  private String inventoryStatus;
  private String partNumber;
  private String backOrderDate;
  private String productType;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("InventoryData [lowInvThreshold=");
    builder.append(lowInvThreshold);
    builder.append(", availableQuantity=");
    builder.append(availableQuantity);
    builder.append(", productId=");
    builder.append(productId);
    builder.append(", unitOfMeasure=");
    builder.append(unitOfMeasure);
    builder.append(", inventoryStatus=");
    builder.append(inventoryStatus);
    builder.append(", partNumber=");
    builder.append(partNumber);
    builder.append(", backOrderDate=");
    builder.append(backOrderDate);
    builder.append(", productType=");
    builder.append(productType);
    builder.append("]");
    return builder.toString();
  }
}
