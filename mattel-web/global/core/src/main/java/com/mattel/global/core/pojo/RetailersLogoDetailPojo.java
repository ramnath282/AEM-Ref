package com.mattel.global.core.pojo;

public class RetailersLogoDetailPojo {

	private String logoImage;
	private String logoAltTxt;
	private String retailerLogoLink;
	private String analyticsText;

	public String getLogoImage() {
		return logoImage;
	}

	public void setLogoImage(String logoImage) {
		this.logoImage = logoImage;
	}

	public String getLogoAltTxt() {
		return logoAltTxt;
	}

	public void setLogoAltTxt(String logoAltTxt) {
		this.logoAltTxt = logoAltTxt;
	}

	public String getRetailerLogoLink() {
		return retailerLogoLink;
	}

	public void setRetailerLogoLink(String retailerLogoLink) {
		this.retailerLogoLink = retailerLogoLink;
	}

	public String getAnalyticsText() {
		return analyticsText;
	}

	public void setAnalyticsText(String analyticsText) {
		this.analyticsText = analyticsText;
	}

  @Override
  public String toString() {
    return "RetailersLogoDetailPojo [logoImage=" + logoImage + ", logoAltTxt=" + logoAltTxt
        + ", retailerLogoLink=" + retailerLogoLink + ", analyticsText=" + analyticsText + "]";
  }
	
}
