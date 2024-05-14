package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MyAccountRequest extends BaseRequest {

	@JsonProperty("WCToken")
	private String wcToken;
	
	@JsonProperty("WCTrustedToken")
	private String wcTrustedToken;
	
	private String endPoint;
}
