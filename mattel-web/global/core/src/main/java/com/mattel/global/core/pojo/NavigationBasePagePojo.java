package com.mattel.global.core.pojo;

/**
 * A simple pojo for page properties.
 */
public class NavigationBasePagePojo {

	protected String pageName;
	protected String name;
	protected String pageUrl;
	protected String thumbnailImg;
	protected Boolean isRedirect = false;
	protected String linkingName;
	protected boolean pageActive = false;
	protected String urlTarget;
	protected String adobeTrackingNameForPage;
	protected String thumbnailAltText;
	protected boolean isNotLinkable;
	protected String specialLink;
	protected boolean confettiEffect;
    protected String promoHoverImagePath;

	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public String getThumbnailImg() {
		return thumbnailImg;
	}
	public void setThumbnailImg(String thumbnailImg) {
		this.thumbnailImg = thumbnailImg;
	}
	public Boolean getIsRedirect() {
		return isRedirect;
	}
	public void setIsRedirect(Boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	public String getLinkingName() {
		return linkingName;
	}
	public void setLinkingName(String linkingName) {
		this.linkingName = linkingName;
	}
	public boolean isPageActive() {
		return pageActive;
	}
	public void setPageActive(boolean pageActive) {
		this.pageActive = pageActive;
	}
	public String getUrlTarget() {
		return urlTarget;
	}
	public void setUrlTarget(String urlTarget) {
		this.urlTarget = urlTarget;
	}
	public String getAdobeTrackingNameForPage() {
		return adobeTrackingNameForPage;
	}
	public void setAdobeTrackingNameForPage(String adobeTrackingNameForPage) {
		this.adobeTrackingNameForPage = adobeTrackingNameForPage;
	}
	public String getThumbnailAltText() {
		return thumbnailAltText;
	}
	public void setThumbnailAltText(String thumbnailAltText) {
		this.thumbnailAltText = thumbnailAltText;
	}
	public boolean isNotLinkable() {
		return isNotLinkable;
	}
	public void setNotLinkable(boolean isNotLinkable) {
		this.isNotLinkable = isNotLinkable;
	}
	public String getSpecialLink() {
		return specialLink;
	}
	public void setSpecialLink(String specialLink) {
		this.specialLink = specialLink;
	}
	public boolean isConfettiEffect() { 
	    return confettiEffect; 
	}
    public void setConfettiEffect(boolean confettiEffect) { 
       this.confettiEffect = confettiEffect; 
    }
    public String getPromoHoverImagePath() {
      return promoHoverImagePath;
    }
    public void setPromoHoverImagePath(String promoHoverImagePath) {
      this.promoHoverImagePath = promoHoverImagePath;
    }
	
	@Override
	public String toString() {
		return "SiteNavigationPojo [pageName=" + pageName + ", name=" + name + ", pageUrl=" + pageUrl
				+ ", thumbnailImg=" + thumbnailImg + ", isRedirect=" + isRedirect + ", linkingName=" + linkingName
				+ ", pageActive=" + pageActive + ", urlTarget=" + urlTarget + ", adobeTrackingNameForPage="
				+ adobeTrackingNameForPage + ", thumbnailAltText=" + thumbnailAltText + ", isNotLinkable="
				+ isNotLinkable + ", specialLink=" + specialLink + ", confettiEffect=" + confettiEffect +
                ", promoHoverImagePath=" + promoHoverImagePath +"]";
	}
	
}
