package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;


public class ShippingModesResponse extends BaseResponse {	
		
	@Setter @Getter @JsonProperty("usableShippingMode")
    private UsableShippingModePojo[] usableShippingMode;         
			 
}

