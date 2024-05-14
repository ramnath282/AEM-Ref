package com.mattel.informational.core.helper;

import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.search.QueryBuilder;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.informational.core.constants.Constants;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ StringUtils.class })
public class InformationalHelperTest {

  @Mock
  Resource resource;
  @Mock
  ResourceResolver resourceResolver;
  @Mock
  PageManager pageManager;
  Page page;
  Session session;
  QueryBuilder queryBuilder;

  @Before
  public void setUp() {
    page = Mockito.mock(Page.class);
    session = Mockito.mock(Session.class);
    queryBuilder = Mockito.mock(QueryBuilder.class);
    PowerMockito.mockStatic(StringUtils.class);

    Mockito.when(resource.getPath()).thenReturn("/content/jcr:content");
    Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
    Mockito.when(resource.getResourceResolver().adaptTo(PageManager.class)).thenReturn(pageManager);
    Mockito.when(pageManager.getPage("/content")).thenReturn(page);
    Mockito.when(page.getAbsoluteParent(2)).thenReturn(page);
    Mockito.when(page.getAbsoluteParent(2).hasChild(Constants.LANG_MASTERS)).thenReturn(false);
    Mockito.when(resourceResolver.map("/content/")).thenReturn("/content");
    Mockito.when(page.getAbsoluteParent(2).hasChild(Constants.LANG_MASTERS)).thenReturn(true);
    Mockito.when(page.getAbsoluteParent(1)).thenReturn(page);
    Mockito.when(page.getAbsoluteParent(1).hasChild(Constants.LANG_MASTERS)).thenReturn(true);
    Mockito.when(InformationalHelper.getBrandName(resource)).thenReturn("mattel-corp");
    Mockito.when(pageManager.getContainingPage(resource)).thenReturn(page);
    Mockito.when(page.getDepth()).thenReturn(8);
    Mockito.when(page.getAbsoluteParent(5)).thenReturn(page);
    Mockito.when(page.getAbsoluteParent(5).getName()).thenReturn("experience-fragment");
    Mockito.when(StringUtils.isNotBlank("/experience-fragment/mattel/mattel-corp"))
        .thenReturn(false);
  }

  @Test
  public void initTest() {
    Mockito.when(StringUtils.isNotBlank("dam/")).thenReturn(true);
    InformationalHelper.checkLink("/content/#dam/#india", resource);
  }

  @Test
  public void initTes_1() {
    Mockito.when(StringUtils.isNotBlank("dam/")).thenReturn(true);
    InformationalHelper.checkLink("/content/#dam/#india", null);
  }

  @Test
  public void initTes_2() {
    Mockito.when(StringUtils.isNotBlank("dam/")).thenReturn(true);
    InformationalHelper.checkLink(null, resource);
  }

  @Test
  public void checkLink() {
    InformationalHelper.checkLink("/content/#dam/#india", resource);
  }

  @Test
  public void getBrandName() {
    InformationalHelper.getBrandName(resource);
  }

  @Test
  public void getBrandName_1() {
    Mockito.when(resource.getPath()).thenReturn(
        "/content/experience-fragments/mattel/mattel-corporate/en/header/master/jcr:content");
    InformationalHelper.getBrandName(resource);
  }

  @Test
  public void getParentBrandName() {
    InformationalHelper.getParentBrandName(resource);
  }

  @Test
  public void getParentBrandName_1() {
    Mockito.when(resource.getPath()).thenReturn(
        "/content/experience-fragments/mattel/mattel-corporate/en/header/master/jcr:content");
    InformationalHelper.getParentBrandName(resource);
  }

  @Test
  public void getPageLocale() {
    InformationalHelper
        .getPageLocale("/content/experience-fragment/mattel/mattel-corp/en/header/header_en-us");
  }

  @Test
  public void getPageLocale_1() {
    InformationalHelper
        .getPageLocale("/content/experience-fragment/mattel/mattel-corp/en/header/en-us");
  }

  @Test
  public void getRelativePath() {
    InformationalHelper.getRelativePath("/content/experience-fragment/mattel/mattel-corp",
        resource);
  }

  @Test
  public void getCountryNodesByLanguage() {
    InformationalHelper.getCountryNodesByLanguage("", session, queryBuilder);
  }

  @Test
  public void testcheckLinkWithDamPath() {
    InformationalHelper.checkLink("/content/dam/india", resource);
  }

  @Test
  public void testCheckLinkWithContentPath() {
    Mockito.when(resourceResolver.map("/content/mattel/mattel-corporate/us/en-us/home"))
        .thenReturn("/content/mattel/mattel-corporate/us/en-us/home");
    InformationalHelper.checkLink("/content/mattel/mattel-corporate/us/en-us/home", resource);
  }
}
