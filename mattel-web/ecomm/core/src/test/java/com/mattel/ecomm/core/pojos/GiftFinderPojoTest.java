package com.mattel.ecomm.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GiftFinderPojoTest {
	private GiftFinderPojo giftFinderPojo = null;

	@Before
	public void setUp() throws Exception {
		giftFinderPojo = new GiftFinderPojo();
		giftFinderPojo.setLabel("label");
		giftFinderPojo.setValue("value");
	}

	@Test
	public void getLabel() {
		Assert.assertEquals("label", giftFinderPojo.getLabel());
	}

	@Test
	public void getValue() {
		Assert.assertEquals("value", giftFinderPojo.getValue());
	}
}
