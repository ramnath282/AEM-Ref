package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author CTS.
 */
@Setter
@Getter
public class LoginResponse extends BaseResponse {

	@JsonProperty("personalizationID")
	private String personalizationID;
	@JsonProperty("WCToken")
	private String wcToken;
	@JsonProperty("userId")
	private String userId;
	@JsonProperty("WCTrustedToken")
	private String wcTrustedToken;
	@JsonProperty("multipassURL")
    private String multipassURL;
	@JsonProperty("identityAccessToken")
    private String identityAccessToken;
	@JsonProperty("shopifyCustomerId")
    private String shopifyCustomerId ;

	@Override
	public String toString() {
		return "{" + "personalizationID='" + personalizationID + '\'' + ", WCToken='" + wcToken + '\'' + ", userId='"
				+ userId + '\'' + ", WCTrustedToken='" + wcTrustedToken + '\''+ ", multipassURL='" + multipassURL + '\''
				+ ", identityAccessToken='" + identityAccessToken  + '\''+ ", shopifyCustomerId='" + shopifyCustomerId  
				+ '\''+ ", errors" + getErrors() + '}';
	}
}
