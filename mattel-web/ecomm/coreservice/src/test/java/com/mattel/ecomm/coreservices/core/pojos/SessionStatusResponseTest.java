
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SessionStatusResponseTest {

    private SessionStatusResponse impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new SessionStatusResponse();
        impl.setStatus("dummy_string");
        impl.setType("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetType()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getType());
    }

    @Test
    public void testGetStatus()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getStatus());
    }

}
