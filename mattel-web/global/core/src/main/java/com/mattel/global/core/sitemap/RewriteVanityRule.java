package com.mattel.global.core.sitemap;

import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RewriteVanityRule extends BaseSiteMapConfig {
  private String forPageHierarchy;
  private String forRegexPattern;
  private String forVanityUrl;
  private String newVanityUrl;
  private String newPattern;
  @JsonIgnore
  private Pattern pattern;
  
  public RewriteVanityRule() {
	  
  }
  public RewriteVanityRule(RewriteVanityRule copy) {
	  this.forPageHierarchy = copy.getForPageHierarchy();
	  this.forRegexPattern = copy.getForRegexPattern();
	  this.forVanityUrl = copy.getForVanityUrl();
	  this.newVanityUrl = copy.getNewVanityUrl();
	  this.newPattern= copy.getNewPattern();
	  this.pattern = copy.getPattern();
	  
	  this.setDefaultFrequency(copy.getDefaultFrequency());
	  this.setExtensionlessUrls(copy.isExtensionlessUrls());
	  this.setIncludeLastModified(copy.isIncludeLastModified());
	  this.setDefaultPriority(copy.getDefaultPriority());
	  this.setStartsWith(copy.getStartsWith());
	  this.setEndsWith(copy.getEndsWith());
	  this.setRemoveTrailingSlash(copy.isRemoveTrailingSlash());
	  this.setIncludeHtmlPrefix(copy.isIncludeHtmlPrefix());
  }

  public String getForPageHierarchy() {
    return forPageHierarchy;
  }

  public void setForPageHierarchy(String forPageHierarchy) {
    this.forPageHierarchy = forPageHierarchy;
  }

  public String getForRegexPattern() {
    return forRegexPattern;
  }

  public void setForRegexPattern(String forRegexPattern) {
    this.forRegexPattern = forRegexPattern;
  }

  public String getForVanityUrl() {
    return forVanityUrl;
  }

  public void setForVanityUrl(String forVanityUrl) {
    this.forVanityUrl = forVanityUrl;
  }

  public String getNewVanityUrl() {
    return newVanityUrl;
  }

  public void setNewVanityUrl(String newVanityUrl) {
    this.newVanityUrl = newVanityUrl;
  }

  public String getNewPattern() {
    return newPattern;
  }

  public void setNewPattern(String newPattern) {
    this.newPattern = newPattern;
  }

  @JsonIgnore
  public Pattern getPattern() {
    return pattern;
  }

  public void setPattern(Pattern pattern) {
    this.pattern = pattern;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("RewriteVanityRule [endsWith=");
    builder.append(getEndsWith());
    builder.append(", forPageHierarchy=");
    builder.append(forPageHierarchy);
    builder.append(", forRegexPattern=");
    builder.append(forRegexPattern);
    builder.append(", forVanityUrl=");
    builder.append(forVanityUrl);
    builder.append(", newVanityUrl=");
    builder.append(newVanityUrl);
    builder.append(", newPattern=");
    builder.append(newPattern);
    builder.append(", startsWith=");
    builder.append(getStartsWith());
    builder.append("]");
    return builder.toString();
  }
}
