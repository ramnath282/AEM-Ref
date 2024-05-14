package com.mattel.global.core.sitemap;

import com.day.cq.wcm.api.Page;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternPredicate extends BasePredicate {
  private final Pattern pattern;

  public PatternPredicate(SiteConfig siteConfig, RewriteVanityRule rewriteVanityRule) {
    super(siteConfig, rewriteVanityRule);
    pattern = Pattern.compile(rewriteVanityRule.getForRegexPattern());
  }

  @Override
  public boolean test(Page page) {
    final String vanityUrl = page.getVanityUrl();
    return pattern.matcher(vanityUrl).find();
  }

  public static void main(String[] args) {

  }

  @Override
  String transformUrl(Page page) {
    final String vanityUrl = page.getVanityUrl();
    final StringBuffer sb = new StringBuffer();
    final Matcher m = pattern.matcher(vanityUrl);
    if (m.find()) {
      m.appendReplacement(sb, getRewriteVanityRule().getNewPattern());
    }
    m.appendTail(sb);
    return buildFinalUrl(sb.toString());
  }
}
