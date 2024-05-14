package com.mattel.global.core.pojo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class InformationalFooterLinkPojoTest {
	
	@InjectMocks
	private InformationalFooterLinkPojo informationalFooterLinkPojo;
	
	@Test
	public void testSetters() {
		informationalFooterLinkPojo.setAlwaysEnglish("alwaysEnglish");
		informationalFooterLinkPojo.setLinkTarget("linkTarget");
		informationalFooterLinkPojo.setLinkText("linkText");
		informationalFooterLinkPojo.setLinkURL("linkURL");
		
		Assert.assertEquals("alwaysEnglish", informationalFooterLinkPojo.getAlwaysEnglish());
		Assert.assertEquals("linkTarget", informationalFooterLinkPojo.getLinkTarget());
		Assert.assertEquals("linkText", informationalFooterLinkPojo.getLinkText());
		Assert.assertEquals("linkURL", informationalFooterLinkPojo.getLinkURL());
		
		informationalFooterLinkPojo.toString();
	}

}
