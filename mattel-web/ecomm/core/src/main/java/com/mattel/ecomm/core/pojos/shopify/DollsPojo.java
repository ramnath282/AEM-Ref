package com.mattel.ecomm.core.pojos.shopify;

/**
 * @author CTS
 *
 */
public class DollsPojo {

  private String dollType;
  private String imageURL;
  private String associationType;
  private String productType;
  private String kitDisplaySequence;
  private String kitDefaultSequence;
  private String partNumber;
  private long variantId;
  private String product_buyable;

  public String getDollType() {
    return dollType;
  }

  public void setDollType(String dollType) {
    this.dollType = dollType;
  }

  public String getImageURL() {
    return imageURL;
  }

  public void setImageURL(String imageURL) {
    this.imageURL = imageURL;
  }

  public String getKitDisplaySequence() {
    return kitDisplaySequence;
  }

  public void setKitDisplaySequence(String kitDisplaySequence) {
    this.kitDisplaySequence = kitDisplaySequence;
  }

  public String getKitDefaultSequence() {
    return kitDefaultSequence;
  }

  public void setKitDefaultSequence(String kitDefaultSequence) {
    this.kitDefaultSequence = kitDefaultSequence;
  }

  public String getPartNumber() {
    return partNumber;
  }

  public void setPartNumber(String partNumber) {
    this.partNumber = partNumber;
  }

  public long getVariantId() {
    return variantId;
  }

  public void setVariantId(long variantId) {
    this.variantId = variantId;
  }

  public String getAssociationType() {
    return associationType;
  }

  public void setAssociationType(String associationType) {
    this.associationType = associationType;
  }

  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public String getProduct_buyable() {
    return product_buyable;
  }
  
  public void setProduct_buyable(String product_buyable) {
    this.product_buyable = product_buyable;
  }
  
  @Override
  public String toString() {
    return "DollsPojo [dollType=" + dollType + ", imageURL=" + imageURL + ", associationType="
        + associationType + ", productType=" + productType + ", kitDisplaySequence="
        + kitDisplaySequence + ", partNumber=" + partNumber + ", kitDefaultSequence="
        + kitDefaultSequence + ", product_buyable=" + product_buyable + "]";
  }
}