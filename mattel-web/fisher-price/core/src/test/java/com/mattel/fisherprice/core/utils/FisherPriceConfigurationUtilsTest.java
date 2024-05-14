package com.mattel.fisherprice.core.utils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.fisherprice.core.utils.FisherPriceConfigurationUtils.Config;

public class FisherPriceConfigurationUtilsTest {
	FisherPriceConfigurationUtils fisherPriceConfigurationUtils;
	Config config;

	@Before
	public void setUp() {
		fisherPriceConfigurationUtils = new FisherPriceConfigurationUtils();
		config = Mockito.mock(Config.class);
	}

	@Test
	public void activate() {
		fisherPriceConfigurationUtils.activate(config);
	}

	@Test
	public void getExpFragmentRootPath() {
		FisherPriceConfigurationUtils.getExpFragmentRootPath();
	}

	@Test
	public void getRootErrorPageName() {
		FisherPriceConfigurationUtils.getRootErrorPageName();
	}

	@Test
	public void getClientlibRootCategoryName() {
		FisherPriceConfigurationUtils.getClientlibRootCategoryName();
	}

	@Test
	public void getHeaderExpFragmentName() {
		FisherPriceConfigurationUtils.getHeaderExpFragmentName();
	}

	@Test
	public void getFooterExpFragmentName() {
		FisherPriceConfigurationUtils.getFooterExpFragmentName();
	}

	@Test
	public void getRetailerInterstitialPath() {
		FisherPriceConfigurationUtils.getRetailerInterstitialPath();
	}

	@Test
	public void getLeavingInterstitialPath() {
		FisherPriceConfigurationUtils.getLeavingInterstitialPath();
	}

	@Test
	public void getInterstitialApp() {
		FisherPriceConfigurationUtils.getInterstitialApp();
	}

	@Test
	public void getGlobalfooterExpFragmentName() {
		FisherPriceConfigurationUtils.getGlobalfooterExpFragmentName();
	}

	@Test
	public void getExcludedBrandsFooter() {
		FisherPriceConfigurationUtils.getExcludedBrandsFooter();
	}
}
