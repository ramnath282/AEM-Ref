package com.mattel.ecomm.coreservices.core.pojos;

import lombok.Getter;
import lombok.Setter;


public class ResetPasswordResponse extends BaseResponse {
	
	@Setter	@Getter
	private boolean passwordChangeStatus = false;
	
	@Override
	public String toString() {
		return "{"  + ", passwordChangeStatus='" + passwordChangeStatus + '\'' + ", cookies='" + getCookieList() + '\'' + ", errors" + getErrors() + '}';
	}
}
