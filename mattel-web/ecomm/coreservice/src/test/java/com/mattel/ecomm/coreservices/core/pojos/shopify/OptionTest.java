package com.mattel.ecomm.coreservices.core.pojos.shopify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OptionTest {
  private Option option;

  @Before
  public void setUp() throws Exception {
    option = new Option();
  }

  @Test
  public void testGetterSetter() {
    List<String> values = new ArrayList<String>();
    option.setId(1234);
    option.setName("name");
    option.setPosition(1);
    option.setProduct_id(123456);
    option.setValues(values);

    Assert.assertEquals(1234, option.getId());
    Assert.assertEquals("name", option.getName());
    Assert.assertEquals(1, option.getPosition());
    Assert.assertEquals(123456, option.getProduct_id());
    Assert.assertEquals(values, option.getValues());
    option.toString();
  }
}
