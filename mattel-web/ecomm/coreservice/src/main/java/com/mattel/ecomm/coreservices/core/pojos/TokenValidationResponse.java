package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

public class TokenValidationResponse extends BaseResponse {
	@Setter
	@Getter
	@JsonProperty("registerType")
	private String registerType;

	@Override
	public String toString() {
		return "TokenValidationResponse [registerType=" + registerType + "]";
	}

	public String getRegisterType() {
		return registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}
}
