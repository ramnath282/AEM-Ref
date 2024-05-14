package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MyAccountResponse extends BaseResponse {
	@JsonProperty("lastName")
	private String lastName;
	@JsonProperty("registrationStatus")
	private String registrationStatus;
	@JsonProperty("resourceId")
	private String resourceId;
	@JsonProperty("preferredCurrency")
	private String preferredCurrency;
	@JsonProperty("distinguishedName")
	private String distinguishedName;
	@JsonProperty("orgizationId")
	private String orgizationId;
	@JsonProperty("addressId")
	private String addressId;
	@JsonProperty("phone1")
	private String phone1;
	@JsonProperty("accountStatus")
	private String accountStatus;
	@JsonProperty("email1")
	private String email1;
	@JsonProperty("profileType")
	private String profileType;
	@JsonProperty("contact")
	private String[] contact;

}
