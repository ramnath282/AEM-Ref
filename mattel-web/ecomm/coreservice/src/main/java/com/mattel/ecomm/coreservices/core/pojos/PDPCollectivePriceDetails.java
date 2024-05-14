package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author CTS
 *
 */
@Getter
@Setter
public class PDPCollectivePriceDetails {
  @JsonProperty("bundle_priceList")
  private List<PDPCollectiveBundlePriceList> bundlePriceList;
  @JsonProperty("product_priceList")
  private List<PDPCollectiveProductPriceList> productPriceList;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("PDPCollectivePriceDetails [bundlePriceList=");
    builder.append(bundlePriceList);
    builder.append(", productPriceList=");
    builder.append(productPriceList);
    builder.append("]");
    return builder.toString();
  }
}
