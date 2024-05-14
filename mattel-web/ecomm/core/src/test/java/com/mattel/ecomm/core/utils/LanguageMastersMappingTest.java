package com.mattel.ecomm.core.utils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.ecomm.core.utils.LanguageMastersMapping.Config;

public class LanguageMastersMappingTest {
	LanguageMastersMapping languageMastersMapping;
	Config config;

	@Before
	public void setUp() {
		languageMastersMapping = new LanguageMastersMapping();
		config = Mockito.mock(Config.class);
	}

	@Test
	public void activate() {
		languageMastersMapping.activate(config);
	}

	@Test
	public void getLanguageMapping() {
		LanguageMastersMapping.getLanguageMapping();
	}

}
