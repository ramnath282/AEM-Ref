package com.mattel.ecomm.coreservices.core.exceptions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mattel.ecomm.coreservices.core.enums.ErrorCodeMapping;

public class BusinessExceptionTest {
    private BusinessException businessException;

    @Before
    public void setUp() throws Exception {
        businessException = new BusinessException(ErrorCodeMapping.CREDENTIALSMISMATCH);
    }

    @Test
    public void testGetErrorCode() throws Exception {
        Assert.assertEquals(ErrorCodeMapping.CREDENTIALSMISMATCH.getErrorCode(), businessException.getErrorCode());
    }

    @Test
    public void testGetErrorMessage() throws Exception {
        Assert.assertEquals(ErrorCodeMapping.CREDENTIALSMISMATCH.getErrorMessage(),
                businessException.getErrorMessage());
    }
}
