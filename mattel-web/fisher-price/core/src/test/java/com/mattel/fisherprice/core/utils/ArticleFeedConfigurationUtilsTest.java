package com.mattel.fisherprice.core.utils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.fisherprice.core.utils.ArticleFeedConfigurationUtils.Config;

public class ArticleFeedConfigurationUtilsTest {
	
	ArticleFeedConfigurationUtils articleFeedConfigurationUtils;
	
	Config config;
	
	@Before
	public void setUp() {
		articleFeedConfigurationUtils = new ArticleFeedConfigurationUtils();
		config = Mockito.mock(Config.class);
	}

	@Test
	public void activate() {
		articleFeedConfigurationUtils.activate(config);
	}
	
	@Test
	public void getLocalesForArticleFeed(){
		ArticleFeedConfigurationUtils.getLocalesForArticleFeed();
	}
	
	@Test
	public void getDamPathToUploadArticleFeed(){
		ArticleFeedConfigurationUtils.getDamPathToUploadArticleFeed();
	}
	
	@Test
	public void getArticleFeedFileInitialName(){
		ArticleFeedConfigurationUtils.getArticleFeedFileInitialName();
	}
	
	@Test
	public void getHeaderLinesForCSV(){
		ArticleFeedConfigurationUtils.getHeaderLinesForCSV();
	}
	
	@Test
	public void getArticleCompResourceType(){
		ArticleFeedConfigurationUtils.getArticleCompResourceType();
	}
	
	@Test
	public void getBrandBasePath(){
		ArticleFeedConfigurationUtils.getBrandBasePath();
	}
	
	@Test
	public void getArticleDetailTemplatePath(){
		ArticleFeedConfigurationUtils.getArticleDetailTemplatePath();
	}
	
	@Test
	public void getArticleBasePath(){
		ArticleFeedConfigurationUtils.getArticleBasePath();
	}

}
