package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderPayment {
	
	@JsonProperty
	private String creditCardNumber;
	
	@JsonProperty
	private String creditCardExpiry;
	
	@JsonProperty
	private String creditCardType;
	
	@JsonProperty
	private String paymentAmount;
	
	@JsonProperty
	private String paymentType;
}
