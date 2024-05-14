package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GTEmailTNSNoticeEventTest {

	@Mock
	GTEmailTNSNoticeDetails gTEmailTNSNoticeDetails;
	@Mock
	GTEmailTNSPayloadDetails gTEmailTNSPayloadDetails;
	
	private GTEmailTNSNoticeEvent gTEmailTNSNoticeEvent;
	
    @Before
	public void setUp() throws Exception {
    	gTEmailTNSNoticeEvent = new GTEmailTNSNoticeEvent();
    	gTEmailTNSNoticeEvent.setCreateNoticeEventReq(gTEmailTNSNoticeDetails);
    	gTEmailTNSNoticeEvent.setPayloadHeader(gTEmailTNSPayloadDetails);
		
	}


	@Test
	public void testGetInstructions() {
		Assert.assertNotNull(gTEmailTNSNoticeEvent.getCreateNoticeEventReq()); 
	}

	@Test
	public void testGetSKU() {
		Assert.assertNotNull(gTEmailTNSNoticeEvent.getPayloadHeader()); 
	}

}
