package com.mattel.global.core.model;

import static org.junit.Assert.assertEquals;

import org.apache.sling.api.resource.Resource;
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
import com.mattel.global.master.core.constants.Constants;

@PrepareForTest(GlobalUtils.class)
@RunWith(PowerMockRunner.class)
public class AssetModelTest {

	@InjectMocks
	private AssetModel assetModel;

	@Mock
	private Resource resource;
	
	@Mock
	private Resource ctaResource;

	@Before
	public void setup() throws Exception {
		PowerMockito.mockStatic(GlobalUtils.class);
		MemberModifier.field(AssetModel.class, "title").set(assetModel, "title");
		MemberModifier.field(AssetModel.class, "subTitle").set(assetModel, "subTitle");
		MemberModifier.field(AssetModel.class, "entrCompClickable").set(assetModel, true);
		MemberModifier.field(AssetModel.class, "resource").set(assetModel, resource);

		MemberModifier.field(AssetModel.class, "imageAltText").set(assetModel, "imageAltText");
		MemberModifier.field(AssetModel.class, "mobileImageAltText").set(assetModel, "mobileImageAltText");
		MemberModifier.field(AssetModel.class, "imageAltText").set(assetModel, "imageAltText");
		MemberModifier.field(AssetModel.class, "image").set(assetModel, "image");

		MemberModifier.field(AssetModel.class, "hoverImage").set(assetModel, "hoverImage");
		MemberModifier.field(AssetModel.class, "hoverImageAltText").set(assetModel, "hoverImageAltText");
		MemberModifier.field(AssetModel.class, "mobileHoverImage").set(assetModel, "mobileHoverImage");
		MemberModifier.field(AssetModel.class, "mobileHoverImageAltText").set(assetModel, "mobileHoverImageAltText");
		
		Mockito.when(GlobalUtils.getCtaURL(resource)).thenReturn(ctaResource);
		Mockito.when(GlobalUtils.removeTags("title", Constants.REMOVE_TAGS, Constants.EMPTY_ARRAY_STRING)).thenReturn("strippedTitle");
		Mockito.when(GlobalUtils.removeTags("subTitle", Constants.REMOVE_TAGS, Constants.EMPTY_ARRAY_STRING)).thenReturn("strippedSubTitle");	}

	@Test
	public void testInit() {
		assetModel.init();
		assertEquals("strippedTitle",assetModel.getStrippedTitle());
		assertEquals("strippedSubTitle",assetModel.getStrippedSubTitle());
	}

}
