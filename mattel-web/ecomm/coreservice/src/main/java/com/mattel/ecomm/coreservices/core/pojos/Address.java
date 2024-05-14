package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {

	@JsonProperty
	private String middleName;

	@JsonProperty
	private String lastName;

	@JsonProperty
	private String billingCodeType;

	@JsonProperty
	private String personTitle;
	
	@JsonProperty
	private String [] addressLine;
	
	@JsonProperty
	private String organizationUnitName;

	@JsonProperty
	private String city;

	@JsonProperty
	private String addressField2;

	@JsonProperty
	private String nickName;

	@JsonProperty
	private String addressType;

	@JsonProperty
	private String addressId;

	@JsonProperty
	private String state;

	@JsonProperty
	private boolean defaultAddress;

	@JsonProperty
	private String country;

	@JsonProperty
	private String officeAddress;

	@JsonProperty
	private String zipCode;

	@JsonProperty
	private String businessTitle;
	
	@JsonProperty
	private String firstName;
	
	@JsonProperty
	private String mailShareIndicator;
	
	@JsonProperty
	private String mailCatalogueIndicator;
	
	@JsonProperty
	private String childIndicator;
	
}
