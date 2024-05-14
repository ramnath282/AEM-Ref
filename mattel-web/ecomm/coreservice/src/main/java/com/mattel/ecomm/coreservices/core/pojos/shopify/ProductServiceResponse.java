package com.mattel.ecomm.coreservices.core.pojos.shopify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
/** Response from Product Availability API*/
public class ProductServiceResponse extends BaseErrorResponse {
  private Product product;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ProductServiceResponse [product=");
    builder.append(product);
    builder.append("]");
    return builder.toString();
  }
}
