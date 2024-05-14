package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GTEmailTNSResponseMessageTest {
	
	private GTEmailTNSResponseMessage gTEmailTNSResponseMessage;

	@Before
	public void setUp() throws Exception {
		gTEmailTNSResponseMessage = new GTEmailTNSResponseMessage();
		gTEmailTNSResponseMessage.setStatus("status");
		gTEmailTNSResponseMessage.setMessage("message");
		gTEmailTNSResponseMessage.setCorrelationId("correlationID");
		gTEmailTNSResponseMessage.setMessageId("messageID");
		gTEmailTNSResponseMessage.setTimeStamp("timeStamp");
		

	}


	@Test
	public void testGetSource() {
		Assert.assertEquals("status", gTEmailTNSResponseMessage.getStatus());
	}

	@Test
	public void testGetTarget() {
		Assert.assertEquals("message", gTEmailTNSResponseMessage.getMessage());
	}
	
		@Test
	    public void testGetcorrelation() throws Exception {
			Assert.assertEquals("correlationID",gTEmailTNSResponseMessage.getCorrelationId());
	    }
	    
	    @Test
	    public void testGetTimestamp() throws Exception {
			Assert.assertEquals("messageID",gTEmailTNSResponseMessage.getMessageId());
	    }
	    
	    @Test
	    public void testGetGIFT_TRUNK_RECIPIENT() throws Exception {
			Assert.assertEquals("timeStamp",gTEmailTNSResponseMessage.getTimeStamp());
	    }
	    
	   

}
