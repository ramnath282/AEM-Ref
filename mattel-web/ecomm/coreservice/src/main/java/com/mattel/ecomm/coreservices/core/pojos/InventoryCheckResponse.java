package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InventoryCheckResponse extends BaseResponse {
  private List<InventoryCheckChildResponse> response;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("InventoryCheckResponse [response=");
    builder.append(response);
    builder.append("]");
    return builder.toString();
  }
}
