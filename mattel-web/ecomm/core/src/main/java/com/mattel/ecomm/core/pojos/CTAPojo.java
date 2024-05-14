package com.mattel.ecomm.core.pojos;

/**
 * @author CTS
 *
 */
public class CTAPojo {
	private String ctaLabel;
	private String ctaLink;
	private String ctaType;
	private String ctaStyle;

	public String getCtaStyle() { return ctaStyle; }

	public void setCtaStyle(String ctaStyle) { this.ctaStyle = ctaStyle; }

	public String getCtaType() { return ctaType; }

	public void setCtaType(String ctaType) { this.ctaType = ctaType; }

	public String getCtaLabel() {
		return ctaLabel;
	}

	public void setCtaLabel(String ctaLabel) {
		this.ctaLabel = ctaLabel;
	}

	public String getCtaLink() {
		return ctaLink;
	}

	public void setCtaLink(String ctaLink) {
		this.ctaLink = ctaLink;
	}

	@Override
	public String toString() {
		return "CTAPojo [ctaLabel=" + ctaLabel + ", ctaLink=" + ctaLink + "]";
	}
}
