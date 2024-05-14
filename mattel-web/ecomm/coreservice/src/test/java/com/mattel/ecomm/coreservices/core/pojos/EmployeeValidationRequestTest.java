package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EmployeeValidationRequestTest {

  private EmployeeValidationRequest employeeValidationRequest;

  @Before
  public void setUp() {
    employeeValidationRequest = new EmployeeValidationRequest();
    employeeValidationRequest.setLangId("en");
  }

  @Test
  public void test() {
    Assert.assertNotNull(employeeValidationRequest.toString());
    Assert.assertNotNull(employeeValidationRequest.getLangId());
    Assert.assertEquals("en", employeeValidationRequest.getLangId());
  }

}
