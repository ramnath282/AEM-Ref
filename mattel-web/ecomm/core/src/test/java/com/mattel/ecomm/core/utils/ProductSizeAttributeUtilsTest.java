package com.mattel.ecomm.core.utils;

import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.pojos.ChildProduct;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class ProductSizeAttributeUtilsTest {
  @Test
  public void testGetProductDefiningAttribute1() throws Exception {
    final ChildProduct childProduct = new ChildProduct();
    final Map<String, Object> definingAttributes = new HashMap<>();

    definingAttributes.put(Constant.CLOTHING_SIZE_DEF_ATTRIBUTE, "xl");
    childProduct.setDefiningAttributes(definingAttributes);
    Assert.assertEquals(Constant.CLOTHING_SIZE_DEF_ATTRIBUTE,
        ProductSizeAttributeUtils.getProductDefiningAttribute(Arrays.asList(childProduct)));
  }

  @Test
  public void testGetProductDefiningAttribute2() throws Exception {
    final ChildProduct childProduct = new ChildProduct();
    final Map<String, Object> definingAttributes = new HashMap<>();

    definingAttributes.put(Constant.CLOTHING_COLOR_DEF_ATTRIBUTE, "purple");
    childProduct.setDefiningAttributes(definingAttributes);
    Assert.assertEquals(Constant.CLOTHING_COLOR_DEF_ATTRIBUTE,
        ProductSizeAttributeUtils.getProductDefiningAttribute(Arrays.asList(childProduct)));
  }

  @Test
  public void testGetProductDefiningAttribute3() throws Exception {
    final ChildProduct childProduct = new ChildProduct();
    final Map<String, Object> definingAttributes = new HashMap<>();

    definingAttributes.put(Constant.COMPONENT_ID, "1223");
    childProduct.setDefiningAttributes(definingAttributes);
    Assert.assertEquals(StringUtils.EMPTY,
        ProductSizeAttributeUtils.getProductDefiningAttribute(Arrays.asList(childProduct)));
  }
}
