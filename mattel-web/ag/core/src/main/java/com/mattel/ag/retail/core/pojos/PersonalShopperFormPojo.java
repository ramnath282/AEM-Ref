package com.mattel.ag.retail.core.pojos;

/**
 * @author CTS.
 */
public class PersonalShopperFormPojo {

	private String location;
	private String contactOption;
	private String firstVisitOption;
  public String getLocation() {
    return location;
  }
  public void setLocation(String location) {
    this.location = location;
  }
  public String getContactOption() {
    return contactOption;
  }
  public void setContactOption(String contactOption) {
    this.contactOption = contactOption;
  }
  public String getFirstVisitOption() {
    return firstVisitOption;
  }
  public void setFirstVisitOption(String firstVisitOption) {
    this.firstVisitOption = firstVisitOption;
  }
  @Override
  public String toString() {
    return "PersonalShopperFormPojo [location=" + location + ", contactOption=" + contactOption
        + ", firstVisitOption=" + firstVisitOption + "]";
  }	
}
