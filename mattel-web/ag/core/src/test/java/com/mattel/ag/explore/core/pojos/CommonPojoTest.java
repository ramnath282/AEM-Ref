package com.mattel.ag.explore.core.pojos;

import org.junit.Before;
import org.junit.Test;

public class CommonPojoTest {

	CommonPojo commonPojo;

	@Before
	public void setUp() {

		commonPojo = new CommonPojo();
	}

	@Test
	public void getCtaExternal() {
		commonPojo.getCtaExternal();
	}

	@Test
	public void setCtaExternal() {
		commonPojo.setCtaExternal(Boolean.FALSE);
	}

	@Test
	public void getExternal() {
		commonPojo.getExternal();
	}

	@Test
	public void setExternal() {
		commonPojo.setExternal(Boolean.FALSE);
	}

}
