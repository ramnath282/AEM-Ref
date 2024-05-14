package com.mattel.global.core.pojo;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NavigationBasePagePojoTest {

	NavigationBasePagePojo navigationBasePagePojo = new NavigationBasePagePojo();

	@Test
	public void testPageName() {
		navigationBasePagePojo.setPageName("pageName");
		assertSame("pageName", navigationBasePagePojo.getPageName());
	}

	@Test
	public void testSetName() {
		navigationBasePagePojo.setName("name");
		assertSame("name", navigationBasePagePojo.getName());
	}
	@Test
	public void testSetPageUrl() {
		navigationBasePagePojo.setPageUrl("pageUrl");
		assertSame("pageUrl", navigationBasePagePojo.getPageUrl());
	}
	@Test
	public void testSetThumbnailImg() {
		navigationBasePagePojo.setThumbnailImg("thumbnailImg");
		assertSame("thumbnailImg", navigationBasePagePojo.getThumbnailImg());
	}
	@Test
	public void testIsRedirect() {
		navigationBasePagePojo.setIsRedirect(false); 
		assertSame(false, navigationBasePagePojo.getIsRedirect());
	}
	@Test
	public void testGetLinkingName() {
		navigationBasePagePojo.setLinkingName("linkingName");
		assertSame("linkingName", navigationBasePagePojo.getLinkingName());
	}
	@Test
	public void testSetPageActive() {
		navigationBasePagePojo.setPageActive(false);
		assertSame(false, navigationBasePagePojo.isPageActive());
	}
	@Test
	public void testSetTargetUrl() {
		navigationBasePagePojo.setUrlTarget("urlTarget");
		assertSame("urlTarget", navigationBasePagePojo.getUrlTarget());
	}
	@Test
	public void testGetAdobeTrackingNameForPage() {
		navigationBasePagePojo.setAdobeTrackingNameForPage("adobeTrackingNameForPage");
		assertSame("adobeTrackingNameForPage", navigationBasePagePojo.getAdobeTrackingNameForPage());
	}
	@Test
	public void testSetThumbnailAltText() {
		navigationBasePagePojo.setThumbnailAltText("thumbnailAltText");
		assertSame("thumbnailAltText", navigationBasePagePojo.getThumbnailAltText());
	}
	@Test
	public void testIsNotLinkable() {
		navigationBasePagePojo.setNotLinkable(false);
		assertSame(false, navigationBasePagePojo.isNotLinkable());
	}
	@Test
	public void testSetSpecialLink() {
		navigationBasePagePojo.setSpecialLink("specialLink");
		assertSame("specialLink", navigationBasePagePojo.getSpecialLink());
	}

	@Test
	public void testConfettiEffect() {
		navigationBasePagePojo.setConfettiEffect(true);
		assertTrue(navigationBasePagePojo.isConfettiEffect());
	}

	@Test
	public void testPromoHoverImagePath() {
		navigationBasePagePojo.setPromoHoverImagePath("hoverImagePath");
		assertSame("hoverImagePath", navigationBasePagePojo.getPromoHoverImagePath());
	}

	@Test
	public void testToString() {
		navigationBasePagePojo.toString();
	}

}
