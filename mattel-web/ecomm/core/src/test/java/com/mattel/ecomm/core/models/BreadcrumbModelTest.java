package com.mattel.ecomm.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
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
import com.mattel.ecomm.core.helper.EcommHelper;

@RunWith(PowerMockRunner.class)
@PrepareForTest({EcommHelper.class})
public class BreadcrumbModelTest {
	@InjectMocks
	private BreadcrumbModel breadcrumbModel;
	@Mock
	private Resource resource;
	@Mock
	private Page currentPage;
	String alignBreadcrumb;
	PageManager pageManager;
	Page tempPage;
	ValueMap currentPageProperties;
	ValueMap tempPageProperties;

	@Before
	public void setup() throws Exception {
		MemberModifier.field(BreadcrumbModel.class, "resource").set(breadcrumbModel, resource);
		pageManager = Mockito.mock(PageManager.class);
		tempPage = Mockito.mock(Page.class);
		currentPageProperties = Mockito.mock(ValueMap.class);
		currentPageProperties.put("adobeTrackingNameForPage", "baby Sleepers and Bassinets");
		currentPageProperties.put("sling:resourceType",
				"mattel/ecomm/fisher-price/components/structure/fp-productdetail-page");
		tempPageProperties = Mockito.mock(ValueMap.class);
		tempPageProperties.put("adobeTrackingNameForPage", "baby-gear");
		Mockito.when(resource.getPath()).thenReturn(
				"/content/fisher-price/us/en-us/home/category/baby-gear/baby-sleepers-bassinets/on-the-baby-dome-dr13");
		Mockito.when(pageManager.getContainingPage(resource)).thenReturn(currentPage);
		Mockito.when(pageManager.getContainingPage(resource).getPath()).thenReturn(
				"/content/fisher-price/us/en-us/home/category/baby-gear/baby-sleepers-bassinets/on-the-baby-dome-dr13");
		Mockito.when(currentPage.getProperties()).thenReturn(currentPageProperties);
		Mockito.when(currentPage.getProperties().get("adobeTrackingNameForPage", String.class))
				.thenReturn("baby Sleepers and Bassinets");
		Mockito.when(currentPage.getProperties().get("sling:resourceType", String.class))
				.thenReturn("mattel/ecomm/fisher-price/components/structure/fp-productdetail-page");
		Mockito.when(currentPage.getDepth()).thenReturn(7);
		Mockito.when(currentPage.getAbsoluteParent(4)).thenReturn(tempPage);
		Mockito.when(currentPage.getAbsoluteParent(5)).thenReturn(tempPage);
		Mockito.when(currentPage.getAbsoluteParent(6)).thenReturn(tempPage);
		Mockito.when(currentPage.getAbsoluteParent(7)).thenReturn(tempPage);
		Mockito.when(currentPage.getName()).thenReturn("on-the-baby-dome-dr13");
		Mockito.when(tempPage.getPath()).thenReturn("/content/fisher-price/us/en-us/home/category/baby-gear");
		Mockito.when(tempPage.getName()).thenReturn("baby-gear");
		PowerMockito.mockStatic(EcommHelper.class);		
		Mockito.when(currentPage.getTitle()).thenReturn("BabyGear");
		PowerMockito.when(EcommHelper.convertSpecialCharacters("BabyGear")).thenReturn("BabyGear");
		Mockito.when(currentPage.getNavigationTitle()).thenReturn("Baby Gear");
		Mockito.when(tempPage.getTitle()).thenReturn("BabyGear");
		Mockito.when(tempPage.getNavigationTitle()).thenReturn("Baby Gear");
		Mockito.when(tempPage.getProperties()).thenReturn(tempPageProperties);
		Mockito.when(tempPage.getProperties().get("adobeTrackingNameForPage", String.class))
				.thenReturn("baby Sleepers and Bassinets");
	}

	@Test
	public void init() {
		breadcrumbModel.init();
	}

	@Test
	public void getBreadcrumbLinks() {
		breadcrumbModel.getBreadcrumbLinks();
	}

	@Test
	public void getAlignBreadcrumb() {
		breadcrumbModel.getAlignBreadcrumb();
	}

	@Test
	public void getResource() {
		breadcrumbModel.getResource();
	}
}
