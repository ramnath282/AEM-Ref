package com.mattel.ecomm.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PDPBreadcrumbPojoTest {
	private PDPBreadcrumbPojo pdpBreadcrumbPojo = null;

	@Before
	public void setUp() throws Exception {
		pdpBreadcrumbPojo = new PDPBreadcrumbPojo();
		pdpBreadcrumbPojo.setTitle("title");
		pdpBreadcrumbPojo.setPageLink("pageLink");
	}

	@Test
	public void getTitle() {
		Assert.assertEquals("title", pdpBreadcrumbPojo.getTitle());
	}

	@Test
	public void getPageLink() {
		Assert.assertEquals("pageLink", pdpBreadcrumbPojo.getPageLink());
	}
}
