package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author CTS
 *
 */
@Getter
@Setter
public class PDPCollectiveOfferPriceResponse extends BaseResponse {
  @JsonProperty
  private PDPCollectivePriceDetails priceDetails;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("PDPCollectiveOfferPriceResponse [priceDetails=");
    builder.append(priceDetails);
    builder.append(", getErrors()=");
    builder.append(getErrors());
    builder.append("]");
    return builder.toString();
  }
}
