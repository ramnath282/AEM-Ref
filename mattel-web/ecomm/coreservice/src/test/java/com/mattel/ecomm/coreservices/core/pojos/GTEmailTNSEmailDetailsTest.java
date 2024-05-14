package com.mattel.ecomm.coreservices.core.pojos;



import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;



@RunWith(MockitoJUnitRunner.class)
public class GTEmailTNSEmailDetailsTest {
	
	private GTEmailTNSEmailDetails gTEmailTNSEmailDetails;
		
	@Mock
	GTEmailTNSRecipientDetails gTEmailTNSRecipientDetails;
		
		@Before
		public void setUp() throws Exception {
			
			gTEmailTNSEmailDetails = new GTEmailTNSEmailDetails();
			gTEmailTNSEmailDetails.setSenderName("lakshmi");
			gTEmailTNSEmailDetails.setEmailSubject("Email");
			gTEmailTNSEmailDetails.setEmailMessage("Message");
			gTEmailTNSEmailDetails.setRecipientDetails(gTEmailTNSRecipientDetails);

		}

		@Test
		public void getSenderName() {
			Assert.assertEquals("lakshmi", gTEmailTNSEmailDetails.getSenderName());
		}
		
			@Test
		    public void getEmailSubject() throws Exception {
				Assert.assertEquals("Email",gTEmailTNSEmailDetails.getEmailSubject());
		    }
		    
		    @Test
		    public void getEmailMessage() throws Exception {
				Assert.assertEquals("Message",gTEmailTNSEmailDetails.getEmailMessage());
		    }
		    
		    @Test
		    public void getRecipientDetails() throws Exception {
				Assert.assertNotNull(gTEmailTNSEmailDetails.getRecipientDetails());
		    }
	

}
