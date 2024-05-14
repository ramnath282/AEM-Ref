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
public class ConsumerLoyaltyRewardsResponse extends BaseResponse {
  @JsonProperty("ConsumerID")
  private String consumerID;
  @JsonProperty("RequestedBy")
  private String requestedBy;
  @JsonProperty("BrandID")
  private String brandID;
  @JsonProperty("consumerLoyalty")
  private ConsumerLoyalty consumerLoyalty;
  @JsonProperty("consumerPromotions")
  private List<ConsumerPromotions> consumerPromotions;
  @JsonProperty("status")
  private Status status;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ConsumerLoyaltyRewardsResponse [ConsumerID=");
    builder.append(consumerID);
    builder.append(", RequestedBy=");
    builder.append(requestedBy);
    builder.append(", BrandID=");
    builder.append(brandID);
    builder.append(", ConsumerLoyalty=");
    builder.append(consumerLoyalty);
    builder.append(", ConsumerPromotions=");
    builder.append(consumerPromotions);
    builder.append(", Status=");
    builder.append(status);
    builder.append(", toString()=");
    builder.append(super.toString());
    builder.append("]");
    return builder.toString();
  }
}
