
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ErrorResponseTest {

    private ErrorResponse impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new ErrorResponse();
        impl.setErrorKey("dummy_string");
        impl.setErrorMessage("dummy_string");
        impl.setErrorCode("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetErrorCode()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getErrorCode());
    }

    @Test
    public void testGetErrorKey()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getErrorKey());
    }

    @Test
    public void testGetErrorMessage()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getErrorMessage());
    }

}
