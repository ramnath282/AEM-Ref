
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ShippingDetailsTest {

    private ShippingDetails impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new ShippingDetails();
        impl.setCountry("dummy_string");
        impl.setFirstName("dummy_string");
        impl.setLastName("dummy_string");
        impl.setZipCode("dummy_string");
        impl.setCity("dummy_string");
        impl.setPhone("dummy_string");
        impl.setAddressLine1("dummy_string");
        impl.setAddressLine2("dummy_string");
        impl.setState("dummy_string");
        impl.setPartyId("dummy_string");
        impl.setEmail("dummy_string");
        impl.setShippingMethod("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetPhone()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPhone());
    }

    @Test
    public void testGetPartyId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPartyId());
    }

    @Test
    public void testGetAddressLine2()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getAddressLine2());
    }

    @Test
    public void testGetState()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getState());
    }

    @Test
    public void testGetCountry()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCountry());
    }

    @Test
    public void testGetZipCode()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getZipCode());
    }

    @Test
    public void testGetEmail()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getEmail());
    }

    @Test
    public void testGetShippingMethod()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getShippingMethod());
    }

    @Test
    public void testGetFirstName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getFirstName());
    }

    @Test
    public void testGetLastName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLastName());
    }

    @Test
    public void testGetAddressLine1()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getAddressLine1());
    }

    @Test
    public void testGetCity()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCity());
    }

}
