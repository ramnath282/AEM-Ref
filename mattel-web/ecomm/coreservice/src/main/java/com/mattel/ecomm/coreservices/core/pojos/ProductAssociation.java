package com.mattel.ecomm.coreservices.core.pojos;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class ProductAssociation extends BaseChildProduct {
  @JsonProperty
  private String associationType;
  @JsonProperty
  private String name;
  @JsonProperty
  private  Map<String,Object>  attributes;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ProductAssociation [associationType=");
    builder.append(associationType);
    builder.append(", name=");
    builder.append(name);
    builder.append(", attributes=");
    builder.append(attributes);
    builder.append(", toString()=");
    builder.append(super.toString());
    builder.append("]");
    return builder.toString();
  }
}
