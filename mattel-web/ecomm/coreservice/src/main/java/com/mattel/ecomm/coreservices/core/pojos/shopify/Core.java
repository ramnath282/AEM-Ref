package com.mattel.ecomm.coreservices.core.pojos.shopify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
/**
 * Core object containing properties of product.
 */
public class Core {
  private String title;
  private String body_html;
  private String product_type;
  private String created_at;
  private String handle;
  private String updated_at;
  private String published_at;

  private String global_title_tag;
  private String global_description_tag;

  private String product_partnumber;
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
  private String product_affirmIneligible;
  private String product_relatedSizingChart;

  private String product_marketingAge;
  private String product_reviewCount;
  private String product_reviewRating;
  private String product_reviewRatingOriginal;
  private String product_reviewEnabled;
  private String product_description;
  private String product_backordarableText;
  private String product_safetyMessage;
  private String product_productStatus;
  private String product_sizeChartLink;
  private String product_hasAddOns;
  private String product_hasQuickSell;
  private String product_hasSwatches;
  private String product_excludeShippingCountriesFlag;
  private String product_disclaimer;
  private String product_isAltCanadaSKU;
  private String product_availabilityStatus;
  private String product_isDynamicKit;
  private String product_releaseDateWeb;
  private String product_productGTIN;
  private String product_productCategory;
  private String product_viewerVideo;
  private String product_character;
  private String product_subCategory;
  private String product_subBrand;
  private String product_giftGuide;
  private String product_promoCategory;
  private String product_smallTrunkLowPrice;
  private String product_largeTrunkLowPrice;
  private String product_affirmInEligibleFlag;
  private String product_disableQuickView;
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Core [title=");
    builder.append(title);
    builder.append(", body_html=");
    builder.append(body_html);
    builder.append(", product_type=");
    builder.append(product_type);
    builder.append(", created_at=");
    builder.append(created_at);
    builder.append(", handle=");
    builder.append(handle);
    builder.append(", updated_at=");
    builder.append(updated_at);
    builder.append(", published_at=");
    builder.append(published_at);
    builder.append(", global_title_tag=");
    builder.append(global_title_tag);
    builder.append(", global_description_tag=");
    builder.append(global_description_tag);
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
    builder.append(", product_affirmIneligible=");
    builder.append(product_affirmIneligible);
    builder.append("]");
    return builder.toString();
  }
}
