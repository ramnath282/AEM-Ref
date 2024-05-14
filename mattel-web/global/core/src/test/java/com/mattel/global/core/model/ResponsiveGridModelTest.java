package com.mattel.global.core.model;

import static org.junit.Assert.assertEquals;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.global.core.utils.GlobalUtils;

@PrepareForTest(GlobalUtils.class)
@RunWith(PowerMockRunner.class)
public class ResponsiveGridModelTest {

	@InjectMocks
	private ResponsiveGridModel responsiveGridModel;

	@Mock
	private Resource resource;
	
	@Mock
	private ResourceResolver resourceResolver;

	private String image = "image";
	private String navigationLink = "navigationLink";
	private String openNavLinkIn = "openNavLinkIn";
	private String backgroundImagePath = "backgroundImagePath";

	@Before
	public void setup() throws Exception {
		PowerMockito.mockStatic(GlobalUtils.class);
		MemberModifier.field(ResponsiveGridModel.class, "resource").set(responsiveGridModel, resource);
		MemberModifier.field(ResponsiveGridModel.class, "image").set(responsiveGridModel, image);
		MemberModifier.field(ResponsiveGridModel.class, "navigationLink").set(responsiveGridModel, navigationLink);
		MemberModifier.field(ResponsiveGridModel.class, "backgroundImagePath").set(responsiveGridModel, backgroundImagePath);
		MemberModifier.field(ResponsiveGridModel.class, "openNavLinkIn").set(responsiveGridModel, openNavLinkIn);
		Mockito.when(GlobalUtils.checkLink(navigationLink, resource)).thenReturn(navigationLink);
		Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
		Mockito.when(GlobalUtils.setBackgroundPath(resourceResolver,image)).thenReturn(backgroundImagePath);
		responsiveGridModel.init();
	}

	@Test
	public void testGettersSetters() {
		assertEquals(backgroundImagePath,responsiveGridModel.getBackgroundImagePath());
		assertEquals(navigationLink,responsiveGridModel.getNavigationLink());
		assertEquals(openNavLinkIn, responsiveGridModel.getOpenNavLinkIn());
	}

}
