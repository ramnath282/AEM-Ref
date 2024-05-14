package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductInterestResponse extends BaseResponse {
	@JsonProperty("eventInterestList")
	private String[] eventInterestList;
	@JsonProperty("productInterestList")
	private String[] productInterestList;
	@JsonProperty("userExistingProductInterest")
	private String userExistingProductInterest;
	@JsonProperty("partyId")
	private String partyId;
	@JsonProperty("locationInterestList")
	private String[] locationInterestList;
	@JsonProperty("userPreviousProductInterest")
	private String userPreviousProductInterest;
	@JsonProperty("email")
	private String email;
	@JsonProperty("hash")
	private String hash;
}
