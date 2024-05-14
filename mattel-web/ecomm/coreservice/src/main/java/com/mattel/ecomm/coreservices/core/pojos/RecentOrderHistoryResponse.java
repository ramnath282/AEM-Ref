package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RecentOrderHistoryResponse extends BaseResponse {

	@JsonProperty
	private OrderHistoryList orderHistoryList;

	@Override
	public String toString() {
		return "{" +
				"orderHistoryList=" + orderHistoryList +
				'}';
	}

	@Getter
	@Setter
	public class OrderHistoryList {
		@JsonProperty("Order")
		private List<Order> order;
		@JsonProperty
		private int recordSetCount;
		@JsonProperty
		private int recordSetTotal;
	}
}
