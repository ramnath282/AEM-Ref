package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PDPOfferPriceResponse extends BaseResponse {
  @JsonProperty
  private String resourceId;
  @JsonProperty("EntitledPrice")
  private List<PDPEntitledPrice> entitledPrice;
  @JsonProperty
  private String resourceName;

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("PDPOfferPriceResponse [resourceId=");
    builder.append(resourceId);
    builder.append(", entitledPrice=");
    builder.append(entitledPrice);
    builder.append(", resourceName=");
    builder.append(resourceName);
    builder.append("]");
    return builder.toString();
  }
}
