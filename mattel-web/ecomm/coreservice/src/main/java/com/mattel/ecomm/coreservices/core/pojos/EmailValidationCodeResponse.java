package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailValidationCodeResponse extends BaseResponse{
	
private boolean emailValidationProcessStatus = false;
@JsonProperty
private String userId;
	
	@Override
	public String toString() {
		return "{"  + ", emailValidationProcessStatus='" + emailValidationProcessStatus + '\'' + ", cookies='" + getCookieList() + '\'' + ", errors" + getErrors() + '}';
	}

}
