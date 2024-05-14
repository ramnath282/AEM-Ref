package com.mattel.ecomm.core.models;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.Template;
import com.mattel.ecomm.core.interfaces.GetProductTypeService;
import com.mattel.ecomm.core.services.MultifieldReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.pojos.Metatag;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@RunWith(PowerMockRunner.class)
public class EcommPageModelTest {

  @InjectMocks
  EcommPageModel ecommPageModel;

  @Mock
  PropertyReaderService propertyReaderService;
  @Mock
  Resource resource;
  @Mock
  MultifieldReader multifieldReader;
  @Mock
  SlingHttpServletRequest request;
  @Mock
  GetProductTypeService getProductTypeService;

  RequestPathInfo requestPathInfo;
  ResourceResolver resourceResolver;
  Resource storeNodeResource;
  Node storeNode;
  HierarchyNodeInheritanceValueMap inheritanceValueMap;
  PageManager pageManager;
  Page currentPage;
  ValueMap valueMap;
  Map<String, String> metaKeywordsDescription;
  List<String> robotTags;

  @SuppressWarnings("unchecked")
  @Before
  public void setUp() throws Exception {
    MemberModifier.field(EcommPageModel.class, "propertyReaderService").set(ecommPageModel,
        propertyReaderService);
    MemberModifier.field(EcommPageModel.class, "resource").set(ecommPageModel, resource);
    MemberModifier.field(EcommPageModel.class, "multifieldReader").set(ecommPageModel,
        multifieldReader);
    MemberModifier.field(EcommPageModel.class, "request").set(ecommPageModel, request);
    MemberModifier.field(EcommPageModel.class, "getProductTypeService").set(ecommPageModel,
        getProductTypeService);

    requestPathInfo = Mockito.mock(RequestPathInfo.class);
    resourceResolver = Mockito.mock(ResourceResolver.class);
    storeNodeResource = Mockito.mock(Resource.class);
    storeNode = Mockito.mock(Node.class);
    inheritanceValueMap = Mockito.mock(HierarchyNodeInheritanceValueMap.class);
    pageManager = Mockito.mock(PageManager.class);
    currentPage = Mockito.mock(Page.class);
    valueMap = Mockito.mock(ValueMap.class);
    metaKeywordsDescription = Mockito.mock(Map.class);
    robotTags = Mockito.mock(List.class);
  }

  @Test
  public void testInit() throws RepositoryException {
    Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
    Mockito.when(requestPathInfo.getSelectors()).thenReturn(new String [] {""});
    Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
    Mockito.when(resource.getPath()).thenReturn("path");
    Mockito.when(resourceResolver.getResource(Mockito.anyString())).thenReturn(storeNodeResource);
    Mockito.when(storeNodeResource.adaptTo(Node.class)).thenReturn(storeNode);
    Mockito.when(storeNode.getPath()).thenReturn("/content/fisher-price");
    Mockito.when(new HierarchyNodeInheritanceValueMap(resource)).thenReturn(inheritanceValueMap);
    Mockito.when(inheritanceValueMap.getInherited("storeName", String.class)).thenReturn("ag_en");
    Mockito.when(inheritanceValueMap.getInherited("domainKey", String.class)).thenReturn("ag_en");
    Mockito.when(inheritanceValueMap.getInherited("siteKey", String.class)).thenReturn("ag_en");
    Mockito.when(inheritanceValueMap.getInherited("pageNameGT", String.class))
        .thenReturn("pageNameGT");
    Mockito.when(inheritanceValueMap.getInherited("pageTypeGT", String.class))
        .thenReturn("pageTypeGT");
    Mockito.when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
    Mockito.when(pageManager.getContainingPage(resource)).thenReturn(currentPage);
    Mockito.when(currentPage.getProperties()).thenReturn(valueMap);
    Mockito.when(currentPage.getVanityUrl()).thenReturn("");
    Mockito.when(valueMap.get("canonicalUrl", String.class)).thenReturn("anyvanitypath");
    Mockito.when(request.getRequestURL()).thenReturn(
        new StringBuffer("https://www.americangirl.com/shop/p/willa-doll-fgd39.ag_en.fgd39.html"));
    Mockito.when(request.getRequestURI()).thenReturn("/shop/p/willa-doll-fgd39.ag_en.fgd39.html");
    ecommPageModel.init();
  }

