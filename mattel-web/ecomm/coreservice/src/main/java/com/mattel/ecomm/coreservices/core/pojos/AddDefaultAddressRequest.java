package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddDefaultAddressRequest extends BaseRequest {

	@JsonProperty("firstName")
	private String firstName;

	@JsonProperty("lastName")
	private String lastName;

	@JsonProperty("addressType")
	private String addressType;

	@JsonProperty("zipCode")
	private String zipCode;

	@JsonProperty("addressLine")
	private String[] addressLine;

	@JsonProperty("country")
	private String country;

	@JsonProperty("phone1")
	private String phone1;

	@JsonProperty("primary")
	private String primary;

	@JsonProperty("city")
	private String city;

	@JsonProperty("state")
	private String state;

	@JsonProperty("addressField2")
	private String addressField2;

	@JsonProperty("organizationUnitName")
	private String organizationUnitName = "";

	@JsonProperty
	private String billingCodeType;

	@Override
	public String toString() {
		return "AddDefaultAddressRequest [firstName=" + firstName + ", lastName=" + lastName + ", addressType="
				+ addressType + ", zipCode=" + zipCode + ", addressLine=" + Arrays.toString(addressLine) + ", country="
				+ country + ", phone1=" + phone1 + ", primary=" + primary + ", city=" + city + ", state=" + state
				+ ", addressField2=" + addressField2 + ", organizationUnitName=" + organizationUnitName
				+ ", billingCodeType=" + billingCodeType + "]";
	}

}
