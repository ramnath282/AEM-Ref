package com.mattel.ecomm.coreservices.core.pojos.shopify;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VariantCore extends Core {
  private String sku;
  private double position;
  private String option1;
  private String option2;
  private String option3;
  private String variant_product_type;
  private String variant_parentpartnumber;
  private String variant_imageLink;
  private String variant_fullimage;
  private String variant_thumbnail;
  private String variant_swatch;
  private String variant_buyable;
  private Map<String, Object> variant_definingAttributes;
  private String variant_inventorystatus;
  private String variant_backorderdate;
  private String variant_itematpreceiptid;
  private String tax_code;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("VariantCore [sku=");
    builder.append(sku);
    builder.append(", position=");
    builder.append(position);
    builder.append(", option1=");
    builder.append(option1);
    builder.append(", option2=");
    builder.append(option2);
    builder.append(", option3=");
    builder.append(option3);
    builder.append(", variant_product_type=");
    builder.append(variant_product_type);
    builder.append(", variant_parentpartNumber=");
    builder.append(variant_parentpartnumber);
    builder.append(", variant_imageLink=");
    builder.append(variant_imageLink);
    builder.append(", variant_fullimage=");
    builder.append(variant_fullimage);
    builder.append(", variant_thumbnail=");
    builder.append(variant_thumbnail);
    builder.append(", variant_swatch=");
    builder.append(variant_swatch);
    builder.append(", variant_buyable=");
    builder.append(variant_buyable);
    builder.append(", variant_inventorystatus=");
    builder.append(variant_inventorystatus);
    builder.append(", variant_backorderdate=");
    builder.append(variant_backorderdate);
    builder.append(", parent_variables=");
    builder.append(", variant_itematpreceiptid=");
    builder.append(variant_itematpreceiptid);
    builder.append(super.toString());
    builder.append("]");
    return builder.toString();
  }
}
