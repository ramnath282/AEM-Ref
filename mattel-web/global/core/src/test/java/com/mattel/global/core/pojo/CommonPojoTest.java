package com.mattel.global.core.pojo;

import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

public class CommonPojoTest {
	CommonPojo commonPojo;

	@Before
	public void setUp(){
		commonPojo = new CommonPojo();
		commonPojo.setCtaExternal(false);
		commonPojo.setExternal(true);
		commonPojo.toString();
	}
	
	@Test
	public void testSetCtaExternal() {
		assertSame(false, commonPojo.getCtaExternal());
	}

	@Test
	public void testGetExternal() {
		assertSame(true,  commonPojo.getExternal());
	}
}
