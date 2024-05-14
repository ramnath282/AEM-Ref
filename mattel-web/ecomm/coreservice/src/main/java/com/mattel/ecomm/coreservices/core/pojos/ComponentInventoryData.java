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
public class ComponentInventoryData {
  String parentPartNumber;
  List<InventoryData> skuArr;
  private List<InventoryData> quickSellAssociation;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ComponentInventoryData [parentPartNumber=");
    builder.append(parentPartNumber);
    builder.append(", skuArr=");
    builder.append(skuArr);
    builder.append(", quickSellAssociation=");
    builder.append(quickSellAssociation);
    builder.append("]");
    return builder.toString();
  }
}
