package com.mattel.ag.explore.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.ag.explore.core.constants.Constants;
import com.mattel.ag.explore.core.pojos.RelatedArticlePojo;
import com.mattel.ag.explore.core.services.GetRelatedArticles;
import com.mattel.ag.explore.core.services.GetResourceResolver;

public class FeaturedArticlesModelTest {
	
	
	FeaturedArticlesModel FeaturedArticlesModel ;
	 GetRelatedArticles getArticles;
	 GetResourceResolver resolver;
	 ResourceResolver resourceResolver;
	 Resource resource;
	 ValueMap valueMap;
	 RelatedArticlePojo relatedArticlePojo;
	 String[] primaryTags = {"abc","def"};
	
	@Before
	public void setUp(){
		FeaturedArticlesModel = new FeaturedArticlesModel();
		getArticles = Mockito.mock(GetRelatedArticles.class);
		resolver = Mockito.mock(GetResourceResolver.class);
		resourceResolver = Mockito.mock(ResourceResolver.class);
		valueMap = Mockito.mock(ValueMap.class);
		resource = Mockito.mock(Resource.class);
		relatedArticlePojo = new RelatedArticlePojo();
		List<String> articlePaths = new ArrayList<>();
		Map<String, List<String>> queryDataMap = new HashMap<>();
		Mockito.when(resolver.getResourceResolver()).thenReturn(resourceResolver);
		FeaturedArticlesModel.setPathForArticle1("pathForArticle1");
		FeaturedArticlesModel.setPathForArticle2("pathForArticle2");
		FeaturedArticlesModel.setPathForArticle3("pathForArticle3");
		FeaturedArticlesModel.setQueryDataMap(queryDataMap);
		FeaturedArticlesModel.setResolver(resolver);
		FeaturedArticlesModel.setGetArticles(getArticles);
		Mockito.when(resourceResolver.getResource("pathForArticle1" + "/jcr:content")).thenReturn(resource);
		Mockito.when(resource.getValueMap()).thenReturn(valueMap);
		Mockito.when(valueMap.get(Constants.TITLE, String.class)).thenReturn("Title");
		Mockito.when(valueMap.get(Constants.ARTICLE_DESCRIPTION, String.class)).thenReturn("description");
		Mockito.when(valueMap.get(Constants.PRIMARY_TAGS, String[].class)).thenReturn(primaryTags);
		Mockito.when(valueMap.get(Constants.ARTICLE_SOCIAL_SHARING, String.class)).thenReturn("socialMediaEnabled");
		Mockito.when(resolver.getResourceResolver().getResource("pathForArticle1" + "/jcr:content/root/articlebannercompone")).thenReturn(resource);
	}
	
	@Test
	public void init(){
		FeaturedArticlesModel.init();
	}
	
	@Test
	public void getArticles(){
		FeaturedArticlesModel.getArticles();
	}

}
