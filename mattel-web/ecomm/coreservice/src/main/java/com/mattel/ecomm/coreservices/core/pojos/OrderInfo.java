package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderInfo {

	@JsonProperty
	private String orderNumber;

	@JsonProperty
	private String orderedFrom;

	@JsonProperty
	private String orderShipping;

	@JsonProperty
	private String orderShippingTax;

	@JsonProperty
	private String orderDiscount;

	@JsonProperty
	private String orderTax;

	@JsonProperty
	private String orderDate;

	@JsonProperty
	private String orderTotal;

	@JsonProperty
	private String orderSubTotal;
	
	@JsonProperty
	private String orderStatusCodeText;
}
