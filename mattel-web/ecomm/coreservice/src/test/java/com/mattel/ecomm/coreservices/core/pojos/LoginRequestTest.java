
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LoginRequestTest {

    private LoginRequest impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new LoginRequest();
        impl.setLogonId("dummy_string");
        impl.setLogonPassword("dummy_string");
        impl.setRedirectURL("dummy_string");
        impl.toString();
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
    
    @Test
    public void testGetRedirectURL()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getRedirectURL());
    }

}
