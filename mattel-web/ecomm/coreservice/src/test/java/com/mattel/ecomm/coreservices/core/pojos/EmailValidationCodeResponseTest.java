
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EmailValidationCodeResponseTest {

    private EmailValidationCodeResponse impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new EmailValidationCodeResponse();
        impl.setEmailValidationProcessStatus(true);
        impl.setUserId("dummy_string");
        impl.toString();
    }

    @Test
    public void testIsEmailValidationProcessStatus()
        throws Exception
    {
        Assert.assertEquals(true, impl.isEmailValidationProcessStatus());
    }

    @Test
    public void testGetUserId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUserId());
    }

}
