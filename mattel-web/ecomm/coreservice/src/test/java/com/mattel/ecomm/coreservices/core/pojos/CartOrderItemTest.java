
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CartOrderItemTest {

    private CartOrderItem impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new CartOrderItem();
        impl.setOrderItemId("dummy_string");
        impl.setPartNumber("dummy_string");
        impl.setQuantity("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetQuantity()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getQuantity());
    }

    @Test
    public void testGetPartNumber()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPartNumber());
    }

    @Test
    public void testGetOrderItemId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOrderItemId());
    }

}
