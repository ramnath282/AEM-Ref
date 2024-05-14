package com.mattel.play.core.pojos;

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

		siteNavigationPojo = new SiteNavigationPojo();	
		childPageList = new ArrayList<>();
	}
	
	@Test
	public void getName() {
		siteNavigationPojo.getName();
	}
	@Test
	public void setName() {
		siteNavigationPojo.setName("");
	}
	@Test
	public void getPageName() {
		siteNavigationPojo.getPageName();
	}
	@Test
	public void setPageName() {
		siteNavigationPojo.setPageName("");
	}
	@Test
	public void getPageUrl() {
		siteNavigationPojo.getPageUrl();
	}
	@Test
	public void setPageUrl() {
		siteNavigationPojo.setPageUrl("");
	}
	@Test
	public void getChildPageList() {
		siteNavigationPojo.getChildPageList();
	}
	@Test
	public void setChildPageList() {
		siteNavigationPojo.setChildPageList(childPageList);
	}
	@Test
	public void getIsRedirect() {
		siteNavigationPojo.getIsRedirect();
	}
	@Test
	public void setIsRedirect() {
		siteNavigationPojo.setIsRedirect(false);
	}
	@Test
	public void getThumbnailImg() {
		siteNavigationPojo.getThumbnailImg();
	}
	@Test
	public void setThumbnailImg() {
		siteNavigationPojo.setThumbnailImg("");
	}
	@Test
	public void isPageActive() {
		siteNavigationPojo.isPageActive();
	}
	@Test
	public void setPageActive() {
		siteNavigationPojo.setPageActive(false);
	}
	@Test
	public void getUrlTarget() {
		siteNavigationPojo.getUrlTarget();
	}
	@Test
	public void setUrlTarget() {
		siteNavigationPojo.setUrlTarget("");
	}
	
	@Test
	public void testGetterSetter(){
		siteNavigationPojo.setLinkingName("linkingName");
		siteNavigationPojo.setAdobeTrackingNameForPage("adobeTrackingNameForPage");
		siteNavigationPojo.setThumbnailAltText("thumbnailAltText");
		siteNavigationPojo.setNotLinkable(true);
		siteNavigationPojo.setSpecialLink("specialLink");
		
		Assert.assertEquals("linkingName", siteNavigationPojo.getLinkingName());
		Assert.assertEquals("adobeTrackingNameForPage", siteNavigationPojo.getAdobeTrackingNameForPage());
		Assert.assertEquals("thumbnailAltText", siteNavigationPojo.getThumbnailAltText());
		Assert.assertEquals(true, siteNavigationPojo.isNotLinkable());
		Assert.assertEquals("specialLink", siteNavigationPojo.getSpecialLink());
	}
}
