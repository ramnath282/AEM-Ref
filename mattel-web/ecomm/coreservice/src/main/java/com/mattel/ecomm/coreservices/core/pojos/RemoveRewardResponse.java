package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoveRewardResponse extends BaseResponse{

	@JsonProperty
	private String orderId;
	
	@Override
	public String toString() {
	  StringBuilder builder = new StringBuilder();
	  builder.append("RemoveRewardResponse [orderId=");
	  builder.append(orderId);
	  builder.append(", toString()=");
	  builder.append(super.toString());
	  builder.append("]");
	  return builder.toString();
	}

}
