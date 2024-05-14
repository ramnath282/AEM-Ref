package com.mattel.play.core.pojos;

import org.junit.Before;
import org.junit.Test;

public class RetailerPojoTest {

	
	RetailerPojo retailerPojo;
	@Before
	public void setUp() {

		
		retailerPojo = new RetailerPojo();
				
	}
	
	@Test
	public void getRetailerLogoSrc() {
		retailerPojo.getRetailerLogoSrc();
	}
	@Test
	public void setRetailerLogoSrc() {
		retailerPojo.setRetailerLogoSrc("");
	}
	@Test
	public void getRetailLogoAlt() {
		retailerPojo.getRetailLogoAlt();
	}
	@Test
	public void setRetailLogoAlt() {
		retailerPojo.setRetailLogoAlt("");
	}
	@Test
	public void getRetailerUrl() {
		retailerPojo.getRetailerUrl();
	}
	@Test
	public void setRetailerUrl() {
		retailerPojo.setRetailerUrl("");
	}
	@Test
	public void getRetailerTarget() {
		retailerPojo.getRetailerTarget();
	}
	@Test
	public void setRetailerTarget() {
		retailerPojo.setRetailerTarget("");
		retailerPojo.toString();
	}
	
}
