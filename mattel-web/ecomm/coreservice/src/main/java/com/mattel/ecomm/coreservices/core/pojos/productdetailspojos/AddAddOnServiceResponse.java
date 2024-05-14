package com.mattel.ecomm.coreservices.core.pojos.productdetailspojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;

import java.util.Arrays;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddAddOnServiceResponse extends BaseResponse {
    @JsonProperty
    private String parentQuantity;
    @JsonProperty
    private String[] orderId;
    @JsonProperty
    private String [] orderItemId;

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("AddAddOnServiceResponse [parentQuantity=");
      builder.append(parentQuantity);
      builder.append(", orderId=");
      builder.append(Arrays.toString(orderId));
      builder.append(", orderItemId=");
      builder.append(Arrays.toString(orderItemId));
      builder.append("]");
      return builder.toString();
    }
}
