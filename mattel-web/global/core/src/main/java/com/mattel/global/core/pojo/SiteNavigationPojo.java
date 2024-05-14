package com.mattel.global.core.pojo;

import java.util.Date;
import java.util.List;

/**
 * A simple pojo for page properties.
 */
public class SiteNavigationPojo extends NavigationBasePagePojo{

  private List<SiteNavigationPojo> childPageList;
  private Date lastModifiedDate;

  /*
   * Variables for FisherPrice Brand Shop All Links
   */
  private String shopAllClass;
  private String subLinkClass;

  public List<SiteNavigationPojo> getChildPageList() {
    return childPageList;
  }

  public void setChildPageList(List<SiteNavigationPojo> childPageList) {
    this.childPageList = childPageList;
  }

  public String getShopAllClass() {
    return shopAllClass;
  }

  public void setShopAllClass(String shopAllClass) {
    this.shopAllClass = shopAllClass;
  }

  public String getSubLinkClass() {
    return subLinkClass;
  }

  public void setSubLinkClass(String subLinkClass) {
    this.subLinkClass = subLinkClass;
  }

  public Date getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(Date lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  @Override
  public String toString() {
    return "SiteNavigationPojo [pageName=" + pageName + ", name=" + name + ", pageUrl=" + pageUrl
        + ", thumbnailImg=" + thumbnailImg + ", isRedirect=" + isRedirect + ", linkingName="
        + linkingName + ", pageActive=" + pageActive + ", urlTarget=" + urlTarget
        + ", adobeTrackingNameForPage=" + adobeTrackingNameForPage + ", thumbnailAltText="
        + thumbnailAltText + ", isNotLinkable=" + isNotLinkable + ", specialLink=" + specialLink
        + ", childPageList=" + childPageList + ", lastModifiedDate=" + lastModifiedDate
        + ", shopAllClass=" + shopAllClass + ", subLinkClass=" + subLinkClass + "]";
  }

}
