package com.mattel.ecomm.coreservices.core.pojos;



import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class GTEmailTNSEventNoticeDataTest {

	@Mock
	GTEmailTNSEmailDetails gTEmailTNSEmailDetails;
	@Mock
	GTEmailTNSEventItems gTEmailTNSEventItems;
	
	private GTEmailTNSEventNoticeData gTEmailTNSEventNoticeData;
	
    @Before
	public void setUp() throws Exception {
    	gTEmailTNSEventNoticeData = new GTEmailTNSEventNoticeData();
    	
    	gTEmailTNSEventNoticeData.setEmailDetails(gTEmailTNSEmailDetails);
    	gTEmailTNSEventNoticeData.setItems(gTEmailTNSEventItems);
		
	}


	@Test
	public void testGetInstructions() {
		Assert.assertNotNull(gTEmailTNSEventNoticeData.getEmailDetails()); 
	}

	@Test
	public void testGetSKU() {
		Assert.assertNotNull(gTEmailTNSEventNoticeData.getItems()); 
	}
	
		
}
