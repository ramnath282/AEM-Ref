package com.mattel.ecomm.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EmailLightboxPojoTest {
	EmailLightboxPojo emailLightboxPojo;

	@Before
	public void setUp() {
		emailLightboxPojo = new EmailLightboxPojo();
		emailLightboxPojo.setGenderOption("genderOption");
		emailLightboxPojo.setPageKeyword("pageKeyword");
		emailLightboxPojo.setPagePath("pagePath");
		emailLightboxPojo.setRelationOption("relationOption");
	}

	@Test
	public void testAccordionPojo() {
		Assert.assertEquals("genderOption", emailLightboxPojo.getGenderOption());
		Assert.assertEquals("pageKeyword", emailLightboxPojo.getPageKeyword());
		Assert.assertEquals("pagePath", emailLightboxPojo.getPagePath());
		Assert.assertEquals("relationOption", emailLightboxPojo.getRelationOption());
	}
}
