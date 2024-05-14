package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

public class CardDetailsResponse extends BaseResponse {
	@Getter
	@Setter
	@JsonProperty("maskAccount")
	private String maskAccount;
	@Getter
	@Setter
	@JsonProperty("lastUser")
	private String lastUser;
	@Getter
	@Setter
	@JsonProperty("usersId")
	private String usersId;
	@Getter
	@Setter
	@JsonProperty("expMonth")
	private String expMonth;
	@Getter
	@Setter
	@JsonProperty("creditCardid")
	private String creditCardid;
	@Getter
	@Setter
	@JsonProperty("cardType")
	private String cardType;
	@Getter
	@Setter
	@JsonProperty("createDate")
	private String createDate;
	@Getter
	@Setter
	@JsonProperty("defaultTag")
	private String defaultTag;
	@Getter
	@Setter
	@JsonProperty("addressId")
	private String addressId;
	@Getter
	@Setter
	@JsonProperty("field1")
	private String field1;
	@Getter
	@Setter
	@JsonProperty("field2")
	private String field2;
	@Getter
	@Setter
	@JsonProperty("token")
	private String token;
	@Getter
	@Setter
	@JsonProperty("field3")
	private String field3;
	@Getter
	@Setter
	@JsonProperty("expYear")
	private String expYear;
	@Getter
	@Setter
	@JsonProperty("hashCode")
	private String hashCode;
	@Getter
	@Setter
	@JsonProperty("cardName")
	private String cardName;

}
