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
public class PDPCollectiveOfferPriceRequest extends BaseRequest {
  @JsonProperty
  private String partNumbers;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("PDPCollectiveOfferPriceRequest [partNumbers=");
    builder.append(partNumbers);
    builder.append("]");
    return builder.toString();
  }
}
