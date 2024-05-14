package com.mattel.ecomm.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.global.core.pojo.SiteNavigationPojo;
import com.mattel.global.core.utils.NavigationUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ NavigationUtil.class, EcommHelper.class })
public class BrandAndCategoryListingModelTest {
	@InjectMocks
	private BrandAndCategoryListingModel brandAndCategoryListingModel;
	@Mock
	private SlingHttpServletRequest request;
	@Mock
	private Resource resource;
	@Mock
	private ResourceResolver resolver;
	Resource childResource;
	PageManager pageManager;
	String listFrom = "static";
	Page currentPage;
	PageFilter pageFilter;
	String[] pagesList = new String[4];
	NavigationUtil navigationService;
	SiteNavigationPojo siteNavigationPojo;
	String title = "page";
	String path = "/content/fisher-price/language-masters/en/home/category/baby-gear";
	ValueMap pageProperties;

	@Before
	public void setup() throws Exception {
		navigationService = Mockito.mock(NavigationUtil.class);
		MemberModifier.field(BrandAndCategoryListingModel.class, "resource").set(brandAndCategoryListingModel,
				resource);
		MemberModifier.field(BrandAndCategoryListingModel.class, "request").set(brandAndCategoryListingModel, request);
		MemberModifier.field(BrandAndCategoryListingModel.class, "resolver").set(brandAndCategoryListingModel,
				resolver);
		brandAndCategoryListingModel.setListFrom(listFrom);
		currentPage = Mockito.mock(Page.class);
		pageManager = Mockito.mock(PageManager.class);
		pageFilter = Mockito.mock(PageFilter.class);
		siteNavigationPojo = new SiteNavigationPojo();
		siteNavigationPojo.setName("simple");
		childResource = resource;
		
		brandAndCategoryListingModel.setNavigationService(navigationService);
		
		pagesList[0] = "/content/fisher-price/language-masters/en/home/category/baby-gear";
		pagesList[1] = "/content/fisher-price/language-masters/en/home/category/baby-toddler-toys";
		pagesList[2] = "/content/fisher-price/language-masters/en/home/category/more-toys";
		pagesList[3] = "/content/fisher-price/language-masters/en/home/category/baby-gear";
		brandAndCategoryListingModel.setPageList(pagesList);
		Mockito.when(resource.getPath()).thenReturn("/content/fisher-price/us/en-us/home/category");
		Mockito.when(request.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(pageManager.getContainingPage(resource)).thenReturn(currentPage);
		Mockito.when(resolver.getResource(pagesList[0])).thenReturn(resource);
		Mockito.when(childResource.adaptTo(Page.class)).thenReturn(currentPage);
		Mockito.when(navigationService.fetchPageProperties(currentPage)).thenReturn(siteNavigationPojo);
		
	}

	@Test
	public void init() {
		brandAndCategoryListingModel.init();
	}

	@Test
	public void getPagesList() {
		brandAndCategoryListingModel.getPagesList();
	}
}
