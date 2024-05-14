package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GiftCardMessageValidityTest {
  GiftCardMessageValidity giftCardMessageValidity;

  @Before
  public void setUp() {
    giftCardMessageValidity = new GiftCardMessageValidity();
    giftCardMessageValidity.setIsNameValid(true);
  }

  @Test
  public void testGiftCardMessageValidity() {
    Assert.assertNotNull(giftCardMessageValidity.toString());
    Assert.assertTrue(giftCardMessageValidity.isIsNameValid());
  }

}
