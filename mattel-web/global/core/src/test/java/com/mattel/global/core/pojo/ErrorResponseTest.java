package com.mattel.global.core.pojo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ErrorResponseTest {

    private ErrorResponse errorResponse = null;

    @Before
    public void setUp()
    {
        errorResponse = new ErrorResponse();
        errorResponse.setErrorKey("dummy_string");
        errorResponse.setErrorMessage("dummy_string");
        errorResponse.setErrorCode("dummy_string");
        errorResponse.toString();
    }

    @Test
    public void testGetErrorCode()
    {
        Assert.assertEquals("dummy_string", errorResponse.getErrorCode());
    }

    @Test
    public void testGetErrorKey()
    {
        Assert.assertEquals("dummy_string", errorResponse.getErrorKey());
    }

    @Test
    public void testGetErrorMessage()
    {
        Assert.assertEquals("dummy_string", errorResponse.getErrorMessage());
    }

}
