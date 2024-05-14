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
public class CustomerResponse extends BaseResponse {
  private String id;
  private String consumerID;
  private String firstName;
  private String[] tags;
  private ConsumerLoyalty consumerLoyalty;
  @JsonProperty("ConsumerPromotions")
  private List<ConsumerPromotions> consumerPromotions;
  private ConsumerSegments consumerSegments;


  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ConsumerLoyaltyRewardsResponse [ConsumerID=");
    builder.append(consumerID);
    builder.append(", id=");
    builder.append(id);
    builder.append(", firstName=");
    builder.append(firstName);
    builder.append(", tags=");
    builder.append(tags);
    builder.append(", ConsumerLoyalty=");
    builder.append(consumerLoyalty);
    builder.append(", ConsumerPromotions=");
    builder.append(consumerPromotions);
    builder.append(", consumerSegments=");
    builder.append(consumerSegments);
    builder.append(", toString()=");
    builder.append(super.toString());
    builder.append("]");
    return builder.toString();
  }
}
