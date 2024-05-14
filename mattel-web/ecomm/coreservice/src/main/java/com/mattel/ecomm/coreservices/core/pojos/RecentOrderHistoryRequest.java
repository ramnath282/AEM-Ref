package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RecentOrderHistoryRequest extends BaseRequest {

	@JsonProperty("WCToken")
	private String wcToken;
	
	@JsonProperty("WCTrustedToken")
	private String wcTrustedToken;
	
	@JsonProperty
	private String storeKey;

}
