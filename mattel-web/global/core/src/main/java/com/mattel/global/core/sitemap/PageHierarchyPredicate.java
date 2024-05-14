package com.mattel.global.core.sitemap;

import com.day.cq.wcm.api.Page;

import org.apache.commons.lang3.StringUtils;

public class PageHierarchyPredicate extends BasePredicate {
  public PageHierarchyPredicate(SiteConfig siteConfig, RewriteVanityRule rewriteVanityRule) {
    super(siteConfig, rewriteVanityRule);
  }

  @Override
  public boolean test(Page page) {
    final String pagePath = page.getPath();
    final RewriteVanityRule rewriteVanityRule = getRewriteVanityRule();
    final String pageHierarchy = rewriteVanityRule.getForPageHierarchy();
    final SiteConfig siteConfig = getSiteConfig();

    return pagePath.contains(pageHierarchy)
        || pagePath.contains(siteConfig.getRootPath() + pageHierarchy);
  }

  @Override
  String transformUrl(Page page) {
    final RewriteVanityRule rewriteVanityRule = getRewriteVanityRule();

    if (StringUtils.isNotBlank(rewriteVanityRule.getNewVanityUrl())) {
      return buildFinalUrl(rewriteVanityRule.getNewVanityUrl());
    }

    return buildFinalUrl(page.getVanityUrl());
  }
}
