
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PaymentInfoRequestTest {

    private PaymentInfoRequest impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new PaymentInfoRequest();
        impl.setBillingAddressId("dummy_string");
        impl.setCardNum("dummy_string");
        impl.setCreditCardId("dummy_string");
        impl.setMonth("dummy_string");
        impl.setNameOnCard("dummy_string");
        impl.setOperation("dummy_string");
        impl.setSaveDefCard("dummy_string");
        impl.setViewName("dummy_string");
        impl.setYear("dummy_string");
        impl.setDefaultChange("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetCardNum()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCardNum());
    }

    @Test
    public void testGetSaveDefCard()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getSaveDefCard());
    }

    @Test
    public void testGetYear()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getYear());
    }

    @Test
    public void testGetDefaultChange()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getDefaultChange());
    }

    @Test
    public void testGetViewName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getViewName());
    }

    @Test
    public void testGetOperation()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOperation());
    }

    @Test
    public void testGetCreditCardId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCreditCardId());
    }

    @Test
    public void testGetMonth()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getMonth());
    }

    @Test
    public void testGetBillingAddressId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getBillingAddressId());
    }

    @Test
    public void testGetNameOnCard()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getNameOnCard());
    }

}
