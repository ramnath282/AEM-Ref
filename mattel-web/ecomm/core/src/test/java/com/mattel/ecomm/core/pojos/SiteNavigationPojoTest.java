package com.mattel.ecomm.core.pojos;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SiteNavigationPojoTest {

	SiteNavigationPojo siteNavigationPojo;
	List<SiteNavigationPojo> childPageList;

	@Before
	public void setUp() {
		childPageList = new ArrayList<>();
		siteNavigationPojo = new SiteNavigationPojo();
		siteNavigationPojo.setPageName("pageName");
		siteNavigationPojo.setName("name");
		siteNavigationPojo.setPageUrl("pageUrl");
		siteNavigationPojo.setThumbnailImg("thumbnailImg");
		siteNavigationPojo.setThumbnailAltText("thumbnailAltText");
		siteNavigationPojo.setIsRedirect(false);
		siteNavigationPojo.setLinkingName("linkingName");
		siteNavigationPojo.setPageActive(false);
		siteNavigationPojo.setUrlTarget("urlTarget");
		siteNavigationPojo.setAdobeTrackingNameForPage("adobeTrackingNameForPage");
		siteNavigationPojo.setNotLinkable(false);
		siteNavigationPojo.setSpecialLink("specialLink");
		siteNavigationPojo.setChildPageList(childPageList);
		siteNavigationPojo.setShopAllClass("shopAllClass");
		siteNavigationPojo.setSubLinkClass("subLinkClass");
	}

	@Test
	public void getPageName() {
		Assert.assertEquals("pageName", siteNavigationPojo.getPageName());
	}

	@Test
	public void getName() {
		Assert.assertEquals("name", siteNavigationPojo.getName());
	}

	@Test
	public void getPageUrl() {
		Assert.assertEquals("pageUrl", siteNavigationPojo.getPageUrl());
	}

	@Test
	public void getChildPageList() {
		Assert.assertEquals(childPageList, siteNavigationPojo.getChildPageList());
	}

	@Test
	public void getIsRedirect() {
		Assert.assertEquals(false, siteNavigationPojo.getIsRedirect());
	}

	@Test
	public void getThumbnailImg() {
		Assert.assertEquals("thumbnailImg", siteNavigationPojo.getThumbnailImg());
	}

	@Test
	public void getThumbnailAltText() {
		Assert.assertEquals("thumbnailAltText", siteNavigationPojo.getThumbnailAltText());
	}

	@Test
	public void getLinkingName() {
		Assert.assertEquals("linkingName", siteNavigationPojo.getLinkingName());
	}

	@Test
	public void isPageActive() {
		Assert.assertEquals(false, siteNavigationPojo.isPageActive());
	}

	@Test
	public void isNotLinkable() {
		Assert.assertEquals(false, siteNavigationPojo.isNotLinkable());
	}

	@Test
	public void getUrlTarget() {
		Assert.assertEquals("urlTarget", siteNavigationPojo.getUrlTarget());
	}

	@Test
	public void getAdobeTrackingNameForPage() {
		Assert.assertEquals("adobeTrackingNameForPage", siteNavigationPojo.getAdobeTrackingNameForPage());
	}
	@Test
	public void getSpecialLink() {
		Assert.assertEquals("specialLink", siteNavigationPojo.getSpecialLink());
	}
	
	@Test
	public void getShopAllClass() {
		Assert.assertEquals("shopAllClass", siteNavigationPojo.getShopAllClass());
	}
	@Test
	public void getSubLinkClass() {
		Assert.assertEquals("subLinkClass", siteNavigationPojo.getSubLinkClass());
	}
}
