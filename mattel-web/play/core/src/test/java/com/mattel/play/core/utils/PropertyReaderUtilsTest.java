package com.mattel.play.core.utils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.play.core.utils.PropertyReaderUtils.Config;

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
	public void getPlayPath() {
		PropertyReaderUtils.getPlayPath();
	}

	@Test
	public void getPlayDamPath() {
		PropertyReaderUtils.getPlayDamPath();
	}

	@Test
	public void getVideoDamPath() {
		PropertyReaderUtils.getVideoDamPath();
	}

	@Test
	public void getProductPath() {
		PropertyReaderUtils.getProductPath();
	}

	@Test
	public void getVideoPath() {
		PropertyReaderUtils.getVideoPath();
	}

	@Test
	public void getCharacterLandingPath() {
		PropertyReaderUtils.getCharacterLandingPath();
	}

	@Test
	public void getCharacterPath() {
		PropertyReaderUtils.getCharacterPath();
	}

	@Test
	public void getCharacterResourceType() {
		PropertyReaderUtils.getCharacterResourceType();
	}

	@Test
	public void getGameResourceType() {
		PropertyReaderUtils.getGameResourceType();
	}

	@Test
	public void getProductResourceType() {
		PropertyReaderUtils.getProductResourceType();
	}

	@Test
	public void getGamePath() {
		PropertyReaderUtils.getGamePath();
	}

	@Test
	public void getDownloadInterstitialApp() {
		PropertyReaderUtils.getDownloadInterstitialApp();
	}

	@Test
	public void getGamesLandingPath() {
		PropertyReaderUtils.getGamesLandingPath();
	}

	@Test
	public void getDamGlobalPath() {
		PropertyReaderUtils.getDamGlobalPath();
	}

	@Test
	public void getHomePath() {
		PropertyReaderUtils.getHomePath();
	}

	@Test
	public void getProductLandingPath() {
		PropertyReaderUtils.getProductLandingPath();
	}

	@Test
	public void getCommerceProductsPath() {
		PropertyReaderUtils.getCommerceProductsPath();
	}

	@Test
	public void getArticleResourceType() {
		PropertyReaderUtils.getArticleResourceType();
	}

	@Test
	public void getArticleSortOrderProperty() {
		PropertyReaderUtils.getArticleSortOrderProperty();
	}

	@Test
	public void getWhyplayScriptUrl() {
		propertyReaderUtils.getWhyplayScriptUrl();
	}

	@Test
	public void getRescuePath() {
		PropertyReaderUtils.getRescuePath();
	}
	@Test
	public void setWhyplayScriptUrl() {
		PropertyReaderUtils.setWhyplayScriptUrl("//assets.adobedtm.com/launch-EN80357e280dce4c1e99b3866b16bdb6b4-development.min.js");
	}
}
