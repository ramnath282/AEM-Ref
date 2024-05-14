package com.mattel.fisherprice.core.constants;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class ProductRespositoryFilterTest {
  @Test
  public void testDefault() throws Exception {
    final JSONObject product = new JSONObject();

    Assert.assertTrue(ProductRespositoryFilter.DEFAULT.test(product));
    Assert.assertFalse(ProductRespositoryFilter.DEFAULT.test(null));
  }

  @Test
  public void testForActive() throws Exception {
    final JSONObject product = new JSONObject();

    product.put(ProductFeederConstants.ACTIVE, "1");
    Assert.assertTrue(ProductRespositoryFilter.IS_ACTIVE.test(product));
  }

  @Test
  public void testForActivePropertyNotFound() throws Exception {
    final JSONObject product = new JSONObject();

    Assert.assertFalse(ProductRespositoryFilter.IS_ACTIVE.test(product));
  }

  @Test
  public void testForActiveInvalidPropertyValue() throws Exception {
    final JSONObject product = new JSONObject();

    product.put(ProductFeederConstants.ACTIVE, "");
    Assert.assertFalse(ProductRespositoryFilter.IS_ACTIVE.test(product));
  }

  @Test
  public void testForDeActive() throws Exception {
    final JSONObject product = new JSONObject();

    product.put(ProductFeederConstants.ACTIVE, "0");
    Assert.assertTrue(ProductRespositoryFilter.IS_DEACTIVE.test(product));
  }

  @Test
  public void testForDeActivePropertyNotFound() throws Exception {
    final JSONObject product = new JSONObject();

    Assert.assertFalse(ProductRespositoryFilter.IS_DEACTIVE.test(product));
  }

  @Test
  public void testForDeActiveInvalidPropertyValue() throws Exception {
    final JSONObject product = new JSONObject();

    product.put(ProductFeederConstants.ACTIVE, "");
    Assert.assertFalse(ProductRespositoryFilter.IS_DEACTIVE.test(product));
  }

  @Test
  public void testForPublish() throws Exception {
    final JSONObject product = new JSONObject();

    product.put(ProductFeederConstants.PUBLISHED, "1");
    Assert.assertTrue(ProductRespositoryFilter.IS_PUBLISH.test(product));
  }

  @Test
  public void testForPublishPropertyNotFound() throws Exception {
    final JSONObject product = new JSONObject();

    Assert.assertFalse(ProductRespositoryFilter.IS_PUBLISH.test(product));
  }

  @Test
  public void testForPublishInvalidPropertyValue() throws Exception {
    final JSONObject product = new JSONObject();

    product.put(ProductFeederConstants.PUBLISHED, "");
    Assert.assertFalse(ProductRespositoryFilter.IS_PUBLISH.test(product));
  }

  @Test
  public void testForUnPublish() throws Exception {
    final JSONObject product = new JSONObject();

    product.put(ProductFeederConstants.PUBLISHED, "0");
    Assert.assertTrue(ProductRespositoryFilter.IS_UNPUBLISH.test(product));
  }

  @Test
  public void testForUnPublishPropertyNotFound() throws Exception {
    final JSONObject product = new JSONObject();

    Assert.assertFalse(ProductRespositoryFilter.IS_UNPUBLISH.test(product));
  }

  @Test
  public void testForUnPublishInvalidPropertyValue() throws Exception {
    final JSONObject product = new JSONObject();

    product.put(ProductFeederConstants.PUBLISHED, "");
    Assert.assertFalse(ProductRespositoryFilter.IS_UNPUBLISH.test(product));
  }
}
