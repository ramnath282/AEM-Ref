package com.mattel.ecomm.core.pojos.shopify;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CTS
 *
 */
public class GTSummaryUIResponse {
  @JsonProperty
  private String buyable;
  @JsonProperty("product_status")
  private String productStatus;
  @JsonProperty
  private String language;
  @JsonProperty
  private String availability;
  @JsonProperty("seo_metaDescription")
  private String seoMetaDescription;
  @JsonProperty
  private String imageLink;
  @JsonProperty("seo_PageTitle")
  private String seoPageTitle;
  @JsonProperty("seo_imageAltText")
  private String seoImageAltText;
  @JsonProperty
  private String thumbnail;
  @JsonProperty
  private String invStatus;
  @JsonProperty
  private String auxDescription1;
  @JsonProperty("seo_urlKeyword")
  private String seoUrlKeyword;
  @JsonProperty("product_type")
  private String productType;
  @JsonProperty
  private String name;
  @JsonProperty
  private String fullimage;
  @JsonProperty
  private String partNumber;
  @JsonProperty
  private long variantId;
  @JsonProperty
  private Map<String, Object> attributes;
  @JsonProperty
  private String region;
  @JsonProperty
  private GTAddonPojo gtHearingAidDetails;
  @JsonProperty
  private GTAddonPojo gtEarPiercingDetails;
  @JsonProperty
  private GTAddonPojo gtBoxDetails;
  @JsonProperty
  private GTAddonPojo gtEnvelopeDetails;
  @JsonProperty
  private GTAddonPojo gtLetterDetails;
  @JsonProperty
  private List<GTAddonPojo> gtNonDisplayableItems;
  @JsonProperty
  private List<GTAddonPojo> gtDisplayableItems;

  /**
   * @return the buyable
   */
  public String getBuyable() {
    return buyable;
  }

  /**
   * @param buyable
   *          the buyable to set
   */
  public void setBuyable(String buyable) {
    this.buyable = buyable;
  }

  /**
   * @return the product_status
   */
  public String getProductStatus() {
    return productStatus;
  }

  /**
   * @param product_status
   *          the product_status to set
   */
  public void setProductStatus(String productStatus) {
    this.productStatus = productStatus;
  }

  /**
   * @return the language
   */
  public String getLanguage() {
    return language;
  }

  /**
   * @param language
   *          the language to set
   */
  public void setLanguage(String language) {
    this.language = language;
  }

  /**
   * @return the availability
   */
  public String getAvailability() {
    return availability;
  }

  /**
   * @param availability
   *          the availability to set
   */
  public void setAvailability(String availability) {
    this.availability = availability;
  }

  /**
   * @return the seo_metaDescription
   */
  public String getSeoMetaDescription() {
    return seoMetaDescription;
  }

  /**
   * @param seo_metaDescription
   *          the seo_metaDescription to set
   */
  public void setSeoMetaDescription(String seoMetaDescription) {
    this.seoMetaDescription = seoMetaDescription;
  }

  /**
   * @return the imageLink
   */
  public String getImageLink() {
    return imageLink;
  }

  /**
   * @param imageLink
   *          the imageLink to set
   */
  public void setImageLink(String imageLink) {
    this.imageLink = imageLink;
  }

  /**
   * @return the seo_PageTitle
   */
  public String getSeoPageTitle() {
    return seoPageTitle;
  }

  /**
   * @param seo_PageTitle
   *          the seo_PageTitle to set
   */
  public void setSeoPageTitle(String seoPageTitle) {
    this.seoPageTitle = seoPageTitle;
  }

  /**
   * @return the seo_imageAltText
   */
  public String getSeoImageAltText() {
    return seoImageAltText;
  }

  /**
   * @param seo_imageAltText
   *          the seo_imageAltText to set
   */
  public void setSeoImageAltText(String seoImageAltText) {
    this.seoImageAltText = seoImageAltText;
  }

  /**
   * @return the thumbnail
   */
  public String getThumbnail() {
    return thumbnail;
  }

