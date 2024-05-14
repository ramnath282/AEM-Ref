package com.mattel.ag.retail.core.pojos;

/**
 * A simple pojo for page properties.
 */
public class StoreDetailsPojo {

	private String pageTitle;
	private String pageUrl;
	private boolean isInternational;
	private String location;

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public boolean isInternational() {
		return isInternational;
	}

	public void setInternational(boolean international) {
		isInternational = international;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "StoreDetailsPojo [pageTitle=" + pageTitle + ", pageUrl=" + pageUrl + ", isInternational="
				+ isInternational + ", location=" + location + "]";
	}

}
