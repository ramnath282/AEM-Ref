package com.mattel.ag.retail.core.pojos;

import org.junit.Before;
import org.junit.Test;

/**
 * @author CTS. A simple pojo for footer links.
 */
public class FooterLinksTest {
	
  FooterLinks footerLinks;
  
  @Before
  public void setup(){
	  footerLinks = new FooterLinks();
  }
  
  @Test
  public void getLinkText() {
	  footerLinks.getLinkText();
  }
  
  @Test
  public void setLinkText() {
	  footerLinks.setLinkText("linktext");
  }
  
  @Test 
  public void toStringTest(){
	  footerLinks.toString();
	}
}
