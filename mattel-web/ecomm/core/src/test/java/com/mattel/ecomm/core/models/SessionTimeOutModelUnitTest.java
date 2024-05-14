
package com.mattel.ecomm.core.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SessionTimeOutModelUnitTest {

    private SessionTimeOutModel impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new SessionTimeOutModel();
        impl.toString();
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
