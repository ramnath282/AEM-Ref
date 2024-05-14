package com.mattel.global.core.pojo;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CarouselPojoTest {
  
  CarouselPojo carouselPojo = new CarouselPojo();
  
  @Test
  public void testSetHeading() {
    carouselPojo.setHeading("heading");
    assertSame("heading", carouselPojo.getHeading());
  }
  @Test
  public void testGetHeading() {
    carouselPojo.setHeading("heading");
    assertSame("heading", carouselPojo.getHeading());
  }
  @Test
  public void testSetSubHeading() {
    carouselPojo.setSubHeading("subHeading");
    assertSame("subHeading", carouselPojo.getSubHeading());
  }
  @Test
  public void testGetSubHeading() {
    carouselPojo.setSubHeading("subHeading");
    assertSame("subHeading", carouselPojo.getSubHeading());
  }
  @Test
  public void testSetImage() {
    carouselPojo.setImage("image");
    assertSame("image", carouselPojo.getImage());
  }
  @Test
  public void testGetImage() {
    carouselPojo.setImage("image");
    assertSame("image", carouselPojo.getImage());
  }
  @Test
  public void testSetImageAlttext() {
    carouselPojo.setImageAlttext("imageAlttext");
    assertSame("imageAlttext", carouselPojo.getImageAlttext());
  }
  @Test
  public void testGetImageAlttext() {
    carouselPojo.setImageAlttext("imageAlttext");
    assertSame("imageAlttext", carouselPojo.getImageAlttext());
  }
  @Test
  public void testSetOverlayHeading() {
    carouselPojo.setOverlayHeading("overlayHeading");
    assertSame("overlayHeading", carouselPojo.getOverlayHeading());
  }
  @Test
  public void testGetOverlayHeading() {
    carouselPojo.setOverlayHeading("overlayHeading");
    assertSame("overlayHeading", carouselPojo.getOverlayHeading());
  }
  @Test
  public void testSetOverlaySubHeading() {
    carouselPojo.setOverlaySubHeading("overlaySubHeading");
    assertSame("overlaySubHeading", carouselPojo.getOverlaySubHeading());
  }
  @Test
  public void testGetOverlaySubHeading() {
    carouselPojo.setOverlaySubHeading("overlaySubHeading");
    assertSame("overlaySubHeading", carouselPojo.getOverlaySubHeading());
  }
  @Test
  public void testSetCtaLabel() {
    carouselPojo.setCtaLabel("ctaLabel");
    assertSame("ctaLabel", carouselPojo.getCtaLabel());
  }
  @Test
  public void testGetCtaLabel() {
    carouselPojo.setCtaLabel("ctaLabel");
    assertSame("ctaLabel", carouselPojo.getCtaLabel());
  }
  @Test
  public void testSetCtaLink() {
    carouselPojo.setCtaLink("ctaLink");
    assertSame("ctaLink", carouselPojo.getCtaLink());
  }
  @Test
  public void testGetCtaLink() {
    carouselPojo.setCtaLink("ctaLink");
    assertSame("ctaLink", carouselPojo.getCtaLink());
  }
  @Test
  public void testSetCtaAltText() {
    carouselPojo.setCtaAltText("ctaAltText");
    assertSame("ctaAltText", carouselPojo.getCtaAltText());
  }
  @Test
  public void testGetCtaAltText() {
    carouselPojo.setCtaAltText("ctaAltText");
    assertSame("ctaAltText", carouselPojo.getCtaAltText());
  }
  @Test
  public void testSetTextPositioning() {
    carouselPojo.setTextPositioning("textPositioning");
    assertSame("textPositioning", carouselPojo.getTextPositioning());
  }
  @Test
  public void testGetTextPositioning() {
    carouselPojo.setTextPositioning("textPositioning");
    assertSame("textPositioning", carouselPojo.getTextPositioning());
  }
  @Test
  public void testSetCtaStyling() {
    carouselPojo.setCtaStyling("ctaStyling");
    assertSame("ctaStyling", carouselPojo.getCtaStyling());
  }
  @Test
  public void testGetCtaStyling() {
    carouselPojo.setCtaStyling("ctaStyling");
    assertSame("ctaStyling", carouselPojo.getCtaStyling());
  }
  @Test
  public void testSetCtaPositioning() {
    carouselPojo.setCtaPositioning("ctaPositioning");
    assertSame("ctaPositioning", carouselPojo.getCtaPositioning());
  }
  @Test
  public void testGetCtaPositioning() {
    carouselPojo.setCtaPositioning("ctaPositioning");
    assertSame("ctaPositioning", carouselPojo.getCtaPositioning());
  }
  @Test
  public void testSetShowArrow() {
    carouselPojo.setShowArrow("showArrow");
    assertSame("showArrow", carouselPojo.getShowArrow());
  }
  @Test
  public void testGetShowArrow() {
    carouselPojo.setShowArrow("showArrow");
    assertSame("showArrow", carouselPojo.getShowArrow());
  }
  @Test
  public void testSetShowPagination() {
    carouselPojo.setShowPagination("showPagination");
    assertSame("showPagination", carouselPojo.getShowPagination());
  }
  @Test
  public void testGetShowPagination() {
    carouselPojo.setShowPagination("showPagination");
    assertSame("showPagination", carouselPojo.getShowPagination());
  }
  @Test
  public void testSetTextBackground() {
    carouselPojo.setTextBackground("textBackground");
    assertSame("textBackground", carouselPojo.getTextBackground());
  }
  @Test
  public void testGetTextBackground() {
    carouselPojo.setTextBackground("textBackground");
    assertSame("textBackground", carouselPojo.getTextBackground());
  }
  @Test
  public void testSetCtaRenderoption() {
    carouselPojo.setCtaRenderoption("ctaRenderoption");
    assertSame("ctaRenderoption", carouselPojo.getCtaRenderoption());
  }
  @Test
  public void testGetCtaRenderoption() {
    carouselPojo.setCtaRenderoption("ctaRenderoption");
    assertSame("ctaRenderoption", carouselPojo.getCtaRenderoption());
  }
  @Test
  public void testSetExternal() {
    carouselPojo.setExternal(true);
    assertSame(true, carouselPojo.getExternal());
  }
  @Test
  public void testGetExternal() {
    carouselPojo.setExternal(false);
    assertSame(false, carouselPojo.getExternal());
  }
  
  @Test
  public void toStringTest10(){
    carouselPojo.setCtaAltText("alttextForCta");
    String ctaAlt = carouselPojo.getCtaAltText();
    assertTrue(carouselPojo.toString().contains("ctaAltText=" + ctaAlt));
  }
}
