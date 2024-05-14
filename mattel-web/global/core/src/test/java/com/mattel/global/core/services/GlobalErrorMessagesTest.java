package com.mattel.global.core.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import com.mattel.global.core.services.GlobalErrorMessages.Config;

public class GlobalErrorMessagesTest {

  GlobalErrorMessages errorMessages;

  Config config;

  @Before
  public void setUp() {
    errorMessages = new GlobalErrorMessages();
    config = Mockito.mock(GlobalErrorMessages.Config.class);
  }

  @Test
  public void testConfig() {
    errorMessages.setErrorMessagePath("/content/ErrorMessagePath");
    when(config.errorMessagePath()).thenReturn(errorMessages.getErrorMessagePath());
    errorMessages.activate(config);
    assertSame("/content/ErrorMessagePath", config.errorMessagePath());

  }

  @Test
  public void NullErrorPath() {
    errorMessages.setErrorMessagePath(null);
    assertSame(null, errorMessages.getErrorMessagePath());
  }

  @Test
  public void NotNullErrorPath() {
    errorMessages.setErrorMessagePath("/content/ErrorMessagePath");
    assertSame("/content/ErrorMessagePath", errorMessages.getErrorMessagePath());
  }
}
