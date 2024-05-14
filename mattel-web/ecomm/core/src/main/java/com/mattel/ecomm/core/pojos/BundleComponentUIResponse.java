package com.mattel.ecomm.core.pojos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mattel.ecomm.coreservices.core.pojos.ChildProduct;
import com.mattel.ecomm.coreservices.core.pojos.Price;
import com.mattel.ecomm.coreservices.core.pojos.ProductAssociation;

/**
 * @author CTS
 *
 */
public class BundleComponentUIResponse {
  @JsonProperty
  private String buyable;

  @JsonProperty
  private String imageLink;

  @JsonProperty
  private String thumbnail;

  @JsonProperty("product_type")
  private String productType;

  @JsonProperty
  private String parentPartnumber;

  @JsonProperty
  private String fullimage;

  @JsonProperty
  private String partNumber;

  @JsonProperty
  private String language;

  @JsonProperty
  private String region;

  @JsonProperty
  private String name;

  @JsonProperty
  private boolean isRetailInventoryCheckEnabled;

  @JsonProperty
  private String sizeChartLink;

  @JsonProperty
  private String definingAttribute;

  @JsonProperty
  private Price listPrice;

  @JsonProperty
  private List<ProductAssociation> associations;

  @JsonProperty
  private List<ChildProduct> childProducts;

  @JsonProperty
  private String hasAddOns;
  
  @JsonProperty
  private String affirmIneligible;

  @JsonProperty
  private String isDynamicKitPrimaryComponent;
  
  @JsonProperty("seo_urlKeyword")
  private String seoUrlKeyword;

  public String getBuyable() {
    return buyable;
  }

  public void setBuyable(String buyable) {
    this.buyable = buyable;
  }

  public String getImageLink() {
    return imageLink;
  }

  public void setImageLink(String imageLink) {
    this.imageLink = imageLink;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }

  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public String getParentPartnumber() {
    return parentPartnumber;
  }

  public void setParentPartnumber(String parentPartnumber) {
    this.parentPartnumber = parentPartnumber;
  }

  public String getFullimage() {
    return fullimage;
  }

  public void setFullimage(String fullimage) {
    this.fullimage = fullimage;
  }

  public String getPartNumber() {
    return partNumber;
  }

  public void setPartNumber(String partNumber) {
    this.partNumber = partNumber;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean getIsRetailInventoryCheckEnabled() {
    return isRetailInventoryCheckEnabled;
  }

  public void setIsRetailInventoryCheckEnabled(boolean isRetailInventoryCheckEnabled) {
    this.isRetailInventoryCheckEnabled = isRetailInventoryCheckEnabled;
  }

  public String getDefiningAttribute() {
    return definingAttribute;
  }

  public void setDefiningAttribute(String definingAttribute) {
    this.definingAttribute = definingAttribute;
  }

  public Price getListPrice() {
    return listPrice;
  }

  public void setListPrice(Price listPrice) {
    this.listPrice = listPrice;
  }

  public List<ProductAssociation> getAssociations() {
    return associations;
  }

  public void setAssociations(List<ProductAssociation> associations) {
    this.associations = associations;
  }

  public List<ChildProduct> getChildProducts() {
    return childProducts;
  }

  public void setChildProducts(List<ChildProduct> childProducts) {
    this.childProducts = childProducts;
  }

  public String getSizeChartLink() {
    return sizeChartLink;
  }

  public void setSizeChartLink(String sizeChartLink) {
    this.sizeChartLink = sizeChartLink;
  }

  public String isHasAddOns() {
    return hasAddOns;
  }

  public void setHasAddOns(String hasAddOns) {
    this.hasAddOns = hasAddOns;
  }
  
  public String getAffirmIneligible() {
    return affirmIneligible;
  }

  public void setAffirmIneligible(String affirmIneligible) {
    this.affirmIneligible = affirmIneligible;
  }

  public String getIsDynamicKitPrimaryComponent() {
    return isDynamicKitPrimaryComponent;
  }

  public void setIsDynamicKitPrimaryComponent(String isDynamicKitPrimaryComponent) {
    this.isDynamicKitPrimaryComponent = isDynamicKitPrimaryComponent;
  }

  public String getSeoUrlKeyword() {
    return seoUrlKeyword;
  }

  public void setSeoUrlKeyword(String seoUrlKeyword) {
    this.seoUrlKeyword = seoUrlKeyword;
  }
  
  @Override
  public String toString() {
    return "BundleComponentUIResponse [buyable=" + buyable + ", imageLink=" + imageLink
        + ", thumbnail=" + thumbnail + ", product_type=" + productType + ", parentPartnumber="
        + parentPartnumber + ", fullimage=" + fullimage + ", partNumber=" + partNumber
        + ", language=" + language + ", region=" + region + ", name=" + name
        + ", isRetailInventoryCheckEnabled=" + isRetailInventoryCheckEnabled + ", sizeChartLink="
        + sizeChartLink + ", definingAttribute=" + definingAttribute + ", listPrice=" + listPrice
        + ", associations=" + associations + ", childProducts=" + childProducts + ", hasAddOns="
        + hasAddOns + ", affirmIneligible="+ affirmIneligible+ ",seo_urlKeyword="+seoUrlKeyword+"]";
  }

}
