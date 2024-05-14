package com.mattel.ecomm.coreservices.core.pojos.productdetailspojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.CartOrderItem;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemCartResponse extends BaseResponse {
  @JsonProperty
  private String orderId;
  @JsonProperty
  private List<CartOrderItem> orderItem;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ItemCartResponse [orderId=");
    builder.append(orderId);
    builder.append(", orderItem=");
    builder.append(orderItem);
    builder.append("]");
    return builder.toString();
  }
}
