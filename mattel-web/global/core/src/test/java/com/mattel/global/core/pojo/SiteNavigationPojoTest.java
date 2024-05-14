package com.mattel.global.core.pojo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class SiteNavigationPojoTest {

	SiteNavigationPojo siteNavigationPojo;
	List<SiteNavigationPojo> childPageList;
	Date date = new Date();

	@Before
	public void setUp() {
		childPageList = new ArrayList<>();
		siteNavigationPojo = new SiteNavigationPojo();
		siteNavigationPojo.setLastModifiedDate(date);
		siteNavigationPojo.setChildPageList(childPageList);
		siteNavigationPojo.setShopAllClass("shopAllClass");
		siteNavigationPojo.setSubLinkClass("subLinkClass");
		siteNavigationPojo.toString();
	}

	@Test
	public void getLastModifiedDate() {
		Assert.assertEquals(date, siteNavigationPojo.getLastModifiedDate());
	}
	@Test
	public void getChildPageList() {
		Assert.assertEquals(childPageList, siteNavigationPojo.getChildPageList());
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
