package com.mattel.productvideos.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VideoJsonMetadataTest {

	VideoJsonMetadata videoJsonMetadata;

	@Before
	public void setup() {
		videoJsonMetadata = new VideoJsonMetadata();
		videoJsonMetadata.setBrandValue("brandValue");
		videoJsonMetadata.setCollectionName("collectionName");
		videoJsonMetadata.setContentName("contentName");
		videoJsonMetadata.setScene7Path("scene7Path");
	}

	@Test
	public void testGetter() {
		Assert.assertEquals(videoJsonMetadata.getBrandValue(), "brandValue");
		Assert.assertEquals(videoJsonMetadata.getCollectionName(), "collectionName");
		Assert.assertEquals(videoJsonMetadata.getContentName(), "contentName");
		Assert.assertEquals(videoJsonMetadata.getScene7Path(), "scene7Path");
	}

}
