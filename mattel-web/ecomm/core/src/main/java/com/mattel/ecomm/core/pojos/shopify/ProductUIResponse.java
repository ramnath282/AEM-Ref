package com.mattel.ecomm.core.pojos.shopify;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Association;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Core;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Option;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Variant;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductUIResponse {
  private long id;
  private long product_id;
  private String partnumber;
  private List<Map<String, Object>> images;
  private Map<String, Object> attributes;
  private Core core;
  private List<String> experienceFragmentPath;
  private List<Variant> variants;
  private List<Option> options;
  private List<Association> associations;
  private List<ComponentUIResponse> components;
  @JsonProperty
  private Map<String, String[]> storeMap;
  private String canonicalUrl;
  private String affirmInfoPagePath;
  private List<DollsPojo> kitComponentHiddenParts;
  private Double priceForRecommendations;
  private String availabilityStatus;
  @JsonProperty
  private KitPojo primaryKit;
  @JsonProperty
  private KitPojo secondaryKit;
  @JsonProperty
  private boolean hasBittyTwinAssociations;

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

  public String getPartnumber() {
    return partnumber;
  }

  public void setPartnumber(String partnumber) {
    this.partnumber = partnumber;
  }

  public List<Map<String, Object>> getImages() {
    return images;
  }

  public void setImages(List<Map<String, Object>> images) {
    this.images = images;
  }

  public Map<String, Object> getAttributes() {
    return attributes;
  }

  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  public Core getCore() {
    return core;
  }

  public void setCore(Core core) {
    this.core = core;
  }

  public List<String> getExperienceFragmentPath() {
    return experienceFragmentPath;
  }

  public void setExperienceFragmentPath(List<String> experienceFragmentPath) {
    this.experienceFragmentPath = experienceFragmentPath;
  }

  public List<Variant> getVariants() {
    return variants;
  }

  public void setVariants(List<Variant> variants) {
    this.variants = variants;
  }

  public List<Option> getOptions() {
    return options;
  }

  public void setOptions(List<Option> options) {
    this.options = options;
  }

  public List<Association> getAssociations() {
    return associations;
  }

  public void setAssociations(List<Association> associations) {
    this.associations = associations;
  }

  public List<ComponentUIResponse> getComponents() {
    return components;
  }

  public void setComponents(List<ComponentUIResponse> components) {
    this.components = components;
  }

  public Map<String, String[]> getStoreMap() {
    return storeMap;
  }

  public void setStoreMap(Map<String, String[]> storeMap) {
    this.storeMap = storeMap;
  }

  public String getCanonicalUrl() {
    return canonicalUrl;
  }

  public void setCanonicalUrl(String canonicalUrl) {
    this.canonicalUrl = canonicalUrl;
  }

  public String getAffirmInfoPagePath() { return affirmInfoPagePath; }

  public void setAffirmInfoPagePath(String affirmInfoPagePath) { this.affirmInfoPagePath = affirmInfoPagePath; }

  public List<DollsPojo> getKitComponentHiddenParts() {
    return kitComponentHiddenParts;
  }
  
  public void setKitComponentHiddenParts(List<DollsPojo> kitComponentHiddenParts) {
    this.kitComponentHiddenParts = kitComponentHiddenParts;
  }

  public KitPojo getPrimaryKit() {
    return primaryKit;
  }

  public void setPrimaryKit(KitPojo primaryKit) {
    this.primaryKit = primaryKit;
  }

  public KitPojo getSecondaryKit() {
    return secondaryKit;
  }

  public void setSecondaryKit(KitPojo secondaryKit) {
    this.secondaryKit = secondaryKit;
  }

  public boolean isHasBittyTwinAssociations() {
    return hasBittyTwinAssociations;
  }

  public void setHasBittyTwinAssociations(boolean hasBittyTwinAssociations) {
    this.hasBittyTwinAssociations = hasBittyTwinAssociations;
  }
  public Double getPriceForRecommendations() {
    return priceForRecommendations;
  }

  public void setPriceForRecommendations(Double priceForRecommendations) {
    this.priceForRecommendations = priceForRecommendations;
  }

  public String getAvailabilityStatus() {
    return availabilityStatus;
  }

  public void setAvailabilityStatus(String availabilityStatus) {
    this.availabilityStatus = availabilityStatus;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ShopifyProductUIResponse [id=");
    builder.append(id);
    builder.append(", product_id=");
    builder.append(product_id);
    builder.append(", images=");
    builder.append(images);
    builder.append(", attributes=");
    builder.append(attributes);
    builder.append(", core=");
    builder.append(core);
    builder.append(", experienceFragmentPath=");
    builder.append(experienceFragmentPath);
    builder.append(", variants=");
    builder.append(variants);
    builder.append(", options=");
    builder.append(options);
    builder.append(", associations=");
    builder.append(associations);
    builder.append(", components=");
    builder.append(components);
    builder.append(", partnumber=");
    builder.append(partnumber);
    builder.append(", storeMap=");
    builder.append(storeMap);
    builder.append(", canonicalUrl=");
    builder.append(canonicalUrl);
    builder.append(", affirmInfoPagePath=");
    builder.append(affirmInfoPagePath);
    builder.append(", KitComponentHiddenParts=");
    builder.append(kitComponentHiddenParts);
    builder.append(", priceForRecommendations=");
    builder.append(priceForRecommendations);
    builder.append(", primaryKit=");
    builder.append(primaryKit);
    builder.append(", secondaryKit=");
    builder.append(secondaryKit);
    builder.append(", hasBittyTwinAssociations=");
    builder.append(hasBittyTwinAssociations);
    builder.append(", components=");
    builder.append(components);
    builder.append("]");
    return builder.toString();
  }


}
