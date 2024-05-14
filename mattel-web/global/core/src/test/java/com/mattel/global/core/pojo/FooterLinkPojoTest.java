package com.mattel.global.core.pojo;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FooterLinkPojoTest {
  
  FooterLinkPojo footerLinkPojo = new FooterLinkPojo();
  @Test
  public void testSetLabel() {
    footerLinkPojo.setLabel("label");
    assertSame("label", footerLinkPojo.getLabel());
  }
  @Test
  public void testGetLabel() {
    footerLinkPojo.setLabel("label");
    assertSame("label", footerLinkPojo.getLabel());
  }
  @Test
  public void testSetUrl() {
    footerLinkPojo.setUrl("url");
    assertSame("url", footerLinkPojo.getUrl());
  }
  @Test
  public void testGetUrl() {
    footerLinkPojo.setUrl("url");
    assertSame("url", footerLinkPojo.getUrl());
  }
  @Test
  public void testSetAlt() {
    footerLinkPojo.setAlt("alt");
    assertSame("alt", footerLinkPojo.getAlt());
  }
  @Test
  public void testGetAlt() {
    footerLinkPojo.setAlt("alt");
    assertSame("alt", footerLinkPojo.getAlt());
  }
  @Test
  public void testSetTargetUrl() {
    footerLinkPojo.setTargetUrl("targetUrl");
    assertSame("targetUrl", footerLinkPojo.getTargetUrl());
  }
  @Test
  public void testGetTargetUrl() {
    footerLinkPojo.setTargetUrl("targetUrl");
    assertSame("targetUrl", footerLinkPojo.getTargetUrl());
  }
  @Test
  public void testSetExternal() {
    footerLinkPojo.setExternal(true);
    assertSame(true, footerLinkPojo.getExternal());
  }
  @Test
  public void testGetExternal() {
    footerLinkPojo.setExternal(false);
    assertSame(false, footerLinkPojo.getExternal());
  }
  
  @Test
  public void testToStringForAllvariables() {
	  footerLinkPojo.setAlt("altText");
	  String alt = footerLinkPojo.getAlt();
	  footerLinkPojo.setExternal(true);
	  footerLinkPojo.setLabel("exampleLabel");
	  footerLinkPojo.setTargetUrl("/content/test/path");
	  footerLinkPojo.setUrl("/content/test/url");
	  //assertTrue(footerLinkPojo.toString().contains("altText")&& footerLinkPojo.toString().contains("altText"));
	  assertTrue(footerLinkPojo.toString().contains("alt=" + alt));
	  
   
  }

}
