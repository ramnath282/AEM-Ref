package com.mattel.ecomm.coreservices.core.enums;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class ServiceDefinationsTest {

  @Test
  public void testToString() {
    Assert.assertEquals(
        new String("https://mdev.americangirl.com/wcs/resources/store/10651"),
        ServiceDefinations.DOMAIN.toString());
  }
  
  @Test
  public void testToStringLogin() {
    Assert.assertEquals(
        new String("/loginidentity?responseFormat=json"),
        ServiceDefinations.LOGIN.toString());
  }

}
