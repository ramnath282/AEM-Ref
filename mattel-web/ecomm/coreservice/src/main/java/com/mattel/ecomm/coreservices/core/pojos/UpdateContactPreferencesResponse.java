package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateContactPreferencesResponse extends BaseResponse {

	@JsonProperty("Response")
	private String response;

	@Override
	public String toString() {
		return "UpdateContactPreferencesResponse [response=" + response + "]";
	}

}
