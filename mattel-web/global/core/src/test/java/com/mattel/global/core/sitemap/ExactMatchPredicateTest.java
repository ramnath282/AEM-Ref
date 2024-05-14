package com.mattel.global.core.sitemap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.wcm.api.Page;

@RunWith(PowerMockRunner.class)
public class ExactMatchPredicateTest {

  @InjectMocks
  ExactMatchPredicate exactMatchPredicate;
  @Mock
  SiteConfig siteConfig;
  @Mock
  RewriteVanityRule rewriteVanityRule;

  @Before
  public void setup() {
    exactMatchPredicate = new ExactMatchPredicate(siteConfig, rewriteVanityRule);
    Mockito.when(rewriteVanityRule.getForVanityUrl()).thenReturn("vanity url");
  }

  @Test
  public void testTest() {
    Page page = Mockito.mock(Page.class);
    Mockito.when(page.getVanityUrl()).thenReturn("vanity url");
    exactMatchPredicate.test(page);
  }

  @Test
  public void testTransformUrl() {
    Page page = Mockito.mock(Page.class);
    exactMatchPredicate.transformUrl(page);
  }

  @Test
  public void getSiteConfig() {
    exactMatchPredicate.getSiteConfig();
  }

  @Test
  public void testGetOverridePriority() {
    exactMatchPredicate.getOverrideFrequency();
    exactMatchPredicate.getOverrideIsLastModified();
    exactMatchPredicate.getOverridePriority();
    exactMatchPredicate.toString();
  }
}
