package com.mattel.global.core.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.global.core.exceptions.ServiceException;

@RunWith(PowerMockRunner.class)
public class ResponseFormatGetterTest {

  @InjectMocks
  private ResponseFormatGetter responseFormatGetter;

  @Mock
  private ServiceException serviceException;

  @Before
  public void setup() throws Exception {
    Mockito.when(serviceException.getErrorKey()).thenReturn("404");
    Mockito.when(serviceException.getErrorMessage()).thenReturn("Page not found");
  }

  @SuppressWarnings("static-access")
  @Test
  public void testInit() {
    responseFormatGetter.getErrorJson(serviceException);
  }

}
