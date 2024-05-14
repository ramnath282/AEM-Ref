package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest extends BaseRequest {

	@JsonProperty("logonId")
	private String logonId;
	@JsonProperty("logonPassword")
	private String logonPassword;
	@JsonProperty("redirectURL")
    private String redirectURL;

	@Override
	public String toString() {
		return "{" + "logonId:'" + logonId + '\'' + ", logonPassword:'" + logonPassword + '\''+ ", redirectURL:'" + redirectURL + '\'' + '}';
	}
}
