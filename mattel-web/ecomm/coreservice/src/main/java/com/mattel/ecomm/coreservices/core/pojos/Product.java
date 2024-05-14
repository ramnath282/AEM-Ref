package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Product {
  @JsonProperty
  private String buyable;
  @JsonProperty("seo_imageAltText")
  private String seoImageAltText;
  @JsonProperty
  private String thumbnail;
  @JsonProperty
  private String code;
  @JsonProperty
  private String invStatus;
  @JsonProperty
  private String auxDescription1;
  @JsonProperty
  private String backorderDate;
  @JsonProperty("product_status")
  private String productStatus;
  @JsonProperty
  private String description;
  @JsonProperty
  private String language;
  @JsonProperty
  private String auxDescription2;
  @JsonProperty
  private String availability;
  @JsonProperty
  private String custSegExcl;
  @JsonProperty("seo_metaDescription")
  private String seoMetaDescription;
  @JsonProperty
  private String imageLink;
  @JsonProperty("product_type")
  private String productType;
  @JsonProperty
  private String fullimage;
  @JsonProperty
  private String partNumber;
  @JsonProperty("seo_PageTitle")
  private String seoPageTitle;
  @JsonProperty
  private String reasonCode;
  @JsonProperty
  private String region;
  @JsonProperty
  private String canonicalCat;
  @JsonProperty
  private String published;
  @JsonProperty("seo_urlKeyword")
  private String seoUrlKeyword;
  @JsonProperty
  private ProductAttributes attributes;
  @JsonProperty
  private List<ProductAssociation> associations;
  @JsonProperty
  private List<PDPProductComponent> components;
  @JsonProperty
  private List<ChildProduct> childProducts;
  @JsonProperty
  private List<Map<String, String>> giftCardMessages;
  @JsonProperty
  private Map<String, Price> price;
  @JsonProperty
  private String affirmIneligible;
  @JsonProperty
  private String newOverrideFlag;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Product [buyable=");
    builder.append(buyable);
    builder.append(", seoImageAltText=");
    builder.append(seoImageAltText);
    builder.append(", thumbnail=");
    builder.append(thumbnail);
    builder.append(", code=");
    builder.append(code);
    builder.append(", invStatus=");
    builder.append(invStatus);
    builder.append(", auxDescription1=");
    builder.append(auxDescription1);
    builder.append(", backorderDate=");
    builder.append(backorderDate);
    builder.append(", productStatus=");
    builder.append(productStatus);
    builder.append(", description=");
    builder.append(description);
    builder.append(", language=");
    builder.append(language);
    builder.append(", auxDescription2=");
    builder.append(auxDescription2);
    builder.append(", availability=");
    builder.append(availability);
    builder.append(", custSegExcl=");
    builder.append(custSegExcl);
    builder.append(", seoMetaDescription=");
    builder.append(seoMetaDescription);
    builder.append(", imageLink=");
    builder.append(imageLink);
    builder.append(", productType=");
    builder.append(productType);
    builder.append(", fullimage=");
    builder.append(fullimage);
    builder.append(", partNumber=");
    builder.append(partNumber);
    builder.append(", seoPageTitle=");
    builder.append(seoPageTitle);
    builder.append(", reasonCode=");
    builder.append(reasonCode);
    builder.append(", region=");
    builder.append(region);
    builder.append(", canonicalCat=");
    builder.append(canonicalCat);
    builder.append(", published=");
    builder.append(published);
    builder.append(", seoUrlKeyword=");
    builder.append(seoUrlKeyword);
    builder.append(", attributes=");
    builder.append(attributes);
    builder.append(", associations=");
    builder.append(associations);
    builder.append(", components=");
    builder.append(components);
    builder.append(", childProducts=");
    builder.append(childProducts);
    builder.append(", giftCardMessages=");
    builder.append(giftCardMessages);
    builder.append(", price=");
    builder.append(price);
    builder.append(", affirmIneligible=");
    builder.append(affirmIneligible);
    builder.append(", newOverrideFlag=");
    builder.append(newOverrideFlag);
    builder.append("]");
    return builder.toString();
  }
}
