package com.mattel.global.core.sitemap;

import com.day.cq.wcm.api.Page;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class PatternPredicateTest {
  @Test
  public void testTest() throws Exception {
    SiteConfig siteConfig = new SiteConfig();
    RewriteVanityRule vanityRule = new RewriteVanityRule();
    vanityRule.setForRegexPattern("/discover");
    vanityRule.setNewPattern("/new-discover");
    PatternPredicate patternPredicate = new PatternPredicate(siteConfig, vanityRule);
    Page page = Mockito.mock(Page.class);
    Mockito.when(page.getVanityUrl()).thenReturn("/xyz/discover");
    Assert.assertTrue(patternPredicate.test(page));
    Assert.assertNotNull(patternPredicate.transformUrl(page));
    vanityRule.toString();
  }
}