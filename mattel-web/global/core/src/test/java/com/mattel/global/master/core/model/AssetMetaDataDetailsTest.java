package com.mattel.global.master.core.model;

import org.apache.sling.api.resource.Resource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class AssetMetaDataDetailsTest {

	@InjectMocks
	private AssetMetaDataDetails assetMetaDataDetails;

	@Mock
	private Resource resource;

	@Before
	public void setup() throws IllegalArgumentException, IllegalAccessException {
		MemberModifier.field(AssetMetaDataDetails.class, "image").set(assetMetaDataDetails, "IMAGEALTTEXT");
		
		MemberModifier.field(AssetMetaDataDetails.class, "mobileImage").set(assetMetaDataDetails, "MOBILEIMAGEALTTEXT");
		
		MemberModifier.field(AssetMetaDataDetails.class, "hoverImage").set(assetMetaDataDetails, "HOVERIMAGEALTTEXT");
		
		MemberModifier.field(AssetMetaDataDetails.class, "mobileHoverImage").set(assetMetaDataDetails,
				"MOBILEHOVERIMAGEALTTEXT");
		
		MemberModifier.field(AssetMetaDataDetails.class, "imageAltText").set(assetMetaDataDetails, "imageAltText");
		
		MemberModifier.field(AssetMetaDataDetails.class, "mobileImageAltText").set(assetMetaDataDetails,
				"mobileImageAltText");
		
		MemberModifier.field(AssetMetaDataDetails.class, "hoverImageAltText").set(assetMetaDataDetails,
				"hoverImageAltText");
		
		MemberModifier.field(AssetMetaDataDetails.class, "mobileHoverImageAltText").set(assetMetaDataDetails,
				"mobileHoverImageAltText");
		
	}
	
	@Test
	public void testInit(){
		assetMetaDataDetails.getAssetsMetaDataDetails();
	}

}
