package com.mattel.ecomm.coreservices.core.exceptions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.pojos.ResponseBody;

public class ServiceExceptionTest {

  private ServiceException serviceException;
  private ResponseBody responseBody = new ResponseBody();
  private Boolean propogateError;
  private Throwable cause;

  @Before
  public void setup() throws Exception {
    cause = new Throwable("test exception");
    propogateError = false;
    responseBody = Mockito.mock(ResponseBody.class);
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
  public void testIsPropogateError() throws Exception {
    Assert.assertEquals(false, serviceException.isPropagateError());
  }

  @Test
  public void testGetResponseBody() throws Exception {
    Assert.assertEquals(null, serviceException.getResponseBody());
  }

  @Test
  public void testServiceExceptionWith_Key_Message_Cause() throws Exception {
    serviceException = new ServiceException(Constant.IO_ERROR_KEY, "IO Exception", cause);
    Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
    Assert.assertEquals("IO Exception", serviceException.getErrorMessage());
    Assert.assertEquals(cause, serviceException.getCause());
  }

  @Test
  public void testServiceExceptionWith_Key_Message_PropogateError() {
    serviceException = new ServiceException(Constant.IO_ERROR_KEY, "IO Exception", propogateError);
    Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
    Assert.assertEquals("IO Exception", serviceException.getErrorMessage());
    Assert.assertEquals(false, serviceException.isPropagateError());
  }

  @Test
  public void testServiceExceptionWith_Key_Message_PropogateError_Cause() {
    serviceException = new ServiceException(Constant.IO_ERROR_KEY, "IO Exception", propogateError,
        cause);
    Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
    Assert.assertEquals("IO Exception", serviceException.getErrorMessage());
    Assert.assertEquals(false, serviceException.isPropagateError());
    Assert.assertEquals(cause, serviceException.getCause());
  }

  @Test
  public void testServiceExceptionWith_Key_ResponseBody_PropogateError() {
    propogateError = true;
    serviceException = new ServiceException(Constant.IO_ERROR_KEY, responseBody, propogateError);
    Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
    Assert.assertEquals(responseBody, serviceException.getResponseBody());
    Assert.assertEquals(true, serviceException.isPropagateError());
  }

  @Test
  public void testServiceExceptionWith_Key_ResponseBody_PropogateError_Cause() {
    propogateError = true;
    serviceException = new ServiceException(Constant.IO_ERROR_KEY, responseBody, propogateError,
        cause);
    Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
    Assert.assertEquals(responseBody, serviceException.getResponseBody());
    Assert.assertEquals(true, serviceException.isPropagateError());
    Assert.assertEquals(cause, serviceException.getCause());
  }
}
