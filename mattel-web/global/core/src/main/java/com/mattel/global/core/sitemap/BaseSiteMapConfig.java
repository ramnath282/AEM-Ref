package com.mattel.global.core.sitemap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseSiteMapConfig {
  private boolean extensionlessUrls;
  private boolean includeLastModified;
  @JsonProperty("defaultpriority")
  private String defaultPriority;
  private String defaultFrequency;
  private String startsWith;
  private String endsWith;
  private boolean removeTrailingSlash;
  private boolean includeHtmlPrefix;
  private List<Map<String, String>> otherLocales;

  public String getDefaultFrequency() {
    return defaultFrequency;
  }

  public void setDefaultFrequency(String defaultFrequency) {
    this.defaultFrequency = defaultFrequency;
  }

  public boolean isExtensionlessUrls() {
    return extensionlessUrls;
  }

  public void setExtensionlessUrls(boolean extensionlessUrls) {
    this.extensionlessUrls = extensionlessUrls;
  }

  public boolean isIncludeLastModified() {
    return includeLastModified;
  }

  public void setIncludeLastModified(boolean includeLastModified) {
    this.includeLastModified = includeLastModified;
  }

  public String getDefaultPriority() {
    return defaultPriority;
  }

  public void setDefaultPriority(String defaultPriority) {
    this.defaultPriority = defaultPriority;
  }

  public String getStartsWith() {
    return startsWith;
  }

  public void setStartsWith(String startsWith) {
    this.startsWith = startsWith;
  }

  public String getEndsWith() {
    return endsWith;
  }

  public void setEndsWith(String endsWith) {
    this.endsWith = endsWith;
  }

  public boolean isRemoveTrailingSlash() {
    return removeTrailingSlash;
  }

  public void setRemoveTrailingSlash(boolean removeTrailingSlash) {
    this.removeTrailingSlash = removeTrailingSlash;
  }

  public List<Map<String, String>> getOtherLocales() {
    return otherLocales;
  }

  public void setOtherLocales(List<Map<String, String>> otherLocales) {
    this.otherLocales = otherLocales;
  }

  public boolean isIncludeHtmlPrefix() {
    return includeHtmlPrefix;
  }

  public void setIncludeHtmlPrefix(boolean includeHtmlPrefix) {
    this.includeHtmlPrefix = includeHtmlPrefix;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("BaseSiteMapConfig [extensionlessUrls=");
    builder.append(extensionlessUrls);
    builder.append(", includeLastModified=");
    builder.append(includeLastModified);
    builder.append(", defaultPriority=");
    builder.append(defaultPriority);
    builder.append(", startsWith=");
    builder.append(startsWith);
    builder.append(", endsWith=");
    builder.append(endsWith);
    builder.append(", removeTrailingSlash=");
    builder.append(removeTrailingSlash);
    builder.append(", otherLocales=");
    builder.append(otherLocales);
    builder.append("]");
    return builder.toString();
  }
}