  @Test
  public void testSetterGetters() throws IllegalArgumentException, IllegalAccessException {
    ecommPageModel.setBazarVoicePassKey("bazarVoicePassKey");
    Assert.assertEquals("bazarVoicePassKey", ecommPageModel.getBazarVoicePassKey());

    ecommPageModel.setCanonicalTag("canonicalTag");
    Assert.assertEquals("canonicalTag", ecommPageModel.getCanonicalTag());

    ecommPageModel.setCatalogId("catalogId");
    Assert.assertEquals("catalogId", ecommPageModel.getCatalogId());

    ecommPageModel.setDescriptionFlag(true);
    Assert.assertEquals(true, ecommPageModel.isDescriptionFlag());

    ecommPageModel.setDomainKey("domainKey");
    Assert.assertEquals("domainKey", ecommPageModel.getDomainKey());

    ecommPageModel.setErrorPage("errorPage");
    Assert.assertEquals("errorPage", ecommPageModel.getErrorPage());

    ecommPageModel.setKeywordsFlag(true);
    Assert.assertEquals(true, ecommPageModel.isKeywordsFlag());

    ecommPageModel.setLangId("langId");
    Assert.assertEquals("langId", ecommPageModel.getLangId());

    ecommPageModel.setPageDescription("pageDescription");
    Assert.assertEquals("pageDescription", ecommPageModel.getPageDescription());

    ecommPageModel.setPageNameGT("pageNameGT");
    Assert.assertEquals("pageNameGT", ecommPageModel.getPageNameGT());

    ecommPageModel.setPageTitleValue("pageTitleValue");
    Assert.assertEquals("pageTitleValue", ecommPageModel.getPageTitleValue());

    ecommPageModel.setPageType("pageType");
    Assert.assertEquals("pageType", ecommPageModel.getPageType());

    ecommPageModel.setPageTypeGT("pageTypeGT");
    Assert.assertEquals("pageTypeGT", ecommPageModel.getPageTypeGT());

    ecommPageModel.setSeoMetaDescription("seoMetaDescription");
    Assert.assertEquals("seoMetaDescription", ecommPageModel.getSeoMetaDescription());

    ecommPageModel.setSeoMetaDescrptionFlag(true);
    Assert.assertEquals(true, ecommPageModel.isSeoMetaDescrptionFlag());

    ecommPageModel.setSeoPageTitleFlag(true);
    Assert.assertEquals(true, ecommPageModel.isSeoPageTitleFlag());

    ecommPageModel.setSeoUrlKeyword("seoUrlKeyword");
    Assert.assertEquals("seoUrlKeyword", ecommPageModel.getSeoUrlKeyword());

    ecommPageModel.setSeoVarPageTitle("seoVarPageTitle");
    Assert.assertEquals("seoVarPageTitle", ecommPageModel.getSeoVarPageTitle());

    ecommPageModel.setSessionTimeout("sessionTimeout");
    Assert.assertEquals("sessionTimeout", ecommPageModel.getSessionTimeout());

    ecommPageModel.setSnpAccountUrl("snpAccountUrl");
    Assert.assertEquals("snpAccountUrl", ecommPageModel.getSnpAccountUrl());

    ecommPageModel.setSnpArticleAccountURLs("snpArticleAccountURLs");
    Assert.assertEquals("snpArticleAccountURLs", ecommPageModel.getSnpArticleAccountURLs());

    ecommPageModel.setStoreId("storeId");
    Assert.assertEquals("storeId", ecommPageModel.getStoreId());

    ecommPageModel.setStoreName("storeName");
    Assert.assertEquals("storeName", ecommPageModel.getStoreName());

    ecommPageModel.setTargetUrl("targetUrl");
    Assert.assertEquals("targetUrl", ecommPageModel.getTargetUrl());

    ecommPageModel.setTealiumUrl("tealiumUrl");
    Assert.assertEquals("tealiumUrl", ecommPageModel.getTealiumUrl());

    ecommPageModel.setTemplateFlag(true);
    Assert.assertEquals(true, ecommPageModel.isTemplateFlag());

    ecommPageModel.setTitleFlag(true);
    Assert.assertEquals(true, ecommPageModel.isTitleFlag());

    ecommPageModel.setViewportFlag(true);
    Assert.assertEquals(true, ecommPageModel.isViewportFlag());

    MemberModifier.field(EcommPageModel.class, "siteKey").set(ecommPageModel, "siteKey");
    Assert.assertEquals("siteKey", ecommPageModel.getSiteKey());

    MemberModifier.field(EcommPageModel.class, "metaKeywordsDescription").set(ecommPageModel,
        metaKeywordsDescription);
    Assert.assertEquals(metaKeywordsDescription, ecommPageModel.getMetaKeywordsDescription());

    MemberModifier.field(EcommPageModel.class, "ogTags").set(ecommPageModel,
        metaKeywordsDescription);
    Assert.assertEquals(metaKeywordsDescription, ecommPageModel.getOgTags());

    MemberModifier.field(EcommPageModel.class, "robotTags").set(ecommPageModel, robotTags);
    Assert.assertEquals(robotTags, ecommPageModel.getRobotTags());
  }

