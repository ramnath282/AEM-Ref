
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OrderDetailsTest {

    private OrderDetails impl = null;
    private OrderPayment orderpayment = new OrderPayment();
    private List<OrderItem> orderitems;
    private ShippingDetails billingdetails = new ShippingDetails();
    private ShippingDetails shippingdetails = new ShippingDetails();
    private OrderInfo orderinfo = new OrderInfo();

    @Before
    public void setUp()
        throws Exception
    {
        impl = new OrderDetails();
        impl.setOrderPayment(orderpayment);
        orderitems = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.OrderItem>();
        impl.setOrderItems(orderitems);
        impl.setBillingDetails(billingdetails);
        impl.setShippingDetails(shippingdetails);
        impl.setOrderInfo(orderinfo);
        impl.toString();
    }

    @Test
    public void testGetOrderPayment()
        throws Exception
    {
        Assert.assertEquals(orderpayment, impl.getOrderPayment());
    }

    @Test
    public void testGetBillingDetails()
        throws Exception
    {
        Assert.assertEquals(billingdetails, impl.getBillingDetails());
    }

    @Test
    public void testGetOrderInfo()
        throws Exception
    {
        Assert.assertEquals(orderinfo, impl.getOrderInfo());
    }

    @Test
    public void testGetOrderItems()
        throws Exception
    {
        Assert.assertEquals(orderitems, impl.getOrderItems());
    }

    @Test
    public void testGetShippingDetails()
        throws Exception
    {
        Assert.assertEquals(shippingdetails, impl.getShippingDetails());
    }

}
