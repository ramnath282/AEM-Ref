package com.mattel.ecomm.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DownloadAppPojoTest {
	private DownloadAppPojo downloadAppPojo = null;

	@Before
	public void setUp() throws Exception {
		downloadAppPojo = new DownloadAppPojo();
		downloadAppPojo.setThumbnailImage("thumbnailImage");
		downloadAppPojo.setThumbnailTitle("thumbnailTitle");
		downloadAppPojo.setThumbnailDescription("thumbnailDescription");
		downloadAppPojo.setAltTextThumbnail("altTextThumbnail");
		downloadAppPojo.setDownloadCtaLabel("downloadCtaLabel");
		downloadAppPojo.setAlwaysEnglish("alwaysEnglish");
		downloadAppPojo.setInterstitialAndroidUrl("interstitialAndroidUrl");
		downloadAppPojo.setInterstitialIosUrl("interstitialIosUrl");
	}

	@Test
	public void getThumbnailImage() {
		Assert.assertEquals("thumbnailImage", downloadAppPojo.getThumbnailImage());
	}

	@Test
	public void getThumbnailTitle() {
		Assert.assertEquals("thumbnailTitle", downloadAppPojo.getThumbnailTitle());
	}

	@Test
	public void getThumbnailDescription() {
		Assert.assertEquals("thumbnailDescription", downloadAppPojo.getThumbnailDescription());
	}

	@Test
	public void getAltTextThumbnail() {
		Assert.assertEquals("altTextThumbnail", downloadAppPojo.getAltTextThumbnail());
	}

	@Test
	public void getDownloadCtaLabel() {
		Assert.assertEquals("downloadCtaLabel", downloadAppPojo.getDownloadCtaLabel());
	}

	@Test
	public void getAlwaysEnglish() {
		Assert.assertEquals("alwaysEnglish", downloadAppPojo.getAlwaysEnglish());
	}

	@Test
	public void getInterstitialAndroidUrl() {
		Assert.assertEquals("interstitialAndroidUrl", downloadAppPojo.getInterstitialAndroidUrl());
	}

	@Test
	public void getInterstitialIosUrl() {
		Assert.assertEquals("interstitialIosUrl", downloadAppPojo.getInterstitialIosUrl());
	}

}
