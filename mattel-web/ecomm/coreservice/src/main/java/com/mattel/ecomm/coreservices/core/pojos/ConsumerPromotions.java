package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsumerPromotions {

	@JsonProperty("PromotionID")
	private String promotionID;

	@JsonProperty("RewardsPromoCode")
	private String rewardsPromoCode;

	@JsonProperty("PromotionType")
	private String promotionType;

	@JsonProperty("PromotionBrand")
	private String promotionBrand;
	
	@JsonProperty("AwardAmount")
	private String awardAmount;
	
	@JsonProperty("PromotionCreatedDate")
	private String promotionCreatedDate;

	@JsonProperty("PromotionStartDate")
	private String promotionStartDate;

	@JsonProperty("PromotionExpiryDate")
	private String promotionExpiryDate;

	@JsonProperty("RedeemedStatus")
	private String redeemedStatus;

	@JsonProperty("RedeemedSource")
	private String redeemedSource;

	@JsonProperty("ActiveFlag")
	private String activeFlag;

	@JsonProperty("ModifiedDate")
	private String modifiedDate;
	
	@Override
	public String toString() {
	  StringBuilder builder = new StringBuilder();
	  builder.append("ConsumerPromotions [PromotionID=");
	  builder.append(promotionID);
	  builder.append(", RewardsPromoCode=");
	  builder.append(rewardsPromoCode);
	  builder.append(", PromotionType=");
	  builder.append(promotionType);
	  builder.append(", PromotionBrand=");
	  builder.append(promotionBrand);
	  builder.append(", AwardAmount=");
	  builder.append(awardAmount);
	  builder.append(", PromotionCreatedDate=");
	  builder.append(promotionCreatedDate);
	  builder.append(", PromotionStartDate=");
	  builder.append(promotionStartDate);
	  builder.append(", PromotionExpiryDate=");
	  builder.append(promotionExpiryDate);
	  builder.append(", RedeemedStatus=");
	  builder.append(redeemedStatus);
	  builder.append(", RedeemedSource=");
	  builder.append(redeemedSource);
	  builder.append(", ActiveFlag=");
	  builder.append(activeFlag);
	  builder.append(", ModifiedDate=");
	  builder.append(modifiedDate);
	  builder.append(", toString()=");
	  builder.append(super.toString());
	  builder.append("]");
	  return builder.toString();
	}
	
}
