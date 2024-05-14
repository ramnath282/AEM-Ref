
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MiniCartResponseTest {

    private MiniCartResponse impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new MiniCartResponse();
        impl.setCustomerSegment("dummy_string");
        impl.setCartQuantity("dummy_string");
        impl.setOrderCurrency("dummy_string");
        impl.setOrderTotal("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetOrderCurrency()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOrderCurrency());
    }

    @Test
    public void testGetOrderTotal()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOrderTotal());
    }

    @Test
    public void testGetCartQuantity()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCartQuantity());
    }

    @Test
    public void testGetCustomerSegment()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCustomerSegment());
    }

}
