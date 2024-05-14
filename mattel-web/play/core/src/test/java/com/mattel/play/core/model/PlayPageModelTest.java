package com.mattel.play.core.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

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
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.play.core.helper.PlayHelper;
import com.mattel.play.core.services.MultifieldReader;
import com.mattel.play.core.services.ProductGalleryAndLandingService;
import com.mattel.play.core.utils.PlaySiteConfigurationUtils;
import com.mattel.play.core.utils.PropertyReaderUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PropertyReaderUtils.class, PlayHelper.class, PlaySiteConfigurationUtils.class })
public class PlayPageModelTest {

  @InjectMocks
  private PlayPageModel playPageModel;

  @Mock
  private MultifieldReader multifieldReader;

  @Mock
  private PropertyReaderUtils propertyReaderUtils;

  @Mock
  private Resource resource;

  @Mock
  private ProductGalleryAndLandingService productGalleryAndLandingService;

  @Mock
  private PageManager pageManager;

  @SuppressWarnings("unchecked")
  @Before
  public void setup() throws RepositoryException {
    PowerMockito.mockStatic(PropertyReaderUtils.class);
    PowerMockito.mockStatic(PlayHelper.class);
    PowerMockito.mockStatic(PlaySiteConfigurationUtils.class);

    Mockito.when(PlayHelper.leftIndexShiftForSiteWOParentName(resource)).thenReturn(0);
    Mockito.when(resource.getPath())
        .thenReturn("/content/mattel-play/polly-pocket/us/en-us/home/jcr:content");
    Mockito
        .when(
            PlayHelper.getPageLocale("/content/mattel-play/polly-pocket/us/en-us/home/jcr:content"))
        .thenReturn("en-us");
   
    Mockito.when(PlayHelper.checkIfSiteisMattelPlay(resource.getPath())).thenReturn(true);
    Mockito.when(PlayHelper.getBrandName(resource.getPath())).thenReturn("polly-pocket");
    Mockito.when(PlayHelper.getRescueBrandName(resource.getPath())).thenReturn("");

    ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class);
    Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
    Resource analyticsNodeResource = Mockito.mock(Resource.class);
    Mockito
        .when(resourceResolver.getResource(
            "/content/mattel-play/polly-pocket/us/en-us/home/jcr:content/analyticsProperties"))
        .thenReturn(analyticsNodeResource);

    Node analyticsNode = Mockito.mock(Node.class);
    Mockito.when(analyticsNodeResource.adaptTo(Node.class)).thenReturn(analyticsNode);

    Map<String, ValueMap> stringValueMapLinkedHashMap = Mockito.mock(Map.class);
    Mockito.when(multifieldReader.propertyReader(analyticsNode))
        .thenReturn(stringValueMapLinkedHashMap);

    Mockito.when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
    Page currentPage = Mockito.mock(Page.class);
    Mockito.when(pageManager.getContainingPage(resource)).thenReturn(currentPage);
    Page homePage = Mockito.mock(Page.class);
    Mockito.when(currentPage.getAbsoluteParent(5)).thenReturn(homePage);
    Mockito.when(homePage.getPath()).thenReturn("/content/mattel-play/polly-pocket/us/en-us/home");
    Mockito.when(currentPage.getPath())
        .thenReturn("/content/mattel-play/polly-pocket/us/en-us/home");

    Resource homePageResource = Mockito.mock(Resource.class);
    Mockito
        .when(resourceResolver
            .getResource("/content/mattel-play/polly-pocket/us/en-us/home/jcr:content"))
        .thenReturn(homePageResource);
    ValueMap homepageproperties = Mockito.mock(ValueMap.class);
    Mockito.when(homePageResource.adaptTo(ValueMap.class)).thenReturn(homepageproperties);
    Mockito.when(homepageproperties.get("globalSeoTitle", "")).thenReturn("Global SEO Title");
    Mockito.when(homepageproperties.get("seoTitle", "")).thenReturn("Local SEO Title");

    Resource seoKeywordsNodeResource = Mockito.mock(Resource.class);
    Mockito
        .when(resourceResolver.getResource(
            "/content/mattel-play/polly-pocket/us/en-us/home/jcr:content/metaKeywords"))
        .thenReturn(seoKeywordsNodeResource);
    Node seoNode = Mockito.mock(Node.class);
    Mockito.when(seoKeywordsNodeResource.adaptTo(Node.class)).thenReturn(seoNode);

    Map<String, ValueMap> seoLinkedHashMap = Mockito.mock(Map.class);
    Mockito.when(multifieldReader.propertyReader(seoNode)).thenReturn(seoLinkedHashMap);

