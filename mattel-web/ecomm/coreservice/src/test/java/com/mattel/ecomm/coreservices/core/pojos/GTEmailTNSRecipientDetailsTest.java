package com.mattel.ecomm.coreservices.core.pojos;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GTEmailTNSRecipientDetailsTest {

	private GTEmailTNSRecipientDetails gTEmailTNSRecipientDetails;
	@Mock
	GTEmailTNSRecipient gTEmailTNSRecipient;
	@Before
	public void setUp() throws Exception {
		gTEmailTNSRecipientDetails = new GTEmailTNSRecipientDetails();
		
		gTEmailTNSRecipientDetails.setRecipientDetail(gTEmailTNSRecipient);
		

	}


	@Test
	public void testGetInstructions() {
		Assert.assertNotNull(gTEmailTNSRecipientDetails.getRecipientDetail());
	}
}
