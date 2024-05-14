package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GTEmailTNSSkuItemTest {

	private GTEmailTNSSkuItem gTEmailTNSSkuItem;

	@Before
	public void setUp() throws Exception {
		gTEmailTNSSkuItem = new GTEmailTNSSkuItem();
		gTEmailTNSSkuItem.setSku("sku");
		gTEmailTNSSkuItem.setItemDescription("itemDescription");
		gTEmailTNSSkuItem.setItemName("itemName");
		
	}


	@Test
	public void testGetSource() {
		Assert.assertEquals("sku", gTEmailTNSSkuItem.getSku());
	}

	@Test
	public void testGetTarget() {
		Assert.assertEquals("itemDescription", gTEmailTNSSkuItem.getItemDescription());
	}
	
		@Test
	    public void testGetcorrelation() throws Exception {
			Assert.assertEquals("itemName",gTEmailTNSSkuItem.getItemName());
	    }
	    

}
