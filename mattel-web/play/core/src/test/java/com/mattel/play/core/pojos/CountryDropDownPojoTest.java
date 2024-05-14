package com.mattel.play.core.pojos;

import org.junit.Before;
import org.junit.Test;

public class CountryDropDownPojoTest {

	CountryDropDownPojo countryDropDownPojo;
	
	@Before
	public void setUp() {

		countryDropDownPojo = new 	CountryDropDownPojo();		
	}
	
	@Test
	public void getCountryName() {
		countryDropDownPojo.getCountryName();
	}
	@Test
 	public void setCountryName() {
		countryDropDownPojo.setCountryName("");
	}
	@Test
 	public void getCountryUrl() {
		countryDropDownPojo.getCountryUrl();
	}
	@Test
 	public void setCountryUrl() {
		countryDropDownPojo.setCountryUrl("");
	}
	@Test
 	public void getTarget() {
		countryDropDownPojo.getTarget();
	}
	@Test
 	public void setTarget() {
		countryDropDownPojo.setTarget("");
	}
	
	@Test
	public void testSetIsCurrentCountry() {
		countryDropDownPojo.setIsCurrentCountry("isCurrentCountry");
	}

	@Test
	public void testGetIsCurrentCountry() {
		countryDropDownPojo.getIsCurrentCountry();
	}
}
