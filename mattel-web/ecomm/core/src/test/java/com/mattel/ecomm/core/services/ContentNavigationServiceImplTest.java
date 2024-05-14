package com.mattel.ecomm.core.services;

import java.util.LinkedList;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.global.core.pojo.PromoImagePojo;
import com.mattel.global.core.pojo.SiteNavigationPojo;
import com.mattel.global.core.utils.NavigationUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ NavigationUtil.class, EcommHelper.class })
public class ContentNavigationServiceImplTest {
	
	@InjectMocks
	private ContentNavigationServiceImpl contentNavigationServiceImpl;
	@Mock
	private Resource resource;
	String[] navigationalLinks = new  String[]{"hello","hello"};
	List<SiteNavigationPojo> categorySectionNavLinks = new LinkedList<>();
	List<SiteNavigationPojo> secondaryNavLinks = new LinkedList<>();
	List<PromoImagePojo> imageSectionList = new LinkedList<>();
	ValueMap nodeValues;
	NavigationUtil navigationService;
	
	@Before
	public void setup() throws Exception {
		navigationService = Mockito.mock(NavigationUtil.class);
		nodeValues = Mockito.mock(ValueMap.class);
		nodeValues.put("displayShopByValue", true);
		nodeValues.put("promoHoverImgPath","/content/fisher-price/us/en-us/home/shop/brands/linkimals.png");
		Mockito.when(resource.adaptTo(ValueMap.class)).thenReturn(nodeValues);
		Mockito.when(nodeValues.containsKey("displayShopByValue")).thenReturn(true);
		Mockito.when(nodeValues.get("displayShopByValue", Boolean.class)).thenReturn(true);
		Mockito.when(nodeValues.get("promoHoverImgPath", String.class)).thenReturn("/content/fisher-price/us/en-us/home/shop/brands/linkimals.png");
		contentNavigationServiceImpl.setNavigationService(navigationService);
		SiteNavigationPojo primaryNavPojo = new SiteNavigationPojo();
		Mockito.when(navigationService.setCategoryDetails(primaryNavPojo, resource, true, "primaryNavTitleLink",
				"primaryNavigationTitle", "linkTargetPrimaryNav", "device")).thenReturn(primaryNavPojo);
		Mockito.when(navigationService.getTemplateVariationType(primaryNavPojo, "templateType", "aeForPrimaryNavTitle",true,"device")).thenReturn(primaryNavPojo);
		Mockito.when(navigationService.getSecondaryCategoryTitle(resource, navigationalLinks, "device", "categoryTitle", 
				categorySectionNavLinks, secondaryNavLinks, "templateType", "/content/fisher-price/us/en-us/home/shop/brands/linkimals.png")).thenReturn(secondaryNavLinks);
		Mockito.when(navigationService.getImageSection(resource, imageSectionList, 4)).thenReturn(imageSectionList);
	}

	@Test
	public void processNavLinks() {
		contentNavigationServiceImpl.processNavLinks("String", resource);
	}
}
