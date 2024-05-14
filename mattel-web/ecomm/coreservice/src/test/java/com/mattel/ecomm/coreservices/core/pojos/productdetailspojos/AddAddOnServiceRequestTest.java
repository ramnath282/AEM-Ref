package com.mattel.ecomm.coreservices.core.pojos.productdetailspojos;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddAddOnServiceRequestTest {
  private AddAddOnServiceRequest impl;

  @Before
  public void setUp() throws Exception {
    impl = new AddAddOnServiceRequest();
    impl.setAddFlag("true");
    impl.setAosItemPartNumber(Arrays.asList("EP"));
    impl.setAosItemQuantitytoBeAddedArray("1");
    impl.setAosProdPartNumber(Arrays.asList("FRH89"));
    impl.setAosProdQuantitytoBeAddedArray("1");
    impl.setCalculateOrder("1");
    impl.setCalculationUsage("-1,-2,-5,-6,-7,-21");
    impl.setCatalogId("10601");
    impl.setLangId("-1");
    impl.setMattelOrderType("Standard Orders");
    impl.setNumAddOnServicesItems("1");
    impl.setNumAddOnServicesProducts("1");
    impl.setParCatentryId("");
    impl.setParentOrderItemId("1473180015");
    impl.setParentPartNumber("FRH89");
    impl.setParentQuantity("1");
    impl.setProductoperationType("addon");
    impl.setSourcePage("");
    impl.setStoreId("10651");
    impl.setUpdateFlag("false");
    impl.setUrl("AjaxOrderItemDisplayView");
  }

  @Test
  public void testGetLangId() {
    Assert.assertEquals("-1", impl.getLangId());
  }

  @Test
  public void testGetNumAddOnServicesItems() {
    Assert.assertEquals("1", impl.getNumAddOnServicesItems());
  }

  @Test
  public void testGetNumAddOnServicesProducts() {
    Assert.assertEquals("1", impl.getNumAddOnServicesProducts());
  }

  @Test
  public void testGetParentPartNumber() {
    Assert.assertEquals("FRH89", impl.getParentPartNumber());
  }

  @Test
  public void testGetProductoperationType() {
    Assert.assertEquals("addon", impl.getProductoperationType());
  }

  @Test
  public void testGetUrl() {
    Assert.assertEquals("AjaxOrderItemDisplayView", impl.getUrl());
  }

  @Test
  public void testGetStoreId() {
    Assert.assertEquals("10651", impl.getStoreId());
  }

  @Test
  public void testGetCatalogId() {
    Assert.assertEquals("10601", impl.getCatalogId());
  }

  @Test
  public void testGetAosItemPartNumber() {
    Assert.assertEquals("EP", impl.getAosItemPartNumber().get(0));
  }

  @Test
  public void testGetAosItemQuantitytoBeAddedArray() {
    Assert.assertEquals("1", impl.getAosItemQuantitytoBeAddedArray());
  }

  @Test
  public void testGetParentQuantity() {
    Assert.assertEquals("1", impl.getParentQuantity());
  }

  @Test
  public void testGetParCatentryId() {
    Assert.assertEquals("", impl.getParCatentryId());
  }

  @Test
  public void testGetParentOrderItemId() {
    Assert.assertEquals("1473180015", impl.getParentOrderItemId());
  }

  @Test
  public void testGetSourcePage() {
    Assert.assertEquals("", impl.getSourcePage());
  }

  @Test
  public void testGetMattelOrderType() {
    Assert.assertEquals("Standard Orders", impl.getMattelOrderType());
  }

  @Test
  public void testGetCalculationUsage() {
    Assert.assertEquals("-1,-2,-5,-6,-7,-21", impl.getCalculationUsage());
  }

  @Test
  public void testGetAddFlag() {
    Assert.assertEquals("true", impl.getAddFlag());
  }

  @Test
  public void testGetUpdateFlag() {
    Assert.assertEquals("false", impl.getUpdateFlag());
  }

  @Test
  public void testGetCalculateOrder() {
    Assert.assertEquals("1", impl.getCalculateOrder());
  }

  @Test
  public void testGetAosProdPartNumber() {
    Assert.assertEquals("FRH89", impl.getAosProdPartNumber().get(0));
  }

  @Test
  public void testGetAosProdQuantitytoBeAddedArray() {
    Assert.assertEquals("1", impl.getAosProdQuantitytoBeAddedArray());
  }
}
