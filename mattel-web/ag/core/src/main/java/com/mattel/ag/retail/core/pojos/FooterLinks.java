package com.mattel.ag.retail.core.pojos;

/**
 * @author CTS. A simple pojo for footer links.
 */
public class FooterLinks {

	private String linkText;

	public String getLinkText() {
		return linkText;
	}

	public void setLinkText(String linkText) {
		this.linkText = linkText;
	}

	@Override
	public String toString() {
		return "FooterLinks [linkText=" + linkText + "]";
	}

}
