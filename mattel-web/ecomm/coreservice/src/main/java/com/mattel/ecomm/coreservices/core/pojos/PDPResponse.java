package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class PDPResponse extends PDPBaseResponse {
  @JsonProperty
  private List<Map<String, Product>> catalogEntryView;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("PDPResponse [catalogEntryView=");
    builder.append(catalogEntryView);
    builder.append(", toString()=");
    builder.append(super.toString());
    builder.append("]");
    return builder.toString();
  }
}
