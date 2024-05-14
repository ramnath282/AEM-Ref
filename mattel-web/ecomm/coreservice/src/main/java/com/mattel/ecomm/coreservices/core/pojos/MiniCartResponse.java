package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MiniCartResponse extends BaseResponse {
  @JsonProperty
  private String customerSegment;

  @JsonProperty
  private String cartQuantity;

  @JsonProperty
  private String orderCurrency;

  @JsonProperty
  private String orderTotal;
}
