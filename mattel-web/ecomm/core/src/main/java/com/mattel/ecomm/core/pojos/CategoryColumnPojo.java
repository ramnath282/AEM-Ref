package com.mattel.ecomm.core.pojos;

/**
 * Pojo for Category Variation in Navigation
 * 
 * @author CTS
 *
 */
public class CategoryColumnPojo {
  private String subCatTitle;
  private String subCatLink;
  private String subCatUrlTarget;
  private String subCatAeText;
  private String parentPage;
  private String columnListFrom;
  private String viewAllText;
  private String viewAllLink;
  private String linkTargetForViewAll;
  private String aeForViewAll;
  private String[] parentPageList;

  public String getSubCatTitle() {
    return subCatTitle;
  }

  public void setSubCatTitle(String subCatTitle) {
    this.subCatTitle = subCatTitle;
  }

  public String getSubCatLink() {
    return subCatLink;
  }

  public void setSubCatLink(String subCatLink) {
    this.subCatLink = subCatLink;
  }

  public String getSubCatUrlTarget() {
    return subCatUrlTarget;
  }

  public void setSubCatUrlTarget(String subCatUrlTarget) {
    this.subCatUrlTarget = subCatUrlTarget;
  }

  public String getSubCatAeText() {
    return subCatAeText;
  }

  public void setSubCatAeText(String subCatAeText) {
    this.subCatAeText = subCatAeText;
  }

  public String getParentPage() {
    return parentPage;
  }

  public void setParentPage(String parentPage) {
    this.parentPage = parentPage;
  }

  public String getColumnListFrom() {
    return columnListFrom;
  }

  public void setColumnListFrom(String columnListFrom) {
    this.columnListFrom = columnListFrom;
  }

  public String getViewAllText() {
    return viewAllText;
  }

  public void setViewAllText(String viewAll) {
    this.viewAllText = viewAll;
  }

  public String getViewAllLink() {
    return viewAllLink;
  }

  public void setViewAllLink(String viewAllLink) {
    this.viewAllLink = viewAllLink;
  }

  public String getLinkTargetForViewAll() {
    return linkTargetForViewAll;
  }

  public void setLinkTargetForViewAll(String linkTargetForViewAll) {
    this.linkTargetForViewAll = linkTargetForViewAll;
  }

  public String getAeForViewAll() {
    return aeForViewAll;
  }

  public void setAeForViewAll(String aeForViewAll) {
    this.aeForViewAll = aeForViewAll;
  }

  public String[] getParentPageList() {
    return parentPageList;
  }

  public void setParentPageList(String[] parentPageList) {
    this.parentPageList = parentPageList;
  }
}
