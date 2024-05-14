package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GTEmailTNSRecipientTest {

	private GTEmailTNSRecipient gTEmailTNSRecipient;

	@Before
	public void setUp() throws Exception {
		gTEmailTNSRecipient = new GTEmailTNSRecipient();
		gTEmailTNSRecipient.setContactEmailID("Email");
		
	}


	@Test
	public void testGetInstructions() {
		Assert.assertEquals("Email", gTEmailTNSRecipient.getContactEmailID());
	}
}
