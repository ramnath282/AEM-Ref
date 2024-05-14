package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Contact {

	@JsonProperty
	private String lastName;
	
	@JsonProperty
	private String zipCode;
	
	@JsonProperty
	private String country;
	
	@JsonProperty
	private String city;
	
	@JsonProperty
	private String nickName;
	
	@JsonProperty
	private String addressType;
	
	@JsonProperty
	private List<ContactAttribute> attributes;
	
	@JsonProperty
	private List<String> addressLine;
	
	@JsonProperty
	private String addressId;
	
	@JsonProperty
	private String phone1;
	
	@JsonProperty
	private String firstName;
	
	@JsonProperty
	private String email1;
	
	
	@JsonProperty
	private String fax2;
	
	
	@JsonProperty
	private String state;
	
	
	@JsonProperty
	private String primary;
	
	
}


