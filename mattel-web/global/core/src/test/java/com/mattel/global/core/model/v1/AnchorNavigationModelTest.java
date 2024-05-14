package com.mattel.global.core.model.v1;

import java.util.Iterator;

import org.apache.sling.api.resource.Resource;
import org.junit.Assert;
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

import com.mattel.global.core.utils.GlobalUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GlobalUtils.class)
public class AnchorNavigationModelTest {

	@InjectMocks
	private AnchorNavigationModel anchorNavigationModel;

	@Mock
	Resource resource;

	@Mock
	Resource contentResource;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() throws IllegalArgumentException, IllegalAccessException {
		PowerMockito.mockStatic(GlobalUtils.class);
		resource = Mockito.mock(Resource.class);
		anchorNavigationModel.setResource(resource);
		Iterator<Resource> resourceList = Mockito.mock(Iterator.class);
		Mockito.when(resource.listChildren()).thenReturn(resourceList);
		Mockito.when(resourceList.hasNext()).thenReturn(true,false);
		Resource anchorModelResource = Mockito.mock(Resource.class);
		Mockito.when(resourceList.next()).thenReturn(anchorModelResource);
		Mockito.when(anchorModelResource.getResourceType()).thenReturn("/xyz/ctaItem");
		AnchorModel anchorModel = Mockito.mock(AnchorModel.class);
		Mockito.when(anchorModelResource.adaptTo(AnchorModel.class)).thenReturn(anchorModel);
		Mockito.when(anchorModel.getAnchorID()).thenReturn("Anchor Id");
		Mockito.when(anchorModel.getAnchorName()).thenReturn("Anchor Name");
		Mockito.when(anchorModel.getAnchorNavUrl()).thenReturn("anchornavurl");
		Mockito.when(GlobalUtils.checkLink("anchornavurl", resource)).thenReturn("anchornavurl");
		MemberModifier.field(AnchorNavigationModel.class, "backgroundColor").set(anchorNavigationModel, "backgroundColor");
		MemberModifier.field(AnchorNavigationModel.class, "backgroundImage").set(anchorNavigationModel, "backgroundImage");
		MemberModifier.field(AnchorNavigationModel.class, "bgType").set(anchorNavigationModel, "bgType");
		MemberModifier.field(AnchorNavigationModel.class, "logoAlt").set(anchorNavigationModel, "logoAlt");
		MemberModifier.field(AnchorNavigationModel.class, "logoNavUrl").set(anchorNavigationModel, "logoNavUrl");
		MemberModifier.field(AnchorNavigationModel.class, "navigationLogo").set(anchorNavigationModel, "navigationLogo");
		MemberModifier.field(AnchorNavigationModel.class, "mobileSubNavMenuText").set(anchorNavigationModel, "Menu");
		Mockito.when(GlobalUtils.checkLink("logoNavUrl", resource)).thenReturn("logoNavUrl");
		anchorNavigationModel.init();
	}

	@Test
	public void testGetters() {
	    Assert.assertEquals("bgType", anchorNavigationModel.getBgType());
		Assert.assertEquals("backgroundColor", anchorNavigationModel.getBackgroundColor());
		Assert.assertEquals("backgroundImage", anchorNavigationModel.getBackgroundImage());
		Assert.assertEquals("logoAlt", anchorNavigationModel.getLogoAlt());
		Assert.assertEquals("logoNavUrl", anchorNavigationModel.getLogoNavUrl());
		Assert.assertEquals("navigationLogo", anchorNavigationModel.getNavigationLogo());
		Assert.assertNotNull(anchorNavigationModel.getAnchorModelList());
		Assert.assertEquals(1, anchorNavigationModel.getAnchorModelList().size());
	}
	
	@Test
    public void testGetMobileSubNavMenuText() {
	    Assert.assertEquals("Menu", anchorNavigationModel.getMobileSubNavMenuText());
	}
}
