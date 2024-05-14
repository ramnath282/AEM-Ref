package com.mattel.ecomm.coreservices.core.enums;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class DataImporterErrorCodeTest {

  @Test
  public void testGetErrorMessage() {
    Assert.assertEquals(
        new String("Unable to create asset"),
        DataImporterErrorCode.ASSERT_MANAGER_CREATE_ASSET_FAILURE.getErrorMessage());
  }

}
