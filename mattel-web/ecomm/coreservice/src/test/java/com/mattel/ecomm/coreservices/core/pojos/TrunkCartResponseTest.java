package com.mattel.ecomm.coreservices.core.pojos;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TrunkCartResponseTest {

	private final List<String> orderId = Arrays.asList("100234578");
	private final List<String> itemId = Arrays.asList("132345678");
	private TrunkCartResponse trunkCartResponse;
	@Before
	public void setUp() throws Exception {
		trunkCartResponse = new TrunkCartResponse();
		trunkCartResponse.setOrderId(orderId);
		trunkCartResponse.setOrderItemId(itemId);
	}

	@Test
	public void testGetOrderId() {
		Assert.assertEquals(orderId, trunkCartResponse.getOrderId());
	}
	
	@Test
	public void testGetItemId() {
		Assert.assertEquals(itemId, trunkCartResponse.getOrderItemId());
	}

}
