
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TokenValidationRequestTest {

    private TokenValidationRequest impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new TokenValidationRequest();
        impl.setWcToken("dummy_string");
        impl.setWcTrustedToken("dummy_string");
        impl.setValidationEndPoint("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetWcToken()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getWcToken());
    }

    @Test
    public void testGetWcTrustedToken()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getWcTrustedToken());
    }

    @Test
    public void testGetValidationEndPoint()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getValidationEndPoint());
    }

}
