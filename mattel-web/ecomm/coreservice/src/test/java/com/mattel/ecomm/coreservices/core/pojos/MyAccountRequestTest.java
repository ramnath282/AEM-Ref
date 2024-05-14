
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MyAccountRequestTest {

    private MyAccountRequest impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new MyAccountRequest();
        impl.setWcToken("dummy_string");
        impl.setWcTrustedToken("dummy_string");
        impl.setEndPoint("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetWcToken()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getWcToken());
    }

    @Test
    public void testGetEndPoint()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getEndPoint());
    }

    @Test
    public void testGetWcTrustedToken()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getWcTrustedToken());
    }

}
