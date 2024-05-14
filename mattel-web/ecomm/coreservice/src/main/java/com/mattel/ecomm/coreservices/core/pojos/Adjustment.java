package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Adjustment {

	@JsonProperty
	private String amount;

	@JsonProperty
	private String code;

	@JsonProperty
	private String displayLevel;

	@JsonProperty
	private String usage;

	@JsonProperty
	private String currency;
}
