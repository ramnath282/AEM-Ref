package com.mattel.ecomm.core.pojos;

/**
 * @author CTS
 *
 *         Referred in AG track
 */
public class BreadcrumbDetails extends BreadcrumbPojo{
  private String pagePath;
  private boolean hideInNav;

  public String getPagePath() {
    return pagePath;
  }

  public void setPagePath(String pagePath) {
    this.pagePath = pagePath;
  }
  
  public boolean isHideInNav() {
	return hideInNav;
  }

  public void setHideInNav(boolean hideInNav) {
	this.hideInNav = hideInNav;
  }

@Override
  public String toString() {
    return "BreadcrumbDetails [title=" + getTitle() + ", pagePath=" + pagePath + ", pageLink=" + getPageLink()
        + ", adobeTrackingName=" + getAdobeTrackingName() +"hideInNav="+ hideInNav + "]";
  }

}
