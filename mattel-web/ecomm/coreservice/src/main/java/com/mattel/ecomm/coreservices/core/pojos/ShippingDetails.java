package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShippingDetails {

	@JsonProperty
	private String country;

	@JsonProperty
	private String firstName;

	@JsonProperty
	private String lastName;

	@JsonProperty
	private String zipCode;

	@JsonProperty
	private String city;

	@JsonProperty
	private String phone;

	@JsonProperty
	private String addressLine1;

	@JsonProperty
	private String addressLine2;

	@JsonProperty
	private String state;
	
	@JsonProperty
	private String partyId;
	
	@JsonProperty
	private String email;
	
	@JsonProperty
	private String shippingMethod;
}
