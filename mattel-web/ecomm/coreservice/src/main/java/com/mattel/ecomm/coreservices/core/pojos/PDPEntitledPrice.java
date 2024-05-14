package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PDPEntitledPrice {
  @JsonProperty("UnitPrice")
  private List<Map<String, Object>> unitPrice;
  @JsonProperty
  private String productId;
  @JsonProperty
  private String contractId;
  @JsonProperty
  private String partNumber;

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("PDPEntitledPrice [unitPrice=");
    builder.append(unitPrice);
    builder.append(", productId=");
    builder.append(productId);
    builder.append(", contractId=");
    builder.append(contractId);
    builder.append(", partNumber=");
    builder.append(partNumber);
    builder.append("]");
    return builder.toString();
  }
}
