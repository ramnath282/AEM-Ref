package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EmployeeValidationResponseTest {

  EmployeeValidationResponse employeeValidationResponse;

  @Before
  public void setUp() {
    employeeValidationResponse = new EmployeeValidationResponse();
    employeeValidationResponse.setRedirecturl("redirectUrl");
    employeeValidationResponse.setCatalogId("catalogId");
    employeeValidationResponse.setPageId("pageID");
    employeeValidationResponse.setViewTaskName("viewTaskName");
    employeeValidationResponse.setStoreId("storeId");
    employeeValidationResponse.setIsErrorPresent("Y");
  }

  @Test
  public void testEmployeeValidationResponse() {

    Assert.assertEquals("redirectUrl", employeeValidationResponse.getRedirecturl());
    Assert.assertEquals("catalogId", employeeValidationResponse.getCatalogId());
    Assert.assertEquals("pageID", employeeValidationResponse.getPageId());
    Assert.assertEquals("viewTaskName", employeeValidationResponse.getViewTaskName());
    Assert.assertEquals("storeId", employeeValidationResponse.getStoreId());
    Assert.assertEquals("Y", employeeValidationResponse.getIsErrorPresent());
    Assert.assertNotNull(employeeValidationResponse.toString());

  }

}
