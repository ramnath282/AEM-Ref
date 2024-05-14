package com.mattel.global.core.pojo;

import static org.junit.Assert.*;

import org.junit.Test;

public class RetailersLogoDetailPojoTest {

	RetailersLogoDetailPojo retailersLogoDetailPojo = new RetailersLogoDetailPojo();

	@Test
	public void tesLogoImage() {
		retailersLogoDetailPojo.setLogoImage("logoImage");
		assertSame("logoImage", retailersLogoDetailPojo.getLogoImage());
	}

	@Test
	public void testLogoAltTxt() {
		retailersLogoDetailPojo.setLogoAltTxt("logoAltTxt");
		assertSame("logoAltTxt", retailersLogoDetailPojo.getLogoAltTxt());
	}

	@Test
	public void testRetailerLogoLink() {
		retailersLogoDetailPojo.setRetailerLogoLink("retailerLogoLink");
		assertSame("retailerLogoLink", retailersLogoDetailPojo.getRetailerLogoLink());
	}

	@Test
	public void testAnalyticsText() {
		retailersLogoDetailPojo.setAnalyticsText("analyticsText");
		assertSame("analyticsText", retailersLogoDetailPojo.getAnalyticsText());
	}

	@Test
	public void testToString() {
		retailersLogoDetailPojo.toString();

	}

}
