
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OrderItemTest {

    private OrderItem impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new OrderItem();
        impl.setPromiseDate("dummy_string");
        impl.setTrackingURL("dummy_string");
        impl.setProductSize("dummy_string");
        impl.setDiscount("dummy_string");
        impl.setProductName("dummy_string");
        impl.setProductSKU("dummy_string");
        impl.setSellingPrice("dummy_string");
        impl.setOrderShipping("dummy_string");
        impl.setImageURL("dummy_string");
        impl.setProductColor("dummy_string");
        impl.setBackOrderDate("dummy_string");
        impl.setTrackingNumber("dummy_string");
        impl.setQuantity("dummy_string");
        impl.setShippingMethod("dummy_string");
        impl.setDollDesignId("dummy_string");
        impl.setOrderTax("dummy_string");
        impl.setDescriptiveText("dummy_string");
        impl.setThumbNail("dummy_string");
        impl.setOrderShippingTax("dummy_string");
        impl.setOrderItemNo("dummy_string");
        impl.setListPrice("dummy_string");
        impl.setStatus("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetTrackingNumber()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getTrackingNumber());
    }

    @Test
    public void testGetQuantity()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getQuantity());
    }

    @Test
    public void testGetStatus()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getStatus());
    }

    @Test
    public void testGetProductName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getProductName());
    }

    @Test
    public void testGetOrderShipping()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOrderShipping());
    }

    @Test
    public void testGetDollDesignId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getDollDesignId());
    }

    @Test
    public void testGetTrackingURL()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getTrackingURL());
    }

    @Test
    public void testGetBackOrderDate()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getBackOrderDate());
    }

    @Test
    public void testGetSellingPrice()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getSellingPrice());
    }

    @Test
    public void testGetPromiseDate()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPromiseDate());
    }

    @Test
    public void testGetProductColor()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getProductColor());
    }

    @Test
    public void testGetDescriptiveText()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getDescriptiveText());
    }

    @Test
    public void testGetDiscount()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getDiscount());
    }

    @Test
    public void testGetListPrice()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getListPrice());
    }

    @Test
    public void testGetProductSize()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getProductSize());
    }

    @Test
    public void testGetImageURL()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getImageURL());
    }

    @Test
    public void testGetOrderShippingTax()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOrderShippingTax());
    }

    @Test
    public void testGetProductSKU()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getProductSKU());
    }

    @Test
    public void testGetShippingMethod()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getShippingMethod());
    }

    @Test
    public void testGetThumbNail()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getThumbNail());
    }

    @Test
    public void testGetOrderItemNo()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOrderItemNo());
    }

    @Test
    public void testGetOrderTax()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOrderTax());
    }

}
