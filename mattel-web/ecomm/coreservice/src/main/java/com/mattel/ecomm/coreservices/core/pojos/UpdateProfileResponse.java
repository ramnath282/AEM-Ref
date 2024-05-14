package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateProfileResponse extends BaseResponse {
	
	@JsonProperty
	String userId;
	
	@JsonProperty
	String addressId;
}
