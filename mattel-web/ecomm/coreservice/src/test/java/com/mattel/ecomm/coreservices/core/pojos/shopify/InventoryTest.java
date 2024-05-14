package com.mattel.ecomm.coreservices.core.pojos.shopify;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InventoryTest {
	private Inventory inventory;

	@Before
	public void setUp() throws Exception {
		inventory = new Inventory();
	}
	
	@Test
	public void testGetterSetter(){
		inventory.setBackorderdate("backorderdate");
		inventory.setInventorystatus("inventorystatus");
		inventory.setPartnumber("partnumber");
		inventory.setProduct_type("product_type");
		
		Assert.assertEquals("backorderdate", inventory.getBackorderdate());
		Assert.assertEquals("inventorystatus", inventory.getInventorystatus());
		Assert.assertEquals("partnumber", inventory.getPartnumber());
		Assert.assertEquals("product_type", inventory.getProduct_type());
		inventory.toString();
	}
}
