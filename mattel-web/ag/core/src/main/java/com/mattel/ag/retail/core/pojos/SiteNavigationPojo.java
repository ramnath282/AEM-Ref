package com.mattel.ag.retail.core.pojos;

/**
 * A simple pojo for page properties.
 */
public class SiteNavigationPojo {

	private String label;
	private String url;
	private String alt;
	private String targetUrl;



	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAlt() {
		return alt;
	}
	
	public void setAlt(String alt) {
		this.alt = alt;
	}


	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public String getTargetUrl() {
		return targetUrl;
	}
	
	@Override
	public String toString() {
		return "SiteNavigationPojo [label=" + label + ", url=" + url + ", alt=" + alt + ", targetUrl=" + targetUrl
				+ "]";
	}
}
