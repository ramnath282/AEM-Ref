package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsumerLoyalty {

	@JsonProperty("LoyaltyID")
	private String loyaltyID;

	@JsonProperty("LoyaltyNumber")
	private String loyaltyNumber;

	@JsonProperty("LoyaltyTierCode")
	private String loyaltyTierCode;

	@JsonProperty("NextTierAmount")
	private String nextTierAmount;
	
	@JsonProperty("LoyaltyPointCount")
	private String loyaltyPointCount;
	
	@JsonProperty("TierStartDate")
	private String tierStartDate;

	@JsonProperty("TierExpirationDate")
	private String tierExpirationDate;

	@JsonProperty("LoyaltyStartDate")
	private String loyaltyStartDate;

	@JsonProperty("LoyaltyEndDate")
	private String loyaltyEndDate;

	@JsonProperty("LoyaltyAcctSrc")
	private String loyaltyAcctSrc;

	@JsonProperty("NextRewardPoint")
	private String nextRewardPoint;
	
	@Override
	public String toString() {
	  StringBuilder builder = new StringBuilder();
	  builder.append("ConsumerLoyalty [LoyaltyID=");
	  builder.append(loyaltyID);
	  builder.append(", LoyaltyNumber=");
	  builder.append(loyaltyNumber);
	  builder.append(", LoyaltyTierCode=");
	  builder.append(loyaltyTierCode);
	  builder.append(", NextTierAmount=");
	  builder.append(nextTierAmount);
	  builder.append(", LoyaltyPointCount=");
	  builder.append(loyaltyPointCount);
	  builder.append(", TierStartDate=");
	  builder.append(tierStartDate);
	  builder.append(", TierExpirationDate=");
	  builder.append(tierExpirationDate);
	  builder.append(", LoyaltyStartDate=");
	  builder.append(loyaltyStartDate);
	  builder.append(", LoyaltyEndDate=");
	  builder.append(loyaltyEndDate);
	  builder.append(", LoyaltyAcctSrc=");
	  builder.append(loyaltyAcctSrc);
	  builder.append(", NextRewardPoint=");
	  builder.append(nextRewardPoint);
	  builder.append(", toString()=");
	  builder.append(super.toString());
	  builder.append("]");
	  return builder.toString();
	}
	
}
