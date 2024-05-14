package com.mattel.ecomm.coreservices.core.pojos.shopify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class BaseErrorResponse extends BaseResponse {
  private String error;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("BaseErrorResponse [error=");
    builder.append(error);
    builder.append("]");
    return builder.toString();
  }
}