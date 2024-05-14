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
public class AssociatedProduct {
  private String partnumber;
  private long id; 
  private Core core;
  private List<Option> options;
  private List<Variant> variants;
  private Map<String, Object> attributes;
  private List<Map<String, Object>> images;
  private List<Association> associations;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("AssociatedProduct [partnumber=");
    builder.append(partnumber);
    builder.append(", id=");
    builder.append(id);
    builder.append(", core=");
    builder.append(core);
    builder.append(", options=");
    builder.append(options);
    builder.append(", variants=");
    builder.append(variants);
    builder.append(", attributes=");
    builder.append(attributes);
    builder.append(", images=");
    builder.append(images);
    builder.append(", associations=");
    builder.append(associations);
    builder.append("]");
    return builder.toString();
  }
}
