
package com.mattel.ecomm.coreservices.core.pojos.productdetailspojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddAddOnServiceResponseTest {

    private AddAddOnServiceResponse impl = null;
    private String[] orderid = new String[] {};
    private String[] orderitemid = new String[] {};

    @Before
    public void setUp()
        throws Exception
    {
        impl = new AddAddOnServiceResponse();
        impl.setParentQuantity("dummy_string");
        impl.setOrderId(orderid);
        impl.setOrderItemId(orderitemid);
        impl.toString();
    }

    @Test
    public void testGetParentQuantity()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getParentQuantity());
    }

    @Test
    public void testGetOrderItemId()
        throws Exception
    {
        Assert.assertArrayEquals(orderitemid, impl.getOrderItemId());
    }

    @Test
    public void testGetOrderId()
        throws Exception
    {
        Assert.assertArrayEquals(orderid, impl.getOrderId());
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
