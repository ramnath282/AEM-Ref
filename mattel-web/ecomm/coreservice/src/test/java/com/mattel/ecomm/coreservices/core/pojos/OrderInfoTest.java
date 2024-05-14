
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OrderInfoTest {

    private OrderInfo impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new OrderInfo();
        impl.setOrderNumber("dummy_string");
        impl.setOrderedFrom("dummy_string");
        impl.setOrderShipping("dummy_string");
        impl.setOrderShippingTax("dummy_string");
        impl.setOrderDiscount("dummy_string");
        impl.setOrderTax("dummy_string");
        impl.setOrderDate("dummy_string");
        impl.setOrderTotal("dummy_string");
        impl.setOrderSubTotal("dummy_string");
        impl.setOrderStatusCodeText("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetOrderSubTotal()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOrderSubTotal());
    }

    @Test
    public void testGetOrderTotal()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOrderTotal());
    }

    @Test
    public void testGetOrderShippingTax()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOrderShippingTax());
    }

    @Test
    public void testGetOrderedFrom()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOrderedFrom());
    }

    @Test
    public void testGetOrderDate()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOrderDate());
    }

    @Test
    public void testGetOrderNumber()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOrderNumber());
    }

    @Test
    public void testGetOrderStatusCodeText()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOrderStatusCodeText());
    }

    @Test
    public void testGetOrderShipping()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOrderShipping());
    }

    @Test
    public void testGetOrderDiscount()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOrderDiscount());
    }

    @Test
    public void testGetOrderTax()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOrderTax());
    }

}
