package com.mattel.ag.explore.core.utils;

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.ag.explore.core.utils.SiteMapServletConfiguration.Config;

public class SiteMapServletConfigurationTest {

	Config config;
	SiteMapServletConfiguration siteMapServletConfiguration;

	@Before
	public void setUp() {
		siteMapServletConfiguration = new SiteMapServletConfiguration();
		config = Mockito.mock(SiteMapServletConfiguration.Config.class);
		siteMapServletConfiguration.setDomain("domain");
		siteMapServletConfiguration.setMyChangeFreqProperties("myChangeFreqProperties");
		siteMapServletConfiguration.setMyPriorityProperties("myPriorityProperties");
		siteMapServletConfiguration.setRootPath("rootPath");
	}

	@Test
	public void testAllGetters() {
		when(config.domain()).thenReturn(siteMapServletConfiguration.getDomain());
		when(config.myChangeFreqProperties()).thenReturn(siteMapServletConfiguration.getMyChangeFreqProperties());
		when(config.myPriorityProperties()).thenReturn(siteMapServletConfiguration.getMyPriorityProperties());
		when(config.rootPath()).thenReturn(siteMapServletConfiguration.getRootPath());
		siteMapServletConfiguration.activate(config);
	}

}
