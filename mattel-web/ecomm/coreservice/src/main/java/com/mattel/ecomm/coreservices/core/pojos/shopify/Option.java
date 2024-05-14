package com.mattel.ecomm.coreservices.core.pojos.shopify;

import java.util.List;

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
 * Object containing details of swatches (products with multiple variants).
 */
public class Option {
  private long id;
  private long product_id;
  private String name;
  private int position;
  private List<String> values;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Option [id=");
    builder.append(id);
    builder.append(", product_id=");
    builder.append(product_id);
    builder.append(", name=");
    builder.append(name);
    builder.append(", position=");
    builder.append(position);
    builder.append(", values=");
    builder.append(values);
    builder.append("]");
    return builder.toString();
  }
}
