package com.mattel.ecomm.core.pojos;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mattel.ecomm.coreservices.core.pojos.ChildProduct;
import com.mattel.ecomm.coreservices.core.pojos.Price;
import com.mattel.ecomm.coreservices.core.pojos.ProductAssociation;

/**
 * @author CTS
 *
 */
public class PDPProductUIResponse {
  @JsonProperty
  private String partNumber;
  @JsonProperty
  private String productCalloutAttribute;
  @JsonProperty
  private String skuName;
  @JsonProperty
  private String marketingAge;
  @JsonProperty
  private String productReviewCount;
  @JsonProperty
  private String productReviewRating;
  @JsonProperty
  private String productReviewEnabled;
  @JsonProperty
  private String availability;
  @JsonProperty
  private String backordarableText;
  @JsonProperty("product_type")
  private String productType;
  @JsonProperty
  private Map<String, String[]> storeMap;
  @JsonProperty
  private String imageLink;
  @JsonProperty
  private Price listPrice;
  @JsonProperty
  private List<ProductAssociation> associationList;
  @JsonProperty
  private Map<String, Object> desciptiveAttributes;
  @JsonProperty
  private String safetyMessage;
  @JsonProperty("product_status")
  private String productStatus;
  @JsonProperty
  private List<ChildProduct> childProducts;
  @JsonProperty
  private String sizeChartLink;
  @JsonProperty
  private String definingAttribute;
  @JsonProperty
  private boolean isRetailInventoryCheckEnabled;
  @JsonProperty
  private List<String> experienceFragmentPath;
  @JsonProperty
  private List<BundleComponentUIResponse> components;
  @JsonProperty
  private String customerSegment;
  @JsonProperty("seo_metaDescription")
  private String seoMetaDescription;
  @JsonProperty
  private String hasAddOns;
  @JsonProperty
  private String affirmIneligible;
  @JsonProperty
  private String excludeShippingCountriesFlag;
  @JsonProperty
  private String isAltCanadaSKU;
  @JsonProperty
  private String thumbnail;
  @JsonProperty
  private String availabilityStatus;
  @JsonProperty
  private String isDynamicKit;
  @JsonProperty
  private String reviewRating;
  @JsonProperty
  private String releaseDateWeb;
  @JsonProperty
  private String offerPrice;
  @JsonProperty("SmallTrunkLowPrice")
  private String smallTrunkLowPrice;
  @JsonProperty("LargeTrunkLowPrice")
  private String largeTrunkLowPrice;
  @JsonProperty
  private String canonicalUrl;
  @JsonProperty
  private String productGTIN;
  @JsonProperty
  private Map<String, String> additionalDescriptiveAttributes;
  @JsonProperty
  private String fullimage;
  @JsonProperty
  private String newOverrideFlag;
  @JsonProperty
  private String viewerVideo;
  @JsonProperty
  private String productCategory;
  @JsonProperty
  private String productCharacter;
  @JsonProperty
  private String productSubCategory;
  @JsonProperty
  private String productSubBrand;
  @JsonProperty
  private String productGiftGuide;
  @JsonProperty
  private String productPromoCategory;
  @JsonProperty
  private String kitComponentHiddenPartNumbers;
  @JsonProperty
  private KitPojo primaryKit;
  @JsonProperty
  private KitPojo secondaryKit;
  @JsonProperty
  private boolean hasBittyTwinAssociations;

  public String getViewerVideo() {
    return viewerVideo;
  }

  public void setViewerVideo(String viewerVideo) {
    this.viewerVideo = viewerVideo;
  }

  public String getPartNumber() {
    return partNumber;
  }

  public void setPartNumber(String partNumber) {
    this.partNumber = partNumber;
  }

  public String getProductCalloutAttribute() {
    return productCalloutAttribute;
  }

  public void setProductCalloutAttribute(String productCalloutAttribute) {
    this.productCalloutAttribute = productCalloutAttribute;
  }

  public String getSkuName() {
    return skuName;
  }

  public void setSkuName(String skuName) {
    this.skuName = skuName;
  }

  public String getMarketingAge() {
    return marketingAge;
  }

  public void setMarketingAge(String marketingAge) {
    this.marketingAge = marketingAge;
  }

  public String getAvailability() {
    return availability;
  }

  public void setAvailability(String availability) {
    this.availability = availability;
  }

  public String getBackordarableText() {
    return backordarableText;
  }

  public void setBackordarableText(String backordarableText) {
    this.backordarableText = backordarableText;
  }

  public String getProductReviewCount() {
    return productReviewCount;
  }

  public void setProductReviewCount(String productReviewCount) {
    this.productReviewCount = productReviewCount;
  }

  public String getProductReviewRating() {
    return productReviewRating;
  }

  public void setProductReviewRating(String productReviewRating) {
    this.productReviewRating = productReviewRating;
  }

  public String getProductReviewEnabled() {
    return productReviewEnabled;
  }

  public void setProductReviewEnabled(String productReviewEnabled) {
    this.productReviewEnabled = productReviewEnabled;
  }

  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public List<ProductAssociation> getAssociationList() {
    return associationList;
  }

