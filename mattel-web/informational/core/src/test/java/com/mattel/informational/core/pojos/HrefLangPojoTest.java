package com.mattel.informational.core.pojos;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class HrefLangPojoTest {
	@InjectMocks
	private HrefLangPojo hrefLangPojo;
	
	@Test
	public void testGetterSetter(){
		hrefLangPojo.setLocale("en");
		hrefLangPojo.setUrl("url");
		assertEquals("en", hrefLangPojo.getLocale());
		assertEquals("url", hrefLangPojo.getUrl());
		hrefLangPojo.toString();
	}
}
