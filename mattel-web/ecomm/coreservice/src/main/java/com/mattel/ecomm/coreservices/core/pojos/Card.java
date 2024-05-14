package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * POJO for holding card details.
 */
@Getter
@Setter
public class Card {
	@JsonProperty
	private String maskAccount;

	@JsonProperty
	private String lastUser;
	
	@JsonProperty
	private String usersId;

	@JsonProperty
	private String expMonth;

	@JsonProperty
	private String creditCardId;

	@JsonProperty
	private String cardType;

	@JsonProperty
	private String createDate;

	@JsonProperty
	private String defaultFlag;
	
	@JsonProperty
	private String addressId;

	@JsonProperty
	private Address address;

	@JsonProperty
	private String field1;

	@JsonProperty
	private String field2;

	@JsonProperty
	private String field3;

	@JsonProperty
	private String token;

	@JsonProperty
	private String expYear;

	@JsonProperty
	private String cardName;

	@JsonProperty
	private String hashCode;
}
