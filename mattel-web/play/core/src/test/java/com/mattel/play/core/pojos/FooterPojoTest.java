package com.mattel.play.core.pojos;

import org.junit.Before;
import org.junit.Test;

public class FooterPojoTest {

	FooterPojo footerPojo;
	
	@Before
	public void setUp() {

		footerPojo = new FooterPojo();	
		
	}
	
	@Test
	public void getLinkTarget() {
		footerPojo.getLinkTarget();
	}
	@Test
	public void setLinkTarget() {
		footerPojo.setLinkTarget("");
	}
	@Test
	public void getLinkText() {
		footerPojo.getLinkText();
	}
	@Test
	public void setLinkText() {
		footerPojo.setLinkText("");
	}
	@Test
	public void getLinkURL() {
		footerPojo.getLinkURL();
	}
	@Test
	public void setLinkURL() {
		footerPojo.setLinkURL("");
		footerPojo.toString();
	}
	
	@Test
	public void testSetAlwaysEnglish() {
		footerPojo.setAlwaysEnglish("alwaysEnglish");
	}
	
	@Test
	public void testGetAlwaysEnglish() {
		footerPojo.getAlwaysEnglish();
	}
}
