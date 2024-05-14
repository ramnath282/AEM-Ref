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
/**
 * Variant object containing variant id, inventory and pricing details of a product or child product.
 */
public class Variant {
  private long id;
  private long product_id;
  private VariantCore core;
  private Pricing pricing;
  private Inventory inventory;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Variant [id=");
    builder.append(id);
    builder.append(", product_id=");
    builder.append(product_id);
    builder.append(", core=");
    builder.append(core);
    builder.append(", pricing=");
    builder.append(pricing);
    builder.append(", inventory=");
    builder.append(inventory);
    builder.append("]");
    return builder.toString();
  }
}
