package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactAttribute {

	@JsonProperty
	private String key;
	
	@JsonProperty
	private String value;
	
}


