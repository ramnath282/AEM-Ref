
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UpdateDefaultAddressRequestTest {

    private UpdateDefaultAddressRequest impl = null;
    private String[] addressline = new String[] {};

    @Before
    public void setUp()
        throws Exception
    {
        impl = new UpdateDefaultAddressRequest();
        impl.setFirstName("dummy_string");
        impl.setLastName("dummy_string");
        impl.setAddressType("dummy_string");
        impl.setZipCode("dummy_string");
        impl.setAddressLine(addressline);
        impl.setCountry("dummy_string");
        impl.setPhone1("dummy_string");
        impl.setPrimary("dummy_string");
        impl.setCity("dummy_string");
        impl.setState("dummy_string");
        impl.setAddressField2("dummy_string");
        impl.setAddressId("dummy_string");
        impl.setOrganizationUnitName("dummy_string");
        impl.setBillingCodeType("dummy_string");
        impl.toString();
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
    public void testGetAddressType()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getAddressType());
    }

    @Test
    public void testGetPrimary()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPrimary());
    }

    @Test
    public void testGetAddressField2()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getAddressField2());
    }

    @Test
    public void testGetAddressLine()
        throws Exception
    {
        Assert.assertArrayEquals(addressline, impl.getAddressLine());
    }

    @Test
    public void testGetLastName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLastName());
    }

    @Test
    public void testGetState()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getState());
    }

    @Test
    public void testGetAddressId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getAddressId());
    }

    @Test
    public void testGetPhone1()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPhone1());
    }

    @Test
    public void testGetFirstName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getFirstName());
    }

    @Test
    public void testGetCity()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCity());
    }

    @Test
    public void testGetBillingCodeType()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getBillingCodeType());
    }

}
