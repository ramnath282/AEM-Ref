
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TokenValidationResponseTest {

    private TokenValidationResponse impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new TokenValidationResponse();
        impl.setRegisterType("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetRegisterType()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getRegisterType());
    }

}
