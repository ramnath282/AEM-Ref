package com.mattel.ag.retail.core.pojos;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class LocationDetailsPojoTest {

	LocationDetailsPojo locationDetailsPojo;
	List<LocationDateDetailsPojo> locationDates;

	@Before
	public void setUp() {
		locationDetailsPojo = new LocationDetailsPojo();
		locationDetailsPojo.setGratuityRequired("gratuityRequired");
		locationDetailsPojo.setStoreName("locationName");
		locationDetailsPojo.setLocationDateDetails(locationDates);
		locationDetailsPojo.setPricingOption("pricingOption");
		locationDetailsPojo.setPricingAmount("pricingAmount");
		locationDetailsPojo.setStoreTag("storeTag");
		locationDetailsPojo.setZomatoURL("zomatoURL");
	}

	@Test
	public void getGratuityRequiredTest() {
		locationDetailsPojo.getGratuityRequired();
	}

	@Test
	public void getLocationDatesTest() {
		locationDetailsPojo.getLocationDateDetails();
	}

	@Test
	public void getLocationNameTest() {
		locationDetailsPojo.getStoreName();
	}


	@Test
	public void getPricingAmountTest() {
		locationDetailsPojo.getPricingAmount();
	}

	@Test
	public void getPricingOption(){
		locationDetailsPojo.getPricingOption();
	}

	@Test
	public void getStoreTagTest() {
		locationDetailsPojo.getStoreTag();
	}

	@Test
	public void getZomatoURLTest() {
		locationDetailsPojo.getZomatoURL();
	}
	
	@Test
	public void toStringTest() {
		locationDetailsPojo.toString();
	}

}
