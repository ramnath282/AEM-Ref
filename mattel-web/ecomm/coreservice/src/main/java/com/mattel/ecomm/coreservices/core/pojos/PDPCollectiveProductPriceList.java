package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author CTS
 *
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PDPCollectiveProductPriceList {
  @JsonProperty
  private List<UnitPrice> unitPrice;
  @JsonProperty
  private String partNumber;
  @JsonProperty
  private List<UnitPrice> listPrice;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("PDPCollectiveProductPriceList [unitPrice=");
    builder.append(unitPrice);
    builder.append(", partNumber=");
    builder.append(partNumber);
    builder.append(", listPrice=");
    builder.append(listPrice);
    builder.append("]");
    return builder.toString();
  }
}
