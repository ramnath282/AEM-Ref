package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RemoveRewardRequest extends BaseRequest {

	@JsonProperty
	private String promoCode;

	@Override
	public String toString() {
		return "{" + "promoCode:'" + promoCode + '}';
	}
}
