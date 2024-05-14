package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
	
@Setter
@Getter
public class EmailValidationCodeRequest extends BaseRequest {
	
	@JsonProperty("challengeAnswer")
	private String challengeAnswer;
	@JsonProperty("storeId")
	private String storeId;
	@JsonProperty("catalogId")
	private String catalogId;
	@JsonProperty("userNameDisplay")
	private String userNameDisplay;
	@JsonProperty("userName")
	private String userName;
	
	@Override
	public String toString() {
		return "{" + "challengeAnswer:'" + challengeAnswer + '\'' + ", storeId:'" + storeId + '\'' + ", catalogId:'" + catalogId + '\'' + ", langId:'" + langId + '\'' + ", userNameDisplay:'" + userNameDisplay + '\'' + ", userName:'" + userName + '\'' + '}';
	}

}
