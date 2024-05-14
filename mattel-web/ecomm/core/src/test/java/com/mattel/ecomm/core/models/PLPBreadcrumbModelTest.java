package com.mattel.ecomm.core.models;

import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ecomm.core.pojos.BreadcrumbDetails;
import com.mattel.ecomm.core.utils.EcomUtil;
import com.mattel.ecomm.core.utils.EcommConfigurationUtils;
import com.mattel.ecomm.coreservices.core.constants.Constant;
@RunWith(PowerMockRunner.class)
@PrepareForTest({ EcommConfigurationUtils.class,EcomUtil.class })
public class PLPBreadcrumbModelTest {

  @InjectMocks
  private PLPBreadcrumbModel plpBreadcrumbModel;
  @Mock
  private SlingHttpServletRequest request;
  @Mock
  private Resource resource;
  @Mock
  ResourceResolver resourceResolver;
  @Mock
  EcommConfigurationUtils ecommConfigurationUtils;
  @Mock
  private PageManager pageManager;
  private String rootPath = "/content/ag/en/retail";
  @Mock
  private Page page;
  @Mock
  private Page currentPage;
  @Mock
  Page landingPage;
  ValueMap valueMap;
  ValueMap currentPageValueMap;
  BreadcrumbDetails plpBreadcrumbPojo;
  @Mock
  Page tempPage;

  @Before
  public void setUp() throws Exception {
    MemberModifier.field(PLPBreadcrumbModel.class, "resource").set(plpBreadcrumbModel, resource);
    MemberModifier.field(PLPBreadcrumbModel.class, "currentPage").set(plpBreadcrumbModel, currentPage);
    Mockito.when(currentPage.getPath()).thenReturn("/content/ag/en/shop/doll/doll-cat/coll-cat-1");
    Mockito.when(resource.getPath()).thenReturn(rootPath);
    Mockito.when(request.getResourceResolver()).thenReturn(resourceResolver);
    Mockito.when(resource.getPath()).thenReturn(rootPath);
    Mockito.when(request.getResourceResolver()).thenReturn(resourceResolver);
    Mockito.when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
    Mockito.when(currentPage.getPageManager()).thenReturn(pageManager);
    PowerMockito.mockStatic(EcomUtil.class);
    Mockito.when(EcomUtil.getInheritedProperty(Mockito.any(), Mockito.any())).thenReturn("/content/ag/en/shop");
    Mockito.when(currentPage.getAbsoluteParent(4)).thenReturn(page);
    Mockito.when(page.getPath()).thenReturn("/content/ag/en/shop");
    Mockito.when(pageManager.getPage("/content/ag/en/shop")).thenReturn(landingPage);
    Mockito.when(page.getParent()).thenReturn(landingPage);
    Mockito.when(landingPage.getPath()).thenReturn("aaa");
    Mockito.when(currentPage.getDepth()).thenReturn(3);
    Mockito.when(page.getDepth()).thenReturn(2);
    Mockito.when(currentPage.getAbsoluteParent(2)).thenReturn(tempPage);
    Mockito.when(tempPage.getPath()).thenReturn("/doll/doll-cat/coll-cat-1");
    Mockito.when(EcomUtil.checkLink(Mockito.anyString(), Mockito.any(Resource.class))).thenReturn("anyString");
    valueMap = Mockito.mock(ValueMap.class);
    valueMap.put( "hideInNav", "false");
    valueMap.put( "jcr:title", "title");

    currentPageValueMap = Mockito.mock(ValueMap.class);
    currentPageValueMap.put( "hideInNav", "false");
    currentPageValueMap.put( "jcr:title", "title");

    Mockito.when(currentPage.getProperties()).thenReturn(currentPageValueMap);
    Mockito.when(currentPageValueMap.get("hideInNav", String.class)).thenReturn("true","false");

    Mockito.when(tempPage.getProperties()).thenReturn(valueMap);
    Mockito.when(valueMap.get("hideInNav", String.class)).thenReturn("true","false");

   /* Mockito.when(resource.getPath()).thenReturn(rootPath);
    Mockito.when(request.getResourceResolver()).thenReturn(resourceResolver);
    Mockito.when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
    PowerMockito.mockStatic(EcommConfigurationUtils.class);
    Mockito.when(EcommConfigurationUtils.getRootCatgoryPagePath()).thenReturn(rootPath);
    Mockito.when(pageManager.getPage(Mockito.anyString())).thenReturn(page);
    Mockito.when(pageManager.getContainingPage(Mockito.any(Resource.class)))
        .thenReturn(currentPage);
    Mockito.when(page.getParent()).thenReturn(page);
    Mockito.when(page.getTitle()).thenReturn("Test Page");
    Mockito.when(page.getPath()).thenReturn(rootPath);
    Mockito.when(currentPage.getAbsoluteParent(4)).thenReturn(page);
    Mockito.when(currentPage.getDepth()).thenReturn(2);
    Mockito.when(page.getDepth()).thenReturn(3);
    valueMap = Mockito.mock(ValueMap.class);
    valueMap.put( "hideInNav", "HIDE_IN_NAV");
    Mockito.when(currentPage.getProperties()).thenReturn(valueMap);*/
  }

  @Test
  public void testGetBreadcrumbLinks() {
    plpBreadcrumbModel.init();

  }

  @Test
  public void testGetBreadcrumbLinks_withNullValue() {
    plpBreadcrumbModel.setBreadcrumbLinks(null);
    Assert.assertNull(plpBreadcrumbModel.getBreadcrumbLinks());
  }

}
