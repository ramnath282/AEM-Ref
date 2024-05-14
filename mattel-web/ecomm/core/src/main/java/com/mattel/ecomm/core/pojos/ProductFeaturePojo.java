package com.mattel.ecomm.core.pojos;

import java.util.List;

public class ProductFeaturePojo {

	private String prodFeatureTitle;
	private String prodFeatureDescription;
	private List<String> prodFeatureBulletItems;
	
	public String getProdFeatureTitle() {
		return prodFeatureTitle;
	}
	public void setProdFeatureTitle(String prodFeatureTitle) {
		this.prodFeatureTitle = prodFeatureTitle;
	}
	public String getProdFeatureDescription() {
		return prodFeatureDescription;
	}
	public void setProdFeatureDescription(String prodFeatureDescription) {
		this.prodFeatureDescription = prodFeatureDescription;
	}
	public List<String> getProdFeatureBulletItems() {
		return prodFeatureBulletItems;
	}
	public void setProdFeatureBulletItems(List<String> prodFeatureBulletItems) {
		this.prodFeatureBulletItems = prodFeatureBulletItems;
	}
	
}
