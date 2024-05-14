package com.mattel.play.core.pojos;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class MattelBrandsPojoTest {
	
	@InjectMocks
	MattelBrandsPojo mattelBrandsPojo;
	
	@Test
	public void testGetterSetter(){
		mattelBrandsPojo.setAlwaysEnglish("alwaysEnglish");
		mattelBrandsPojo.setLogoAltText("logoAltText");
		mattelBrandsPojo.setLogoImage("logoImage");
		mattelBrandsPojo.setLogoURL("logoURL");
		mattelBrandsPojo.setTargetURL("targetURL");
		
		Assert.assertEquals("alwaysEnglish", mattelBrandsPojo.getAlwaysEnglish());
		Assert.assertEquals("logoAltText", mattelBrandsPojo.getLogoAltText());
		Assert.assertEquals("logoImage", mattelBrandsPojo.getLogoImage());
		Assert.assertEquals("logoURL", mattelBrandsPojo.getLogoURL());
		Assert.assertEquals("targetURL", mattelBrandsPojo.getTargetURL());
	}

}