    Mockito
        .when(productGalleryAndLandingService.getCurrentBrandExpFragmentRootPath("polly-pocket",
            "experience-fragments", "/content/mattel-play/polly-pocket/us/en-us/home/jcr:content"))
        .thenReturn("experience-fragments");

    Mockito
        .when(productGalleryAndLandingService.getCurrentBrandExpFragmentRootPath("polly-pocket",
            "brandsite", "/content/mattel-play/polly-pocket/us/en-us/home/jcr:content"))
        .thenReturn("mattel-play/");

    Mockito
        .when(PlayHelper
            .checkIfSiteisMattelPlay("/content/mattel-play/polly-pocket/us/en-us/home/jcr:content"))
        .thenReturn(true);
    Mockito.when(PlaySiteConfigurationUtils.getRootErrorPageName()).thenReturn("/error/");

    Mockito.when(
        PlayHelper.getRelativePath("/content/mattel-play/polly-pocket/us/en-us/home/", resource))
        .thenReturn("/us/en-us");
    Session session = Mockito.mock(Session.class);
    Mockito.when(resourceResolver.adaptTo(Session.class)).thenReturn(session);

    QueryBuilder queryBuilder = Mockito.mock(QueryBuilder.class);
    Mockito.when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);

    Mockito.when(PropertyReaderUtils.getPlayPath()).thenReturn("playpath/");
    SearchResult result = Mockito.mock(SearchResult.class);
    Mockito
        .when(PlayHelper.getCountryNodesByLanguage("playpath/polly-pocket", session, queryBuilder))
        .thenReturn(result);

    List<Hit> hits = new ArrayList<>();
    Hit hit = Mockito.mock(Hit.class);
    hits.add(hit);
    Mockito.when(result.getHits()).thenReturn(hits);
    Mockito.when(hit.getPath()).thenReturn("path");

    Page testPage = Mockito.mock(Page.class);
    Mockito.when(pageManager.getPage("path")).thenReturn(testPage);

    Iterator<Resource> resources = Mockito.mock(Iterator.class);
    Mockito.when(result.getResources()).thenReturn(resources);
    Mockito.when(resources.hasNext()).thenReturn(true, false);
    Resource testResource = Mockito.mock(Resource.class);
    Mockito.when(resources.next()).thenReturn(testResource);
    ResourceResolver testResourceResolver = Mockito.mock(ResourceResolver.class);
    Mockito.when(testResource.getResourceResolver()).thenReturn(testResourceResolver);
    Mockito.when(testResourceResolver.isLive()).thenReturn(true);

    HierarchyNodeInheritanceValueMap inheritanceValueMap = Mockito
        .mock(HierarchyNodeInheritanceValueMap.class);
    Mockito.when(new HierarchyNodeInheritanceValueMap(resource)).thenReturn(inheritanceValueMap);
    Mockito.when(inheritanceValueMap.getInherited("clientlibCategory", String.class))
        .thenReturn("clientlib");

    Mockito.when(PlaySiteConfigurationUtils.getFpRootPath()).thenReturn("/play");
  }

  @Test
  public void testInit1() {
    Page page = Mockito.mock(Page.class);
    Mockito.when(pageManager.getPage("path/home")).thenReturn(page);
    ValueMap pageProps = Mockito.mock(ValueMap.class);
    Mockito.when(page.getProperties()).thenReturn(pageProps);
    Mockito.when(pageProps.containsKey("cq:redirectTarget")).thenReturn(true);
    Mockito.when(pageProps.get("cq:redirectTarget")).thenReturn("redirettarget");
    playPageModel.init();
  }

  @SuppressWarnings("rawtypes")
  @Test
  public void testGetterSetter() throws IllegalArgumentException, IllegalAccessException {

    MemberModifier.field(PlayPageModel.class, "adobeTrackingNameForPage").set(playPageModel,
        "adobeTrackingNameForPage");
    MemberModifier.field(PlayPageModel.class, "brandName").set(playPageModel, "brandName");
    MemberModifier.field(PlayPageModel.class, "businessSiteName").set(playPageModel,
        "businessSiteName");
    MemberModifier.field(PlayPageModel.class, "characterCategoryFilterPath").set(playPageModel,
        "characterCategoryFilterPath");
    MemberModifier.field(PlayPageModel.class, "clientlibCategory").set(playPageModel,
        "clientlibCategory");
    MemberModifier.field(PlayPageModel.class, "footerPath").set(playPageModel, "footerPath");
    MemberModifier.field(PlayPageModel.class, "gameLandingGridPath").set(playPageModel,
        "gameLandingGridPath");
    MemberModifier.field(PlayPageModel.class, "headerPath").set(playPageModel, "headerPath");
    MemberModifier.field(PlayPageModel.class, "homePagePath").set(playPageModel, "homePagePath");
    MemberModifier.field(PlayPageModel.class, "interstitialApp").set(playPageModel,
        "interstitialApp");
    MemberModifier.field(PlayPageModel.class, "leavingInterstitialPath").set(playPageModel,
        "leavingInterstitialPath");
    MemberModifier.field(PlayPageModel.class, "pageLocale").set(playPageModel, "pageLocale");
    MemberModifier.field(PlayPageModel.class, "pageName").set(playPageModel, "pageName");
    MemberModifier.field(PlayPageModel.class, "parentPageType").set(playPageModel,
        "parentPageType");
    MemberModifier.field(PlayPageModel.class, "productCategoryFilterPath").set(playPageModel,
        "productCategoryFilterPath");
    MemberModifier.field(PlayPageModel.class, "productDetailGridTitleFragPath").set(playPageModel,
        "productDetailGridTitleFragPath");
    MemberModifier.field(PlayPageModel.class, "productThumbnailGridFragPath").set(playPageModel,
        "productThumbnailGridFragPath");
    MemberModifier.field(PlayPageModel.class, "propertiesJson").set(playPageModel,
        "propertiesJson");
    MemberModifier.field(PlayPageModel.class, "rescueBrandName").set(playPageModel,
        "rescueBrandName");
    MemberModifier.field(PlayPageModel.class, "rescueParentName").set(playPageModel,
        "rescueParentName");
    MemberModifier.field(PlayPageModel.class, "retailerInterstitialPath").set(playPageModel,
        "retailerInterstitialPath");
    MemberModifier.field(PlayPageModel.class, "scriptUrl").set(playPageModel, "scriptUrl");
    MemberModifier.field(PlayPageModel.class, "siteCountry").set(playPageModel, "siteCountry");
    MemberModifier.field(PlayPageModel.class, "title").set(playPageModel, "title");
    MemberModifier.field(PlayPageModel.class, "keywordCommaSeparated").set(playPageModel,
        "keywordCommaSeparated");
    List hrefLangList = Mockito.mock(List.class);
    MemberModifier.field(PlayPageModel.class, "hrefLangList").set(playPageModel, hrefLangList);

    Assert.assertNotNull(playPageModel.getAdobeTrackingNameForPage());
    Assert.assertNotNull(playPageModel.getBrandName());
    Assert.assertNotNull(playPageModel.getBusinessSiteName());
    Assert.assertNotNull(playPageModel.getCharacterCategoryFilterPath());
    Assert.assertNotNull(playPageModel.getClientlibCategory());
    Assert.assertNotNull(playPageModel.getFooterPath());
    Assert.assertNotNull(playPageModel.getGameLandingGridPath());
    Assert.assertNotNull(playPageModel.getHeaderPath());
    Assert.assertNotNull(playPageModel.getHomePagePath());
    Assert.assertNotNull(playPageModel.getInterstitialApp());
    Assert.assertNotNull(playPageModel.getLeavingInterstitialPath());
    Assert.assertNotNull(playPageModel.getPageLocale());
    Assert.assertNotNull(playPageModel.getPageName());
    Assert.assertNotNull(playPageModel.getParentPageType());
    Assert.assertNotNull(playPageModel.getProductCategoryFilterPath());
    Assert.assertNotNull(playPageModel.getProductDetailGridTitleFragPath());
    Assert.assertNotNull(playPageModel.getProductThumbnailGridFragPath());
    Assert.assertNotNull(playPageModel.getPropertiesJson());
    Assert.assertNotNull(playPageModel.getRescueBrandName());
    Assert.assertNotNull(playPageModel.getRescueParentName());
    Assert.assertNotNull(playPageModel.getRetailerInterstitialPath());
    Assert.assertNotNull(playPageModel.getScriptUrl());
    Assert.assertNotNull(playPageModel.getSiteCountry());
    Assert.assertNotNull(playPageModel.getTitle());
    Assert.assertNotNull(playPageModel.getHrefLangList());
    Assert.assertNotNull(playPageModel.getKeywordCommaSeparated());

    playPageModel.setHeaderPath("headerPath");
    playPageModel.setHomePagePath("homePagePath");
    playPageModel.setMultifieldReader(multifieldReader);
    playPageModel.setProductCategoryFilterPath("productCategoryFilterPath");
    playPageModel.setProductDetailGridTitleFragPath("productDetailGridTitleFragPath");
    playPageModel.setProductGalleryAndLandingService(productGalleryAndLandingService);
    playPageModel.setProductThumbnailGridFragPath("productThumbnailGridFragPath");
    playPageModel.setPropertyReaderUtils(propertyReaderUtils);
    playPageModel.setResource(resource);
  }

}
