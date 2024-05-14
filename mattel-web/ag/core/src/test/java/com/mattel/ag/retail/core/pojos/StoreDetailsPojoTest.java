package com.mattel.ag.retail.core.pojos;

import org.junit.Before;
import org.junit.Test;

/**
 * A simple pojo for page properties.
 */
public class StoreDetailsPojoTest {

	StoreDetailsPojo storeDetailsPojo;

	@Before
	public void setUp() {
		storeDetailsPojo = new StoreDetailsPojo();
	}

	@Test
	public void setPageTitle() {
		storeDetailsPojo.setPageTitle("PageTitle");
	}

	@Test
	public void setPageUrl() {
		storeDetailsPojo.setPageUrl("PageURL");
	}

	@Test
	public void setInternational() {
		storeDetailsPojo.setInternational(true);
	}

	@Test
	public void setLocation() {
		storeDetailsPojo.setLocation("Location");
	}

	@Test
	public void getPageTitle() {
		storeDetailsPojo.getPageTitle();
	}

	@Test
	public void getPageUrl() {
		storeDetailsPojo.getPageUrl();
	}

	@Test
	public void isInternational() {
		storeDetailsPojo.isInternational();
	}

	@Test
	public void getLocation() {
		storeDetailsPojo.getLocation();
	}
	
	@Test 
	public void toStringTest(){
		storeDetailsPojo.toString();
	}

}
