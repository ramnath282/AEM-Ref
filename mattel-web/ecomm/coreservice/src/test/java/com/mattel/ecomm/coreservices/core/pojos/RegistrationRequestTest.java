
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RegistrationRequestTest {

    private RegistrationRequest impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new RegistrationRequest();
        impl.setFirstName("dummy_string");
        impl.setLastName("dummy_string");
        impl.setStoreId("dummy_string");
        impl.setCatalogId("dummy_string");
        impl.setEmail1("dummy_string");
        impl.setLogonId("dummy_string");
        impl.setLogonIdVerify("dummy_string");
        impl.setLogonPassword("dummy_string");
        impl.setLogonPasswordVerify("dummy_string");
        impl.setPhone1("dummy_string");
        impl.setBirthMonth("dummy_string");
        impl.setBirthDate("dummy_string");
        impl.setBirthYear("dummy_string");
        impl.setDemographicField5("dummy_string");
        impl.setRegistrationFromRewards("dummy_string");
        impl.setSourceName("dummy_string");
        impl.setReceiveEmail("dummy_string");
        impl.setUsersId("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetBirthYear()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getBirthYear());
    }

    @Test
    public void testGetDemographicField5()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getDemographicField5());
    }

    @Test
    public void testGetSourceName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getSourceName());
    }

    @Test
    public void testGetUsersId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUsersId());
    }

    @Test
    public void testGetCatalogId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCatalogId());
    }

    @Test
    public void testGetLastName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLastName());
    }

    @Test
    public void testGetReceiveEmail()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getReceiveEmail());
    }

    @Test
    public void testGetBirthDate()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getBirthDate());
    }

    @Test
    public void testGetRegistrationFromRewards()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getRegistrationFromRewards());
    }

    @Test
    public void testGetEmail1()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getEmail1());
    }

    @Test
    public void testGetBirthMonth()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getBirthMonth());
    }

    @Test
    public void testGetLogonPasswordVerify()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLogonPasswordVerify());
    }

    @Test
    public void testGetLogonIdVerify()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLogonIdVerify());
    }

    @Test
    public void testGetPhone1()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPhone1());
    }

    @Test
    public void testGetStoreId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getStoreId());
    }

    @Test
    public void testGetLogonPassword()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLogonPassword());
    }

    @Test
    public void testGetFirstName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getFirstName());
    }

    @Test
    public void testGetLogonId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLogonId());
    }

}
