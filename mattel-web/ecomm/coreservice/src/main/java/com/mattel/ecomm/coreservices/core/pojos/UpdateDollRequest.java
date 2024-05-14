package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class UpdateDollRequest extends BaseRequest {
	@JsonProperty
	private String operation;
	@JsonProperty
	private String whatDoll;
	@JsonProperty
	private String whenDoll;
	@JsonProperty
	private String whereDoll;
	@JsonProperty
	private String whoDoll;
	@JsonProperty
	private String whyDoll;
	@JsonProperty
	private String productID;

	@Override
	public String toString() {
		return "UpdateDollRequest [operation=" + operation + ", whatDoll=" + whatDoll + ", whenDoll=" + whenDoll
				+ ", whereDoll=" + whereDoll + ", whoDoll=" + whoDoll + ", whyDoll=" + whyDoll + ", productID="
				+ productID + "]";
	}

}
