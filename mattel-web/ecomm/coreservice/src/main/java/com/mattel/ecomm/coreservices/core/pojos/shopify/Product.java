package com.mattel.ecomm.coreservices.core.pojos.shopify;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
/**
 * Main product object containing details of all the product.
 */
public class Product {
  private long id;
  private long product_id;
  private String partnumber;
  private Core core;
  private List<Map<String, Object>> images;
  private List<Variant> variants;
  private List<Option> options;
  private List<Association> associations;
  private Map<String, Object> attributes;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Product [id=");
    builder.append(id);
    builder.append(", product_id=");
    builder.append(product_id);
    builder.append(", partnumber=");
    builder.append(partnumber);
    builder.append(", core=");
    builder.append(core);
    builder.append(", images=");
    builder.append(images);
    builder.append(", variants=");
    builder.append(variants);
    builder.append(", options=");
    builder.append(options);
    builder.append(", associations=");
    builder.append(associations);
    builder.append(", attributes=");
    builder.append(attributes);
    builder.append("]");
    return builder.toString();
  }
}
