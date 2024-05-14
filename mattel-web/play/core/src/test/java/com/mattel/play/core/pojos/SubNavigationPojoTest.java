package com.mattel.play.core.pojos;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class SubNavigationPojoTest {
	
	@InjectMocks
	SubNavigationPojo subNavigationPojo;
	
	@Test
	public void testGetterSetter(){
		subNavigationPojo.setAlwaysEnglish("alwaysEnglish");
		subNavigationPojo.setBrandActiveM("brandHoverM");
		subNavigationPojo.setBrandHoverD("brandHoverD");
		subNavigationPojo.setBrandLogoAlt("brandLogoAltD");
		subNavigationPojo.setBrandLogoD("brandLogoD");
		subNavigationPojo.setBrandLogoM("brandLogoM");
		subNavigationPojo.setBrandTarget("brandTarget");
		subNavigationPojo.setSubBrandUrl("subBrandUrl");
		
		Assert.assertEquals("alwaysEnglish", subNavigationPojo.getAlwaysEnglish());
		Assert.assertEquals("brandHoverM", subNavigationPojo.getBrandActiveM());
		Assert.assertEquals("brandHoverD", subNavigationPojo.getBrandHoverD());
		Assert.assertEquals("brandLogoAltD", subNavigationPojo.getBrandLogoAlt());
		Assert.assertEquals("brandLogoD", subNavigationPojo.getBrandLogoD());
		Assert.assertEquals("brandLogoM", subNavigationPojo.getBrandLogoM());
		Assert.assertEquals("brandTarget", subNavigationPojo.getBrandTarget());
		Assert.assertEquals("subBrandUrl", subNavigationPojo.getSubBrandUrl());
	}
	
}
