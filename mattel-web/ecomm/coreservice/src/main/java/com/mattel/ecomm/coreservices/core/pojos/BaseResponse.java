package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

@Getter
@Setter
public class BaseResponse {
	@JsonProperty("errors")
	private List<ErrorResponse> errors;

	private List<Cookie> cookieList;
	
	@JsonIgnore
	private Map<String,String> responseHeaders;

	@Override
	public String toString() {
		return "{" + "errors=" + errors + "cookieList=" + cookieList
				+ "responseHeaders=" + responseHeaders + '}';
	}
}
