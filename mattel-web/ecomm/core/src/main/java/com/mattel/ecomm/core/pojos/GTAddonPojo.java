package com.mattel.ecomm.core.pojos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mattel.ecomm.coreservices.core.pojos.ChildProduct;

public class GTAddonPojo {

  @JsonProperty
  private String buyable;
  @JsonProperty("product_type")
  private String productType;
  @JsonProperty
  private String associationType;
  @JsonProperty
  private String name;
  @JsonProperty
  private String optionalName;
  @JsonProperty
  private String partNumber;
  @JsonProperty
  private String thumbnail;
  @JsonProperty
  private String fullimage;
  @JsonProperty("KitOption")
  private String kitOption;
  @JsonProperty("KitDisplayable")
  private String kitDisplayable;
  @JsonProperty("KitDisplaySequence")
  private String kitDisplaySequence;
  @JsonProperty("KitInstructions")
  private String kitInstructions;
  @JsonProperty("TrunkDescription")
  private String trunkDescription;
  @JsonProperty
  private String imageLink;
  @JsonProperty
  private List<ChildProduct> childProducts;
  /**
   * @return the buyable
   */
  public String getBuyable() {
    return buyable;
  }
  /**
   * @param buyable the buyable to set
   */
  public void setBuyable(String buyable) {
    this.buyable = buyable;
  }
  /**
   * @return the product_type
   */
  public String getProductType() {
    return productType;
  }
  /**
   * @param product_type the product_type to set
   */
  public void setProductType(String productType) {
    this.productType = productType;
  }
  /**
   * @return the associationType
   */
  public String getAssociationType() {
    return associationType;
  }
  /**
   * @param associationType the associationType to set
   */
  public void setAssociationType(String associationType) {
    this.associationType = associationType;
  }
  /**
   * @return the name
   */
  public String getName() {
    return name;
  }
  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }
  /**
   * @return the partNumber
   */
  public String getPartNumber() {
    return partNumber;
  }
  /**
   * @param partNumber the partNumber to set
   */
  public void setPartNumber(String partNumber) {
    this.partNumber = partNumber;
  }
  /**
   * @return the thumbnail
   */
  public String getThumbnail() {
    return thumbnail;
  }
  /**
   * @param thumbnail the thumbnail to set
   */
  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }
  /**
   * @return the fullimage
   */
  public String getFullimage() {
    return fullimage;
  }
  /**
   * @param fullimage the fullimage to set
   */
  public void setFullimage(String fullimage) {
    this.fullimage = fullimage;
  }
  /**
   * @return the kitOption
   */
  public String getKitOption() {
    return kitOption;
  }
  /**
   * @param kitOption the kitOption to set
   */
  public void setKitOption(String kitOption) {
    this.kitOption = kitOption;
  }
  /**
   * @return the kitDisplayable
   */
  public String getKitDisplayable() {
    return kitDisplayable;
  }
  /**
   * @param kitDisplayable the kitDisplayable to set
   */
  public void setKitDisplayable(String kitDisplayable) {
    this.kitDisplayable = kitDisplayable;
  }
  /**
   * @return the kitDisplaySequence
   */
  public String getKitDisplaySequence() {
    return kitDisplaySequence;
  }
  /**
   * @param kitDisplaySequence the kitDisplaySequence to set
   */
  public void setKitDisplaySequence(String kitDisplaySequence) {
    this.kitDisplaySequence = kitDisplaySequence;
  }
  /**
   * @return the kitInstructions
   */
  public String getKitInstructions() {
    return kitInstructions;
  }
  /**
   * @param kitInstructions the kitInstructions to set
   */
  public void setKitInstructions(String kitInstructions) {
    this.kitInstructions = kitInstructions;
  }
  /**
   * @return the trunkDescription
   */
  public String getTrunkDescription() {
    return trunkDescription;
  }
  /**
   * @param trunkDescription the trunkDescription to set
   */
  public void setTrunkDescription(String trunkDescription) {
    this.trunkDescription = trunkDescription;
  }
  /**
   * @return the imageLink
   */
  public String getImageLink() {
    return imageLink;
  }
  /**
   * @param imageLink the imageLink to set
   */
  public void setImageLink(String imageLink) {
    this.imageLink = imageLink;
  }
  /**
   * @return the childProducts
   */
  public List<ChildProduct> getChildProducts() {
    return childProducts;
  }
  /**
   * @param childProducts the childProducts to set
   */
  public void setChildProducts(List<ChildProduct> childProducts) {
    this.childProducts = childProducts;
  }
/**
 * @return the optionalName
 */
public String getOptionalName() {
	return optionalName;
}
/**
 * @param optionalName the optionalName to set
 */
public void setOptionalName(String optionalName) {
	this.optionalName = optionalName;
}
  
  
}