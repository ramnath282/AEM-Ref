package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PDPCollectiveOfferPriceRequestTest {

  PDPCollectiveOfferPriceRequest pdpCollectiveOfferPriceRequest;

  @Before
  public void setUp() {
    pdpCollectiveOfferPriceRequest = new PDPCollectiveOfferPriceRequest();
    pdpCollectiveOfferPriceRequest.setPartNumbers("06BUN12");
  }

  @Test
  public void testPartNumber() {
    Assert.assertNotNull(pdpCollectiveOfferPriceRequest.getPartNumbers());
    Assert.assertNotNull(pdpCollectiveOfferPriceRequest.toString());
  }

  @Test
  public void testPartNumberWithNull() {
    pdpCollectiveOfferPriceRequest.setPartNumbers(null);
    Assert.assertNull(pdpCollectiveOfferPriceRequest.getPartNumbers());
  }

}
