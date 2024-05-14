package com.mattel.informational.core.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.informational.core.utils.InformationalConfigurationUtils.Config;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ InformationalUtils.class})
public class InformationalConfigurationUtilsTest {
	@InjectMocks
	private InformationalConfigurationUtils informationalConfigurationUtils;

	Config config;

	@Before
	public void setUp() {
		informationalConfigurationUtils = new InformationalConfigurationUtils();
		PowerMockito.mockStatic(InformationalUtils.class);
		config = Mockito.mock(Config.class);
	}

	@Test
	public void activate() {
		informationalConfigurationUtils.activate(config);
	}

	@Test
	public void getRootErrorPageName() {
		InformationalConfigurationUtils.getRootErrorPageName();
	}

	@Test
	public void getClientlibRootCategoryName() {
		InformationalConfigurationUtils.getClientlibRootCategoryName();
	}

	@Test
	public void getHeaderExpFragmentName() {
		InformationalConfigurationUtils.getHeaderExpFragmentName();
	}

	@Test
	public void getFooterExpFragmentName() {
		InformationalConfigurationUtils.getFooterExpFragmentName();
	}

	@Test
	public void getRetailerInterstitialPath() {
		InformationalConfigurationUtils.getRetailerInterstitialPath();
	}

	@Test
	public void getLeavingInterstitialPath() {
		InformationalConfigurationUtils.getLeavingInterstitialPath();
	}

	@Test
	public void getInterstitialApp() {
		InformationalConfigurationUtils.getInterstitialApp();
	}

	@Test
	public void getExpFragmentRootPathArray() {
		InformationalConfigurationUtils.getExpFragmentRootPathArray();
	}

	@Test
	public void getScriptUrl() {
		InformationalConfigurationUtils.getScriptUrl();
	}

	@Test
	public void getSiteErrorPages() {
		informationalConfigurationUtils.getSiteErrorPages("");
	}

	@Test
	public void testGetValueFromKeyMappings() {
		String[] idMappings = { "id1:1", "id1:2" };
		InformationalUtils.getValueFromKeyMappings(idMappings, "id1");
	}
}
