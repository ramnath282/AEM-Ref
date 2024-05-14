package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

public class UpdateDefaultAddressResponse extends BaseResponse {

	@Getter @Setter @JsonProperty
	String viewTaskName;
	
	@Getter @Setter @JsonProperty
	String addressId;
}
