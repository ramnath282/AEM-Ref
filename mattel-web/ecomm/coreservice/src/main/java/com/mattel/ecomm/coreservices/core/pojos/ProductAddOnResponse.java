package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductAddOnResponse extends PDPBaseResponse {
  private List<Map<String, List<ProductAddOnAssociation>>> catalogEntryView;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ProductAddOnResponse [catalogEntryView=");
    builder.append(catalogEntryView);
    builder.append("]");
    return builder.toString();
  }
}
