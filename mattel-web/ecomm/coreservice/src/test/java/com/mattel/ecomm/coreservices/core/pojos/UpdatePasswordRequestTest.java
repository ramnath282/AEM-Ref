
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UpdatePasswordRequestTest {

    private UpdatePasswordRequest impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new UpdatePasswordRequest();
        impl.setLogonPassword("dummy_string");
        impl.setLogonPasswordVerify("dummy_string");
        impl.setLogonPasswordOld("dummy_string");
        impl.setLogonId("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetLogonPasswordOld()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLogonPasswordOld());
    }

    @Test
    public void testGetLogonPasswordVerify()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLogonPasswordVerify());
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
