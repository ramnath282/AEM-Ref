package com.mattel.ecomm.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LogInPojoTest {

  private LogInPojo logInPojo = null;

  @Before
  public void setUp() throws Exception {
    logInPojo = new LogInPojo();
    logInPojo.setDescriptionPointStr("descriptionPointStr");
  }

  @Test
  public void test() {
    Assert.assertEquals("descriptionPointStr", logInPojo.getDescriptionPointStr());
  }

}
