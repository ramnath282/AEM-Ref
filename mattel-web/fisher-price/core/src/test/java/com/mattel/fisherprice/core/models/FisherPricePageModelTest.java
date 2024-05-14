package com.mattel.fisherprice.core.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;


import com.day.cq.replication.ReplicationStatus;
import com.day.cq.wcm.api.PageFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;

import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.fisherprice.core.constants.Constants;
import com.mattel.fisherprice.core.helper.FisherPriceHelper;
import com.mattel.fisherprice.core.services.MultifieldReader;
import com.mattel.fisherprice.core.utils.FisherPriceConfigurationUtils;
import com.mattel.fisherprice.core.utils.FisherPriceUtils;
import com.mattel.fisherprice.core.utils.PropertyReaderUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ FisherPriceHelper.class, FisherPriceConfigurationUtils.class,ResourceUtil.class, FisherPriceUtils.class })
public class FisherPricePageModelTest {
	@Mock
	SlingHttpServletRequest request;
	FisherPricePageModel fisherPricePageModel = new FisherPricePageModel();
	MultifieldReader multifieldReader;
	PropertyReaderUtils propertyReaderUtils;
	Resource resource = Mockito.mock(Resource.class);;
	ResourceResolver resolver;
	Node node;
	Map<String, ValueMap> stringValueMapLinkedHashMap = new HashMap<>();
	ValueMap valueMap,valueMapTags;
	Map.Entry<String, ValueMap> mapEntry;
	PageManager pageManager;
	Page currentPage;
	HierarchyNodeInheritanceValueMap hierarchyNodeInheritanceValueMap;

	TagManager tagManager;
	Iterator<Resource> resources;
	String val[] = { "fisher-price" };
	String tag[] = { "early-child-development" };
	List<String> keywordsList = new ArrayList<>();
	Iterator<Page> rootPageIterator;
	Iterator<Page> pageIterator;
	Page siteRootPage;
	PageFilter pageFilter;