  /**
   * @param thumbnail
   *          the thumbnail to set
   */
  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }

  /**
   * @return the invStatus
   */
  public String getInvStatus() {
    return invStatus;
  }

  /**
   * @param invStatus
   *          the invStatus to set
   */
  public void setInvStatus(String invStatus) {
    this.invStatus = invStatus;
  }

  /**
   * @return the auxDescription1
   */
  public String getAuxDescription1() {
    return auxDescription1;
  }

  /**
   * @param auxDescription1
   *          the auxDescription1 to set
   */
  public void setAuxDescription1(String auxDescription1) {
    this.auxDescription1 = auxDescription1;
  }

  /**
   * @return the seo_urlKeyword
   */
  public String getSeoUrlKeyword() {
    return seoUrlKeyword;
  }

  /**
   * @param seo_urlKeyword
   *          the seo_urlKeyword to set
   */
  public void setSeoUrlKeyword(String seoUrlKeyword) {
    this.seoUrlKeyword = seoUrlKeyword;
  }

  /**
   * @return the product_type
   */
  public String getProductType() {
    return productType;
  }

  /**
   * @param product_type
   *          the product_type to set
   */
  public void setProductType(String productType) {
    this.productType = productType;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name
   *          the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the fullimage
   */
  public String getFullimage() {
    return fullimage;
  }

  /**
   * @param fullimage
   *          the fullimage to set
   */
  public void setFullimage(String fullimage) {
    this.fullimage = fullimage;
  }

  /**
   * @return the partNumber
   */
  public String getPartNumber() {
    return partNumber;
  }

  /**
   * @param partNumber
   *          the partNumber to set
   */
  public void setPartNumber(String partNumber) {
    this.partNumber = partNumber;
  }

  /**
   * @return the attributes
   */
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  /**
   * @param productAttributes
   *          the attributes to set
   */
  public void setAttributes(Map<String, Object> productAttributes) {
    this.attributes = productAttributes;
  }

  /**
   * @return the region
   */
  public String getRegion() {
    return region;
  }

  /**
   * @param region
   *          the region to set
   */
  public void setRegion(String region) {
    this.region = region;
  }

  /**
   * @return the gtHearingAidDetails
   */
  public GTAddonPojo getGtHearingAidDetails() {
    return gtHearingAidDetails;
  }

  /**
   * @param gtHearingAidDetails
   *          the gtHearingAidDetails to set
   */
  public void setGtHearingAidDetails(GTAddonPojo gtHearingAidDetails) {
    this.gtHearingAidDetails = gtHearingAidDetails;
  }

  /**
   * @return the gtEarPiercingDetails
   */
  public GTAddonPojo getGtEarPiercingDetails() {
    return gtEarPiercingDetails;
  }

  /**
   * @param gtEarPiercingDetails
   *          the gtEarPiercingDetails to set
   */
  public void setGtEarPiercingDetails(GTAddonPojo gtEarPiercingDetails) {
    this.gtEarPiercingDetails = gtEarPiercingDetails;
  }

  /**
   * @return the gtBoxDetails
   */
  public GTAddonPojo getGtBoxDetails() {
    return gtBoxDetails;
  }

  /**
   * @param gtBoxDetails
   *          the gtBoxDetails to set
   */
  public void setGtBoxDetails(GTAddonPojo gtBoxDetails) {
    this.gtBoxDetails = gtBoxDetails;
  }

  /**
   * @return the gtEnvelopeDetails
   */
  public GTAddonPojo getGtEnvelopeDetails() {
    return gtEnvelopeDetails;
  }

  /**
   * @param gtEnvelopeDetails
   *          the gtEnvelopeDetails to set
   */
  public void setGtEnvelopeDetails(GTAddonPojo gtEnvelopeDetails) {
    this.gtEnvelopeDetails = gtEnvelopeDetails;
  }

  /**
   * @return the getLetterDetails
   */
  public GTAddonPojo getGtLetterDetails() {
    return gtLetterDetails;
  }

  /**
   * @param getLetterDetails
   *          the getLetterDetails to set
   */
  public void setGtLetterDetails(GTAddonPojo gtLetterDetails) {
    this.gtLetterDetails = gtLetterDetails;
  }

  /**
   * @return the gtNonDisplayableItems
   */
  public List<GTAddonPojo> getGtNonDisplayableItems() {
    return gtNonDisplayableItems;
  }

  /**
   * @param gtNonDisplayableItems
   *          the gtNonDisplayableItems to set
   */
  public void setGtNonDisplayableItems(List<GTAddonPojo> gtNonDisplayableItems) {
    this.gtNonDisplayableItems = gtNonDisplayableItems;
  }

  /**
   * @return the gtDisplayableItems
   */
  public List<GTAddonPojo> getGtDisplayableItems() {
    return gtDisplayableItems;
  }

  /**
   * @param gtDisplayableItems
   *          the gtDisplayableItems to set
   */
  public void setGtDisplayableItems(List<GTAddonPojo> gtDisplayableItems) {
    this.gtDisplayableItems = gtDisplayableItems;
  }

  public long getVariantId() {
    return variantId;
  }

  public void setVariantId(long variantId) {
    this.variantId = variantId;
  }

  @Override
  public String toString() {
    return "GTSummaryUIResponse [buyable=" + buyable + ", productStatus=" + productStatus
        + ", language=" + language + ", availability=" + availability + ", seoMetaDescription="
        + seoMetaDescription + ", imageLink=" + imageLink + ", seoPageTitle=" + seoPageTitle
        + ", seoImageAltText=" + seoImageAltText + ", thumbnail=" + thumbnail + ", invStatus="
        + invStatus + ", auxDescription1=" + auxDescription1 + ", seoUrlKeyword=" + seoUrlKeyword
        + ", productType=" + productType + ", name=" + name + ", fullimage=" + fullimage
        + ", partNumber=" + partNumber + ", variantId=" + variantId + ", attributes=" + attributes
        + ", region=" + region + ", gtHearingAidDetails=" + gtHearingAidDetails
        + ", gtEarPiercingDetails=" + gtEarPiercingDetails + ", gtBoxDetails=" + gtBoxDetails
        + ", gtEnvelopeDetails=" + gtEnvelopeDetails + ", gtLetterDetails=" + gtLetterDetails
        + ", gtNonDisplayableItems=" + gtNonDisplayableItems + ", gtDisplayableItems="
        + gtDisplayableItems + "]";
  }

}