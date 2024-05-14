package com.mattel.fisherprice.core.pojos;

import org.junit.Before;
import org.junit.Test;

public class HrefLangPojoTest {

	HrefLangPojo hrefLangPojo;

	@Before
	public void setUp() {
		hrefLangPojo = new HrefLangPojo();

	}

	@Test
	public void getLocale() {
		hrefLangPojo.getLocale();
	}

	@Test
	public void setLocale() {
		hrefLangPojo.setLocale("");
	}

	@Test
	public void getUrl() {
		hrefLangPojo.getUrl();
	}

	@Test
	public void setUrl() {
		hrefLangPojo.setUrl("");
		hrefLangPojo.toString();
	}

}
