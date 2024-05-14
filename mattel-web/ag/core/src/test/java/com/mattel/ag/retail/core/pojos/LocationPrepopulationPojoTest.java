package com.mattel.ag.retail.core.pojos;

import org.junit.Before;
import org.junit.Test;

public class LocationPrepopulationPojoTest {
	LocationPrepopulationPojo locationPrepopulationPojo;

	@Before
	public void setUp() {
		locationPrepopulationPojo = new LocationPrepopulationPojo();
		locationPrepopulationPojo.setStoreId("1");
		locationPrepopulationPojo.setStoreName("Atlanta");
	}

	@Test
	public void getStoreId() {
		locationPrepopulationPojo.getStoreId();
	}

	@Test
	public void getStoreName() {
		locationPrepopulationPojo.getStoreName();
	}
}
