package com.mattel.ecomm.core.utils;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.pojos.ProductAssociation;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Association;

public class ProductAddOnUtilsTest {
  
  @Test
  public void testIsAddOn() {
    final ProductAssociation productAssociation = new ProductAssociation();

    productAssociation.setAssociationType(Constant.ADD_ON_ASSOCIATION_TYPE);
    Assert.assertTrue(ProductAddOnUtils.isAddOn(Arrays.asList(productAssociation)));
  }

  @Test
  public void testIsNotAddOn() {
    final ProductAssociation productAssociation = new ProductAssociation();

    productAssociation.setAssociationType("QUICK_SELL");
    Assert.assertFalse(ProductAddOnUtils.isAddOn(Arrays.asList(productAssociation)));
  }

  @Test
  public void testIsNotAssociation() {
    Assert.assertFalse(ProductAddOnUtils.isAddOn(null));
  }

  @Test
  public void testHasAddOn() {
    final Association association = new Association();
    association.setAssociation_type(Constant.ADD_ON_ASSOCIATION_TYPE);
    Assert.assertTrue(ProductAddOnUtils.hasAddOn(Arrays.asList(association)));
  }
  
  @Test
  public void testNoHasAddOn() {
    final Association association = new Association();
    association.setAssociation_type("QUICK_SELL");
    Assert.assertFalse(ProductAddOnUtils.hasAddOn(Arrays.asList(association)));
  }
  
  @Test
  public void testHasNotAssociation() {
    Assert.assertFalse(ProductAddOnUtils.hasAddOn(null));
  }
  
}
