package com.mattel.ecomm.coreservices.core.enums;

import org.junit.Assert;
import org.junit.Test;

public class ErrorCodeMappingTest {
    @Test
    public void testGetErrorCode() throws Exception {
        Assert.assertEquals(1003, ErrorCodeMapping.CREDENTIALSMISMATCH.getErrorCode());
    }

    @Test
    public void testGetErrorMessage() throws Exception {
        Assert.assertEquals("Login id or password is incorrect", ErrorCodeMapping.CREDENTIALSMISMATCH.getErrorMessage());
    }
}
