package com.mattel.play.core.utils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.play.core.utils.PlaySiteConfigurationUtils.Config;

public class PlaySiteConfigurationUtilsTest {
	PlaySiteConfigurationUtils playSiteConfigurationUtils;
	Config config;
	@Before
	public void setUp() {
		playSiteConfigurationUtils = new PlaySiteConfigurationUtils();
		config = Mockito.mock(Config.class);
	}
	@Test
	public void activate() {
		playSiteConfigurationUtils.activate(config);
	}
	@Test
	public void getExpFragmentRootPath() {
		PlaySiteConfigurationUtils.getExpFragmentRootPath();
	}
	@Test
	public void getRootErrorPageName() {
		PlaySiteConfigurationUtils.getRootErrorPageName();
	}
	@Test
	public void getHeaderExpFragmentName() {
		PlaySiteConfigurationUtils.getHeaderExpFragmentName();
	}
	@Test
	public void getFooterExpFragmentName() {
		PlaySiteConfigurationUtils.getFooterExpFragmentName();
	}
	@Test
	public void getClientlibRootCategoryName() {
		PlaySiteConfigurationUtils.getClientlibRootCategoryName();
	}
	@Test
	public void getGameLandingExpFragmentName() {
		PlaySiteConfigurationUtils.getGameLandingExpFragmentName();
	}
	@Test
	public void getCategoryFilterExpFragmentName() {
		PlaySiteConfigurationUtils.getCategoryFilterExpFragmentName();
	}
	@Test
	public void getRetailerInterstitialPath() {
		PlaySiteConfigurationUtils.getRetailerInterstitialPath();
	}
}
