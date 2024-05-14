package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressInfoResponse extends BaseResponse {
	
	@JsonProperty("addresses")
	private List<Address> address;
	
	@JsonProperty
	private String defaultShipMethod;

}
