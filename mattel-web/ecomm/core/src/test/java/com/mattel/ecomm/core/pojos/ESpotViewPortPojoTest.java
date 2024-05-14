package com.mattel.ecomm.core.pojos;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ESpotViewPortPojoTest {

  private ESpotViewPortPojo eSpotViewPortPojo = null;

  @Before
  public void setUp() throws Exception {
    eSpotViewPortPojo = new ESpotViewPortPojo();
    final JSONObject json = new JSONObject();
    json.append("key", "value");
    eSpotViewPortPojo.setColumnNo(2);
    eSpotViewPortPojo.setRowNo(2);
    eSpotViewPortPojo.setSpanLength(5);
    final MarketingBannerDisplayPojo displayObject = new MarketingBannerDisplayPojo();
    displayObject.setImageAltText("image");
    eSpotViewPortPojo.setDisplayObject(displayObject);
  }

  @Test
  public void testGetRowNo() {
    Assert.assertEquals(2, eSpotViewPortPojo.getRowNo());
  }

  @Test
  public void testGetColumnNo() {
    Assert.assertEquals(2, eSpotViewPortPojo.getColumnNo());
  }

  @Test
  public void testGetSpanLength() {
    Assert.assertEquals(5, eSpotViewPortPojo.getSpanLength());
  }

  @Test
  public void testGetDisplayObject() {
    Assert.assertEquals("image", eSpotViewPortPojo.getDisplayObject().getImageAltText());
  }

}
