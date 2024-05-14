package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ValidationResultTest {

  ValidationResult validationResult;

  @Before
  public void setUp() {
    validationResult = new ValidationResult();
    validationResult.setSuccess(true);
    validationResult.addErrorMessage("error message");
  }

  @Test
  public void testValidationResult() {
    Assert.assertNotNull(validationResult.toString());
    Assert.assertTrue(validationResult.isSuccess());
    Assert.assertNotNull(validationResult.getErrorMessages());
  }
}
