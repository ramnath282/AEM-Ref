package com.mattel.ecomm.coreservices.core.utilities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class PropertyReaderServiceTest {
  private PropertyReaderService impl;

  @Before
  public void setUp() throws Exception {
    final PropertyReaderService.Config config = Mockito.mock(PropertyReaderService.Config.class);
    impl = new PropertyReaderService();

    Mockito.when(config.storeId()).thenReturn("ag_en:10651,fp_en:10651".split(","));
    Mockito.when(config.cookieDomians()).thenReturn("ag_en:,fp_en:".split(","));
    Mockito.when(config.productpagePath()).thenReturn(
        "ag_en:/var/commerce/products/ag/en,fp_en-us:/var/commerce/products/fisher-price/en-us,fp_en-gb:/var/commerce/products/fisher-price/en-gb,fp_de-de:/var/commerce/products/fisher-price/de-de,fp_es-es:/var/commerce/products/fisher-price/es-es,fp_fr-fr:/var/commerce/products/fisher-price/fr-fr,fp_it-it:/var/commerce/products/fisher-price/it-it"
            .split(","));
    Mockito.when(config.productCategoryPaths()).thenReturn("ag_en:/shop/c".split(","));
    Mockito.when(config.adminPagePathConfiguration()).thenReturn(
        "ag_en:/content/ag/en/shop/admin-page,fp_en:/content/ag/en/shop/admin-page".split(","));
    Mockito.when(config.bvPassKeys()).thenReturn(
        "ag_en:dfdsfsdfeq43434324fadfsdf,fp_en:caSUGpboNfdfsdf3z6BiMaSKqeFds".split(","));
    Mockito.when(config.experienceFragmentRootPath()).thenReturn(
        "ag_en:/content/experience-fragments/ag/shop/marketing-contents,fp_en:/content/experience-fragments/ag/shop/marketing-contents"
            .split(","));
    Mockito.when(config.tagrootPath())
        .thenReturn("ag_en:/content/cq:tags/ag/Shop,fp_en:/content/cq:tags/ag/Shop".split(","));
    Mockito.when(config.siteErrorPages()).thenReturn(
        "ag_en:/content/ag/en/error/404.html,fp_en-us:/content/fisher-price/us/en-us/error/404.html,fp_en-gb:/content/fisher-price/gb/en-gb/error/404.html,fp_de-de:/content/fisher-price/de/de-de/error/404.html,fp_es-es:/content/fisher-price/es/es-es/error/404.html,fp_fr-fr:/content/fisher-price/fr/fr-fr/error/404.html,fp_it-it:/content/fisher-price/it/it-it/error/404.html"
            .split(","));
    Mockito.when(config.snpAccountURLs()).thenReturn(
        "ag_en://stage-sp1004f984.guided.ss-omtrdc.net/?index=qa,fp_en-us://stage-sp1004f9de.guided.ss-omtrdc.net/,fp_de-de://stage-sp1004fa1d.guided.ss-omtrdc.net/,fp_es-es://stage-sp10056c6b.guided.ss-omtrdc.net/,fp_en-gb://stage-sp10056c6c.guided.ss-omtrdc.net/,fp_fr-fr://stage-sp1004fa1c.guided.ss-omtrdc.net/,fp_it-it://stage-sp1004fa1e.guided.ss-omtrdc.net/"
            .split(","));
    Mockito.when(config.snpArticleAccountURLs())
        .thenReturn("ag_en://stage-sp1004f984.guided.ss-omtrdc.net/".split(","));
    Mockito.when(config.httpServiceDefConnectTimeouts()).thenReturn("ag_en:15000".split(","));
    Mockito.when(config.sessionTimeout()).thenReturn("ag_en:5400000".split(","));
    Mockito.when(config.productDetailPaths()).thenReturn("ag_en:/shop/p".split(","));
    Mockito.when(config.scene7Url()).thenReturn("http://mattel.scene7url.com");
    impl.activate(config);
  }

  @Test
  public void testGetSnpArticleAccountURLs() throws Exception {
    Assert.assertEquals("//stage-sp1004f984.guided.ss-omtrdc.net/",
        impl.getSnpArticleAccountURLs("ag_en"));
  }

  @Test
  public void testGetStoreId() throws Exception {
    Assert.assertEquals("10651", impl.getStoreId("ag_en"));
  }

  @Test
  public void testGetCookieDomain() throws Exception {
    Assert.assertEquals("", impl.getCookieDomain("ag_en"));
  }

  @Test
  public void testGetProductPath() throws Exception {
    Assert.assertEquals("/var/commerce/products/ag/en", impl.getProductPath("ag_en"));
  }

  @Test
  public void testGetadminPagePath() throws Exception {
    Assert.assertEquals("/content/ag/en/shop/admin-page", impl.getadminPagePath("ag_en"));
  }

  @Test
  public void testGetSessionTimeout() throws Exception {
    Assert.assertEquals("5400000", impl.getSessionTimeout("ag_en"));
  }

  @Test
  public void testGetBvPassKey() throws Exception {
    Assert.assertEquals("dfdsfsdfeq43434324fadfsdf", impl.getBvPassKey("ag_en"));
  }

  @Test
  public void testGetExperienceFragmentRootPath() throws Exception {
    Assert.assertEquals("/content/experience-fragments/ag/shop/marketing-contents",
        impl.getExperienceFragmentRootPath("fp_en"));
  }

  @Test
  public void testGetTagRootPaths() throws Exception {
    Assert.assertEquals("/content/cq:tags/ag/Shop", impl.getTagRootPaths("ag_en"));
  }

  @Test
  public void testGetProductCategoryPath() throws Exception {
    Assert.assertEquals("/shop/c", impl.getProductCategoryPath("ag_en"));
  }

  @Test
  public void testGetProductDetailPath() throws Exception {
    Assert.assertEquals("/shop/p", impl.getProductDetailPath("ag_en"));
  }

  @Test
  public void testGetSiteErrorPage() throws Exception {
    Assert.assertEquals("/content/ag/en/error/404.html", impl.getSiteErrorPage("ag_en"));
  }

  @Test
  public void testGetSnpAccountURL() throws Exception {
    Assert.assertEquals("//stage-sp1004f984.guided.ss-omtrdc.net/?index=qa",
        impl.getSnpAccountURL("ag_en"));
  }

  @Test
  public void testGetHttpServiceDefConnectTimeout() throws Exception {
    Assert.assertEquals(15000, (int) impl.getHttpServiceDefConnectTimeout("ag_en"));
  }
  
  @Test
  public void getScene7Url() {
	  Assert.assertEquals("http://mattel.scene7url.com", impl.getScene7Url());
	}

}
