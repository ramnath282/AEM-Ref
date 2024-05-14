package com.mattel.ecomm.coreservices.core.pojos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class DefaultPayment {
	@JsonProperty
	private String maskAccount;
	
	@JsonProperty
	private String lastUser;
	
	@JsonProperty
	private String usersId;
	
	@JsonProperty
	private String expMonth;
	
	@JsonProperty
	private String expYear;
	
	@JsonProperty
	private String creditCardId;
	
	@JsonProperty
	private String cardType;
	
	@JsonProperty
	private Date createDate;
		
	@JsonProperty
	private String defaultFlag;
	
	@JsonProperty
	private String addressId;
	
	@JsonProperty
	private String field1;
	
	@JsonProperty
	private String field2;
	
	@JsonProperty
	private String token;
	
	@JsonProperty
	private String field3;
	
	@JsonProperty
	private String cardName;
	
	@JsonProperty
	private String hashCode;
	
}

