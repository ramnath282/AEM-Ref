package com.mattel.global.core.pojo;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class NavigationParentPagePojoTest {

	NavigationParentPagePojo navigationParentPagePojo = new NavigationParentPagePojo();

	@Test
	public void testPageName() {
		navigationParentPagePojo.setPageName("pageName");
		assertSame("pageName", navigationParentPagePojo.getPageName());
	}

	@Test
	public void testSetName() {
		navigationParentPagePojo.setName("name");
		assertSame("name", navigationParentPagePojo.getName());
	}
	@Test
	public void testSetPageUrl() {
		navigationParentPagePojo.setPageUrl("pageUrl");
		assertSame("pageUrl", navigationParentPagePojo.getPageUrl());
	}
	@Test
	public void testSetThumbnailImg() {
		navigationParentPagePojo.setThumbnailImg("thumbnailImg");
		assertSame("thumbnailImg", navigationParentPagePojo.getThumbnailImg());
	}
	@Test
	public void testIsRedirect() {
		navigationParentPagePojo.setIsRedirect(false); 
		assertSame(false, navigationParentPagePojo.getIsRedirect());
	}
	@Test
	public void testGetLinkingName() {
		navigationParentPagePojo.setLinkingName("linkingName");
		assertSame("linkingName", navigationParentPagePojo.getLinkingName());
	}
	@Test
	public void testSetPageActive() {
		navigationParentPagePojo.setPageActive(false);
		assertSame(false, navigationParentPagePojo.isPageActive());
	}
	@Test
	public void testSetTargetUrl() {
		navigationParentPagePojo.setUrlTarget("urlTarget");
		assertSame("urlTarget", navigationParentPagePojo.getUrlTarget());
	}
	@Test
	public void testGetAdobeTrackingNameForPage() {
		navigationParentPagePojo.setAdobeTrackingNameForPage("adobeTrackingNameForPage");
		assertSame("adobeTrackingNameForPage", navigationParentPagePojo.getAdobeTrackingNameForPage());
	}
	@Test
	public void testSetThumbnailAltText() {
		navigationParentPagePojo.setThumbnailAltText("thumbnailAltText");
		assertSame("thumbnailAltText", navigationParentPagePojo.getThumbnailAltText());
	}
	@Test
	public void testIsNotLinkable() {
		navigationParentPagePojo.setNotLinkable(false);
		assertSame(false, navigationParentPagePojo.isNotLinkable());
	}
	@Test
	public void testSetSpecialLink() {
		navigationParentPagePojo.setSpecialLink("specialLink");
		assertSame("specialLink", navigationParentPagePojo.getSpecialLink());
	}
	@Test
	public void testSetChildPageList() {
		List<NavigationBasePagePojo> childPageList = new ArrayList<>();
		childPageList.add(navigationParentPagePojo);
		navigationParentPagePojo.setChildPageList(childPageList);
		assertSame(childPageList, navigationParentPagePojo.getChildPageList());
		
	}
	@Test
	public void testToString() {
		navigationParentPagePojo.toString();
	}

}
