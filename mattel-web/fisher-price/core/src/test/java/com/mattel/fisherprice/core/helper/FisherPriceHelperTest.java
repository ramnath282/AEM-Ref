package com.mattel.fisherprice.core.helper;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.fisherprice.core.constants.Constants;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ StringUtils.class })
public class FisherPriceHelperTest {
	
	FisherPriceHelper fisherPriceHelper = new FisherPriceHelper();
	Resource resource;
	ResourceResolver resourceResolver;
	PageManager pageManager;
	Page page;
	
	@Before
	public void setUp() {
		
		resource = Mockito.mock(Resource.class);
		resourceResolver = Mockito.mock(ResourceResolver.class);
		pageManager = Mockito.mock(PageManager.class);
		page = Mockito.mock(Page.class);

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
		Mockito.when(FisherPriceHelper.getBrandName(resource)).thenReturn("rescue-heros");
		Mockito.when(pageManager.getContainingPage(resource)).thenReturn(page);
		Mockito.when(page.getDepth()).thenReturn(8);
		Mockito.when(page.getAbsoluteParent(5)).thenReturn(page);
		Mockito.when(page.getAbsoluteParent(5).getName()).thenReturn("experience-fragment");
		Mockito.when(StringUtils.isNotBlank("/experience-fragment/rescue-hero")).thenReturn(false);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void initTest() throws Exception {
		Mockito.when(StringUtils.isNotBlank("dam/")).thenReturn(true);
		fisherPriceHelper.checkLink("/content/#dam/#india", resource);
	}
	@SuppressWarnings("static-access")
	@Test
	public void initTest1() throws Exception {
		fisherPriceHelper.leftIndexShiftForSiteWOParentName(resource);
	}
	@SuppressWarnings("static-access")
	@Test
	public void checkLink() { 
		fisherPriceHelper.checkLink("/content/#dam/#india", resource);
	}
	@SuppressWarnings("static-access")
	@Test
	public void getBrandName() {
		fisherPriceHelper.getBrandName(resource);
	}
	@SuppressWarnings("static-access")
	@Test
	public void getParentBrandName() {
		fisherPriceHelper.getParentBrandName(resource);
	}
	@SuppressWarnings("static-access")
	@Test
	public void getPageLocale() {
		fisherPriceHelper.getPageLocale("/content/experience-fragment/rescue-heros/hero/india");
	}
	@SuppressWarnings("static-access")
	@Test
	public void getRelativePath() {
		fisherPriceHelper.getRelativePath("/content/experience-fragment/rescue-heros", resource);
	}

	
	@SuppressWarnings("static-access")
	@Test
	public void testGetBrandOrParentBrandName() {		
		Mockito.when(resource.getPath()).thenReturn("/content/experience-fragments/fisher-price/rescue-heroes/en/header/header_en-us/jcr:content");
		assertEquals("rescue-heroes", fisherPriceHelper.getBrandName(resource));		
		Mockito.when(resource.getPath()).thenReturn("/content/experience-fragments/fisher-price/en/header/header_en-us/jcr:content");
		fisherPriceHelper.getParentBrandName(resource);				
		assertEquals("fisher-price", fisherPriceHelper.getBrandName(resource));	
		
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testcheckLinkWithDamPath() {		
		fisherPriceHelper.checkLink("/content/dam/india", resource);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testCheckLinkWithContentPath() {
		Mockito.when(resourceResolver.map("/content/fisher-price/us/en-us/home")).thenReturn("/content/fisher-price/us/en-us/home");
		fisherPriceHelper.checkLink("/content/fisher-price/us/en-us/home", resource);
	}

	@Test
	public void getPathURL() {
		fisherPriceHelper.getPathURL();
	}
}
	
