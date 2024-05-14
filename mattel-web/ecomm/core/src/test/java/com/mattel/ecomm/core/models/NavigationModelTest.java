package com.mattel.ecomm.core.models;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
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
import com.mattel.ecomm.core.pojos.NavigationPojo;
import com.mattel.ecomm.core.services.MultifieldReader;
import com.mattel.ecomm.core.utils.EcomUtil;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ EcomUtil.class })
public class NavigationModelTest {

  @InjectMocks
  NavigationModel navigationModel;

  @Mock
  private Resource resource;

  String navRootPath = "/content/ag/en";
  String externalStoreRootPath = "/content/ag/en/retail";

  @Mock
  private Node navigationLinks;

  @Mock
  private Node externalStores;

  @Mock
  private Node externalLinks;

  @Mock
  private MultifieldReader multifieldReader;

  ResourceResolver resourceResolver;
  PageManager pageManager;

  @Before
  public void setUp() throws Exception {
    MemberModifier.field(NavigationModel.class, "resource").set(navigationModel, resource);
    MemberModifier.field(NavigationModel.class, "navigationLinks").set(navigationModel,
        navigationLinks);
    MemberModifier.field(NavigationModel.class, "externalStores").set(navigationModel,
        externalStores);
    MemberModifier.field(NavigationModel.class, "multifieldReader").set(navigationModel,
        multifieldReader);
    MemberModifier.field(NavigationModel.class, "myAccountLink").set(navigationModel,
        "myAccountLink");
    MemberModifier.field(NavigationModel.class, "agSignInLink").set(navigationModel,
        "agSignInLink");
    MemberModifier.field(NavigationModel.class, "signoutLink").set(navigationModel,
            "signoutLink");
    MemberModifier.field(NavigationModel.class, "agRewardsLink").set(navigationModel,
        "agRewardsLink");
    MemberModifier.field(NavigationModel.class, "shopLinkUrl").set(navigationModel, "shopLinkUrl");
    MemberModifier.field(NavigationModel.class, "shoppingBagLink").set(navigationModel,
        "shoppingBagLink");
    MemberModifier.field(NavigationModel.class, "helpLink").set(navigationModel, "helpLink");
    MemberModifier.field(NavigationModel.class, "signUpUrl").set(navigationModel,
        "signUpUrl");
    MemberModifier.field(NavigationModel.class, "navRootPath").set(navigationModel, navRootPath);
    MemberModifier.field(NavigationModel.class, "externalStoreRootPath").set(navigationModel,
        externalStoreRootPath);
    

    resourceResolver = Mockito.mock(ResourceResolver.class);
    pageManager = Mockito.mock(PageManager.class);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Test
  public void testInit() throws ServiceException, JSONException, RepositoryException {
    Iterator<Page> rootPageItr = Mockito.mock(Iterator.class);
    Page firstLvlPage = Mockito.mock(Page.class);
    Page rootPage = Mockito.mock(Page.class);
    Resource pageResource = Mockito.mock(Resource.class);
    ValueMap valueMap = Mockito.mock(ValueMap.class);
    Iterator<Page> secondLvlPageItr = Mockito.mock(Iterator.class);
    Page secondLvlPage = Mockito.mock(Page.class);
    Iterator<Page> thirdLvlItr = Mockito.mock(Iterator.class);
    Page thirldLvlPage = Mockito.mock(Page.class);

    Map<String, ValueMap> map = new HashMap();
    ValueMap valMap = Mockito.mock(ValueMap.class);
    map.put("test", valMap);

    Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
    Mockito.when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
    Mockito.when(pageManager.getPage(navRootPath)).thenReturn(rootPage);
    Mockito.when(pageManager.getPage(externalStoreRootPath)).thenReturn(rootPage);
    Mockito.when(rootPage.getNavigationTitle()).thenReturn("");
    Mockito.when(rootPage.getTitle()).thenReturn("");
    Mockito.when(rootPage.getName()).thenReturn("PageName");
    Mockito.when(rootPage.listChildren()).thenReturn(rootPageItr);
    Mockito.when(rootPageItr.hasNext()).thenReturn(true, false);
    Mockito.when(rootPageItr.next()).thenReturn(firstLvlPage);
    Mockito.when(rootPage.getContentResource()).thenReturn(pageResource);
    Mockito.when(pageResource.getValueMap()).thenReturn(valueMap);
    Mockito.when(valueMap.get("hideInNav", String.class)).thenReturn("true");
    Mockito.when(firstLvlPage.getPath()).thenReturn("/content/ag/en/shop/categories");
    PowerMockito.mockStatic(EcomUtil.class);
    PowerMockito.when(EcomUtil.checkLink("/content/ag/en/shop/categories", resource))
        .thenReturn("shop/c");
    Mockito.when(firstLvlPage.listChildren()).thenReturn(secondLvlPageItr);
    Mockito.when(secondLvlPageItr.hasNext()).thenReturn(true, false);
    Mockito.when(secondLvlPageItr.next()).thenReturn(secondLvlPage);
    Mockito.when(secondLvlPage.getName()).thenReturn("PageName");
    Mockito.when(secondLvlPage.getPath()).thenReturn("/content/ag/en/shop/categories/dolls");
    PowerMockito.when(EcomUtil.checkLink("/content/ag/en/shop/categories/dolls", resource))
        .thenReturn("shop/c/dolls");
    Mockito.when(secondLvlPage.listChildren()).thenReturn(thirdLvlItr);
    Mockito.when(thirdLvlItr.hasNext()).thenReturn(true, false);
    Mockito.when(thirdLvlItr.next()).thenReturn(thirldLvlPage);
    Mockito.when(thirldLvlPage.getName()).thenReturn("PageName");
    Mockito.when(thirldLvlPage.getPath())
        .thenReturn("/content/ag/en/shop/categories/dolls/new-dolls");
    PowerMockito
        .when(EcomUtil.checkLink("/content/ag/en/shop/categories/dolls/new-dolls", resource))
        .thenReturn("shop/c/new-dolls");

    Mockito.when(multifieldReader.propertyReader(navigationLinks)).thenReturn(map);
    Mockito.when(valMap.get("navLinkText", String.class)).thenReturn("navLinkText");
    Mockito.when(valMap.get("navLinkUrl", String.class))
        .thenReturn("/content/ag/en/shop/categories/dolls");

    Mockito.when(multifieldReader.propertyReader(externalStores)).thenReturn(map);
    Mockito.when(valMap.get("Store location", String.class)).thenReturn("Store Location");
    Mockito.when(valMap.get("storeLocation", String.class)).thenReturn("Store Location");
    Mockito.when(valMap.get("storeLink", String.class)).thenReturn("storeLink");

    Mockito.when(multifieldReader.propertyReader(externalLinks)).thenReturn(map);
    Mockito.when(valMap.get("externalLinkText", String.class)).thenReturn("externalLinkText");
    Mockito.when(valMap.get("externalLinkPath", String.class)).thenReturn("externalLinkPath");
    navigationModel.init();
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testGetters() throws IllegalArgumentException, IllegalAccessException {
    Assert.assertEquals("myAccountLink", navigationModel.getMyAccountLink());
    Assert.assertEquals("agSignInLink", navigationModel.getAgSignInLink());
    Assert.assertEquals("signoutLink", navigationModel.getSignoutLink());
    Assert.assertEquals("agRewardsLink", navigationModel.getAgRewardsLink());
    Assert.assertEquals("shopLinkUrl", navigationModel.getShopLinkUrl());
    Assert.assertEquals("shoppingBagLink", navigationModel.getShoppingBagLink());
    Assert.assertEquals("helpLink", navigationModel.getHelpLink());
    Assert.assertEquals("signUpUrl", navigationModel.getSignUpUrl());
    Assert.assertEquals(multifieldReader, navigationModel.getMultifieldReader());
    
    Page rootPage = Mockito.mock(Page.class);
    MemberModifier.field(NavigationModel.class, "rootPage").set(navigationModel, rootPage);
    Assert.assertEquals(rootPage, navigationModel.getRootPage());

    List<NavigationPojo> navLinkList = Mockito.mock(List.class);
    navigationModel.setNavLinksList(navLinkList);
    Assert.assertEquals(navLinkList, navigationModel.getNavLinksList());

    navigationModel.setNavigationLinks(navigationLinks);
    Assert.assertEquals(navigationLinks, navigationModel.getNavigationLinks());
    
    navigationModel.setExternalStores(externalStores);
    Assert.assertEquals(externalStores, navigationModel.getExternalStores());

    List<NavigationPojo> externalStoreLocations = Mockito.mock(List.class);
    navigationModel.setExternalStoreLocations(externalStoreLocations);
    Assert.assertEquals(externalStoreLocations, navigationModel.getExternalStoreLocations());

    Map<String, Map<String, String>> levelMap = new HashMap<>();
    navigationModel.setFirstLevel(levelMap);
    navigationModel.setSecondLevel(levelMap);
    navigationModel.setThirdLevel(levelMap);
    Assert.assertEquals(levelMap, navigationModel.getFirstLevel());
    Assert.assertEquals(levelMap, navigationModel.getSecondLevel());
    Assert.assertEquals(levelMap, navigationModel.getThirdLevel());
  }
}
