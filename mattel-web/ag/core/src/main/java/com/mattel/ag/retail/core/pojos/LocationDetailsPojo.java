package com.mattel.ag.retail.core.pojos;

import java.util.List;

public class LocationDetailsPojo {

	private String storeName;
	private String zomatoURL;
	private String storeTag;
	private String gratuityRequired;
	private String pricingAmount;
	private String pricingOption;
	private List<LocationDateDetailsPojo> locationDateDetails;
	private String costInfo;
	
	

	public String getCostInfo() {
		return costInfo;
	}

	public void setCostInfo(String costInfo) {
		this.costInfo = costInfo;
	}

	public String getPricingAmount() {
		return pricingAmount;
	}

	public void setPricingAmount(String pricingAmount) {
		this.pricingAmount = pricingAmount;
	}

	public String getPricingOption() {
		return pricingOption;
	}

	public void setPricingOption(String pricingOption) {
		this.pricingOption = pricingOption;
	}

	public String getGratuityRequired() {
		return gratuityRequired;
	}

	public void setGratuityRequired(String gratuityRequired) {
		this.gratuityRequired = gratuityRequired;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getZomatoURL() {
		return zomatoURL;
	}

	public void setZomatoURL(String zomatoURL) {
		this.zomatoURL = zomatoURL;
	}

	public String getStoreTag() {
		return storeTag;
	}

	public void setStoreTag(String storeTag) {
		this.storeTag = storeTag;
	}

	public List<LocationDateDetailsPojo> getLocationDateDetails() {
		return locationDateDetails;
	}

	public void setLocationDateDetails(List<LocationDateDetailsPojo> locationDateDetails) {
		this.locationDateDetails = locationDateDetails;
	}

	@Override
	public String toString() {
		return "LocationDetailsPojo [storeName=" + storeName + ", zomatoURL=" + zomatoURL + ", storeTag=" + storeTag
				+ ", locationDateDetails=" + locationDateDetails + "]";
	}

}
