package com.mattel.ag.explore.core.pojos;

import org.junit.Before;
import org.junit.Test;

public class ExploreNavigationPojoTest {
	
	ExploreNavigationPojo exploreNavigationPojo;
	
	@Before
	public void setUp() {
		 exploreNavigationPojo = new ExploreNavigationPojo();
	}
	
	@Test
	public void getPageTitle() {
		exploreNavigationPojo.getPageTitle();
	}
    
	@Test
	public void setPageTitle() {
		exploreNavigationPojo.setPageTitle("abc");
	}
    
	@Test
	public void getPageUrl() {
		exploreNavigationPojo.getPageUrl();
	}
    
	@Test
	public void setPageUrl() {
		exploreNavigationPojo.setPageUrl("/content/ag/en/explore");
	}

}
