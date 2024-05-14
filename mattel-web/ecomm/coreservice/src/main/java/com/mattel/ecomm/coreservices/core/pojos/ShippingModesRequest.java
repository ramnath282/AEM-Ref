package com.mattel.ecomm.coreservices.core.pojos;

import javax.servlet.http.Cookie;

import lombok.Getter;
import lombok.Setter;

public class ShippingModesRequest extends BaseRequest {
		
	@Getter @Setter
	private String storeKey;
	@Getter @Setter
    private Cookie[] cookies;
}
