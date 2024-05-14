package com.mattel.ecomm.coreservices.core.pojos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DHAddCartResponseTest {

    DHAddCartResponse dhAddCartResponse;

    @Before
    public void setUp() {
        dhAddCartResponse = new DHAddCartResponse();
        dhAddCartResponse.setOrderId("orderId");
        List<DHOrderItem> orderItem = new ArrayList<>();
        DHOrderItem dhOrderItem = new DHOrderItem();
        dhOrderItem.setOrderItemId("ItemorderItem");
        dhOrderItem.setPartNumber("DHRD");
        Map<String, Object> additionalProperties = new HashMap<>();
        dhOrderItem.setAdditionalProperty("", additionalProperties);
        orderItem.add(dhOrderItem);
        dhAddCartResponse.setOrderItem(orderItem);

    }

    @Test
    public void testAddToCartResponse() {
        Assert.assertNotNull(dhAddCartResponse.getOrderId());
        Assert.assertNotNull(dhAddCartResponse.getAdditionalProperties());
        Assert.assertNotNull(dhAddCartResponse.getOrderItem());
        Assert.assertEquals("ItemorderItem", dhAddCartResponse.getOrderItem().get(0).getOrderItemId());
        Assert.assertEquals("DHRD", dhAddCartResponse.getOrderItem().get(0).getPartNumber());
        Assert.assertNotNull(dhAddCartResponse.getOrderItem().get(0).getAdditionalProperties());
    }

}
