package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class CustomerRequest extends BaseRequest {
  private String shopifyCustomerID;
  private String identityAccessToken;


  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("CustomerRequest [shopifyCustomerID=");
    builder.append(shopifyCustomerID);
    builder.append(", identityAccessToken=");
    builder.append(identityAccessToken);
    builder.append(", toString()=");
    builder.append(super.toString());
    builder.append("]");
    return builder.toString();
  }
}
