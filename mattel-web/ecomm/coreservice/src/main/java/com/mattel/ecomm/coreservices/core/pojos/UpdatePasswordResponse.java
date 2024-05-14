package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdatePasswordResponse extends BaseResponse {

	@JsonProperty
	private String viewTaskName;

	@JsonProperty
	private String userId;

	@JsonProperty
	private String addressId;

	@Override
	public String toString() {
		return "UpdatePasswordResponse [viewTaskName=" + viewTaskName + ", userId=" + userId + ", addressId="
				+ addressId + "]";
	}

}