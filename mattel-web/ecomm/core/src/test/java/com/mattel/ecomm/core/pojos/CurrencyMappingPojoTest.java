package com.mattel.ecomm.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyMappingPojoTest {
	private CurrencyMappingPojo currencyMappingPojo = null;

	@Before
	public void setUp() throws Exception {
		currencyMappingPojo = new CurrencyMappingPojo();
		currencyMappingPojo.setCurrencyType("currencyType");
		currencyMappingPojo.setCurrencySymbol("currencySymbol");
	}

	@Test
	public void getCurrencyType() {
		Assert.assertEquals("currencyType", currencyMappingPojo.getCurrencyType());
	}

	@Test
	public void getCurrencySymbol() {
		Assert.assertEquals("currencySymbol", currencyMappingPojo.getCurrencySymbol());
	}

}
