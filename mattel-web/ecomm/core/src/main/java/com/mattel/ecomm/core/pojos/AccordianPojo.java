package com.mattel.ecomm.core.pojos;

import java.util.List;

/**
 * @author CTS
 *
 */
public class AccordianPojo {

  private String accordianTitle;
  private List<String> accordionKeyList;
  private boolean flag;

  public String getAccordianTitle() {
    return accordianTitle;
  }

  public void setAccordianTitle(String accordianTitle) {
    this.accordianTitle = accordianTitle;
  }

  public List<String> getAccordionKeyList() {
    return accordionKeyList;
  }

  public void setAccordionKeyList(List<String> accordionKeyList) {
    this.accordionKeyList = accordionKeyList;
  }

  public boolean isFlag() {
    return flag;
  }

  public void setFlag(boolean flag) {
    this.flag = flag;
  }

  @Override
  public String toString() {
    return "AccordianPojo [accordianTitle=" + accordianTitle + ", accordionKeyList="
        + accordionKeyList + ", flag=" + flag + "]";
  }

}