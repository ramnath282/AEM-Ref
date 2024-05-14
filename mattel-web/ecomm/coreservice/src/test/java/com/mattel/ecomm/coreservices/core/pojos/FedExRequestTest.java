
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FedExRequestTest {

    private FedExRequest impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new FedExRequest();
        impl.setFirstName("dummy_string");
        impl.setLastName("dummy_string");
        impl.setState("dummy_string");
        impl.setZipCode("dummy_string");
        impl.setIsGT("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetState()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getState());
    }

    @Test
    public void testGetZipCode()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getZipCode());
    }

    @Test
    public void testGetFirstName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getFirstName());
    }

    @Test
    public void testGetLastName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLastName());
    }

    @Test
    public void testGetIsGT()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getIsGT());
    }

}
