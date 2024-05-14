package com.mattel.ecomm.coreservices.core.pojos;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GTEmailTNSPayloadDetailsTest {

	private GTEmailTNSPayloadDetails gTEmailTNSPayloadDetails;

	@Before
	public void setUp() throws Exception {
		gTEmailTNSPayloadDetails = new GTEmailTNSPayloadDetails();
		gTEmailTNSPayloadDetails.setSource("source");
		gTEmailTNSPayloadDetails.setTarget("target");
		gTEmailTNSPayloadDetails.setCorrelationID("correlationID");
		gTEmailTNSPayloadDetails.setTimeStamp("timeStamp");
		gTEmailTNSPayloadDetails.setEnvironment("environment");
		gTEmailTNSPayloadDetails.setHostName("hostname");
		gTEmailTNSPayloadDetails.setUser("user");

	}


	@Test
	public void testGetSource() {
		Assert.assertEquals("source", gTEmailTNSPayloadDetails.getSource());
	}

	@Test
	public void testGetTarget() {
		Assert.assertEquals("target", gTEmailTNSPayloadDetails.getTarget());
	}
	
		@Test
	    public void testGetcorrelation() throws Exception {
			Assert.assertEquals("correlationID",gTEmailTNSPayloadDetails.getCorrelationID());
	    }
	    
	    @Test
	    public void testGetTimestamp() throws Exception {
			Assert.assertEquals("timeStamp",gTEmailTNSPayloadDetails.getTimeStamp());
	    }
	    
	    @Test
	    public void testGetGIFT_TRUNK_RECIPIENT() throws Exception {
			Assert.assertEquals("environment",gTEmailTNSPayloadDetails.getEnvironment());
	    }
	    
	    @Test
	    public void testGethostName() throws Exception {
			Assert.assertEquals("hostname",gTEmailTNSPayloadDetails.getHostName());
	    }
	    
	    @Test
	    public void testGetUser() throws Exception {
			Assert.assertEquals("user",gTEmailTNSPayloadDetails.getUser());
	    }

}