  public void setAssociationList(List<ProductAssociation> associationList) {
    this.associationList = associationList;
  }

  public String getImageLink() {
    return imageLink;
  }

  public void setImageLink(String imageLink) {
    this.imageLink = imageLink;
  }

  public Price getListPrice() {
    return listPrice;
  }

  public void setListPrice(Price listPrice) {
    this.listPrice = listPrice;
  }

  public Map<String, Object> getDesciptiveAttributes() {
    return desciptiveAttributes;
  }

  public void setDesciptiveAttributes(Map<String, Object> desciptiveAttributes) {
    this.desciptiveAttributes = desciptiveAttributes;
  }

  public String getSafetyMessage() {
    return safetyMessage;
  }

  public void setSafetyMessage(String safetyMessage) {
    this.safetyMessage = safetyMessage;
  }

  public String getProductStatus() {
    return productStatus;
  }

  public void setProductStatus(String productStatus) {
    this.productStatus = productStatus;
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

  public Map<String, String[]> getStoreMap() {
    return storeMap;
  }

  public void setStoreMap(Map<String, String[]> storeMap) {
    this.storeMap = storeMap;
  }

  public String getDefiningAttribute() {
    return definingAttribute;
  }

  public void setDefiningAttribute(String definingAttribute) {
    this.definingAttribute = definingAttribute;
  }

  public boolean isRetailInventoryCheckEnabled() {
    return isRetailInventoryCheckEnabled;
  }

  public void setRetailInventoryCheckEnabled(boolean isRetailInventoryCheckEnabled) {
    this.isRetailInventoryCheckEnabled = isRetailInventoryCheckEnabled;
  }

  public void setExperienceFragmentPath(List<String> experienceFragmentPath) {
    this.experienceFragmentPath = experienceFragmentPath;
  }

  public List<String> getExperienceFragmentPath() {
    return experienceFragmentPath;
  }

  public List<BundleComponentUIResponse> getComponents() {
    return components;
  }

  public void setComponents(List<BundleComponentUIResponse> components) {
    this.components = components;
  }

  public String getCustomerSegment() {
    return customerSegment;
  }

  public void setCustomerSegment(String customerSegment) {
    this.customerSegment = customerSegment;
  }

  public String getSeoMetaDescription() {
    return seoMetaDescription;
  }

  public void setSeoMetaDescription(String seoMetaDescription) {
    this.seoMetaDescription = seoMetaDescription;
  }

  public String isHasAddOns() {
    return hasAddOns;
  }

  public void setHasAddOns(String hasAddOns) {
    this.hasAddOns = hasAddOns;
  }

  public void setAffirmIneligible(String affirmIneligible) {
    this.affirmIneligible = affirmIneligible;
  }

  public String getExcludeShippingCountriesFlag() {
    return excludeShippingCountriesFlag;
  }

  public void setExcludeShippingCountriesFlag(String excludeShippingCountriesFlag) {
    this.excludeShippingCountriesFlag = excludeShippingCountriesFlag;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }

  public String getAvailabilityStatus() {
    return availabilityStatus;
  }

  public void setAvailabilityStatus(String availabilityStatus) {
    this.availabilityStatus = availabilityStatus;
  }

  public String getReviewRating() {
    return reviewRating;
  }

  public void setReviewRating(String reviewRating) {
    this.reviewRating = reviewRating;
  }

  public String getIsDynamicKit() {
    return isDynamicKit;
  }

  public void setIsDynamicKit(String isDynamicKit) {
    this.isDynamicKit = isDynamicKit;
  }

  public String getReleaseDateWeb() {
    return releaseDateWeb;
  }

  public void setReleaseDateWeb(String releaseDateWeb) {
    this.releaseDateWeb = releaseDateWeb;
  }

  public String getOfferPrice() {
    return offerPrice;
  }

  public void setOfferPrice(String offerPrice) {
    this.offerPrice = offerPrice;
  }

public String getSmallTrunkLowPrice() {
    return smallTrunkLowPrice;
  }

  public void setSmallTrunkLowPrice(String smallTrunkLowPrice) {
    this.smallTrunkLowPrice = smallTrunkLowPrice;
  }

  public String getLargeTrunkLowPrice() {
    return largeTrunkLowPrice;
  }

  public void setLargeTrunkLowPrice(String largeTrunkLowPrice){
      this.largeTrunkLowPrice = largeTrunkLowPrice;
    }

	public String getIsAltCanadaSKU() {
		return isAltCanadaSKU;
	}

	public void setIsAltCanadaSKU(String isAltCanadaSKU) {
		this.isAltCanadaSKU = isAltCanadaSKU;
	}


  public String getCanonicalUrl() {
    return canonicalUrl;
  }

  public void setCanonicalUrl(String canonicalUrl) {
    this.canonicalUrl = canonicalUrl;
  }
  
  public String getProductGTIN() {
    return productGTIN;
  }

  public void setProductGTIN(String productGTIN) {
    this.productGTIN = productGTIN;
  }

  public Map<String, String> getAdditionalDescriptiveAttributes() {
    return additionalDescriptiveAttributes;
  }

  public void setAdditionalDescriptiveAttributes(
      Map<String, String> additionalDescriptiveAttributes) {
    this.additionalDescriptiveAttributes = additionalDescriptiveAttributes;
  }
  
  public String getFullimage() {
     return fullimage;
  }

  public void setFullimage(String fullimage) {
     this.fullimage = fullimage;
  }

  public String getNewOverrideFlag() {
    return newOverrideFlag;
  }

  public void setNewOverrideFlag(String newOverrideFlag) {
    this.newOverrideFlag = newOverrideFlag;
  }
  
  public String getProductCategory() {
    return productCategory;
  }

  public void setProductCategory(String productCategory) {
    this.productCategory = productCategory;
  }

  public String getProductCharacter() {
    return productCharacter;
  }

  public void setProductCharacter(String productCharacter) {
    this.productCharacter = productCharacter;
  }

  public String getProductSubCategory() {
    return productSubCategory;
  }

  public void setProductSubCategory(String productSubCategory) {
    this.productSubCategory = productSubCategory;
  }

  public String getProductSubBrand() {
    return productSubBrand;
  }

  public void setProductSubBrand(String productSubBrand) {
    this.productSubBrand = productSubBrand;
  }

  public String getProductGiftGuide() {
    return productGiftGuide;
  }

  public void setProductGiftGuide(String productGiftGuide) {
    this.productGiftGuide = productGiftGuide;
  }

  public String getProductPromoCategory() {
    return productPromoCategory;
  }

  public void setProductPromoCategory(String productPromoCategory) {
    this.productPromoCategory = productPromoCategory;
  }
  
  public String getKitComponentHiddenPartNumbers() {
	return kitComponentHiddenPartNumbers;
  }

  public void setKitComponentHiddenPartNumbers(String kitComponentHiddenPartNumbers) {
	this.kitComponentHiddenPartNumbers = kitComponentHiddenPartNumbers;
  }

  public KitPojo getPrimaryKit() {
	return primaryKit;
  }

  public void setPrimaryKit(KitPojo primaryKit) {
	this.primaryKit = primaryKit;
  }

  public KitPojo getSecondaryKit() {
	return secondaryKit;
  }

  public void setSecondaryKit(KitPojo secondaryKit) {
	this.secondaryKit = secondaryKit;
  }

  public boolean isHasBittyTwinAssociations() {
	return hasBittyTwinAssociations;
  }

  public void setHasBittyTwinAssociations(boolean hasBittyTwinAssociations) {
	this.hasBittyTwinAssociations = hasBittyTwinAssociations;
  }

  @Override
  public String toString() {
    return "PDPProductUIResponse [partNumber=" + partNumber + ", productCalloutAttribute="
        + productCalloutAttribute + ", skuName=" + skuName + ", marketingAge=" + marketingAge
        + ", productReviewCount=" + productReviewCount + ", productReviewRating="
        + productReviewRating + ", productReviewEnabled=" + productReviewEnabled + ", availability="
        + availability + ", backordarableText=" + backordarableText + ", product_type="
        + productType + ", storeMap=" + storeMap + ", imageLink=" + imageLink + ", listPrice="
        + listPrice + ", associationList=" + associationList + ", desciptiveAttributes="
        + desciptiveAttributes + ", safetyMessage=" + safetyMessage + ", product_status="
        + productStatus + ", childProducts=" + childProducts + ", sizeChartLink=" + sizeChartLink
        + ", definingAttribute=" + definingAttribute + ", isRetailInventoryCheckEnabled="
        + isRetailInventoryCheckEnabled + ", experienceFragmentPath=" + experienceFragmentPath
        + ", components=" + components + ", customerSegment=" + customerSegment
        + ", seo_metaDescription=" + seoMetaDescription + ", hasAddOns=" + hasAddOns
        + " ,affirmIneligible =" + affirmIneligible + " ,excludeShippingCountriesFlag ="
        + excludeShippingCountriesFlag + ", thumbnail=" + thumbnail + ", releaseDateWeb="
        + releaseDateWeb + ",offerPrice=" + offerPrice + ",SmallTrunkLowPrice="
        + smallTrunkLowPrice + ",LargeTrunkLowPrice=" + largeTrunkLowPrice + ",productGTIN="
        + productGTIN + ",isAltCanadaSKU=" + isAltCanadaSKU + ",additionalDescriptiveAttributes="
        + additionalDescriptiveAttributes + ", fullimage=" + fullimage + ",newOverrideFlag="+ newOverrideFlag
        + ", productCategory=" + productCategory + ", productCharacter=" + productCharacter
        + ", productSubCategory=" + productSubCategory + ", productSubBrand=" + productSubBrand
        + ", productGiftGuide=" + productGiftGuide + ", productPromoCategory=" + productPromoCategory
        + ",viewerVideo="+ viewerVideo + ",kitComponentHiddenPartNumbers="+ kitComponentHiddenPartNumbers + ",primaryKit="+ primaryKit
        + ",secondaryKit="+ secondaryKit + ",hasBittyTwinAssociations="+ hasBittyTwinAssociations+ "]";
  }

}
