package com.mattel.ecomm.core.utils;

import com.adobe.xfa.ut.StringUtils;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ecomm.coreservices.core.constants.Constant;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;

public class EcomUtilTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testCheckLink0() throws Exception {
    final String url = "/var/tmp/ag/en";

    Assert.assertEquals(url, EcomUtil.checkLink(url, null));
  }

  @Test
  public void testCheckLink1() throws Exception {
    final String url = "/var/tmp/ag/en";
    final Resource resource = Mockito.mock(Resource.class);
    final ResourceResolver rr = Mockito.mock(ResourceResolver.class);

    Mockito.when(resource.getResourceResolver()).thenReturn(rr);
    Assert.assertEquals(url, EcomUtil.checkLink(url, resource));
  }

  @Test
  public void testCheckLink7() throws Exception {
    final String url = "/var/tmp/ag/en";
    final Resource resource = Mockito.mock(Resource.class);

    Mockito.when(resource.getResourceResolver()).thenReturn(null);
    Assert.assertEquals(url, EcomUtil.checkLink(url, resource));
  }

  @Test
  public void testCheckLink2() throws Exception {
    final String url = "/content/dam";
    final Resource resource = Mockito.mock(Resource.class);
    final ResourceResolver rr = Mockito.mock(ResourceResolver.class);

    Mockito.when(resource.getResourceResolver()).thenReturn(rr);
    Assert.assertEquals(url, EcomUtil.checkLink(url, resource));
  }

  @Test
  public void testCheckLink3() throws Exception {
    final String url = "/content/ag/en/shop.html";
    final Resource resource = Mockito.mock(Resource.class);
    final ResourceResolver rr = Mockito.mock(ResourceResolver.class);
    final PageManager pageManager = Mockito.mock(PageManager.class);
    final Page page = Mockito.mock(Page.class);

    Mockito.when(resource.getResourceResolver()).thenReturn(rr);
    Mockito.when(rr.map(url)).thenReturn(url);
    Mockito.when(rr.adaptTo(PageManager.class)).thenReturn(pageManager);
    Mockito.when(pageManager.getPage(url)).thenReturn(page);
    Mockito.when(page.getVanityUrl()).thenReturn("/landing");
    Assert.assertEquals("/landing", EcomUtil.checkLink(url, resource));
  }

  @Test
  public void testCheckLink4() throws Exception {
    final String url = "/content/ag/en/shop.html";
    final Resource resource = Mockito.mock(Resource.class);
    final ResourceResolver rr = Mockito.mock(ResourceResolver.class);
    final PageManager pageManager = Mockito.mock(PageManager.class);
    final Page page = Mockito.mock(Page.class);

    Mockito.when(resource.getResourceResolver()).thenReturn(rr);
    Mockito.when(rr.map(url)).thenReturn(url);
    Mockito.when(rr.adaptTo(PageManager.class)).thenReturn(pageManager);
    Mockito.when(pageManager.getPage(url)).thenReturn(page);
    Mockito.when(page.getVanityUrl()).thenReturn("/landing");
    Assert.assertEquals("/landing", EcomUtil.checkLink(url, resource));
  }

  @Test
  public void testCheckLink5() throws Exception {
    final String url = "/content/ag/en/shop.html";
    final Resource resource = Mockito.mock(Resource.class);
    final ResourceResolver rr = Mockito.mock(ResourceResolver.class);
    final PageManager pageManager = Mockito.mock(PageManager.class);
    final Page page = Mockito.mock(Page.class);
    final ValueMap properties = Mockito.mock(ValueMap.class);

    Mockito.when(resource.getResourceResolver()).thenReturn(rr);
    Mockito.when(rr.map(url)).thenReturn(url);
    Mockito.when(rr.adaptTo(PageManager.class)).thenReturn(pageManager);
    Mockito.when(pageManager.getPage(url)).thenReturn(page);
    Mockito.when(page.getVanityUrl()).thenReturn(null);

    Mockito.when(properties.containsKey("cq:redirectTarget")).thenReturn(true);
    Mockito.when(properties.get("cq:redirectTarget", String.class)).thenReturn("/error");
    Mockito.when(page.getProperties()).thenReturn(properties);
    Assert.assertEquals("/error", EcomUtil.checkLink(url, resource));
  }

  @Test
  public void testCheckLink6() throws Exception {
    final String url = "/content/ag/en/shop";
    final Resource resource = Mockito.mock(Resource.class);
    final ResourceResolver rr = Mockito.mock(ResourceResolver.class);
    final PageManager pageManager = Mockito.mock(PageManager.class);
    final Page page = Mockito.mock(Page.class);
    final ValueMap properties = Mockito.mock(ValueMap.class);

    Mockito.when(resource.getResourceResolver()).thenReturn(rr);
    Mockito.when(rr.map(url)).thenReturn(url);
    Mockito.when(rr.adaptTo(PageManager.class)).thenReturn(pageManager);
    Mockito.when(pageManager.getPage(url)).thenReturn(page);
    Mockito.when(page.getVanityUrl()).thenReturn(null);
    Mockito.when(properties.containsKey("cq:redirectTarget")).thenReturn(false);
    Assert.assertEquals(url + ".html", EcomUtil.checkLink(url, resource));
  }

  @Test
  public void testBuildCanonicalTag() throws Exception {
    final SlingHttpServletRequest httpServletRequest = Mockito.mock(SlingHttpServletRequest.class);
    final RequestPathInfo requestPathInfo = Mockito.mock(RequestPathInfo.class);

    Mockito.when(httpServletRequest.getRequestURL()).thenReturn(
        new StringBuffer("https://mdev.americangirl.com/shop/p/fgd39.ag_en.willa-doll-fgd39.html"));
    Mockito.when(httpServletRequest.getRequestPathInfo()).thenReturn(requestPathInfo);
    Mockito.when(requestPathInfo.getSelectors())
        .thenReturn(new String[] { "fgd39", "ag_en", "willa-doll-fgd39" });
    Assert.assertEquals("https://mdev.americangirl.com/shop/p/willa-doll-fgd39",
        EcomUtil.buildCanonicalTag(new HashMap<>(), httpServletRequest));
  }

  @Test
  public void testBuildCanonicalTag0() throws Exception {
    final SlingHttpServletRequest httpServletRequest = Mockito.mock(SlingHttpServletRequest.class);
    final RequestPathInfo requestPathInfo = Mockito.mock(RequestPathInfo.class);

    Mockito.when(httpServletRequest.getRequestURL()).thenReturn(
        new StringBuffer("https://mdev.americangirl.com/shop/p/ggh49.agcar.ag_en.snowflake-gift-card-ggh49agcar.html"));
    Mockito.when(httpServletRequest.getRequestPathInfo()).thenReturn(requestPathInfo);
    Mockito.when(requestPathInfo.getSelectors())
        .thenReturn(new String[] { "ggh49", "agcar", "ag_en", "snowflake-gift-card-ggh49agcar" });
    Assert.assertEquals("https://mdev.americangirl.com/shop/p/snowflake-gift-card-ggh49agcar",
        EcomUtil.buildCanonicalTag(new HashMap<>(), httpServletRequest));
  }


  @Test
  public void testBuildCanonicalTag1() throws Exception {
    final SlingHttpServletRequest httpServletRequest = Mockito.mock(SlingHttpServletRequest.class);
    final Map<String,String> seoProperties = new HashMap<>();
   
    seoProperties.put(Constant.SEO_CANONICAL_TAGS, "https://mdev.americangirl.com/landing");
    Assert.assertEquals("https://mdev.americangirl.com/landing",
        EcomUtil.buildCanonicalTag(seoProperties, httpServletRequest));
  }

  @Test
  public void testBuildCanonicalTag2() throws Exception {
    final SlingHttpServletRequest httpServletRequest = Mockito.mock(SlingHttpServletRequest.class);
    final Map<String,String> seoProperties = new HashMap<>();
    final RequestPathInfo requestPathInfo = Mockito.mock(RequestPathInfo.class);
 
    Mockito.when(httpServletRequest.getRequestPathInfo()).thenReturn(requestPathInfo);
    Mockito.when(requestPathInfo.getSelectors())
        .thenReturn(new String[] { "" });
    Assert.assertEquals("",
        EcomUtil.buildCanonicalTag(seoProperties, httpServletRequest));
  }

  @Test
  public void testBuildCanonicalTag3() throws Exception {
    final SlingHttpServletRequest httpServletRequest = Mockito.mock(SlingHttpServletRequest.class);
    final Map<String,String> seoProperties = new HashMap<>();
    final RequestPathInfo requestPathInfo = Mockito.mock(RequestPathInfo.class);
 
    Mockito.when(httpServletRequest.getRequestPathInfo()).thenReturn(requestPathInfo);
    Mockito.when(requestPathInfo.getSelectors())
        .thenReturn(new String[] { "" });
    Assert.assertEquals("",
        EcomUtil.buildCanonicalTag(seoProperties, httpServletRequest));
  }

  @Test
  public void testGetPlpPageLink() throws Exception {
    final Resource resource = Mockito.mock(Resource.class);

    Mockito.when(resource.getResourceResolver()).thenReturn(null);
    Assert.assertEquals("http://demourl", EcomUtil.getPlpPageLink("http://demourl", resource));
  }

  @Test
  public void testGetPlpPageLink1() throws Exception {
    final Resource resource = Mockito.mock(Resource.class);

    Mockito.when(resource.getResourceResolver()).thenReturn(null);
    Assert.assertEquals("http://demourl", EcomUtil.getPlpPageLink("http://demourl", resource));
  }

  @Test
  public void testGetPlpPageLink2() throws Exception {
    final Resource resource = Mockito.mock(Resource.class);
    final ResourceResolver rr = Mockito.mock(ResourceResolver.class);
    final PageManager pageManager = Mockito.mock(PageManager.class);
    final Page page = Mockito.mock(Page.class);

    Mockito.when(resource.getResourceResolver()).thenReturn(rr);
    Mockito.when(rr.map("/content/ag/en/dolls.html")).thenReturn("/content/ag/en/dolls.html");
    Mockito.when(rr.adaptTo(PageManager.class)).thenReturn(pageManager);
    Mockito.when(pageManager.getPage("/content/ag/en/dolls.html")).thenReturn(page);
    Mockito.when(page.getVanityUrl()).thenReturn("/dolls");
    Assert.assertEquals("/shop/c/dolls", EcomUtil.getPlpPageLink("/content/ag/en/dolls.html", resource));
  }

  @Test
  public void testGetPlpPageLink3() throws Exception {
    final Resource resource = Mockito.mock(Resource.class);
    final ResourceResolver rr = Mockito.mock(ResourceResolver.class);
    final PageManager pageManager = Mockito.mock(PageManager.class);
    final Page page = Mockito.mock(Page.class);

    Mockito.when(resource.getResourceResolver()).thenReturn(rr);
    Mockito.when(rr.map("/content/ag/en/clothing.html")).thenReturn("/content/ag/en/clothing.html");
    Mockito.when(rr.adaptTo(PageManager.class)).thenReturn(pageManager);
    Mockito.when(pageManager.getPage("/content/ag/en/clothing.html")).thenReturn(page);
    Mockito.when(page.getVanityUrl()).thenReturn("/shop/c/clothing");
    Assert.assertEquals("/shop/c/clothing", EcomUtil.getPlpPageLink("/content/ag/en/clothing.html", resource));
  }

  @Test
  public void testGetPlpPageLink4() throws Exception {
    final Resource resource = Mockito.mock(Resource.class);
    final ResourceResolver rr = Mockito.mock(ResourceResolver.class);
    final PageManager pageManager = Mockito.mock(PageManager.class);
    final Page page = Mockito.mock(Page.class);

    Mockito.when(resource.getResourceResolver()).thenReturn(rr);
    Mockito.when(rr.map("/content/ag/en/clothing")).thenReturn("/content/ag/en/clothing");
    Mockito.when(rr.adaptTo(PageManager.class)).thenReturn(pageManager);
    Mockito.when(pageManager.getPage("/content/ag/en/clothing")).thenReturn(page);
    Mockito.when(page.getVanityUrl()).thenReturn(null);
    Assert.assertEquals("/content/ag/en/clothing.html", EcomUtil.getPlpPageLink("/content/ag/en/clothing", resource));
  }

  @Test
  public void testGetSpanAddedString() throws Exception {
	final EcomUtil ecomUtil = new EcomUtil();
    final String template = new StringBuilder(Constant.GIVER_NAME).append(Constant.RECIPIENT_NAME)
        .append(Constant.OCCASSION).append(Constant.ASPIRATION).append(Constant.ATTRIBUTES).toString();
    
    Assert.assertFalse(StringUtils.isEmpty(ecomUtil.getSpanAddedString(template)));
  }

  @Test
  public void testGetSpanAddedString1() throws Exception {
	final EcomUtil ecomUtil = new EcomUtil();
    final String template = "";
    
    Assert.assertTrue(StringUtils.isEmpty(ecomUtil.getSpanAddedString(template)));
  }

  @Test
  public void testGetDynamicTemplate() throws Exception {
    final EcomUtil ecomUtil = new EcomUtil();
    
    MemberModifier.field(EcomUtil.class, "dynamicTemplate").set(ecomUtil, "/ag/template");
    Assert.assertEquals("/ag/template", ecomUtil.getDynamicTemplate());
  }

  @Test
  public void testGetPageUrl() throws Exception {
    final EcomUtil ecomUtil = new EcomUtil();
    
    MemberModifier.field(EcomUtil.class, "pageUrl").set(ecomUtil, "http://url");
    Assert.assertEquals( "http://url", ecomUtil.getPageUrl());
  }

  @Test(expected=Exception.class)
  public void testGetId() throws Exception {
    final EcomUtil ecomUtil = new EcomUtil();
    
    ecomUtil.getId();
  }

  @Test(expected=Exception.class)
  public void testGetActivate() throws Exception {
    final EcomUtil ecomUtil = new EcomUtil();
    
    ecomUtil.activate();
  }
}
