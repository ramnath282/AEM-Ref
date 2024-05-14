package com.mattel.ecomm.coreservices.core.pojos.shopify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pricing {
  private String price;
  private String compare_at_price;
  private String employeeLoyalty;
  private String employee;
  private String loyalty;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Pricing [price=");
    builder.append(price);
    builder.append(", compare_at_price=");
    builder.append(compare_at_price);
    builder.append(", employeeLoyalty=");
    builder.append(employeeLoyalty);
    builder.append(", employee=");
    builder.append(employee);
    builder.append(", loyalty=");
    builder.append(loyalty);
    builder.append("]");
    return builder.toString();
  }
}
