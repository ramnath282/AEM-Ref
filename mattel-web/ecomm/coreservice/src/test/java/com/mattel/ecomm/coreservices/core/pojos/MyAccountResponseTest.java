
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MyAccountResponseTest {

    private MyAccountResponse impl = null;
    private String[] contact = new String[] {};

    @Before
    public void setUp()
        throws Exception
    {
        impl = new MyAccountResponse();
        impl.setLastName("dummy_string");
        impl.setRegistrationStatus("dummy_string");
        impl.setResourceId("dummy_string");
        impl.setPreferredCurrency("dummy_string");
        impl.setDistinguishedName("dummy_string");
        impl.setOrgizationId("dummy_string");
        impl.setAddressId("dummy_string");
        impl.setPhone1("dummy_string");
        impl.setAccountStatus("dummy_string");
        impl.setEmail1("dummy_string");
        impl.setProfileType("dummy_string");
        impl.setContact(contact);
        impl.toString();
    }

    @Test
    public void testGetProfileType()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getProfileType());
    }

    @Test
    public void testGetDistinguishedName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getDistinguishedName());
    }

    @Test
    public void testGetRegistrationStatus()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getRegistrationStatus());
    }

    @Test
    public void testGetResourceId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getResourceId());
    }

    @Test
    public void testGetAddressId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getAddressId());
    }

    @Test
    public void testGetPreferredCurrency()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPreferredCurrency());
    }

    @Test
    public void testGetEmail1()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getEmail1());
    }

    @Test
    public void testGetAccountStatus()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getAccountStatus());
    }

    @Test
    public void testGetOrgizationId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOrgizationId());
    }

    @Test
    public void testGetPhone1()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPhone1());
    }

    @Test
    public void testGetLastName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLastName());
    }

    @Test
    public void testGetContact()
        throws Exception
    {
        Assert.assertArrayEquals(contact, impl.getContact());
    }

}
