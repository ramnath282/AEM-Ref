package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class ProductAddOnAssociation extends ProductAssociation {
  @JsonProperty
  private String description;
  @JsonProperty
  private Map<String, Map<String, String>> price;
  @JsonProperty
  private List<ChildProduct> childProducts;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ProductAddOnAssociation [description=");
    builder.append(description);
    builder.append(", price=");
    builder.append(price);
    builder.append(", childProducts=");
    builder.append(childProducts);
    builder.append("]");
    return builder.toString();
  }
}
