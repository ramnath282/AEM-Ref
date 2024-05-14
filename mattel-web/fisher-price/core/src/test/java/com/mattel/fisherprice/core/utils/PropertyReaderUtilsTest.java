package com.mattel.fisherprice.core.utils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.fisherprice.core.utils.PropertyReaderUtils.Config;

public class PropertyReaderUtilsTest {
	PropertyReaderUtils propertyReaderUtils;
	Config config;

	@Before
	public void setUp() {
		propertyReaderUtils = new PropertyReaderUtils();
		config = Mockito.mock(Config.class);
	}

	@Test
	public void activate() {
		propertyReaderUtils.activate(config);
	}

	@Test
	public void getScriptUrl() {
		propertyReaderUtils.getScriptUrl();
	}

	@Test
	public void getFisherPricePath() {
		PropertyReaderUtils.getFisherPricePath();
	}
}
