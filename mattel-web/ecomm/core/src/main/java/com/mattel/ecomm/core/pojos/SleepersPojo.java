package com.mattel.ecomm.core.pojos;

import java.util.List;

/**
 * @author CTS
 *
 */
public class SleepersPojo {

  private String sleeperType;
  private String imageURL;
  private boolean hasFirstSequenceDoll;
  private boolean hasSecondSequenceDoll;
  private List<DollsPojo> dolls;
  
  public String getSleeperType() {
    return sleeperType;
  }

  public void setSleeperType(String sleeperType) {
    this.sleeperType = sleeperType;
  }

  public List<DollsPojo> getDolls() {
    return dolls;
  }

  public void setDolls(List<DollsPojo> dolls) {
    this.dolls = dolls;
  }

  public String getImageURL() {
    return imageURL;
  }

  public void setImageURL(String imageURL) {
    this.imageURL = imageURL;
  }
  
  public boolean isHasFirstSequenceDoll() {
	return hasFirstSequenceDoll;
  }

  public void setHasFirstSequenceDoll(boolean hasFirstSequenceDoll) {
	this.hasFirstSequenceDoll = hasFirstSequenceDoll;
  }
  
  public boolean isHasSecondSequenceDoll() {
	return hasSecondSequenceDoll;
  }

  public void setHasSecondSequenceDoll(boolean hasSecondSequenceDoll) {
	this.hasSecondSequenceDoll = hasSecondSequenceDoll;
  }

  @Override
  public String toString() {
    return "SleepersPojo [sleeperType=" + sleeperType + ", imageURL="
        + imageURL + ", dolls=" + dolls + ", hasFirstSequenceDoll=" 
    	+ hasFirstSequenceDoll + ", hasSecondSequenceDoll=" + hasSecondSequenceDoll + "]";
  }

}