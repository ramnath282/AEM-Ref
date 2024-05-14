package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplyRewardsResponse extends BaseResponse{

	@JsonProperty
	private String orderId;

	@JsonProperty
	private String promoCode;
	
	@Override
	public String toString() {
	  StringBuilder builder = new StringBuilder();
	  builder.append("ApplyRewardsResponse [orderId=");
	  builder.append(orderId);
	  builder.append(", promoCode=");
	  builder.append(promoCode);
	  builder.append(", toString()=");
	  builder.append(super.toString());
	  builder.append("]");
	  return builder.toString();
	}

}
