
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ResetPasswordResponseTest {

    private ResetPasswordResponse impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new ResetPasswordResponse();
        impl.setPasswordChangeStatus(true);
        impl.toString();
    }

    @Test
    public void testIsPasswordChangeStatus()
        throws Exception
    {
        Assert.assertEquals(true, impl.isPasswordChangeStatus());
    }

}
