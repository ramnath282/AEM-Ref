package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetails {
	
	@JsonProperty
	private OrderPayment orderPayment;
	
	@JsonProperty("OrderItems")
	private List<OrderItem> orderItems;
	
	@JsonProperty
	private ShippingDetails billingDetails;
	
	@JsonProperty
	private ShippingDetails shippingDetails;
	
	@JsonProperty
	private OrderInfo orderInfo;
	

}
