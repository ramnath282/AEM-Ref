package com.mattel.ecomm.core.pojos;

/**
 * @author CTS
 *
 */
public class DollsPojo {

  private String dollType;
  private String imageURL;
  private String kitDisplaySequence;
  private String kitDefaultSequence;
  private String partNumber;

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

  @Override
  public String toString() {
    return "DollsPojo [dollType=" + dollType + ", imageURL="
        + imageURL + ", kitDisplaySequence=" + kitDisplaySequence 
        + ", kitDefaultSequence=" + kitDefaultSequence + ", partNumber=" + partNumber + "]";
  }

}