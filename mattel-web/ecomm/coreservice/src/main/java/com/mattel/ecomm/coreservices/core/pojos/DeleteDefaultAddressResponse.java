package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DeleteDefaultAddressResponse extends BaseResponse{

	@JsonProperty
	private List<String> addressId;
	
}
