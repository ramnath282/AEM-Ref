package com.mattel.ag.explore.core.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ag.retail.core.services.MultifieldReader;
import com.mattel.ag.retail.core.utils.PropertyReaderUtils;

public class PageModelTest {

  PageModel pageModel;
  MultifieldReader multifieldReader;
  Resource resource;
  ResourceResolver resolver;
  Node node;
  ValueMap valuemap;
  Map<String, String> ogTags = new LinkedHashMap<>();
  PropertyReaderUtils propertyReaderUtils;
  PageManager pageManger;

  @Before
  public void setUp() {
    pageModel = new PageModel();
    propertyReaderUtils = Mockito.mock(PropertyReaderUtils.class);
    pageModel.setPropertyReaderUtils(propertyReaderUtils);
    Mockito.when(propertyReaderUtils.getTargetUrl()).thenReturn("targetUrl");
    resource = Mockito.mock(Resource.class);
    multifieldReader = Mockito.mock(MultifieldReader.class);
    resolver = Mockito.mock(ResourceResolver.class);
    node = Mockito.mock(Node.class);
    valuemap = Mockito.mock(ValueMap.class);
    pageManger = Mockito.mock(PageManager.class);
    Page currentPage = Mockito.mock(Page.class);
    ValueMap currentPageProperties = Mockito.mock(ValueMap.class);

    pageModel.setMultifieldReader(multifieldReader);
    pageModel.setResource(resource);
    ogTags.put("og:description", "");
    ogTags.put("og:title", "");
    ogTags.put("og:image", "");
    pageModel.setOgTags(ogTags);
    pageModel.setDescriptionFlag(true);
    pageModel.setKeywordsFlag(true);
    pageModel.setTemplateFlag(true);
    pageModel.setViewportFlag(true);
    pageModel.setTitleFlag(true);

    assertSame(true, pageModel.isDescriptionFlag());
    assertSame(true, pageModel.isViewportFlag());
    assertSame(true, pageModel.isTitleFlag());
    assertSame(true, pageModel.isTemplateFlag());
    assertSame(true, pageModel.isKeywordsFlag());

    Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
    Mockito.when(resolver.adaptTo(PageManager.class)).thenReturn(pageManger);
    Mockito.when(pageManger.getContainingPage(resource)).thenReturn(currentPage);
    Mockito.when(currentPage.getProperties()).thenReturn(currentPageProperties);
    Mockito.when(currentPage.getVanityUrl()).thenReturn("");
    Mockito.when(resource.getPath()).thenReturn("");
    Mockito.when(resolver.getResource(resource.getPath() + "/" + "metaKeywordsDescription"))
        .thenReturn(resource);
    Mockito.when(resource.adaptTo(Node.class)).thenReturn(node);
    Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
    Mockito.when(resource.getResourceResolver()
        .getResource(resource.getPath() + "/root/articlebannercompone")).thenReturn(resource);
    Mockito.when(resource.getValueMap()).thenReturn(valuemap);
    Mockito.when(resource.getValueMap().get("articleGridImagePath", String.class)).thenReturn("");
  }

  @Test
  public void init() {
    pageModel.init();
  }

  @Test
  public void testToVerifyTargetURL() {
    pageModel.init();
    assertEquals("targetUrl", pageModel.getTargetUrl());
  }

  @Test
  public void testGetters() throws IllegalArgumentException, IllegalAccessException{
    
    @SuppressWarnings("unchecked")
    Map<String, String> testMap = Mockito.mock(Map.class);
    @SuppressWarnings("unchecked")
    List<String> testList = Mockito.mock(List.class);
    
    MemberModifier.field(PageModel.class, "canonicalTag").set(pageModel,
        "canonicalTag");
    MemberModifier.field(PageModel.class, "metaKeywordsDescription").set(pageModel,
        testMap);
    MemberModifier.field(PageModel.class, "ogTags").set(pageModel,
        testMap);
    MemberModifier.field(PageModel.class, "robotTags").set(pageModel,
        testList);
    MemberModifier.field(PageModel.class, "isHasOgImage").set(pageModel,
        true);
    MemberModifier.field(PageModel.class, "isHasOgDescription").set(pageModel,
        true);
    MemberModifier.field(PageModel.class, "isHasOgTitle").set(pageModel,
        true);
    MemberModifier.field(PageModel.class, "imagePath").set(pageModel,
        "/imagePath");
    MemberModifier.field(PageModel.class, "cssPath").set(pageModel,
        "/cssPath");
    MemberModifier.field(PageModel.class, "categoryName").set(pageModel,
        "/categoryName");
    
    assertEquals("canonicalTag", pageModel.getCanonicalTag());
    assertEquals(testMap, pageModel.getMetaKeywordsDescription());
    assertEquals(testMap, pageModel.getOgTags());
    assertEquals(testList, pageModel.getRobotTags());
    assertEquals(true, pageModel.isHasOgImage());
    assertEquals(true, pageModel.isHasOgDescription());
    assertEquals(true, pageModel.isHasOgTitle());
    assertEquals("/imagePath", pageModel.getImagePath());
    assertEquals("/cssPath", pageModel.getCssPath());
    assertEquals("/categoryName", pageModel.getCategoryName());
  }
}
