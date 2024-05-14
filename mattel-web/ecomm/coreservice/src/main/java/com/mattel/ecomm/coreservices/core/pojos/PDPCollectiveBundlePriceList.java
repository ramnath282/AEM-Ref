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
public class PDPCollectiveBundlePriceList {
  @JsonProperty
  private String parentPartNumber;

  @JsonProperty("component_priceDetails")
  private List<PDPCollectiveProductPriceList> componentPriceDetails;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("PDPCollectiveBundlePriceList [parentPartNumber=");
    builder.append(parentPartNumber);
    builder.append(", componentPriceDetails=");
    builder.append(componentPriceDetails);
    builder.append("]");
    return builder.toString();
  }
}
