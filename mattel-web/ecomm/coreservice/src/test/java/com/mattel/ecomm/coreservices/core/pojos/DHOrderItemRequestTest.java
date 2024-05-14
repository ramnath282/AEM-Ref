package com.mattel.ecomm.coreservices.core.pojos;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DHOrderItemRequestTest {

    DHOrderItemRequest dhOrderItemRequest;

    @Before
    public void setUp() {
        dhOrderItemRequest = new DHOrderItemRequest();
        Map<String, Object> additionalProperties = new HashMap<>();
        dhOrderItemRequest.setPartNumber("DHRD");
        dhOrderItemRequest.setAdditionalProperty("key", additionalProperties);
        dhOrderItemRequest.setQuantity("1");
    }

    @Test
    public void testOrderItemRequest() {
        Assert.assertNotNull(dhOrderItemRequest.getAdditionalProperties());
        Assert.assertNotNull(dhOrderItemRequest.getPartNumber());
        Assert.assertEquals("DHRD", dhOrderItemRequest.getPartNumber());
        Assert.assertNotNull(dhOrderItemRequest.getQuantity());

    }

}
