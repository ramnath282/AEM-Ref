package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Order {
	
	@JsonProperty
	private String orderTypeCode;
	
	@JsonProperty
	private String totalShippingCharge;
	
	@JsonProperty
	private String resourceId;
	
	@JsonProperty ("orderId")
	private String orderId;
	
	@JsonProperty
	private String lastUpdateDate;
	
	@JsonProperty
	private Channel channel;
	
	@JsonProperty
	private String orderStatus;
	
	@JsonProperty (value="x_isPurchaseOrderNumberRequired")
    private String isPurchaseOrderNumberRequired;
	
	@JsonProperty
    private String totalShippingChargeCurrency;
	
	@JsonProperty
    private String buyerId;
	
	@JsonProperty
    private String grandTotalCurrency;
	
	@JsonProperty
    private String buyerDistinguishedName;
	
	@JsonProperty(value="x_isPersonalAddressesAllowedForShipping")
    private String isPersonalAddressesAllowedForShipping;
	
	@JsonProperty
    private String storeNameIdentifier;
	
	@JsonProperty
    private String totalProductPriceCurrency;
	
	@JsonProperty
    private String totalProductPrice;
	
	@JsonProperty
    private String locked;
	
	@JsonProperty(value="x_field1")
    private String field1;
	
	@JsonProperty
    private String placedDate;
	
	@JsonProperty
    private String totalAdjustmentCurrency;
	
	@JsonProperty
    private String totalSalesTaxCurrency;
	
	@JsonProperty
    private String totalSalesTax;
	
	@JsonProperty
    private String grandTotal;
	
	@JsonProperty
    private String storeUniqueID;
	
	@JsonProperty
    private String shipAsComplete;
	
	@JsonProperty(value="x_trackingIds")
    private String trackingIds;

	@JsonProperty
    private String totalShippingTax;
	
	@JsonProperty
    private String prepareIndicator;
	
	@JsonProperty
    private String totalShippingTaxCurrency;
	@JsonProperty
    private List<Adjustment> adjustment;
	
	@JsonProperty
    private String totalAdjustment;
	@JsonProperty ("storeFrontId")
	private String storeFrontId;
	@JsonProperty ("orderNoHash")
	private String orderNoHash;
	@JsonProperty
	private String originalSystem;
	@JsonProperty
	private String orderType;
}
