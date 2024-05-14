package com.mattel.ag.retail.core.pojos;

import org.junit.Before;
import org.junit.Test;

/**
 * A simple pojo for page properties.
 */
public class SiteNavigationPojoTest {

	SiteNavigationPojo siteNavigationPojo;
	
	@Before
	public void setUp(){
		siteNavigationPojo = new SiteNavigationPojo();
	}

	@Test
	public void setLabel() {
		siteNavigationPojo.setLabel("Label");
	}

	@Test
	public void setUrl() {
		siteNavigationPojo.setUrl("URL");
	}

	@Test
	public void setAlt() {
		siteNavigationPojo.setAlt("Alt");
	}

	@Test
	public void setTargetUrl() {
		siteNavigationPojo.setTargetUrl("TargetURL");
	}

	@Test
	public void getLabel() {
		siteNavigationPojo.getLabel();
	}

	@Test
	public void getUrl() {
		siteNavigationPojo.getUrl();
	}

	@Test
	public void getAlt() {
		siteNavigationPojo.getAlt();
	}

	@Test
	public void getTargetUrl() {
		siteNavigationPojo.getTargetUrl();
	}
	
	@Test 
	public void toStringTest(){
		siteNavigationPojo.toString();
	}

}
