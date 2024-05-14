package com.mattel.ecomm.coreservices.core.pojos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PDPCollectiveBundlePriceListTest {

  PDPCollectiveBundlePriceList pdpCollectiveBundlePriceList;

  @Before
  public void setUp() {
    pdpCollectiveBundlePriceList = new PDPCollectiveBundlePriceList();
    pdpCollectiveBundlePriceList.setParentPartNumber("06BUN12");
    PDPCollectiveProductPriceList pdpCollectiveProductPriceList = new PDPCollectiveProductPriceList();
    List<UnitPrice> unitPriceList = new ArrayList<>();
    UnitPrice unitPrice = new UnitPrice();
    Map<String, String> price = new HashMap<String, String>();
    price.put("key1", "value1");
    unitPrice.setPrice(price);
    Map<String, String> quantity = new HashMap<>();
    unitPrice.setQuantity(quantity);
    unitPriceList.add(unitPrice);
    List<UnitPrice> listPriceList = new ArrayList<>();
    listPriceList.add(unitPrice);
    pdpCollectiveProductPriceList.setListPrice(listPriceList);
    pdpCollectiveProductPriceList.setUnitPrice(unitPriceList);
    pdpCollectiveProductPriceList.setPartNumber("GLM12");
    List<PDPCollectiveProductPriceList> componentPriceDetails = new ArrayList<PDPCollectiveProductPriceList>();
    componentPriceDetails.add(pdpCollectiveProductPriceList);
    pdpCollectiveBundlePriceList.setComponentPriceDetails(componentPriceDetails);
  }

  @Test
  public void testPDPCollectiveBundlePriceList() {
    Assert.assertNotNull(pdpCollectiveBundlePriceList.getComponentPriceDetails());
    Assert.assertEquals("06BUN12", pdpCollectiveBundlePriceList.getParentPartNumber());
    Assert.assertNotNull(pdpCollectiveBundlePriceList.toString());
    Assert.assertNotNull(
        pdpCollectiveBundlePriceList.getComponentPriceDetails().get(0).getPartNumber());
    Assert.assertNotNull(
        pdpCollectiveBundlePriceList.getComponentPriceDetails().get(0).getListPrice());
    Assert.assertNotNull(
        pdpCollectiveBundlePriceList.getComponentPriceDetails().get(0).getUnitPrice());
    Assert.assertNotNull(pdpCollectiveBundlePriceList.getComponentPriceDetails().get(0).toString());
    Assert.assertNotNull(pdpCollectiveBundlePriceList.getComponentPriceDetails().get(0)
        .getUnitPrice().get(0).getPrice());
    Assert.assertNotNull(pdpCollectiveBundlePriceList.getComponentPriceDetails().get(0)
        .getUnitPrice().get(0).getQuantity());
    Assert.assertNotNull(pdpCollectiveBundlePriceList.getComponentPriceDetails().get(0)
        .getUnitPrice().get(0).toString());
  }

}
