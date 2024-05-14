package com.mattel.global.core.pojo;

/**
 * @author CTS. A simple pojo for footer links.
 */
public class CarouselPojo {

	private String heading;
	private String subHeading;
	private String image;
	private String imageAlttext;
	private String overlayHeading;
	private String overlaySubHeading;
	private String ctaLabel;
	private String ctaLink;
	private String ctaAltText;
	private String textPositioning;
	private String ctaStyling;
	private String ctaPositioning;
	private String showArrow;
	private String showPagination;
	private String textBackground;
	private String ctaRenderoption;
	private Boolean external;
	
	public Boolean getExternal() {
		return external;
	}

	public void setExternal(Boolean external) {
		this.external = external;
	}
	
	public String getCtaRenderoption() {
		return ctaRenderoption;
	}

	public void setCtaRenderoption(String ctaRenderoption) {
		this.ctaRenderoption = ctaRenderoption;
	}

	public String getShowArrow() {
		return showArrow;
	}

	public void setShowArrow(String showArrow) {
		this.showArrow = showArrow;
	}

	public String getShowPagination() {
		return showPagination;
	}

	public void setShowPagination(String showPagination) {
		this.showPagination = showPagination;
	}

	public String getTextBackground() {
		return textBackground;
	}

	public void setTextBackground(String textBackground) {
		this.textBackground = textBackground;
	}

	public String getTextPositioning() {
		return textPositioning;
	}

	public void setTextPositioning(String textPositioning) {
		this.textPositioning = textPositioning;
	}

	public String getCtaStyling() {
		return ctaStyling;
	}

	public void setCtaStyling(String ctaStyling) {
		this.ctaStyling = ctaStyling;
	}

	public String getCtaPositioning() {
		return ctaPositioning;
	}

	public void setCtaPositioning(String ctaPositioning) {
		this.ctaPositioning = ctaPositioning;
	}

	public String getOverlayHeading() {
		return overlayHeading;
	}

	public void setOverlayHeading(String overlayHeading) {
		this.overlayHeading = overlayHeading;
	}

	public String getOverlaySubHeading() {
		return overlaySubHeading;
	}

	public void setOverlaySubHeading(String overlaySubHeading) {
		this.overlaySubHeading = overlaySubHeading;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImageAlttext() {
		return imageAlttext;
	}

	public void setImageAlttext(String imageAlttext) {
		this.imageAlttext = imageAlttext;
	}

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

	public String getCtaAltText() {
		return ctaAltText;
	}

	public void setCtaAltText(String ctaAltText) {
		this.ctaAltText = ctaAltText;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getSubHeading() {
		return subHeading;
	}
	
	public void setSubHeading(String subHeading) {
		this.subHeading = subHeading;
	}

  @Override
  public String toString() {
    return "CarouselPojo [heading=" + heading + ", subHeading=" + subHeading + ", image=" + image
        + ", imageAlttext=" + imageAlttext + ", overlayHeading=" + overlayHeading
        + ", overlaySubHeading=" + overlaySubHeading + ", ctaLabel=" + ctaLabel + ", ctaLink="
        + ctaLink + ", ctaAltText=" + ctaAltText + ", textPositioning=" + textPositioning
        + ", ctaStyling=" + ctaStyling + ", ctaPositioning=" + ctaPositioning + ", showArrow="
        + showArrow + ", showPagination=" + showPagination + ", textBackground=" + textBackground
        + ", ctaRenderoption=" + ctaRenderoption + ", external=" + external + "]";
  }

	

}
