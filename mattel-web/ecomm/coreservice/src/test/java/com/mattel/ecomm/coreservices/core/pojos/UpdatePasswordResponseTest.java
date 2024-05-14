
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UpdatePasswordResponseTest {

    private UpdatePasswordResponse impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new UpdatePasswordResponse();
        impl.setViewTaskName("dummy_string");
        impl.setUserId("dummy_string");
        impl.setAddressId("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetAddressId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getAddressId());
    }

    @Test
    public void testGetViewTaskName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getViewTaskName());
    }

    @Test
    public void testGetUserId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUserId());
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
