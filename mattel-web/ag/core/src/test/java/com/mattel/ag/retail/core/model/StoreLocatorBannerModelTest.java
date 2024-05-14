package com.mattel.ag.retail.core.model;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

/**
 * @author CTS. A Model class for Store Locator Banner
 */

public class StoreLocatorBannerModelTest {

	StoreLocatorBannerModel storeLocatorBannerModel;
	
	Resource resource;
	ResourceResolver resolver;
	PageManager pageManager;
	Page page;
	ValueMap valueMap;

	@Before
	public void setUp() {
		
		storeLocatorBannerModel = new StoreLocatorBannerModel();
		
		resource = Mockito.mock(Resource.class);
		storeLocatorBannerModel.setResource(resource);
		resolver = Mockito.mock(ResourceResolver.class);
		pageManager = Mockito.mock(PageManager.class);
		page = Mockito.mock(Page.class);
		valueMap = Mockito.mock(ValueMap.class);
	}

	@Test
	public void initTest() {
		Object setVal = "/conf/ag/settings/wcm/templates/retail-template-store-page";
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(pageManager.getContainingPage(resource)).thenReturn(page);
		Mockito.when(page.getProperties()).thenReturn(valueMap);
		Mockito.when(valueMap.get("cq:template")).thenReturn(setVal);
		Mockito.when(valueMap.get("storeLocation")).thenReturn(setVal);
		storeLocatorBannerModel.init();
	}
	
	@Test
	public void TestNullPageManager() {
		PageManager pageManager1 = null;
		Object setVal = "/conf/ag/settings/wcm/templates/retail-template-store-page";
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.adaptTo(PageManager.class)).thenReturn(pageManager1);
		Mockito.when(pageManager.getContainingPage(resource)).thenReturn(page);
		storeLocatorBannerModel.init();
	}
	
	@Test
	public void TestStoreLocationNull() {
		Object setVal = "/conf/ag/settings/wcm/templates/retail-template-store-page";
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(pageManager.getContainingPage(resource)).thenReturn(page);
		Mockito.when(page.getProperties()).thenReturn(valueMap);
		Mockito.when(valueMap.get("cq:template")).thenReturn(setVal);
		Mockito.when(valueMap.get("storeLocation")).thenReturn(null);
		storeLocatorBannerModel.init();
	}
	
	@Test
	public void TestTemplateNotEqual() {
		Object setVal = "/conf/ag/settings/wcm/templates/retail-template-store-page1";
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(pageManager.getContainingPage(resource)).thenReturn(page);
		Mockito.when(page.getProperties()).thenReturn(valueMap);
		Mockito.when(valueMap.get("cq:template")).thenReturn(setVal);
		Mockito.when(valueMap.get("storeLocation")).thenReturn(setVal);
		storeLocatorBannerModel.init();
	}

}
