package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddTrunkToCartRequestTest {

	private AddTrunkToCartRequest addTrunkToCartRequest;
	
	@Before
    public void setUp()
        throws Exception
    {
		addTrunkToCartRequest = new AddTrunkToCartRequest();
		addTrunkToCartRequest.setPartNumber("GTC011");
		addTrunkToCartRequest.setQuantity("1");
		addTrunkToCartRequest.setFromGT("true");
		addTrunkToCartRequest.setDescriptionData("Title");
		addTrunkToCartRequest.setImageURL("https://mattel.com/image");
		addTrunkToCartRequest.setPrintImageURL("printImageURL");
		addTrunkToCartRequest.setRetailStoreId("1322");
    }
	 	
		@Test
	    public void testGetPartNumber() throws Exception {
			Assert.assertEquals("GTC011",addTrunkToCartRequest.getPartNumber());
	    }

	    @Test
	    public void testGetQuantity() throws Exception {
	        Assert.assertEquals("1", addTrunkToCartRequest.getQuantity());
	    }
	    
	    @Test
	    public void testGetFromGT() throws Exception {
			Assert.assertEquals("true",addTrunkToCartRequest.getFromGT());
	    }
	    
	    @Test
	    public void testGetDescriptionData() throws Exception {
			Assert.assertEquals("Title",addTrunkToCartRequest.getDescriptionData());
	    }
	    
	    @Test
	    public void testGetImageURL() throws Exception {
			Assert.assertEquals("https://mattel.com/image",addTrunkToCartRequest.getImageURL());
	    }
	    
	    @Test
	    public void testGetPrintImageURL() throws Exception {
			Assert.assertEquals("printImageURL",addTrunkToCartRequest.getPrintImageURL());
	    }
	    
	    @Test
	    public void testGetRetailStoreId() throws Exception {
			Assert.assertEquals("1322",addTrunkToCartRequest.getRetailStoreId());
	    }
	   
}
