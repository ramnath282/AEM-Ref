
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LoginResponseTest {

    private LoginResponse impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new LoginResponse();
        impl.setPersonalizationID("dummy_string");
        impl.setWcToken("dummy_string");
        impl.setUserId("dummy_string");
        impl.setWcTrustedToken("dummy_string");
        impl.setMultipassURL("dummy_string");
        impl.setIdentityAccessToken("dummy_string");
        impl.setShopifyCustomerId("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetWcToken()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getWcToken());
    }

    @Test
    public void testGetPersonalizationID()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPersonalizationID());
    }

    @Test
    public void testGetWcTrustedToken()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getWcTrustedToken());
    }

    @Test
    public void testGetUserId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUserId());
    }
    
    @Test
    public void testGetMultipassURL()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getMultipassURL());
    }
    
    @Test
    public void testGetIdentityAccessToken()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getIdentityAccessToken());
    }
    
    @Test
    public void testGetShopifyCustomerId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getShopifyCustomerId());
    }

}
