package com.mattel.global.core.services;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.wcm.api.Page;
import com.mattel.global.core.pojo.NavigationParentPagePojo;

@RunWith(PowerMockRunner.class)
public class SiteNavigationServiceTest {
  private SiteNavigationService impl = new SiteNavigationService();

  @Test
  public void testGetNavigationDetails() throws Exception {
    boolean collectRootPage = true;
    String currentPath = "/content/mattel/mattel-corporate/en-ud/home";
    ValueMap properties = Mockito.mock(ValueMap.class);
    Resource resource = Mockito.mock(Resource.class);
    Page rootPage = Mockito.mock(Page.class);
    Iterator<Page> iterator = Mockito.mock(Iterator.class);

    Mockito.when(properties.get("navThumbImage", StringUtils.EMPTY)).thenReturn("/content/dam/corp-site/images/test.png");
    Mockito.when(properties.get("cq:redirectTarget", StringUtils.EMPTY)).thenReturn("/some/redirect/path");
    Mockito.when(properties.get("cq:redirectTargetOption", StringUtils.EMPTY)).thenReturn("_self");
    Mockito.when(properties.get("adobeTrackingNameForPage", StringUtils.EMPTY)).thenReturn("homepage");
    Mockito.when(rootPage.getProperties()).thenReturn(properties);
    Mockito.when(rootPage.getPath()).thenReturn(currentPath);
    Mockito.when(rootPage.getTitle()).thenReturn("Home Page");
    Mockito.when(rootPage.getName()).thenReturn("Home Page");
    Mockito.when(rootPage.getNavigationTitle()).thenReturn("Home Page");
    Mockito.when(rootPage.getNavigationTitle()).thenReturn("Home Page");
    Mockito.when(rootPage.getContentResource()).thenReturn(resource);
    Mockito.when(iterator.hasNext()).thenReturn(true, false);

    ValueMap properties1 = Mockito.mock(ValueMap.class);
    Resource resource1 = Mockito.mock(Resource.class);
    Page childPage = Mockito.mock(Page.class);
    Iterator<Page> iterator1 = Mockito.mock(Iterator.class);

    Mockito.when(properties1.get("navThumbImage", StringUtils.EMPTY)).thenReturn("/content/dam/corp-site/images/test.png");
    Mockito.when(properties1.get("cq:redirectTarget", StringUtils.EMPTY)).thenReturn(StringUtils.EMPTY);
    Mockito.when(properties1.get("cq:redirectTargetOption", StringUtils.EMPTY)).thenReturn("_self");
    Mockito.when(properties1.get("adobeTrackingNameForPage", StringUtils.EMPTY)).thenReturn("childpage");
    Mockito.when(childPage.getProperties()).thenReturn(properties1);
    Mockito.when(childPage.getPath()).thenReturn(currentPath + "/abouy");
    Mockito.when(childPage.getTitle()).thenReturn("Child Page");
    Mockito.when(childPage.getName()).thenReturn("Child Page");
    Mockito.when(childPage.getNavigationTitle()).thenReturn("Child Page");
    Mockito.when(childPage.getNavigationTitle()).thenReturn("Child Page");
    Mockito.when(childPage.getContentResource()).thenReturn(resource1);
    Mockito.when(iterator1.hasNext()).thenReturn(true,false);
    ValueMap properties11 = Mockito.mock(ValueMap.class);
    Resource resource11 = Mockito.mock(Resource.class);
    Page childPage1 = Mockito.mock(Page.class);
    Iterator<Page> iterator11 = Mockito.mock(Iterator.class);
    Mockito.when(properties11.get("navThumbImage", StringUtils.EMPTY)).thenReturn("/content/dam/corp-site/images/test.png");
    Mockito.when(properties11.get("cq:redirectTarget", StringUtils.EMPTY)).thenReturn(StringUtils.EMPTY);
    Mockito.when(properties11.get("cq:redirectTargetOption", StringUtils.EMPTY)).thenReturn("_self");
    Mockito.when(properties11.get("adobeTrackingNameForPage", StringUtils.EMPTY)).thenReturn("grandchildpage");
    Mockito.when(childPage1.getProperties()).thenReturn(properties1);
    Mockito.when(childPage1.getPath()).thenReturn(currentPath + "/abouy");
    Mockito.when(childPage1.getTitle()).thenReturn("Grand Child Page");
    Mockito.when(childPage1.getName()).thenReturn("Grand Child Page");
    Mockito.when(childPage1.getNavigationTitle()).thenReturn("Grand Child Page");
    Mockito.when(childPage1.getNavigationTitle()).thenReturn("Grand Child Page");
    Mockito.when(childPage1.getContentResource()).thenReturn(resource11);
    Mockito.when(iterator11.hasNext()).thenReturn(true);
    Mockito.when(childPage1.listChildren(Mockito.any(),Mockito.anyBoolean())).thenReturn(iterator11);
    Mockito.when(iterator1.next()).thenReturn(childPage1);
    Mockito.when(childPage.listChildren(Mockito.any(),Mockito.anyBoolean())).thenReturn(iterator1);
    Mockito.when(iterator.next()).thenReturn(childPage);
    Mockito.when(rootPage.listChildren(Mockito.any(),Mockito.anyBoolean())).thenReturn(iterator);
    List<NavigationParentPagePojo> list = impl.getNavigationDetails(rootPage, 2, true, "/content/mattel/mattel-corporate/us/en-us/home");
    Assert.assertNotNull(list);
    Assert.assertEquals(2, list.size());
  }

}
