package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InventoryCheckChildResponse {
  private String partNumber;
  private List<ComponentInventoryData> componentInventory;
  private List<InventoryData> inventory;
  private List<InventoryData> headerInventoryDetail;
  private List<InventoryData> quickSellAssociation;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("InventoryCheckChildResponse [partNumber=");
    builder.append(partNumber);
    builder.append(", componentInventory=");
    builder.append(componentInventory);
    builder.append(", inventory=");
    builder.append(inventory);
    builder.append(", headerInventoryDetail=");
    builder.append(headerInventoryDetail);
    builder.append(", quickSellAssociation=");
    builder.append(quickSellAssociation);
    builder.append("]");
    return builder.toString();
  }
}
