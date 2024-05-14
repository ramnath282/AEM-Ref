package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class PDPBaseResponse extends BaseResponse {
  @JsonProperty
  private String recordSetCount;
  @JsonProperty
  private Map<String, String> metaData;
  @JsonProperty
  private String resourceId;
  @JsonProperty
  private String recordSetStartNumber;
  @JsonProperty
  private String resourceName;
  @JsonProperty
  private String recordSetTotal;
  @JsonProperty
  private String recordSetTotalMatches;
  @JsonProperty
  private String recordSetComplete;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("PDPBaseResponse [recordSetCount=");
    builder.append(recordSetCount);
    builder.append(", metaData=");
    builder.append(metaData);
    builder.append(", resourceId=");
    builder.append(resourceId);
    builder.append(", recordSetStartNumber=");
    builder.append(recordSetStartNumber);
    builder.append(", resourceName=");
    builder.append(resourceName);
    builder.append(", recordSetTotal=");
    builder.append(recordSetTotal);
    builder.append(", recordSetTotalMatches=");
    builder.append(recordSetTotalMatches);
    builder.append(", recordSetComplete=");
    builder.append(recordSetComplete);
    builder.append("]");
    return builder.toString();
  }
}
