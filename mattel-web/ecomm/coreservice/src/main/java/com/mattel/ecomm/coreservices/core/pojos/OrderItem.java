package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItem {
	
	@JsonProperty
	private String promiseDate;
	
	@JsonProperty
	private boolean isDynamicKitComponent ;
    
	@JsonProperty
	private String trackingURL;
	
	@JsonProperty
	private String productSize;
	
	@JsonProperty
	private String discount;
	
	@JsonProperty
	private String productName ;
    
	@JsonProperty
	private String productSKU;
	
	@JsonProperty
	private String sellingPrice;
	
	@JsonProperty
	private String orderShipping;
	
	@JsonProperty
	private String imageURL ;
    
	@JsonProperty
	private String productColor;
	
	@JsonProperty
	private String backOrderDate;
	
	@JsonProperty
	private String trackingNumber;
	
	@JsonProperty
	private String quantity ;
    
	@JsonProperty
	private String shippingMethod;
	
	@JsonProperty
	private String dollDesignId;
	
	@JsonProperty
	private String orderTax;
	
	@JsonProperty
	private String descriptiveText ;
    
	@JsonProperty
	private String thumbNail;
	
	@JsonProperty
	private String orderShippingTax;
	
	@JsonProperty
	private String orderItemNo;
	
	@JsonProperty
	private String listPrice ;
    
	@JsonProperty
	private String status;

	@Override
	public String toString() {
		return "promiseDate=" + promiseDate + ", isDynamicKitComponent=" + isDynamicKitComponent
				+ ", trackingURL=" + trackingURL + ", productSize=" + productSize + ", discount=" + discount
				+ ", productName=" + productName + ", productSKU=" + productSKU + ", sellingPrice=" + sellingPrice
				+ ", orderShipping=" + orderShipping + ", imageURL=" + imageURL + ", productColor=" + productColor
				+ ", backOrderDate=" + backOrderDate + ", trackingNumber=" + trackingNumber + ", quantity=" + quantity
				+ ", shippingMethod=" + shippingMethod + ", dollDesignId=" + dollDesignId + ", orderTax=" + orderTax
				+ ", descriptiveText=" + descriptiveText + ", thumbNail=" + thumbNail + ", orderShippingTax="
				+ orderShippingTax + ", orderItemNo=" + orderItemNo + ", listPrice=" + listPrice + ", status=" + status
				;
	}
	
	
	

}
