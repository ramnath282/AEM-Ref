package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PDPPriceResultList {
  @JsonProperty("UnitPrice")
  private List<Map<String, Object>> unitPrice;
  private String catalogEntryId;
  private String partNumber;
  private String priceRuleName;
  private String priceRuleId;

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("PDPPriceResultList [unitPrice=");
    builder.append(unitPrice);
    builder.append(", catalogEntryId=");
    builder.append(catalogEntryId);
    builder.append(", partNumber=");
    builder.append(partNumber);
    builder.append(", priceRuleName=");
    builder.append(priceRuleName);
    builder.append(", priceRuleId=");
    builder.append(priceRuleId);
    builder.append("]");
    return builder.toString();
  }
}
