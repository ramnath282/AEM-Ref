
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OrderPaymentTest {

    private OrderPayment impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new OrderPayment();
        impl.setCreditCardNumber("dummy_string");
        impl.setCreditCardExpiry("dummy_string");
        impl.setCreditCardType("dummy_string");
        impl.setPaymentAmount("dummy_string");
        impl.setPaymentType("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetCreditCardNumber()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCreditCardNumber());
    }

    @Test
    public void testGetCreditCardType()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCreditCardType());
    }

    @Test
    public void testGetPaymentAmount()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPaymentAmount());
    }

    @Test
    public void testGetPaymentType()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPaymentType());
    }

    @Test
    public void testGetCreditCardExpiry()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCreditCardExpiry());
    }

}
