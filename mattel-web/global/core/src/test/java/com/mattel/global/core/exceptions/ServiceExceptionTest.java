package com.mattel.global.core.exceptions;

import com.mattel.global.core.constants.Constant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ServiceExceptionTest {

    private ServiceException serviceException;

    @Before
    public void setUp() throws Exception {
        serviceException = new ServiceException(Constant.IO_ERROR_KEY, "IO Exception");
    }

    @Test
    public void testGetErrorKey() throws Exception {
        Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
    }

    @Test
    public void testGetErrorMessage() throws Exception {
        Assert.assertEquals("IO Exception", serviceException.getErrorMessage());
    }
	
	@Test
	public void testServiceExceptionWithCause() throws Exception {
    final Throwable cause = new Throwable("test exception");
    serviceException = new ServiceException("IO Exception", cause, "IO_ERROR_KEY");

    Assert.assertEquals("IO_ERROR_KEY", serviceException.getErrorKey());
    Assert.assertEquals("IO Exception", serviceException.getErrorMessage());
    Assert.assertEquals(cause, serviceException.getCause());
  }


}