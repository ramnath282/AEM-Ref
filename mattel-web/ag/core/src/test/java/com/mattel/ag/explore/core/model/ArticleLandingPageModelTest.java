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

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ag.explore.core.constants.Constants;
import com.mattel.ag.explore.core.pojos.RelatedArticlePojo;
import com.mattel.ag.explore.core.pojos.TagsPojo;
import com.mattel.ag.explore.core.services.GetRelatedArticles;

public class ArticleLandingPageModelTest {

	GetRelatedArticles getRelatedArticles;

	ArticleLandingPageModel articleLandingPageModel;

	PageManager pageManager;

	RelatedArticlePojo relatedArticlePojo;

	ResourceResolver resourceResolver;

	TagsPojo tagsPojo;

	Resource resource;

	Page page;

	ValueMap valueMap;

	String[] primaryTags = { "abc", "cde" };

	String[] secondaryTags = { "abc", "cde" };

	List<RelatedArticlePojo> relatedArticlePojos;

	List<TagsPojo> tagsPojos;

	@Before
	public void setUp() {

		getRelatedArticles = Mockito.mock(GetRelatedArticles.class);
		articleLandingPageModel = new ArticleLandingPageModel();
		valueMap = Mockito.mock(ValueMap.class);
		tagsPojos = new ArrayList<>();
		tagsPojo = new TagsPojo();
		tagsPojo.setTagName("abc");
		tagsPojos.add(tagsPojo);
		relatedArticlePojos = new ArrayList<>();
		pageManager = Mockito.mock(PageManager.class);
		relatedArticlePojo = new RelatedArticlePojo();
		relatedArticlePojo.setArticleTitle("articleTitle");
		relatedArticlePojo.setArticleDescription("articleDescription");
		relatedArticlePojo.setArticleLandingHero("articleLandingHero");
		relatedArticlePojo.setPagePath("pagePath");
		relatedArticlePojo.setEnableSocialMediaSharing("enableSocialMediaSharing");
		relatedArticlePojo.setPrimaryTag(tagsPojos);
		resourceResolver = Mockito.mock(ResourceResolver.class);
		page = Mockito.mock(Page.class);
		resource = Mockito.mock(Resource.class);
		articleLandingPageModel.setGetRelatedArticles(getRelatedArticles);
		articleLandingPageModel.setResource(resource);
		List<String> primaryTagList = new ArrayList<>();
		primaryTagList.add("abc");
		primaryTagList.add("cde");
		List<String> secondaryTagList = new ArrayList<>();
		secondaryTagList.add("abc");
		secondaryTagList.add("cde");
		List<String> limit = new ArrayList<>();
		limit.add("1");
		Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
		Mockito.when(resource.getResourceResolver().adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(pageManager.getContainingPage(resource)).thenReturn(page);
		Mockito.when(page.getProperties()).thenReturn(valueMap);
		Mockito.when(page.getProperties().get(Constants.PRIMARY_TAGS, String[].class)).thenReturn(primaryTags);
		Mockito.when(page.getProperties().get(Constants.SECONDARY_TAGS, String[].class)).thenReturn(secondaryTags);
		Map<String, List<String>> listMap = new HashMap<>();
		listMap.put(Constants.PRIMARY_TAGS, primaryTagList);
		listMap.put(Constants.DEFAULT_LIMIT, limit);
		relatedArticlePojos.add(relatedArticlePojo);
		Mockito.when(getRelatedArticles.getRelatedArticles(listMap)).thenReturn(relatedArticlePojos);

	}

	@Test
	public void init() {
		articleLandingPageModel.init();
	}

	@Test
	public void getDescription() {
		articleLandingPageModel.getDescription();
	}

	@Test
	public void getPrimaryTagTitle() {
		articleLandingPageModel.getPrimaryTagTitle();
	}

	@Test
	public void getTitle() {
		articleLandingPageModel.getTitle();
	}

	@Test
	public void getArticleBannerImagePath() {
		articleLandingPageModel.getArticleBannerImagePath();
	}

	@Test
	public void getPagePath() {
		articleLandingPageModel.getPagePath();
	}

	@Test
	public void getSocialMediaEnabled() {
		articleLandingPageModel.getSocialMediaEnabled();
	}

}
