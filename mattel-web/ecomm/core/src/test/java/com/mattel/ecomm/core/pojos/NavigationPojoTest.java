package com.mattel.ecomm.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NavigationPojoTest {

	NavigationPojo navigationPojo;

	@Before
	public void setUp() {
		navigationPojo = new NavigationPojo();
		navigationPojo.setStoreLocation("storelocation");
		navigationPojo.setStoreLink("storelink");
		navigationPojo.setNavLinkUrl("linkurl");
		navigationPojo.setNavLinkText("navlinktest");
		navigationPojo.setNavLinkOpenIn("linkopenin");
	}

	@Test
	public void testAccordionPojo() {
		Assert.assertEquals("storelocation", navigationPojo.getStoreLocation());
		Assert.assertEquals("storelink", navigationPojo.getStoreLink());
		Assert.assertEquals("linkurl", navigationPojo.getNavLinkUrl());
		Assert.assertEquals("navlinktest", navigationPojo.getNavLinkText());
		Assert.assertEquals("linkopenin", navigationPojo.getNavLinkOpenIn());
	}

}
