package com.mattel.ecomm.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BreadcrumbPojoTest {
	private BreadcrumbPojo breadcrumbPojo = null;

	@Before
	public void setUp() throws Exception {
		breadcrumbPojo = new BreadcrumbPojo();
		breadcrumbPojo.setAdobeTrackingName("adobeTrackingName");
		breadcrumbPojo.setPageLink("pageLink");
		breadcrumbPojo.setTitle("title");
	}

	@Test
	public void testGetTitle() {
		Assert.assertEquals("title", breadcrumbPojo.getTitle());
	}

	@Test
	public void testGetPageLink() {
		Assert.assertEquals("pageLink", breadcrumbPojo.getPageLink());
	}

	@Test
	public void testGetAdobeTrackingName() {
		Assert.assertEquals("adobeTrackingName", breadcrumbPojo.getAdobeTrackingName());
	}

}
