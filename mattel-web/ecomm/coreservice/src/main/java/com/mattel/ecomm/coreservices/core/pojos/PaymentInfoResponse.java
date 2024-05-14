package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * POJO holding payment information.
 */
@Getter
@Setter
public class PaymentInfoResponse extends BaseResponse {
	@JsonProperty("Cards")
	private List<Card> cards; 
}
