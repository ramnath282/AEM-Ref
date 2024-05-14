package com.mattel.ecomm.coreservices.core.pojos;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PDPCollectiveOfferPriceResponseTest {
  PDPCollectiveOfferPriceResponse pdpCollectiveOfferPriceResponse;

  @Before
  public void setUp() {
    pdpCollectiveOfferPriceResponse = new PDPCollectiveOfferPriceResponse();
    PDPCollectivePriceDetails pdpCollectivePriceDetails = new PDPCollectivePriceDetails();
    List<PDPCollectiveBundlePriceList> bundlePriceList = new ArrayList<>();
    pdpCollectivePriceDetails.setBundlePriceList(bundlePriceList);
    List<PDPCollectiveProductPriceList> productPriceList = new ArrayList<>();
    pdpCollectivePriceDetails.setProductPriceList(productPriceList);
    pdpCollectiveOfferPriceResponse.setPriceDetails(pdpCollectivePriceDetails);
  }

  @Test
  public void testPDPCollectiveOfferPriceResponse() {
    Assert.assertNotNull(pdpCollectiveOfferPriceResponse.getPriceDetails());
    Assert.assertNotNull(pdpCollectiveOfferPriceResponse.toString());
    Assert.assertNotNull(pdpCollectiveOfferPriceResponse.getPriceDetails().getBundlePriceList());
    Assert.assertNotNull(pdpCollectiveOfferPriceResponse.getPriceDetails().getProductPriceList());
    Assert.assertNotNull(pdpCollectiveOfferPriceResponse.getPriceDetails().toString());
  }

}
