package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddDefaultAddressResponseTest {
    private final String addressId = "1234";

    private final String billAddressId = "1111";

    private final String userId = "5555";
    private AddDefaultAddressResponse addDefaultAddressResponse;

    @Before
    public void createAddDefaultAddressResponse() throws Exception {
        addDefaultAddressResponse = new AddDefaultAddressResponse();
        addDefaultAddressResponse.setAddressId(addressId);
        addDefaultAddressResponse.setBillAddressId(billAddressId);
        addDefaultAddressResponse.setUserId(userId);
    }

    @Test
    public void testGetUserId() throws Exception {
        Assert.assertEquals(userId, addDefaultAddressResponse.getUserId());
    }

    @Test
    public void testGetBillAddressId() throws Exception {
        Assert.assertEquals("1111", addDefaultAddressResponse.getBillAddressId());
    }

    @Test
    public void testGetAddressId() throws Exception {
        Assert.assertEquals("1234", addDefaultAddressResponse.getAddressId());
    }
}
