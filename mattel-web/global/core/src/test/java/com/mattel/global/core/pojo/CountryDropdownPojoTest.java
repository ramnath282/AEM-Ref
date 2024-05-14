package com.mattel.global.core.pojo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CountryDropdownPojoTest {

	CountryDropdownPojo countryDropdownPojo = null;

	@Before
	public void setUp() throws Exception {
		countryDropdownPojo = new CountryDropdownPojo();
		countryDropdownPojo.setCountryName("countryName");
		countryDropdownPojo.setCountrySiteUrl("countrySiteUrl");
		countryDropdownPojo.toString();
	}


	@Test
	public void testcountryName() {
		assertSame("countryName", countryDropdownPojo.getCountryName());
	}

	@Test
	public void testcountrySiteUrl() {
		assertSame("countrySiteUrl", countryDropdownPojo.getCountrySiteUrl());
	}
}
