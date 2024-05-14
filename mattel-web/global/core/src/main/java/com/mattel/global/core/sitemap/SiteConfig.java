package com.mattel.global.core.sitemap;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class SiteConfig extends BaseSiteMapConfig  {
  private String rootPath;
  private boolean enableHideInSiteMap;
  private boolean useTreeAlgorithm;
  private List<String> excludePagesOfTemplateType;
  private List<DynamicSiteMapPageConfig> dynamicPageConfigs;
  private List<RewriteVanityRule> rewriteVanityRules;
  private String externalizerDomain;
  private String pageExcludeProperty;
  private String pageSkipProperty;
  private String pageStopProperty;
  private List<String> priorityProperties;
  private List<String> changeFreqProperties;
  private boolean includeInheritance;
  @JsonIgnore
  private List<BasePredicate> predicates;
  private String damAssetProperty;
  private List<String> damAssetTypes;
  private String siteName;
  private String locale;
  private String siteKey;
  private String additionalSkipProperty;
  
  public SiteConfig() {
	  
  }
  
  public SiteConfig(SiteConfig copy) {
	  this.rootPath = copy.getRootPath();
	  this.enableHideInSiteMap = copy.isEnableHideInSiteMap();
	  this.useTreeAlgorithm = copy.isUseTreeAlgorithm();
	  this.excludePagesOfTemplateType = copy.getExcludePagesOfTemplateType();
	  this.dynamicPageConfigs = copy.getDynamicPageConfigs();
	  this.rewriteVanityRules = copy.getRewriteVanityRules();
	  this.externalizerDomain = copy.getExternalizerDomain();
	  this.pageExcludeProperty = copy.getPageExcludeProperty();
	  this.pageSkipProperty = copy.getPageSkipProperty();
	  this.pageStopProperty = copy.getPageSkipProperty();
	  this.priorityProperties = copy.getPriorityProperties();
	  this.changeFreqProperties = copy.getChangeFreqProperties();
	  this.includeInheritance = copy.isIncludeInheritance();
	  this.predicates = copy.getPredicates();
	  this.damAssetProperty = copy.getDamAssetProperty();
	  this.damAssetTypes = copy.getDamAssetTypes();
	  this.siteName = copy.getSiteName();
	  this.locale = copy.getLocale();
	  this.siteKey = copy.getSiteKey();
	  this.additionalSkipProperty = copy.getAdditionalSkipProperty();
	  

	  this.setDefaultFrequency(copy.getDefaultFrequency());
	  this.setExtensionlessUrls(copy.isExtensionlessUrls());
	  this.setIncludeLastModified(copy.isIncludeLastModified());
	  this.setDefaultPriority(copy.getDefaultPriority());
	  this.setStartsWith(copy.getStartsWith());
	  this.setEndsWith(copy.getEndsWith());
	  this.setRemoveTrailingSlash(copy.isRemoveTrailingSlash());
	  this.setIncludeHtmlPrefix(copy.isIncludeHtmlPrefix());
	  this.setOtherLocales(copy.getOtherLocales());
  }

  public String getRootPath() {
    return rootPath;
  }

  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  public boolean isEnableHideInSiteMap() {
    return enableHideInSiteMap;
  }

  public void setEnableHideInSiteMap(boolean enableHideInSiteMap) {
    this.enableHideInSiteMap = enableHideInSiteMap;
  }
  
  public boolean isUseTreeAlgorithm() {
	return useTreeAlgorithm;
  }

  public void setUseTreeAlgorithm(boolean useTreeAlgorithm) {
	this.useTreeAlgorithm = useTreeAlgorithm;
  }

  public List<String> getExcludePagesOfTemplateType() {
    return excludePagesOfTemplateType;
  }

  public void setExcludePagesOfTemplateType(List<String> excludePagesOfTemplateType) {
    this.excludePagesOfTemplateType = excludePagesOfTemplateType;
  }

  public List<DynamicSiteMapPageConfig> getDynamicPageConfigs() {
    return dynamicPageConfigs;
  }

  public void setDynamicPageConfigs(List<DynamicSiteMapPageConfig> dynamicPageConfigs) {
    this.dynamicPageConfigs = dynamicPageConfigs;
  }

  public List<RewriteVanityRule> getRewriteVanityRules() {
    return rewriteVanityRules;
  }

  public void setRewriteVanityRules(List<RewriteVanityRule> rewriteVanityRules) {
    this.rewriteVanityRules = rewriteVanityRules;
  }

  public String getExternalizerDomain() {
    return externalizerDomain;
  }

  public void setExternalizerDomain(String externalizerDomain) {
    this.externalizerDomain = externalizerDomain;
  }

  public String getPageExcludeProperty() {
    return pageExcludeProperty;
  }

  public void setPageExcludeProperty(String pageExcludeProperty) {
    this.pageExcludeProperty = pageExcludeProperty;
  }
  
  public String getPageSkipProperty() {
	return pageSkipProperty;
  }

  public void setPageSkipProperty(String pageSkipProperty) {
	this.pageSkipProperty = pageSkipProperty;
  }
  
  public String getPageStopProperty() {
	return pageStopProperty;
  }

  public void setPageStopProperty(String pageStopProperty) {
	this.pageStopProperty = pageStopProperty;
  }

  public List<String> getPriorityProperties() {
    return priorityProperties;
  }

  public void setPriorityProperties(List<String> priorityProperties) {
    this.priorityProperties = priorityProperties;
  }

  public List<String> getChangeFreqProperties() {
    return changeFreqProperties;
  }

  public void setChangeFreqProperties(List<String> changeFreqProperties) {
    this.changeFreqProperties = changeFreqProperties;
  }

  public boolean isIncludeInheritance() {
    return includeInheritance;
  }

  public void setIncludeInheritance(boolean includeInheritance) {
    this.includeInheritance = includeInheritance;
  }

  @JsonIgnore
  public List<BasePredicate> getPredicates() {
    return predicates;
  }

  public void setPredicates(List<BasePredicate> predicates) {
    this.predicates = predicates;
  }

  public String getDamAssetProperty() {
    return damAssetProperty;
  }

  public void setDamAssetProperty(String damAssetProperty) {
    this.damAssetProperty = damAssetProperty;
  }

  public List<String> getDamAssetTypes() {
    return damAssetTypes;
  }

  public void setDamAssetTypes(List<String> damAssetTypes) {
    this.damAssetTypes = damAssetTypes;
  }

  public String getSiteName() {
    return siteName;
  }

  public void setSiteName(String siteName) {
    this.siteName = siteName;
  }

  public String getLocale() {
    return locale;
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }

  public String getSiteKey() {
    return siteKey;
  }

  public void setSiteKey(String siteKey) {
    this.siteKey = siteKey;
  }
  
  public String getAdditionalSkipProperty() {
	return additionalSkipProperty;
  }

  public void setAdditionalSkipProperty(String additionalSkipProperty) {
	this.additionalSkipProperty = additionalSkipProperty;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("SiteConfig [rootPath=");
    builder.append(rootPath);
    builder.append(", enableHideInSiteMap=");
    builder.append(enableHideInSiteMap);
    builder.append(", useTreeAlgorithm=");
    builder.append(useTreeAlgorithm);
    builder.append(", excludePagesOfTemplateType=");
    builder.append(excludePagesOfTemplateType);
    builder.append(", externalizerDomain=");
    builder.append(externalizerDomain);
    builder.append(", dynamicPageConfigs=");
    builder.append(dynamicPageConfigs);
    builder.append(", rewriteVanityRules=");
    builder.append(dynamicPageConfigs);
    builder.append(", pageExcludeProperty=");
    builder.append(pageExcludeProperty);
    builder.append(", pageSkipProperty=");
    builder.append(pageSkipProperty);
    builder.append(", pageStopProperty=");
    builder.append(pageStopProperty);
    builder.append(", rewriteVanityRules=");
    builder.append(rewriteVanityRules);
    builder.append(", extensionlessUrls()=");
    builder.append(isExtensionlessUrls());
    builder.append(", isIncludeLastModified()=");
    builder.append(isIncludeLastModified());
    builder.append(", getDefaultPriority()=");
    builder.append(getDefaultPriority());
    builder.append(", getStartsWith()=");
    builder.append(getStartsWith());
    builder.append(", getEndsWith()=");
    builder.append(getEndsWith());
    builder.append(", predicates=");
    builder.append(predicates);
    builder.append(", additionalSkipProperty=");
    builder.append(additionalSkipProperty);
    builder.append("]");
    return builder.toString();
  }
}