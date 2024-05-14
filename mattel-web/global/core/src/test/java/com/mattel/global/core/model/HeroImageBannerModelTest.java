package com.mattel.global.core.model;

import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

import com.mattel.global.core.pojo.CommonPojo;

public class HeroImageBannerModelTest {

	HeroImageBannerModel heroImageBanner;
	CommonPojo commonpojo = new CommonPojo();
	@Before
	public void setUp(){
		heroImageBanner = new HeroImageBannerModel();

	}
	@Test
	public void testGetCtaLink() {
		heroImageBanner.setCtaLink("/content/test/path");
		assertSame("/content/test/path", heroImageBanner.getCtaLink());
	}

	@Test
	public void testGetImgUrl() {
		heroImageBanner.setImgUrl("/content/dam/imagePath");
		assertSame("/content/dam/imagePath",  heroImageBanner.getImgUrl());
	}
	@Test
	public void testSetCtaLink() {
		heroImageBanner.setCtaLink("/content/somePath");
		assertSame("/content/somePath",  heroImageBanner.getCtaLink());
	}
	@Test
	public void testSetImgUrl() {
		heroImageBanner.setImgUrl("test1");
		assertSame("test1",  heroImageBanner.getImgUrl());
	}

	@Test
	public void testInitmethodWhenImagePathNotNull() {
		heroImageBanner.setImgUrl("/content/dam/some/image/path");
		heroImageBanner.setCtaLink("/content/some/page/path");
		heroImageBanner.init();
	}

	@Test
	public void testInitmethodWhenImagePathNull() {
		heroImageBanner.setImgUrl(null);
		heroImageBanner.setCtaLink("/content/some/page/path");
		heroImageBanner.init();
	}

	@Test
	public void testInitmethodWhenCtaLinkNotNull() {
		heroImageBanner.setCtaLink("/content/some/page/path");
		heroImageBanner.setImgUrl("/content/dam/some/image/path");
		heroImageBanner.init();
	}

	@Test
	public void testInitmethodWhenCtaLinkNull() {
		heroImageBanner.setCtaLink(null);
		heroImageBanner.setImgUrl("/content/dam/some/image/path");
		heroImageBanner.init();
	}

}