	@Mock
	ValueMap properties;
	@Mock
	ReplicationStatus status;
	@Mock
	Page page;
	Page languagePage;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws RepositoryException {
		multifieldReader = Mockito.mock(MultifieldReader.class);
		propertyReaderUtils = Mockito.mock(PropertyReaderUtils.class);
		siteRootPage = Mockito.mock(Page.class);
		rootPageIterator = Mockito.mock(Iterator.class);
		pageIterator = Mockito.mock(Iterator.class);
		resolver = Mockito.mock(ResourceResolver.class);
		node = Mockito.mock(Node.class);
		valueMap = Mockito.mock(ValueMap.class);
		valueMapTags = Mockito.mock(ValueMap.class);
		mapEntry = Mockito.mock(Map.Entry.class);
		pageManager = Mockito.mock(PageManager.class);
		languagePage = Mockito.mock(Page.class);
		currentPage = Mockito.mock(Page.class);
		hierarchyNodeInheritanceValueMap = Mockito.mock(HierarchyNodeInheritanceValueMap.class);

		resources = Mockito.mock(Iterator.class);
		tagManager = Mockito.mock(TagManager.class);
		pageFilter = Mockito.mock(PageFilter.class);
		PowerMockito.mockStatic(FisherPriceHelper.class);
		PowerMockito.mockStatic(FisherPriceConfigurationUtils.class);
		PowerMockito.mockStatic(ResourceUtil.class);
		PowerMockito.mockStatic(FisherPriceUtils.class);


		keywordsList.add("abc");
		fisherPricePageModel.setResource(resource);
		fisherPricePageModel.setRequest(request);
		fisherPricePageModel.setMultifieldReader(multifieldReader);
		fisherPricePageModel.setPropertyReaderUtils(propertyReaderUtils);
		fisherPricePageModel.setHeaderPath("/header");

		stringValueMapLinkedHashMap.put("", valueMap);
		stringValueMapLinkedHashMap.put("", valueMap);

		Mockito.when(resource.getPath()).thenReturn("/content");
		Mockito.when(FisherPriceHelper.leftIndexShiftForSiteWOParentName(resource)).thenReturn(1);
		Mockito.when(FisherPriceHelper.getPageLocale(resource.getPath())).thenReturn("en/en-us");
		Mockito.when(FisherPriceHelper.getBrandName(resource)).thenReturn("fisher-price");
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resource.getPath() + "/analyticsProperties")
				.thenReturn("/analyticsProperties/en/en-us/fisherprice");
		Mockito.when(request.getPathInfo()).thenReturn("/analyticsProperties/en/en-us/fisherprice");
		Mockito.when(resource.getResourceResolver().getResource(resource.getPath() + "/analyticsProperties"))
				.thenReturn(resource);
		Mockito.when(resource.adaptTo(Node.class)).thenReturn(node);
		Mockito.when(multifieldReader.propertyReader(node)).thenReturn(stringValueMapLinkedHashMap);
		Mockito.when(mapEntry.getValue()).thenReturn(valueMap);
		Mockito.when(mapEntry.getValue().get("propertyName")).thenReturn(valueMap);
		Mockito.when(mapEntry.getValue().get("propertyName").toString()).thenReturn("");
		Mockito.when(mapEntry.getValue().get("propertyValue")).thenReturn(valueMap);
		Mockito.when(mapEntry.getValue().get("propertyValue").toString()).thenReturn("");
		Mockito.when(propertyReaderUtils.getScriptUrl()).thenReturn("");
		Mockito.when(resource.getResourceResolver().adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(pageManager.getContainingPage(resource)).thenReturn(currentPage);
		Mockito.when(pageManager.getPage(Mockito.anyString())).thenReturn(page);
		Mockito.when(page.adaptTo(ReplicationStatus.class)).thenReturn(status);
		Mockito.when(status.isActivated()).thenReturn(true,false);
		Mockito.when(currentPage.getAbsoluteParent(6)).thenReturn(currentPage);
		Mockito.when(currentPage.getContentResource()).thenReturn(resource);
		Mockito.when(resource.getValueMap()).thenReturn(valueMapTags);
		Mockito.when(valueMapTags.get("primaryTags", String[].class)).thenReturn(tag);
		Mockito.when(resolver.adaptTo(TagManager.class)).thenReturn(tagManager);
		Mockito.when(currentPage.getPath()).thenReturn("/analyticsProperties/en/en-us/fisherprice");
		Mockito.when(FisherPriceConfigurationUtils.getRootErrorPageName()).thenReturn("/fisher-price");
		Mockito.when(FisherPriceConfigurationUtils.getExpFragmentRootPath()).thenReturn("/fisher-price");
		Mockito.when(FisherPriceHelper.getRelativePath("/analyticsProperties/en/en-us/fisherprice", resource))
				.thenReturn("/en/en-us/");
		Mockito.when(new HierarchyNodeInheritanceValueMap(resource)).thenReturn(hierarchyNodeInheritanceValueMap);
		Mockito.when(currentPage.getDepth()).thenReturn(5);
		Mockito.when(currentPage.getProperties()).thenReturn(valueMap);
		Mockito.when(currentPage.getProperties().get("sling:resourceType", String.class))
				.thenReturn("fp-homepage-page");
		Mockito.when(currentPage.getAbsoluteParent(4)).thenReturn(currentPage);

		Mockito.when(request.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(pageManager.getPage(Mockito.anyString())).thenReturn(page);
		Mockito.when(page.listChildren()).thenReturn(pageIterator);
		Mockito.when(pageIterator.hasNext()).thenReturn(true,false);
		Mockito.when(pageIterator.next()).thenReturn(siteRootPage);
		Mockito.when(siteRootPage.listChildren(Mockito.any(),Mockito.anyBoolean())).thenReturn(rootPageIterator);
		Mockito.when(siteRootPage.getPath()).thenReturn("/content/fisher-price/us/en-us");
		Mockito.when(rootPageIterator.hasNext()).thenReturn(true,false);
		Mockito.when(rootPageIterator.next()).thenReturn(languagePage);
		Mockito.when(FisherPriceHelper.getPageLocale(languagePage.getPath())).thenReturn("locale");
		Mockito.when(languagePage.getPath()).thenReturn("/content/fisher-price/us/en-us");   
		Mockito.when(
				resource.getResourceResolver().getResource("/analyticsProperties/en/en-us/fisherprice/jcr:content"))
				.thenReturn(resource);
		Mockito.when(FisherPriceConfigurationUtils.getHeaderExpFragmentName()).thenReturn("/content/mattel-play");
		Mockito.when(resource.adaptTo(ValueMap.class)).thenReturn(valueMap);
		Mockito.when(FisherPriceConfigurationUtils.getExcludedBrandsFooter()).thenReturn(val);
		Mockito.when(pageManager.getPage("/content/mattel-play/polly-pocket/us/en/en-us")).thenReturn(currentPage);
		Mockito.when(pageManager.getPage("/content/mattel-play/polly-pocket/us/en/en-us/home")).thenReturn(currentPage);
		Mockito.when(valueMap.containsKey("cq:redirectTarget")).thenReturn(true);
		Mockito.when(valueMap.get("cq:redirectTarget")).thenReturn(val);
		Mockito.when(
				resource.getResourceResolver().getResource("/analyticsProperties/en/en-us/fisherprice/metaKeywords"))
				.thenReturn(resource);
		Mockito.when(mapEntry.getValue().get("keyword")).thenReturn(valueMap);
		Mockito.when(mapEntry.getValue().get("keyword").toString()).thenReturn("");

		Mockito.when(resource.adaptTo(ValueMap.class)).thenReturn(properties);
		Mockito.when(properties.get("canonicalUrl", String.class)).thenReturn("canonical-url");
		Mockito.when(FisherPriceUtils.getInheritedProperty(Mockito.any(Resource.class), Mockito.anyString())).thenReturn("AnyString");

	}

	@Test
	public void initTest() {
		fisherPricePageModel.init();
		Mockito.when(currentPage.getPath()).thenReturn("/analyticsProperties/en/en-us/fisherprice/language-masters");
	}

	@Test
	public void initTestContentPageTemplate() {
		Mockito.when(currentPage.getPath()).thenReturn("/analyticsProperties/en/en-us/fisherprice");
		Mockito.when(currentPage.getProperties().get("sling:resourceType", String.class)).thenReturn("fp-contentpage");
		fisherPricePageModel.init();
	}
	@Test
	public void initTestPDPPageTemplate() {
		Mockito.when(currentPage.getPath()).thenReturn("/analyticsProperties/en/en-us/fisherprice");
		Mockito.when(currentPage.getProperties().get("sling:resourceType", String.class)).thenReturn("fp-productdetail-page");
		Mockito.when(request.getPathInfo()).thenReturn("/content/fisher-price/us/en-us/home/product/productfinder/laugh-learn-smart-stages-chair-bfk51");
		fisherPricePageModel.init();
	}
	@Test
	public void initTestPLPPageTemplate() {
		Mockito.when(currentPage.getPath()).thenReturn("/content/fisher-price/us/en-us/home/shop/category/");
		Mockito.when(currentPage.getProperties().get("sling:resourceType", String.class)).thenReturn("fp-productlisting-page");
		Mockito.when(FisherPriceHelper.getRelativePath("/content/fisher-price/us/en-us/home/shop/category/", resource))
				.thenReturn("/us/en-us");
		Mockito.when(request.getPathInfo()).thenReturn("/content/fisher-price/us/en-us/home/product/productfinder/laugh-learn-smart-stages-chair-bfk51");
		fisherPricePageModel.init();
	}
	@Test
	public void initTestSearchageTemplate() {
		Mockito.when(currentPage.getPath()).thenReturn("/content/fisher-price/us/en-us/home/search-results/");
		Mockito.when(currentPage.getProperties().get("sling:resourceType", String.class)).thenReturn("fp-productlisting-page");
		Mockito.when(resource.getPath()).thenReturn("/content/fisher-price/us/en-us/home/search-results/jcr:data");
		Mockito.when(FisherPriceHelper.getPageLocale("/content/fisher-price/us/en-us/home/search-results/jcr:data")).thenReturn("us/en-us");
		Mockito.when(FisherPriceHelper.getRelativePath("/content/fisher-price/us/en-us/home/search-results/", resource))
				.thenReturn("/us/en-us");
		Mockito.when(request.getPathInfo()).thenReturn("/content/fisher-price/us/en-us/home/product/productfinder/laugh-learn-smart-stages-chair-bfk51");
		fisherPricePageModel.init();
	}

	@Test
	public void testFetchParentPageType() throws Exception{
		Mockito.when(currentPage.getProperties().get("sling:resourceType", String.class))
				.thenReturn("fp-contentpage");
		Whitebox.invokeMethod(fisherPricePageModel, "fetchParentPageType", currentPage);
		Mockito.when(currentPage.getProperties().get("sling:resourceType", String.class))
				.thenReturn("fp-productdetail-page");
		Whitebox.invokeMethod(fisherPricePageModel, "fetchParentPageType", currentPage);
		Mockito.when(currentPage.getProperties().get("sling:resourceType", String.class))
				.thenReturn("fp-productlisting-page");
		Whitebox.invokeMethod(fisherPricePageModel, "fetchParentPageType", currentPage);
	}

	@Test
	public void testCheckClientLibCategory() throws Exception{
		Mockito.when(resource.getPath()).thenReturn("/content/mattel-play/fisher-price/us/en-us/rescue-heroes");
		Mockito.when(FisherPriceConfigurationUtils.getClientlibRootCategoryName()).thenReturn("clientlib.");
		Whitebox.invokeMethod(fisherPricePageModel, "checkClientLibCategory");
		Mockito.when(resource.getPath()).thenReturn("/content/mattel-play/fisher-price/us/en-us/home");
		Mockito.when(FisherPriceHelper.getParentBrandName(resource)).thenReturn("fisher-price1");
		Mockito.when(multifieldReader
				.checkIfClientLibExists(Constants.CLIENTLIB_DOT + FisherPriceHelper.getParentBrandName(resource))).thenReturn(true);
		Mockito.when(hierarchyNodeInheritanceValueMap.getInherited("clientlibCategory", String.class)).thenReturn(StringUtils.EMPTY);
		Whitebox.invokeMethod(fisherPricePageModel, "checkClientLibCategory");
	}

	@Test
	public void clientLibCategoryIsEmpty() {
		Mockito.when(resource.getPath()).thenReturn("/content/fisher-price/language-masters/en/home/jcr:content");
		Mockito.when(FisherPriceHelper.getRelativePath("/content/fisher-price/language-masters/en/home/", resource))
				.thenReturn("/en/");
		Mockito.when(multifieldReader.checkIfClientLibExists("clientlib.fisher-price")).thenReturn(true);
		Mockito.when(FisherPriceConfigurationUtils.getExpFragmentRootPath()).thenReturn("/content/experience-fragments/");
		Mockito.when(hierarchyNodeInheritanceValueMap.getInherited("clientlibCategory", String.class)).thenReturn(StringUtils.EMPTY);
		fisherPricePageModel.init();
	}

	@Test
	public void pagePathContainsProductFinder() {
		Mockito.when(resource.getPath()).thenReturn("/content/fisher-price/us/en-us/home/product/productfinder/laugh-learn-smart-stages-chair-bfk51");
		Mockito.when(request.getPathInfo()).thenReturn("/content/fisher-price/us/en-us/home/product/productfinder/laugh-learn-smart-stages-chair-bfk51");
		Mockito.when(currentPage.getAbsoluteParent(4)).thenReturn(currentPage);
		Mockito.when(currentPage.getName()).thenReturn("home");
		Mockito.when(request.getResourceResolver()).thenReturn(resolver);
		Mockito.when(ResourceUtil.isNonExistingResource(Mockito.any())).thenReturn(false,true);
		fisherPricePageModel.init();
		Mockito.when(resolver.getResource("/content/fisher-price/us/en-us/home/product/productfinder/laugh-learn-smart-stages-chair-bfk51".replace("/productfinder","").replace(".html", ""))).thenReturn(resource);
		fisherPricePageModel.init();
	}

	@Test
	public void testFetchTitleDetails() throws Exception{
		Mockito.when(currentPage.getPath()).thenReturn("/content/fisher-price/language-masters/fr_fr/home/explore");
		Whitebox.invokeMethod(fisherPricePageModel, "fetchtitleDetails", currentPage);
	}

	@Test
	public void getPropertiesJson() {
		fisherPricePageModel.getPropertiesJson();
	}

	@Test
	public void getTitle() {
		fisherPricePageModel.getTitle();
	}

	@Test
	public void getScriptUrl() {
		fisherPricePageModel.getScriptUrl();
	}

	@Test
	public void getKeywordCommaSeparated() {
		fisherPricePageModel.getKeywordCommaSeparated();
	}

	@Test
	public void getHrefLangList() {
		fisherPricePageModel.getHrefLangList();
	}

	@Test
	public void getClientlibCategory() {
		fisherPricePageModel.getClientlibCategory();
	}

	@Test
	public void getGameLandingGridPath() {
		fisherPricePageModel.getGameLandingGridPath();
	}

	@Test
	public void getCharacterCategoryFilterPath() {
		fisherPricePageModel.getCharacterCategoryFilterPath();
	}

	@Test
	public void getHeaderPath() {
		fisherPricePageModel.getHeaderPath();
	}

	@Test
	public void getFooterPath() {
		fisherPricePageModel.getFooterPath();
	}

	@Test
	public void getRetailerInterstitialPath() {
		fisherPricePageModel.getRetailerInterstitialPath();
	}

	@Test
	public void getProductThumbnailGridFragPath() {
		fisherPricePageModel.getProductThumbnailGridFragPath();
	}

	@Test
	public void setProductThumbnailGridFragPath() {
		fisherPricePageModel.setProductThumbnailGridFragPath("");
	}

	@Test
	public void getProductCategoryFilterPath() {
		fisherPricePageModel.getProductCategoryFilterPath();
	}

	@Test
	public void setProductCategoryFilterPath() {
		fisherPricePageModel.setProductCategoryFilterPath("");
	}

	@Test
	public void getProductDetailGridTitleFragPath() {
		fisherPricePageModel.getProductDetailGridTitleFragPath();
	}

	@Test
	public void setProductDetailGridTitleFragPath() {
		fisherPricePageModel.setProductDetailGridTitleFragPath("");
	}

	@Test
	public void getSiteCountry() {
		fisherPricePageModel.getSiteCountry();
	}

	@Test
	public void getLeavingInterstitialPath() {
		fisherPricePageModel.getLeavingInterstitialPath();
	}

	@Test
	public void getInterstitialApp() {
		fisherPricePageModel.getInterstitialApp();
	}

	@Test
	public void getBrandName() {
		fisherPricePageModel.getBrandName();
	}

	@Test
	public void setHeaderPath() {
		fisherPricePageModel.setHeaderPath("");
	}

	@Test
	public void setHomePagePath() {
		fisherPricePageModel.setHomePagePath("");
	}

	@Test
	public void getParentPageType() {
		fisherPricePageModel.getParentPageType();
	}

	@Test
	public void getPageName() {
		fisherPricePageModel.getPageName();
	}

	@Test
	public void getRescueBrandName() {
		fisherPricePageModel.getRescueBrandName();
	}

	@Test
	public void getPageLocale() {
		fisherPricePageModel.getPageLocale();
	}

	@Test
	public void getSiteSection() {
		fisherPricePageModel.getSiteSection();
	}

	@Test
	public void getHomePagePath() {
		fisherPricePageModel.getHomePagePath();
	}

	@Test
	public void getProductId() {
		fisherPricePageModel.getProductId();
	}

	@Test
	public void getSiteSubSection() {
		fisherPricePageModel.getSiteSubSection();
	}

	@Test
	public void getCurrentProductPath() {
		fisherPricePageModel.getCurrentProductPath();
	}

	@Test
	public void getPrimaryTag() {
		fisherPricePageModel.getPrimaryTag();
	}

	@Test
	public void getShortUrlSiteDomain() {
		fisherPricePageModel.getShortUrlSiteDomain();
	}
	@Test
	public void getCurrentPagePath() {
		fisherPricePageModel.getCurrentPagePath();
	}
	@Test
	public void getCanonicalUrl() {
		fisherPricePageModel.getCanonicalUrl();
	}
}
