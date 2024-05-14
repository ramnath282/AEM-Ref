package com.mattel.play.core.pojos;

import org.junit.Before;
import org.junit.Test;

public class ParentFooterPojoTest {
	
	ParentFooterPojo parentFooterPojo;

	@Before
	public void setUp() {
		parentFooterPojo = new ParentFooterPojo();
	}
	
	@Test
	public void testGetterSetters(){
		parentFooterPojo.setAlwaysEnglish("alwaysEnglish");
		parentFooterPojo.setLinkTarget("linkTarget");
		parentFooterPojo.setLinkText("linkText");
		parentFooterPojo.setLinkURL("linkURL");
		
		parentFooterPojo.getAlwaysEnglish();
		parentFooterPojo.getLinkTarget();
		parentFooterPojo.getLinkText();
		parentFooterPojo.getLinkURL();
	}
}
