package com.mattel.play.core.pojos;

import org.junit.Before;
import org.junit.Test;

public class SocialIconsPojoTest {
	SocialIconsPojo socialIconsPojo;
	
	@Before
	public void init(){
		socialIconsPojo = new SocialIconsPojo();
	}
	
	@Test
	public void testGetterSetters(){
		socialIconsPojo.setAlwaysEnglish("alwaysEnglish");
		socialIconsPojo.setIcons("icons");
		socialIconsPojo.setLinkTarget("linkTarget");
		socialIconsPojo.setLinkText("linkText");
		socialIconsPojo.setSocialLinkURL("socialLinkURL");
		
		socialIconsPojo.getAlwaysEnglish();
		socialIconsPojo.getLinkTarget();
		socialIconsPojo.getIcons();
		socialIconsPojo.getLinkText();
		socialIconsPojo.getSocialLinkURL();
	}
}
