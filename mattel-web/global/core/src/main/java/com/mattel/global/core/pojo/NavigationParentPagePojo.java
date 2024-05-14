package com.mattel.global.core.pojo;

import java.util.List;

public class NavigationParentPagePojo extends NavigationBasePagePojo{
	
	private List<NavigationBasePagePojo> childPageList;

	public List<NavigationBasePagePojo> getChildPageList() {
		return childPageList;
	}

	public void setChildPageList(List<NavigationBasePagePojo> childPageList) {
		this.childPageList = childPageList;
	}
	
	@Override
	public String toString() {
		return "SiteNavigationPojo [pageName=" + pageName + ", name=" + name + ", pageUrl=" + pageUrl
				+ ", thumbnailImg=" + thumbnailImg + ", isRedirect=" + isRedirect + ", linkingName=" + linkingName
				+ ", pageActive=" + pageActive + ", urlTarget=" + urlTarget + ", adobeTrackingNameForPage="
				+ adobeTrackingNameForPage + ", thumbnailAltText=" + thumbnailAltText + ", isNotLinkable="
				+ isNotLinkable + ", specialLink=" + specialLink  + ", childPageList=" + childPageList + "]";
	}
}
