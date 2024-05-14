package com.mattel.ecomm.coreservices.core.pojos.shopify;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
/**
 * Association object containing details of product associations. Association
 * types: {@link ProductAssociationMapping#ASSOCIATIONS}
 */
public class Association {
  private long product_id;;
  private String association_type;
  private double association_sequence;
  private AssociatedProduct product;
  private List<Association> associations;
  private String partnumber;

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("Association [product_id=");
    builder.append(product_id);
    builder.append(", association_type=");
    builder.append(association_type);
    builder.append(", product=");
    builder.append(product);
    builder.append(", associations=");
    builder.append(associations);
    builder.append(", association_sequence=");
    builder.append(association_sequence);
    builder.append("]");
    return builder.toString();
  }
}
