package com.mattel.ecomm.core.models;

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

import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.global.core.utils.NavigationUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ NavigationUtil.class, EcommHelper.class })
public class CategoryNavigationModelTest {
	@InjectMocks
	private CategoryNavigationModel categoryNavigationModel;
	@Mock
	private Resource resource;
	@Mock
	private ResourceResolver resolver;
	ValueMap nodeValues;
	NavigationUtil navigationService;

	@Before
	public void setup() throws Exception {
		navigationService = Mockito.mock(NavigationUtil.class);
		MemberModifier.field(CategoryNavigationModel.class, "resource").set(categoryNavigationModel, resource);
		MemberModifier.field(CategoryNavigationModel.class, "resolver").set(categoryNavigationModel, resolver);
		nodeValues = Mockito.mock(ValueMap.class);
		nodeValues.put("displayShopByValue", true);
		Mockito.when(resource.adaptTo(ValueMap.class)).thenReturn(nodeValues);
		Mockito.when(nodeValues.containsKey("displayShopByValue")).thenReturn(true);
		categoryNavigationModel.setNavigationUtil(navigationService);
	}

	@Test
	public void init() {
		categoryNavigationModel.init();
	}

	@Test
	public void getCategoryNavLinks() {
		categoryNavigationModel.getCategoryNavLinks();
	}

	@Test
	public void getFeaturedLinksList() {
		categoryNavigationModel.getFeaturedLinksList();
	}

	@Test
	public void getImageSectionList() {
		categoryNavigationModel.getImageSectionList();
	}

	@Test
	public void getHeaderShopByValue() {
		categoryNavigationModel.getHeaderShopByValue();
	}

	@Test
	public void getDevice() {
		categoryNavigationModel.getDevice();
	}

	@Test
	public void getResource() {
		categoryNavigationModel.getResource();
	}
}
