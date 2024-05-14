package com.mattel.global.core.pojo;

import static org.junit.Assert.assertSame;

import org.junit.Test;

public class ImageWithTextPojoTest {
  ImageBlockWithTextPojo imageBlockWithTextPojo = new ImageBlockWithTextPojo();
  @Test
  public void testSetImageUrl() {
    imageBlockWithTextPojo.setImageUrl("imageUrl");
    assertSame("imageUrl", imageBlockWithTextPojo.getImageUrl());
  }
  @Test
  public void testGetImageUrl() {
    imageBlockWithTextPojo.setImageUrl("imageUrl");
    assertSame("imageUrl", imageBlockWithTextPojo.getImageUrl());
  }
  @Test
  public void testSetImageAltText() {
    imageBlockWithTextPojo.setImageAltText("imageAltText");
    assertSame("imageAltText", imageBlockWithTextPojo.getImageAltText());
  }
  @Test
  public void testGetImageAltText() {
    imageBlockWithTextPojo.setImageAltText("imageAltText");
    assertSame("imageAltText", imageBlockWithTextPojo.getImageAltText());
  }
  @Test
  public void testSetTitle() {
    imageBlockWithTextPojo.setTitle("title");
    assertSame("title", imageBlockWithTextPojo.getTitle());
  }
  @Test
  public void testGetTitle() {
    imageBlockWithTextPojo.setTitle("title");
    assertSame("title", imageBlockWithTextPojo.getTitle());
  }
  @Test
  public void testSetSubTitle() {
    imageBlockWithTextPojo.setSubTitle("subTitle");
    assertSame("subTitle", imageBlockWithTextPojo.getSubTitle());
  }
  @Test
  public void testGetSubTitle() {
    imageBlockWithTextPojo.setSubTitle("subTitle");
    assertSame("subTitle", imageBlockWithTextPojo.getSubTitle());
  }
  @Test
  public void testSetDescription() {
    imageBlockWithTextPojo.setDescription("description");
    assertSame("description", imageBlockWithTextPojo.getDescription());
  }
  @Test
  public void testGetDescription() {
    imageBlockWithTextPojo.setDescription("description");
    assertSame("description", imageBlockWithTextPojo.getDescription());
  }
  @Test
  public void testSetCtaLabel() {
    imageBlockWithTextPojo.setCtaLabel("ctaLabel");
    assertSame("ctaLabel", imageBlockWithTextPojo.getCtaLabel());
  }
  @Test
  public void testGetCtaLabel() {
    imageBlockWithTextPojo.setCtaLabel("ctaLabel");
    assertSame("ctaLabel", imageBlockWithTextPojo.getCtaLabel());
  }
  @Test
  public void testSetCtaUrl() {
    imageBlockWithTextPojo.setCtaUrl("ctaUrl");
    assertSame("ctaUrl", imageBlockWithTextPojo.getCtaUrl());
  }
  @Test
  public void testGetCtaUrl() {
    imageBlockWithTextPojo.setCtaUrl("ctaUrl");
    assertSame("ctaUrl", imageBlockWithTextPojo.getCtaUrl());
  }
  @Test
  public void testSetTargetUrl() {
    imageBlockWithTextPojo.setTargetUrl("targetUrl");
    assertSame("targetUrl", imageBlockWithTextPojo.getTargetUrl());
  }
  @Test
  public void testGetTargetUrl() {
    imageBlockWithTextPojo.setTargetUrl("targetUrl");
    assertSame("targetUrl", imageBlockWithTextPojo.getTargetUrl());
  }
  @Test
  public void testSetImageLink() {
    imageBlockWithTextPojo.setImageLink("imageLink");
    assertSame("imageLink", imageBlockWithTextPojo.getImageLink());
  }
  @Test
  public void testGetImageLink() {
    imageBlockWithTextPojo.setImageLink("imageLink");
    assertSame("imageLink", imageBlockWithTextPojo.getImageLink());
  }
  @Test
  public void testSetExternal() {
    imageBlockWithTextPojo.setExternal(true);
    assertSame(true, imageBlockWithTextPojo.getExternal());
  }
  @Test
  public void testGetExternal() {
    imageBlockWithTextPojo.setExternal(false);
    assertSame(false, imageBlockWithTextPojo.getExternal());
  }
  @Test
  public void toStringTest1(){
    imageBlockWithTextPojo.setImageUrl("imageUrl");
    String imageUrl = imageBlockWithTextPojo.getImageUrl();
    assertSame(true,imageBlockWithTextPojo.toString().contains("imageUrl=" + imageUrl));
  }
  @Test
  public void toStringTest2(){
    imageBlockWithTextPojo.setImageAltText("imageAltText");
    String imageAltText = imageBlockWithTextPojo.getImageAltText();
    assertSame(true,imageBlockWithTextPojo.toString().contains("imageAltText=" + imageAltText));
  }
  @Test
  public void toStringTest3(){
    imageBlockWithTextPojo.setTitle("title");
    String title = imageBlockWithTextPojo.getTitle();
    assertSame(true,imageBlockWithTextPojo.toString().contains("title=" + title));
  }
  @Test
  public void toStringTest4(){
    imageBlockWithTextPojo.setSubTitle("subTitle");
    String subTitle = imageBlockWithTextPojo.getSubTitle();
    assertSame(true,imageBlockWithTextPojo.toString().contains("subTitle=" + subTitle));
  }
  @Test
  public void toStringTest5(){
    imageBlockWithTextPojo.setDescription("description");
    String description = imageBlockWithTextPojo.getDescription();
    assertSame(true,imageBlockWithTextPojo.toString().contains("description=" + description));
  }
  @Test
  public void toStringTest6(){
    imageBlockWithTextPojo.setCtaLabel("ctaLabel");
    String ctaLabel = imageBlockWithTextPojo.getCtaLabel();
    assertSame(true,imageBlockWithTextPojo.toString().contains("ctaLabel=" + ctaLabel));
  }
  @Test
  public void toStringTest7(){
    imageBlockWithTextPojo.setCtaUrl("ctaUrl");
    String ctaUrl = imageBlockWithTextPojo.getCtaUrl();
    assertSame(true,imageBlockWithTextPojo.toString().contains("ctaUrl=" + ctaUrl));
  }
  @Test
  public void toStringTest8(){
    imageBlockWithTextPojo.setTargetUrl("targetUrl");
    String targetUrl = imageBlockWithTextPojo.getTargetUrl();
    assertSame(true,imageBlockWithTextPojo.toString().contains("targetUrl=" + targetUrl));
  }
  @Test
  public void toStringTest9(){
    imageBlockWithTextPojo.setImageLink("imageLink");
    String imageLink = imageBlockWithTextPojo.getImageLink();
    assertSame(true,imageBlockWithTextPojo.toString().contains("imageLink=" + imageLink));
  }
  @Test
  public void toStringTest10(){
    imageBlockWithTextPojo.setExternal(true);
    boolean external = imageBlockWithTextPojo.getExternal();
    assertSame(true,imageBlockWithTextPojo.toString().contains("external=" + external));
  }
  
}
