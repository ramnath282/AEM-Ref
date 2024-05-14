package com.mattel.ecomm.coreservices.core.pojos.shopify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
/**
 * Wrapper object containing details of product inventory.
 */
public class Inventory {
  private String partnumber;
  private String product_type;
  private String inventorystatus;
  private String backorderdate;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Inventory [partnumber=");
    builder.append(partnumber);
    builder.append(", product_type=");
    builder.append(product_type);
    builder.append(", inventorystatus=");
    builder.append(inventorystatus);
    builder.append(", backorderdate=");
    builder.append(backorderdate);
    builder.append("]");
    return builder.toString();
  }
}
