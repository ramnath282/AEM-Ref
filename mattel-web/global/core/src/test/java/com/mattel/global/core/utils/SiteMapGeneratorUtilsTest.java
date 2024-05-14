package com.mattel.global.core.utils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.global.core.utils.SiteMapGeneratorUtils;
import com.mattel.global.core.utils.SiteMapGeneratorUtils.Config;

public class SiteMapGeneratorUtilsTest {
	SiteMapGeneratorUtils siteMapGeneratorUtils;
	Config config;

	@Before
	public void setUp() {
		siteMapGeneratorUtils = new SiteMapGeneratorUtils();
		config = Mockito.mock(Config.class);
	}

	@Test
	public void activate() {
		siteMapGeneratorUtils.activate(config);
	}	

	@Test
	public void getCountryMapping() {
		siteMapGeneratorUtils.getCountryMapping();
		siteMapGeneratorUtils.getBrandSiteMapDetails();
	}

}
