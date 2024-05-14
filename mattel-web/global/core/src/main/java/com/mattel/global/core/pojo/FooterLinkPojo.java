package com.mattel.global.core.pojo;

/**
 * A simple pojo for page properties.
 */
public class FooterLinkPojo {

	private String label;
	private String url;
	private String alt;
	private String targetUrl;
	private Boolean external;
	
	public Boolean getExternal() {
		return external;
	}

	public void setExternal(Boolean external) {
		this.external = external;
	}
	
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
		return "Footer Link Pojo List [label=" + label + ", url=" + url + ", alt=" + alt + ", external=" + external + " targetUrl=" + targetUrl
				+ "]";
	}
}
