package com.mattel.global.core.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
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
import com.mattel.global.core.pojo.NavigationParentPagePojo;
import com.mattel.global.core.services.SiteNavigationService;
import com.mattel.global.core.utils.GlobalUtils;

@PrepareForTest(GlobalUtils.class)
@RunWith(PowerMockRunner.class)
public class NavigationHeaderModelTest {

	@InjectMocks
	private NavigationHeaderModel navigationHeaderModel;

	@Mock
	private SiteNavigationService siteNavigationService;

	@Mock
	private ResourceResolver resolver;

	@Mock
	private SlingHttpServletRequest request;

	@Mock
	private Resource resource;

	@Mock
	private Resource rootPageResource;

	@Mock
	private Page rootPage;

	List<NavigationParentPagePojo> navItemsList = new ArrayList<>();

	@Before
	public void setup() throws Exception {

		PowerMockito.mockStatic(GlobalUtils.class);

		MemberModifier.field(NavigationHeaderModel.class, "resource").set(navigationHeaderModel, resource);
		MemberModifier.field(NavigationHeaderModel.class, "pageUrl").set(navigationHeaderModel, "pageUrl");
		MemberModifier.field(NavigationHeaderModel.class, "brandNavUrl").set(navigationHeaderModel, "brandNavUrl");
		MemberModifier.field(NavigationHeaderModel.class, "linkUrl").set(navigationHeaderModel, "linkUrl");
		MemberModifier.field(NavigationHeaderModel.class, "collectRootPage").set(navigationHeaderModel, true);
		MemberModifier.field(NavigationHeaderModel.class, "depth").set(navigationHeaderModel, "1");

		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.getResource("pageUrl")).thenReturn(rootPageResource);
		Mockito.when(rootPageResource.adaptTo(Page.class)).thenReturn(rootPage);
		Mockito.when(request.getPathInfo()).thenReturn("/content/mattel/mattel-corporate/us/en-us/home");
		Mockito.when(GlobalUtils.checkLink("brandNavUrl",resource)).thenReturn("brandNavUrl");
		Mockito.when(GlobalUtils.checkLink("linkUrl",resource)).thenReturn("linkUrl");

		Mockito.when(siteNavigationService.getNavigationDetails(rootPage, 2, true, "/content/mattel/mattel-corporate/us/en-us/home")).thenReturn(navItemsList);
	}

	@Test
	public void testInit() {
		navigationHeaderModel.init();
	}

	@Test
	public void testGettersSetters() {
		navigationHeaderModel.setTileGalleryAndLandingService(siteNavigationService);
		navigationHeaderModel.setResource(resource);
		assertEquals("pageUrl",navigationHeaderModel.getPageUrl());
		assertEquals("brandNavUrl",navigationHeaderModel.getBrandNavUrl());
		assertEquals("linkUrl", navigationHeaderModel.getLinkUrl());
		assertEquals(navItemsList, navigationHeaderModel.getNavItemsList());
	}

}
