package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class ProductAttributesResponse extends PDPBaseResponse {
  @JsonProperty
  private List<Map<String, Map<String, List<String>>>> catalogEntryView;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ProductAttributesResponse [recordSetCount=");
    builder.append(getRecordSetCount());
    builder.append(", metaData=");
    builder.append(getMetaData());
    builder.append(", resourceId=");
    builder.append(getResourceId());
    builder.append(", recordSetStartNumber=");
    builder.append(getRecordSetStartNumber());
    builder.append(", resourceName=");
    builder.append(getResourceName());
    builder.append(", recordSetTotal=");
    builder.append(getRecordSetTotal());
    builder.append(", recordSetTotalMatches=");
    builder.append(getRecordSetTotalMatches());
    builder.append(", recordSetComplete=");
    builder.append(getRecordSetComplete());
    builder.append(", catalogEntryView=");
    builder.append(catalogEntryView);
    builder.append("]");
    return builder.toString();
  }
}
