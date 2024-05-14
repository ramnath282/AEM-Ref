package com.mattel.global.core.sitemap;

import com.day.cq.wcm.api.Page;

import java.util.Optional;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;

public abstract class BasePredicate implements Predicate<Page> {
  private final SiteConfig siteConfig;
  private final RewriteVanityRule rewriteVanityRule;

  public BasePredicate(SiteConfig siteConfig, RewriteVanityRule rewriteVanityRule) {
    super();
    this.siteConfig = siteConfig;
    this.rewriteVanityRule = rewriteVanityRule;
  }

  public SiteConfig getSiteConfig() {
    return siteConfig;
  }

  public RewriteVanityRule getRewriteVanityRule() {
    return rewriteVanityRule;
  }

  abstract String transformUrl(Page page);

  public String buildFinalUrl(String vanityUrl) {
    final StringBuilder sb = new StringBuilder();
    final RewriteVanityRule rule = getRewriteVanityRule();

    Optional.ofNullable(rule.getStartsWith()).filter(StringUtils::isNotBlank).ifPresent(sb::append);
    Optional.ofNullable(vanityUrl).filter(StringUtils::isNotBlank)
        .map(v -> v.startsWith("/") ? v : "/" + v).ifPresent(sb::append);
    Optional.ofNullable(rule.getEndsWith()).filter(StringUtils::isNotBlank)
        .map(v -> v.startsWith("/") ? v : "/" + v).ifPresent(sb::append);

    if (rule.isRemoveTrailingSlash()) {
      sb.replace(sb.length() - 1, sb.length(), StringUtils.EMPTY);
    }

    if (rule.isIncludeHtmlPrefix()) {
      sb.append(".html");
    }

    return sb.toString().replaceAll("//", "/");
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("BasePredicate [");
    builder.append("rewriteVanityRule=");
    builder.append(rewriteVanityRule);
    builder.append("]");
    return builder.toString();
  }

  public String getOverridePriority() {
    return getRewriteVanityRule().getDefaultPriority();
  }

  public String getOverrideFrequency() {
    return getRewriteVanityRule().getDefaultFrequency();
  }

  public boolean getOverrideIsLastModified() {
    return getRewriteVanityRule().isIncludeLastModified();
  }
}