  @Test
  public void testPopulateAnalyticsMetatags() throws Exception {
    final Template template = Mockito.mock(Template.class);
    Mockito.when(request.getRequestURL()).thenReturn(
        new StringBuffer("https://www.americangirl.com/shop/p/willa-doll-fgd39.ag_en.fgd39.html"));
    Mockito.when(request.getRequestURI()).thenReturn("/shop/p/willa-doll-fgd39.ag_en.fgd39.html");
    Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
    Mockito.when(requestPathInfo.getSelectors())
        .thenReturn(new String[] { "fgd39", "ag_en", "willa-doll-fgd39" });
    Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
    Mockito.when(resource.getPath()).thenReturn("shop");
    Mockito.when(resourceResolver.getResource(Mockito.anyString()))
        .thenReturn(storeNodeResource);
    Mockito.when(storeNodeResource.adaptTo(Node.class)).thenReturn(storeNode);
    Mockito.when(storeNode.getPath()).thenReturn("/content/ag/en/");
    Mockito.when(new HierarchyNodeInheritanceValueMap(resource)).thenReturn(inheritanceValueMap);
    Mockito.when(inheritanceValueMap.getInherited("storeName", String.class)).thenReturn("ag_en");
    Mockito.when(inheritanceValueMap.getInherited("domainKey", String.class)).thenReturn("ag_en");
    Mockito.when(inheritanceValueMap.getInherited("siteKey", String.class)).thenReturn("ag_en");
    Mockito.when(inheritanceValueMap.getInherited("pageNameGT", String.class))
        .thenReturn("pageNameGT");
    Mockito.when(inheritanceValueMap.getInherited("pageTypeGT", String.class))
        .thenReturn(StringUtils.EMPTY);
    Mockito.when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
    Mockito.when(pageManager.getContainingPage(resource)).thenReturn(currentPage);
    Mockito.when(currentPage.getProperties()).thenReturn(valueMap);
    Mockito.when(currentPage.getVanityUrl()).thenReturn("");
    Mockito.when(valueMap.get("canonicalUrl", String.class)).thenReturn("anyvanitypath");
    Mockito.when(currentPage.getTemplate()).thenReturn(template);
    Mockito.when(template.getName()).thenReturn("ecomm-pdp-template");
    Mockito.when(template.getPath()).thenReturn(Constant.PRODUCT_DETAIL_PAGE_PATH);
    Mockito.when(propertyReaderService.getStoreId("ag_en")).thenReturn("10651");
    ecommPageModel.init();
    final List<Metatag> tags = ecommPageModel.getAnalyticsMetaTags();

    Assert.assertNotNull(tags);
    final Map<String, String> tagContent = tags.stream()
        .filter(t -> !"store_id".equals(t.getName()))
        .collect(Collectors.toMap(Metatag::getName, Metatag::getContent));

    Assert.assertEquals("shop:p:willa-doll-fgd39", tagContent.get("page_name"));
    Assert.assertEquals("product detail page", tagContent.get("page_type"));
    Assert.assertEquals("shop", tagContent.get("site_section"));
    Assert.assertEquals("commerce", tagContent.get("site_type"));
    Assert.assertEquals("american girl shop", tagContent.get("company_division"));
    Assert.assertEquals("ecomm-pdp-template", tagContent.get("page_template_id"));
  }
}
