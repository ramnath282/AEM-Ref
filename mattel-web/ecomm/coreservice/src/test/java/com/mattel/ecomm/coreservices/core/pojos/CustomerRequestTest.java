
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CustomerRequestTest {

    private CustomerRequest impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new CustomerRequest();
        impl.setShopifyCustomerID("dummy_string");
        impl.setIdentityAccessToken("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetShopifyCustomerID()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getShopifyCustomerID());
    }

    @Test
    public void testGetIdentityAccessToken()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getIdentityAccessToken());
    }

}
