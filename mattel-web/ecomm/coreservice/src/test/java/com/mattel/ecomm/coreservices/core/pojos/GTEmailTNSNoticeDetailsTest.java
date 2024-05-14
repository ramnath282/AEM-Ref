package com.mattel.ecomm.coreservices.core.pojos;



import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class GTEmailTNSNoticeDetailsTest {

	
    @Mock
    GTEmailTNSEventNoticeData gTEmailTNSEventNoticeData;
    
    
    private GTEmailTNSNoticeDetails gTEmailTNSNoticeDetails;

	@Before
	public void setUp() throws Exception {
		
		gTEmailTNSNoticeDetails = new GTEmailTNSNoticeDetails();
		gTEmailTNSNoticeDetails.setNoticeName("name");
		gTEmailTNSNoticeDetails.setOrganizationId("301");
		gTEmailTNSNoticeDetails.setOriginatingSystemCode("AEM");
		gTEmailTNSNoticeDetails.setCreateDate("date");
		gTEmailTNSNoticeDetails.setEventNoticeData(gTEmailTNSEventNoticeData);

	}


	@Test
	public void testGetInstructions() {
		Assert.assertEquals("name", gTEmailTNSNoticeDetails.getNoticeName());
	}

	@Test
	public void testGetSKU() {
		Assert.assertEquals("301", gTEmailTNSNoticeDetails.getOrganizationId());
	}
	
		@Test
	    public void testGetGIFT_TRUNK_LETTER_EDITED() throws Exception {
			Assert.assertEquals("AEM",gTEmailTNSNoticeDetails.getOriginatingSystemCode());
	    }
	    
	    @Test
	    public void testGetGIFT_TRUNK_LETTER_TEXT() throws Exception {
			Assert.assertEquals("date",gTEmailTNSNoticeDetails.getCreateDate());
	    }
	    
	    @Test
	    public void testGetGIFT_TRUNK_RECIPIENT() throws Exception {
			Assert.assertNotNull(gTEmailTNSNoticeDetails.getEventNoticeData());
	    }


}
