
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ResetPasswordRequestTest {

    private ResetPasswordRequest impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new ResetPasswordRequest();
        impl.setStoreId("dummy_string");
        impl.setCatalogId("dummy_string");
        impl.setLangId("dummy_string");
        impl.setUrl("dummy_string");
        impl.setLogonId("dummy_string");
        impl.setValidationCode("dummy_string");
        impl.setLogonPasswordOld("dummy_string");
        impl.setLogonPassword("dummy_string");
        impl.setLogonPasswordVerify("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetURL()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUrl());
    }

    @Test
    public void testGetLangId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLangId());
    }

    @Test
    public void testGetLogonPasswordOld()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLogonPasswordOld());
    }

    @Test
    public void testGetValidationCode()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getValidationCode());
    }

    @Test
    public void testGetLogonPasswordVerify()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLogonPasswordVerify());
    }

    @Test
    public void testGetStoreId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getStoreId());
    }

    @Test
    public void testGetCatalogId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCatalogId());
    }

    @Test
    public void testGetLogonPassword()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLogonPassword());
    }

    @Test
    public void testGetLogonId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLogonId());
    }

}
