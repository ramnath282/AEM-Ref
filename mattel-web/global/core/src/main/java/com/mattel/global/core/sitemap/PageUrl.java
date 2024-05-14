package com.mattel.global.core.sitemap;

public class PageUrl {
  private boolean urlOverriden;
  private String url;
  private String defaultPriority;
  private String defaultFrequency;
  private String defaultLastModified;
  private boolean includeLastModified;

  public boolean isIncludeLastModified() {
    return includeLastModified;
  }

  public void setIncludeLastModified(boolean includeLastModified) {
    this.includeLastModified = includeLastModified;
  }

  public String getDefaultLastModified() {
    return defaultLastModified;
  }

  public void setDefaultLastModified(String defaultLastModified) {
    this.defaultLastModified = defaultLastModified;
  }

  public PageUrl(boolean urlOverriden, String url) {
    super();
    this.urlOverriden = urlOverriden;
    this.url = url;
  }

  public boolean isUrlOverriden() {
    return urlOverriden;
  }

  public void setUrlOverriden(boolean urlOverriden) {
    this.urlOverriden = urlOverriden;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getDefaultPriority() {
    return defaultPriority;
  }

  public void setDefaultPriority(String defaultPriority) {
    this.defaultPriority = defaultPriority;
  }

  public String getDefaultFrequency() {
    return defaultFrequency;
  }

  public void setDefaultFrequency(String defaultFrequency) {
    this.defaultFrequency = defaultFrequency;
  }
}
