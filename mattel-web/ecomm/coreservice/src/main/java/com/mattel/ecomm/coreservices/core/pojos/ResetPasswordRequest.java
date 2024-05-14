package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResetPasswordRequest extends BaseRequest {

	@JsonProperty("storeId")
	private String storeId;
	@JsonProperty("catalogId")
	private String catalogId;
	@JsonProperty("URL")
	private String url;
	@JsonProperty("logonId")
	private String logonId;
	@JsonProperty("validationCode")
	private String validationCode;
	@JsonProperty("logonPasswordOld")
	private String logonPasswordOld;
	@JsonProperty("logonPassword")
	private String logonPassword;
	@JsonProperty("logonPasswordVerify")
	private String logonPasswordVerify;
	
	@Override
	public String toString() {
		return "{" + "storeId:'" + storeId + '\'' + ", catalogId:'" + catalogId + '\'' + ", langId:'" + langId + '\'' + ", url:'" + url + '\''  + ", logonId:'" + logonId + '\'' +  ", validationCode:'" + validationCode + '\'' + ", logonPasswordOld:'" + logonPasswordOld + '\'' + ", logonPassword:'" + logonPassword + '\'' + ", logonPasswordVerify:'" + logonPasswordVerify + '\'' + '}';
	}
}
