package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PDPListPriceResponse extends BaseResponse {
  @JsonProperty
  private String resourceId;
  @JsonProperty
  private String resourceName;
  @JsonProperty
  private List<PDPPriceResultList> resultList;

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("PDPListPriceResponse [resourceId=");
    builder.append(resourceId);
    builder.append(", resourceName=");
    builder.append(resourceName);
    builder.append(", resultList=");
    builder.append(resultList);
    builder.append("]");
    return builder.toString();
  }
}
