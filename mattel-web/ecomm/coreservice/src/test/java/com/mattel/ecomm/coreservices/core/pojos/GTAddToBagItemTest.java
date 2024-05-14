package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GTAddToBagItemTest {

	private GTAddToBagItem gTAddToBagItem;
	
	@Before
	public void setUp() throws Exception {
		gTAddToBagItem = new GTAddToBagItem();

	}
	
	@Test
	public void testGetInstructions() {
		Assert.assertNotNull(gTAddToBagItem.toString());
	}

}
