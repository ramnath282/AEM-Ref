//package com.mattel.informational.core.models;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import javax.jcr.Node;
//import javax.jcr.RepositoryException;
//import javax.jcr.Session;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.sling.api.SlingHttpServletRequest;
//import org.apache.sling.api.request.RequestPathInfo;
//import org.apache.sling.api.resource.Resource;
//import org.apache.sling.api.resource.ResourceResolver;
//import org.apache.sling.api.resource.ValueMap;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.api.support.membermodification.MemberModifier;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//import org.powermock.reflect.Whitebox;
//
//import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
//import com.day.cq.search.QueryBuilder;
//import com.day.cq.search.result.Hit;
//import com.day.cq.search.result.SearchResult;
//import com.day.cq.wcm.api.Page;
//import com.day.cq.wcm.api.PageManager;
//import com.mattel.informational.core.helper.InformationalHelper;
//import com.mattel.informational.core.services.MultifieldReader;
//import com.mattel.informational.core.utils.InformationalConfigurationUtils;
//import com.mattel.informational.core.utils.InformationalUtils;
//
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({ InformationalHelper.class, InformationalConfigurationUtils.class, InformationalUtils.class, StringBuffer.class })
//public class InformationalPageModelTest {
//	@InjectMocks
//	private InformationalPageModel informationalPageModel;
//
//	@Mock
//	private MultifieldReader multifieldReader;
//	
//	@Mock
//	SlingHttpServletRequest request;
//
//	@Mock
//	private Resource resource;
//	
//	@Mock
//	private Page currentPage;
//	
//    @Mock
//    private RequestPathInfo pathInfo;
//
//	@SuppressWarnings({ "unchecked" })
//	@Before
//	public void setup() throws RepositoryException, Exception {
//		PowerMockito.mockStatic(InformationalHelper.class);
//		PowerMockito.mockStatic(InformationalConfigurationUtils.class);
//		PowerMockito.mockStatic(InformationalUtils.class);
//		PowerMockito.mockStatic(StringBuffer.class);
//		Mockito.when(resource.getPath()).thenReturn(
//				"/content/mattel/mattel-corporate/us/en-us/home/jcr:content");
//		Mockito.when(InformationalHelper.leftIndexShiftForSiteWOParentName(resource)).thenReturn(0);
//		Mockito.when(InformationalHelper.getPageLocale(resource.getPath())).thenReturn("");
//		Mockito.when(InformationalHelper.getBrandName(resource)).thenReturn("mattel-corporate");
//		ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class);
//		Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
//		Resource analyticsNodeResource = Mockito.mock(Resource.class);
//		Mockito.when(resourceResolver.getResource(
//				"/content/mattel/mattel-corporate/us/en-us/home/jcr:content/analyticsProperties"))
//				.thenReturn(analyticsNodeResource);
//		Node analyticsNode = Mockito.mock(Node.class);
//		Mockito.when(analyticsNodeResource.adaptTo(Node.class)).thenReturn(analyticsNode);
//		Map<String, ValueMap> stringValueMapLinkedHashMap = Mockito.mock(Map.class);
//		ValueMap vm = Mockito.mock(ValueMap.class);
//		vm.put("propertyName", "propertyName");
//		vm.put("propertyValue", "propertyValue");
//		stringValueMapLinkedHashMap.put("prop1", vm);
//		Mockito.when(multifieldReader.propertyReader(analyticsNode)).thenReturn(stringValueMapLinkedHashMap);
//		Mockito.when(InformationalConfigurationUtils.getScriptUrl())
//				.thenReturn("//assets.adobedtm.com/9ddfa3252d66/e73483a0a4d5/launch-3257fd3abc82-development.min.js");
//
//		PageManager pageManager = Mockito.mock(PageManager.class);
//		Mockito.when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
//		Mockito.when(pageManager.getPage("path")).thenReturn(currentPage);
//		Mockito.when(pageManager.getContainingPage(resource)).thenReturn(currentPage);
//		Page homepage = Mockito.mock(Page.class);
//		Mockito.when(currentPage.getAbsoluteParent(5)).thenReturn(homepage);
//		Mockito.when(homepage.getPath()).thenReturn("/content/mattel/mattel-corporate/us/en-us/home");
//		Mockito.when(currentPage.getDepth()).thenReturn(8);
//		Page lvl6Page = Mockito.mock(Page.class);
//		Mockito.when(currentPage.getAbsoluteParent(6)).thenReturn(lvl6Page);
//		Mockito.when(lvl6Page.getName()).thenReturn("about");
//		Page lvl7Page = Mockito.mock(Page.class);
//		Mockito.when(currentPage.getAbsoluteParent(6)).thenReturn(lvl7Page);
//		Mockito.when(lvl7Page.getName()).thenReturn("executive-leadership");
//		Resource seoKeywordsNodeResource = Mockito.mock(Resource.class);
//		ValueMap prop = Mockito.mock(ValueMap.class);
//		Mockito.when(resource.adaptTo(ValueMap.class)).thenReturn(prop);
//		Mockito.when(prop.get("seoTitle", "")).thenReturn("Executive Leadership");
//		Resource homePageResource = Mockito.mock(Resource.class);
//		Mockito.when(
//				resourceResolver.getResource("/content/mattel/mattel-corporate/us/en-us/home//jcr:content"))
//				.thenReturn(homePageResource);
//		ValueMap homepageProp = Mockito.mock(ValueMap.class);
//		Mockito.when(homePageResource.adaptTo(ValueMap.class)).thenReturn(homepageProp);
//
//		Mockito.when(resourceResolver.getResource(
//				"/content/mattel/mattel-corporate/us/en-us/home/jcr:content/metaKeywords"))
//				.thenReturn(seoKeywordsNodeResource);
//		Node seoNode = Mockito.mock(Node.class);
//		Mockito.when(seoKeywordsNodeResource.adaptTo(Node.class)).thenReturn(seoNode);
//		Map<String, ValueMap> seoLinkedHashMap = Mockito.mock(Map.class);
//		ValueMap seoVm = Mockito.mock(ValueMap.class);
//		seoVm.put("keyword", "keyword1,keyword2");
//		seoLinkedHashMap.put("prop", seoVm);
//		Mockito.when(multifieldReader.propertyReader(seoNode)).thenReturn(seoLinkedHashMap);
//
//		Mockito.when(InformationalUtils.getCurrentBrandExpFragmentRootPath("mattel-corporate", "experience-fragments",
//				"/content/mattel/mattel-corporate/us/en-us/home/jcr:content"))
//				.thenReturn("/content/experience-fragments/mattel/");
//		Mockito.when(InformationalConfigurationUtils.getHeaderExpFragmentName()).thenReturn("header");
//		Mockito.when(InformationalConfigurationUtils.getRetailerInterstitialPath()).thenReturn("retailer");
//		Mockito.when(InformationalConfigurationUtils.getLeavingInterstitialPath()).thenReturn("leaving");
//		Mockito.when(InformationalConfigurationUtils.getInterstitialApp()).thenReturn("app");
//
//		Mockito.when(InformationalConfigurationUtils.getRootErrorPageName()).thenReturn("/error/");
//
//		Mockito.when(InformationalHelper.getRelativePath(
//				"/content/mattel/mattel-corporate/us/en-us/home/", resource))
//				.thenReturn("/us/en-us");
//
//		Session session = Mockito.mock(Session.class);
//		Mockito.when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
//		QueryBuilder queryBuilder = Mockito.mock(QueryBuilder.class);
//		Mockito.when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
//		SearchResult result = Mockito.mock(SearchResult.class);
//
//		Mockito.when(InformationalHelper.getCountryNodesByLanguage("/content/mattel/mattel-corporate", session,
//				queryBuilder)).thenReturn(result);
//
//		Hit hit = Mockito.mock(Hit.class);
//		List<Hit> hits = new ArrayList<>();
//		hits.add(hit);
//		Mockito.when(result.getHits()).thenReturn(hits);
//		Mockito.when(hit.getPath()).thenReturn("path");
//
//		Iterator<Resource> resources = Mockito.mock(Iterator.class);
//		Mockito.when(result.getResources()).thenReturn(resources);
//		Mockito.when(resources.hasNext()).thenReturn(true, false);
//		Resource dummyresource = Mockito.mock(Resource.class);
//		Mockito.when(resources.next()).thenReturn(dummyresource);
//		Mockito.when(dummyresource.getResourceResolver()).thenReturn(resourceResolver);
//
//		HierarchyNodeInheritanceValueMap inheritanceValueMap = Mockito.mock(HierarchyNodeInheritanceValueMap.class);
//		Mockito.when(new HierarchyNodeInheritanceValueMap(resource)).thenReturn(inheritanceValueMap);
//		Mockito.when(inheritanceValueMap.getInherited("clientlibCategory", String.class))
//				.thenReturn("clientlibCategory");
//
//		Mockito.when(InformationalHelper.getRelativePath(
//				"/content/mattel/mattel-corporate/us/en-us/home", resource))
//				.thenReturn("/us/en-us");
//
//		ValueMap currentPageProp = Mockito.mock(ValueMap.class);
//		Mockito.when(currentPage.getProperties()).thenReturn(currentPageProp);
//		Mockito.when(currentPageProp.get("sling:resourceType", String.class)).thenReturn("pageResourceType");
//		MemberModifier.field(InformationalPageModel.class, "request").set(informationalPageModel, request);
//		Mockito.when(request.getRequestURL().toString()).thenReturn("http://localhost:4502/content/mattel/mattel-corporate/us/en-us/home/news/american-girl-unveils-its-wow-gifts-ahead-of-the-holiday-season.html");
//	    Mockito.when(request.getRequestURI()).thenReturn("/content/mattel/mattel-corporate/us/en-us/home/news/american-girl-unveils-its-wow-gifts-ahead-of-the-holiday-season.html");
//	    System.out.println(request.getRequestURL());
//	    Mockito.when(request.getRequestURL().toString().replace(request.getRequestURI(), "")).thenReturn("http://localhost:4502/");
//	    MockitoAnnotations.initMocks(this);
//	}
//
//	@Test
//	public void test() {
//		Mockito.when(currentPage.getPath())
//		.thenReturn("/content/mattel/mattel-corporate/us/en-us/home");
//		informationalPageModel.init();
//	}
//
//    @Test
//	public void testForLangMast() {
//		try {
//			Whitebox.invokeMethod(informationalPageModel, "getExpFragmentPathDetails",
//					"/content/mattel/mattel-corporate/language-masters/en/home/about/executive-leadership/", "/us/en-us", StringUtils.EMPTY, "header");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testWithAboutPage() {
//		currentPage = Mockito.mock(Page.class);
//		Mockito.when(currentPage.getPath()).thenReturn("/content/mattel/mattel-corporate/us/en-us/home/about");
//		try {
//			Whitebox.invokeMethod(informationalPageModel, "fetchtitleDetails", currentPage);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//  @Test
//  public void testFetchFallbackImage() {
//    try {
//      Whitebox.invokeMethod(informationalPageModel, "fetchFallbackImage", Mockito.mock(Resource.class));
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//  }
//
//	@Test
//	public void getTitle() {
//		informationalPageModel.getTitle();
//	}
//
//	@Test
//	public void getScriptUrl() {
//		informationalPageModel.getScriptUrl();
//	}
//
//	@Test
//	public void getKeywordCommaSeparated() {
//		informationalPageModel.getKeywordCommaSeparated();
//	}
//
//	@Test
//	public void getHrefLangList() {
//		informationalPageModel.getHrefLangList();
//	}
//
//	@Test
//	public void getClientlibCategory() {
//		informationalPageModel.getClientlibCategory();
//	}
//
//	@Test
//	public void getHeaderPath() {
//		informationalPageModel.getHeaderPath();
//	}
//
//	@Test
//	public void getFooterPath() {
//		informationalPageModel.getFooterPath();
//	}
//
//	@Test
//	public void getRetailerInterstitialPath() {
//		informationalPageModel.getRetailerInterstitialPath();
//	}
//
//	@Test
//	public void getLeavingInterstitialPath() {
//		informationalPageModel.getLeavingInterstitialPath();
//	}
//
//	@Test
//	public void getInterstitialApp() {
//		informationalPageModel.getInterstitialApp();
//	}
//
//	@Test
//	public void getBrandName() {
//		informationalPageModel.getBrandName();
//	}
//
//	@Test
//	public void getParentPageType() {
//		informationalPageModel.getParentPageType();
//	}
//
//	@Test
//	public void getPageName() {
//		informationalPageModel.getPageName();
//	}
//
//	@Test
//	public void getSiteSection() {
//		informationalPageModel.getSiteSection();
//	}
//
//	@Test
//	public void getHomePagePath() {
//		informationalPageModel.getHomePagePath();
//	}
//
//	@Test
//	public void getSiteSubSection() {
//		informationalPageModel.getSiteSubSection();
//	}
//
//	@Test
//	public void getCurrentPagePath() {
//		informationalPageModel.getCurrentPagePath();
//	}
//	
//	@Test
//	public void getSiteCountry() {
//		informationalPageModel.getSiteCountry();
//	}
//	
//	@Test
//    public void setHeaderPath() {
//        informationalPageModel.setHeaderPath("path");
//    }
//	
//	@Test
//    public void setHomePagePath() {
//        informationalPageModel.setHomePagePath("homePagePath");
//    }
//
//}
