package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReceivePreference {

	@JsonProperty
	private String storeID;
	
	@JsonProperty
	private String value;
}
