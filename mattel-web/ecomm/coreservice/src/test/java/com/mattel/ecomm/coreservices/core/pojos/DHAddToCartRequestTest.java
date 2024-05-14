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
public class DHAddToCartRequestTest {

    DHAddToCartRequest dhAddToCartRequest;

    @Before
    public void setUp() {
        dhAddToCartRequest = new DHAddToCartRequest();
        List<DHOrderItemRequest> orderItem = new ArrayList<>();
        List<String> aosItemPartNumber = new ArrayList<>();
        List<String> aosProdPartNumber = new ArrayList<>();
        List<Map<String, String>> notes = new ArrayList<>();
        Map<String, Object> additionalProperties = new HashMap<>();
        dhAddToCartRequest.setOrderId("orderId");
        dhAddToCartRequest.setOrderItem(orderItem);
        dhAddToCartRequest.setXInventoryValidation("inventoryValidation");
        dhAddToCartRequest.setAosItemPartNumber(aosItemPartNumber);
        dhAddToCartRequest.setCalculationUsage("calculationUsage");
        dhAddToCartRequest.setAddFlag("Y");
        dhAddToCartRequest.setUpdateFlag("Y");
        dhAddToCartRequest.setAosProdPartNumber(aosProdPartNumber);
        dhAddToCartRequest.setRetailStoreId("retailStoreId");
        dhAddToCartRequest.setNotes(notes);
        dhAddToCartRequest.setAdditionalProperties(additionalProperties);
    }

    @Test
    public void testDHAddToCartRequest() {
        Assert.assertNotNull(dhAddToCartRequest.getAdditionalProperties());
        Assert.assertNotNull(dhAddToCartRequest.getNotes());
        Assert.assertNotNull(dhAddToCartRequest.getAosProdPartNumber());
        Assert.assertEquals("orderId", dhAddToCartRequest.getOrderId());
        Assert.assertNotNull(dhAddToCartRequest.getOrderItem());
        Assert.assertNotNull(dhAddToCartRequest.getXInventoryValidation());
        Assert.assertNotNull(dhAddToCartRequest.getAosItemPartNumber());
        Assert.assertNotNull(dhAddToCartRequest.getCalculationUsage());
        Assert.assertEquals("Y", dhAddToCartRequest.getAddFlag());
        Assert.assertEquals("Y", dhAddToCartRequest.getUpdateFlag());
        Assert.assertNotNull(dhAddToCartRequest.getRetailStoreId());

    }

}
