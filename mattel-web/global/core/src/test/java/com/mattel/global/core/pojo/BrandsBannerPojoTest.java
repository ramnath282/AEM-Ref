package com.mattel.global.core.pojo;

import static org.junit.Assert.assertSame;

import org.junit.Test;

public class BrandsBannerPojoTest {
  
  BrandsBannerPojo brandsBannerPojo = new BrandsBannerPojo();
  
  @Test
  public void testSetAlttext() {
    brandsBannerPojo.setAlttext("alt-text");
    assertSame("alt-text", brandsBannerPojo.getAlttext());
  }
  @Test
  public void testGetAlttext() {
    brandsBannerPojo.setAlttext("alt text");
    assertSame("alt text", brandsBannerPojo.getAlttext());
  }
  @Test
  public void testGetExternal() {
    brandsBannerPojo.setExternal(true);
    assertSame(true, brandsBannerPojo.getExternal());
  }
  @Test
  public void testSetExternal() {
    brandsBannerPojo.setExternal(false);
    assertSame(false, brandsBannerPojo.getExternal());
  }
  @Test
  public void testSetRenderoption() {
    brandsBannerPojo.setRenderoption("test1");
    assertSame("test1", brandsBannerPojo.getRenderoption());
  }
  @Test
  public void testGetRenderoption() {
    brandsBannerPojo.setRenderoption("test1");
    assertSame("test1", brandsBannerPojo.getRenderoption());
  }
  @Test
  public void testSetBrandlogolink() {
    brandsBannerPojo.setBrandlogolink("brand logo link");
    assertSame("brand logo link", brandsBannerPojo.getBrandlogolink());
  }
  @Test
  public void testGetBrandlogolink() {
    brandsBannerPojo.setBrandlogolink("brand logo link");
    assertSame("brand logo link", brandsBannerPojo.getBrandlogolink());
  }
  @Test
  public void testSetLogoImage() {
    brandsBannerPojo.setLogoImage("brand logo image");
    assertSame("brand logo image", brandsBannerPojo.getLogoImage());
  }
  @Test
  public void testGetLogoImage() {
    brandsBannerPojo.setLogoImage("brand logo image");
    assertSame("brand logo image", brandsBannerPojo.getLogoImage());
  }
  @Test
  public void toStringTest(){
	  brandsBannerPojo.setAlttext("alttext");
    String alttext = brandsBannerPojo.getAlttext();
    assertSame(true,brandsBannerPojo.toString().contains("alttext=" + alttext));
  }

  @Test
  public void testSetTitle() {
    brandsBannerPojo.setTitle("title");
    assertSame("title", brandsBannerPojo.getTitle());
  }



}
