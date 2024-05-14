package com.mattel.global.core.sitemap;

import com.day.cq.wcm.api.Page;

import org.apache.commons.lang3.StringUtils;

public class ExactMatchPredicate extends BasePredicate {
  public ExactMatchPredicate(SiteConfig siteConfig, RewriteVanityRule rewriteVanityRule) {
    super(siteConfig, rewriteVanityRule);
  }

  @Override
  public boolean test(Page t) {
    final RewriteVanityRule rewriteVanityRule = getRewriteVanityRule();
    return rewriteVanityRule.getForVanityUrl().equals(t.getVanityUrl());
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
