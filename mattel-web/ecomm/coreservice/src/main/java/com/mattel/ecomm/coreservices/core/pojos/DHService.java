package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class DHService extends BaseDHService{

  @JsonProperty
  private List<Price> price;
  @JsonProperty
  private String name;
  @JsonProperty
  private String partNumber;
  @JsonProperty
  private List<DHTreatmentServiceAttributes> attributes;
  @JsonProperty
  private String catalogEntryTypeCode;
  @JsonProperty
  private String uniqueID;
  

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("DHService [price=");
    builder.append(price);
    builder.append(", name=");
    builder.append(name);
    builder.append(", partNumber=");
    builder.append(partNumber);
    builder.append(", attributes=");
    builder.append(attributes);
    builder.append(", catalogEntryTypeCode=");
    builder.append(catalogEntryTypeCode);
    builder.append(", uniqueID=");
    builder.append(uniqueID);
    builder.append("]");
    return builder.toString();
  }
}
