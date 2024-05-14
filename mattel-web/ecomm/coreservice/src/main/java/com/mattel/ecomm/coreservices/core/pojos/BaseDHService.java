package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class BaseDHService {

  @JsonProperty
  private String buyable;
  @JsonProperty
  private String resourceId;
  @JsonProperty
  private String thumbnail;
  @JsonProperty
  private String shortDescription;
  @JsonProperty
  private String storeID;
  @JsonProperty
  private Boolean hasSingleSKU;
  

  @Override
  public String toString() {
    return "BaseDHService [buyable=" + buyable + ", resourceId=" + resourceId + ", thumbnail="
        + thumbnail + ", shortDescription=" + shortDescription + ", storeID=" + storeID
        + ", hasSingleSKU=" + hasSingleSKU + "]";
  }

}
