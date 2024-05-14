package com.mattel.ecomm.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MarketingBannerDisplayPojoTest {

  private MarketingBannerDisplayPojo marketingBannerDisplayPojo = null;

  @Before
  public void setUp() throws Exception {
    marketingBannerDisplayPojo = new MarketingBannerDisplayPojo();
    marketingBannerDisplayPojo.setImageAltText("imageAltText");
    marketingBannerDisplayPojo.setMediaType("video");
    marketingBannerDisplayPojo.setPromoImage("/content/dam/image.png");
    marketingBannerDisplayPojo.setPromoText("promoText");
    marketingBannerDisplayPojo.setVideoType("videoType");
    marketingBannerDisplayPojo.setVideoUrl("/content/dam/video");
  }

  @Test
  public void testGetMediaType() {
    Assert.assertEquals("video", marketingBannerDisplayPojo.getMediaType());
  }

  @Test
  public void testGetPromoImage() {
    Assert.assertEquals("/content/dam/image.png", marketingBannerDisplayPojo.getPromoImage());
  }

  @Test
  public void testGetImageAltText() {
    Assert.assertEquals("imageAltText", marketingBannerDisplayPojo.getImageAltText());
  }

  @Test
  public void testGetPromoText() {
    Assert.assertEquals("promoText", marketingBannerDisplayPojo.getPromoText());
  }

  @Test
  public void testGetVideoUrl() {
    Assert.assertEquals("/content/dam/video", marketingBannerDisplayPojo.getVideoUrl());
  }

  @Test
  public void testGetVideoType() {
    Assert.assertEquals("videoType", marketingBannerDisplayPojo.getVideoType());
  }

}
