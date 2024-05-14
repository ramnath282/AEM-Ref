package com.mattel.ecomm.core.pojos;

/**
 * @author CTS
 *
 */
public class ESpotViewPortPojo {

  private int rowNo;
  private int columnNo;
  private int spanLength;
  private MarketingBannerDisplayPojo displayObject;

  public int getRowNo() {
    return rowNo;
  }

  public void setRowNo(int rowNo) {
    this.rowNo = rowNo;
  }

  public int getColumnNo() {
    return columnNo;
  }

  public void setColumnNo(int columnNo) {
    this.columnNo = columnNo;
  }

  public int getSpanLength() {
    return spanLength;
  }

  public void setSpanLength(int spanLength) {
    this.spanLength = spanLength;
  }

  public MarketingBannerDisplayPojo getDisplayObject() {
    return displayObject;
  }

  public void setDisplayObject(MarketingBannerDisplayPojo displayObject) {
    this.displayObject = displayObject;
  }

  @Override
  public String toString() {
    return "ESpotViewPortPojo [rowNo=" + rowNo + ", columnNo=" + columnNo + ", spanLength="
        + spanLength + ", displayObject=" + displayObject + "]";
  }

}