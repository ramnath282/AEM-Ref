
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ValidateAddressRequestTest {

    private ValidateAddressRequest impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new ValidateAddressRequest();
        impl.setAddressLine1("dummy_string");
        impl.setAddressLine2("dummy_string");
        impl.setAddressLine3("dummy_string");
        impl.setAddressLine4("dummy_string");
        impl.setCity("dummy_string");
        impl.setState("dummy_string");
        impl.setZipCode("dummy_string");
        impl.setCountry("dummy_string");
        impl.setOrganizationUnitName("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetAddressLine4()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getAddressLine4());
    }

    @Test
    public void testGetAddressLine2()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getAddressLine2());
    }

    @Test
    public void testGetAddressLine3()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getAddressLine3());
    }

    @Test
    public void testGetState()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getState());
    }

    @Test
    public void testGetOrganizationUnitName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOrganizationUnitName());
    }

    @Test
    public void testGetZipCode()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getZipCode());
    }

    @Test
    public void testGetCountry()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCountry());
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
