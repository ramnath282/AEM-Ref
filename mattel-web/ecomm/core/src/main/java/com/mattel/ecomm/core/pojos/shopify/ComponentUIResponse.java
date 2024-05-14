package com.mattel.ecomm.core.pojos.shopify;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Association;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Option;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Variant;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ComponentUIResponse {
  private long id;
  private long product_id;
  private String title;
  private String product_type;
  private String handle;
  private String product_partnumber;
  private String product_parentPartNumber;
  private String product_auxdescription;
  private String product_imagelink;
  private String product_fullimage;
  private String product_thumnail;
  private String product_buyable = "1";
  private String product_productcallout;
  private String product_newoverrideflag;
  private String product_custsegexcl;
  private String product_isretailinventorycheckenabled;
  private String product_primarybundlesku;
  private String product_hasAddOns;
  private String product_hasQuickSell;
  private String product_isDynamicKit;
  private String product_sizeChartLink;
  private String product_affirmInEligibleFlag;
  private String product_hasSwatches;
  private String product_inventorystatus;
  private double association_sequence;
  private String price;
  private List<Association> associations;
  private List<Variant> variants;
  private Map<String, Object> attributes;
  private List<Option> options;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getProduct_type() {
    return product_type;
  }

  public void setProduct_type(String product_type) {
    this.product_type = product_type;
  }

  public String getHandle() {
    return handle;
  }

  public void setHandle(String handle) {
    this.handle = handle;
  }

  public String getProduct_partnumber() {
    return product_partnumber;
  }

  public void setProduct_partnumber(String product_partnumber) {
    this.product_partnumber = product_partnumber;
  }

  public String getProduct_auxdescription() {
    return product_auxdescription;
  }

  public void setProduct_auxdescription(String product_auxdescription) {
    this.product_auxdescription = product_auxdescription;
  }

  public String getProduct_imagelink() {
    return product_imagelink;
  }

  public void setProduct_imagelink(String product_imagelink) {
    this.product_imagelink = product_imagelink;
  }

  public String getProduct_fullimage() {
    return product_fullimage;
  }

  public void setProduct_fullimage(String product_fullimage) {
    this.product_fullimage = product_fullimage;
  }

  public String getProduct_thumnail() {
    return product_thumnail;
  }

  public void setProduct_thumnail(String product_thumnail) {
    this.product_thumnail = product_thumnail;
  }

  public String getProduct_buyable() {
    return product_buyable;
  }

  public void setProduct_buyable(String product_buyable) {
    this.product_buyable = product_buyable;
  }

  public String getProduct_productcallout() {
    return product_productcallout;
  }

  public void setProduct_productcallout(String product_productcallout) {
    this.product_productcallout = product_productcallout;
  }

  public String getProduct_newoverrideflag() {
    return product_newoverrideflag;
  }

  public void setProduct_newoverrideflag(String product_newoverrideflag) {
    this.product_newoverrideflag = product_newoverrideflag;
  }

  public String getProduct_custsegexcl() {
    return product_custsegexcl;
  }

  public void setProduct_custsegexcl(String product_custsegexcl) {
    this.product_custsegexcl = product_custsegexcl;
  }

  public String getProduct_isretailinventorycheckenabled() {
    return product_isretailinventorycheckenabled;
  }

  public void setProduct_isretailinventorycheckenabled(String product_isretailinventorycheckenabled) {
    this.product_isretailinventorycheckenabled = product_isretailinventorycheckenabled;
  }

  public String getProduct_primarybundlesku() {
    return product_primarybundlesku;
  }

  public void setProduct_primarybundlesku(String product_primarybundlesku) {
    this.product_primarybundlesku = product_primarybundlesku;
  }

  public String getProduct_hasAddOns() {
    return product_hasAddOns;
  }

  public void setProduct_hasAddOns(String product_hasAddOns) {
    this.product_hasAddOns = product_hasAddOns;
  }

  public String getProduct_hasQuickSell() {
    return product_hasQuickSell;
  }

  public void setProduct_hasQuickSell(String product_hasQuickSell) {
    this.product_hasQuickSell = product_hasQuickSell;
  }

  public String getProduct_isDynamicKit() {
    return product_isDynamicKit;
  }

  public void setProduct_isDynamicKit(String product_isDynamicKit) {
    this.product_isDynamicKit = product_isDynamicKit;
  }

  public String getProduct_sizeChartLink() {
    return product_sizeChartLink;
  }

  public void setProduct_sizeChartLink(String product_sizeChartLink) {
    this.product_sizeChartLink = product_sizeChartLink;
  }

  public List<Association> getAssociations() {
    return associations;
  }

  public void setAssociations(List<Association> associations) {
    this.associations = associations;
  }

  public List<Variant> getVariants() {
    return variants;
  }

  public void setVariants(List<Variant> variants) {
    this.variants = variants;
  }

  public Map<String, Object> getAttributes() {
    return attributes;
  }

  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getProduct_id() {
    return product_id;
  }

  public void setProduct_id(long product_id) {
    this.product_id = product_id;
  }

  public String getProduct_parentPartNumber() {
    return product_parentPartNumber;
  }

  public void setProduct_parentPartNumber(String product_parentPartNumber) {
    this.product_parentPartNumber = product_parentPartNumber;
  }

  public String getProduct_affirmInEligibleFlag() {
    return product_affirmInEligibleFlag;
  }

  public void setProduct_affirmInEligibleFlag(String product_affirmInEligibleFlag) {
    this.product_affirmInEligibleFlag = product_affirmInEligibleFlag;
  }

  public String getProduct_hasSwatches() {
    return product_hasSwatches;
  }

  public void setProduct_hasSwatches(String product_hasSwatches) {
    this.product_hasSwatches = product_hasSwatches;
  }

  public double getAssociation_sequence() {
    return association_sequence;
  }

  public void setAssociation_sequence(double association_sequence) {
    this.association_sequence = association_sequence;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public List<Option> getOptions() {
    return options;
  }

  public void setOptions(List<Option> options) {
    this.options = options;
  }

  public String getProduct_inventorystatus() {
    return product_inventorystatus;
  }

  public void setProduct_inventorystatus(String product_inventorystatus) {
    this.product_inventorystatus = product_inventorystatus;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("ComponentUIResponse [title=");
    builder.append(title);
    builder.append(", product_type=");
    builder.append(product_type);
    builder.append(", handle=");
    builder.append(handle);
    builder.append(", product_partnumber=");
    builder.append(product_partnumber);
    builder.append(", product_auxdescription=");
    builder.append(product_auxdescription);
    builder.append(", product_imagelink=");
    builder.append(product_imagelink);
    builder.append(", product_fullimage=");
    builder.append(product_fullimage);
    builder.append(", product_thumnail=");
    builder.append(product_thumnail);
    builder.append(", product_buyable=");
    builder.append(product_buyable);
    builder.append(", product_productcallout=");
    builder.append(product_productcallout);
    builder.append(", product_newoverrideflag=");
    builder.append(product_newoverrideflag);
    builder.append(", product_custsegexcl=");
    builder.append(product_custsegexcl);
    builder.append(", product_isretailinventorycheckenabled=");
    builder.append(product_isretailinventorycheckenabled);
    builder.append(", product_primarybundlesku=");
    builder.append(product_primarybundlesku);
    builder.append(", product_hasAddOns=");
    builder.append(product_hasAddOns);
    builder.append(", product_isDynamicKit=");
    builder.append(product_isDynamicKit);
    builder.append(", product_sizeChartLink=");
    builder.append(product_sizeChartLink);
    builder.append(", associations=");
    builder.append(associations);
    builder.append(", variants=");
    builder.append(variants);
    builder.append("]");
    return builder.toString();
  }
}
