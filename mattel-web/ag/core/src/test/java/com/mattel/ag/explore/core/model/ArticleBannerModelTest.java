package com.mattel.ag.explore.core.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ag.explore.core.pojos.TagsPojo;
import com.mattel.ag.explore.core.services.GetRelatedArticles;

public class ArticleBannerModelTest {
	ArticleBannerModel articleBannerModel = new ArticleBannerModel();
	Resource resource;
	GetRelatedArticles getRelatedArticles;
	ResourceResolver resourceResolver;
	PageManager pageManager;
	Page page;
	TagsPojo tagsPojo;
	List<TagsPojo> primaryTag;
	List<TagsPojo> secondaryTag;
	ValueMap valueMap ;
	String[] primaryTags;
	String[] secondaryTags;
	String datePublished = "2018-09-12T14:03:30.033+05:30";
	String dateHide;
	String pageTitle;

	@Before
	public void setUp() {
		resource = Mockito.mock(Resource.class);
		getRelatedArticles = Mockito.mock(GetRelatedArticles.class);
		pageManager = Mockito.mock(PageManager.class);
		resourceResolver = Mockito.mock(ResourceResolver.class);
		valueMap = Mockito.mock(ValueMap.class);
		page = Mockito.mock(Page.class);
		tagsPojo = new TagsPojo();
		primaryTag = new ArrayList<TagsPojo>();
		secondaryTag = new ArrayList<TagsPojo>();
		tagsPojo.setTagName("abc");
		primaryTag.add(tagsPojo);
		secondaryTag.add(tagsPojo);
		articleBannerModel.setGetRelatedArticles(getRelatedArticles);
		articleBannerModel.setResource(resource);
		Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
		Mockito.when(pageManager.getContainingPage(resource)).thenReturn(page);
		Mockito.when(resource.getResourceResolver().adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(page.getProperties()).thenReturn(valueMap);
		Mockito.when( page.getProperties().get("primaryTags", String[].class)).thenReturn(primaryTags);
		Mockito.when(page.getProperties().get("secondaryTags", String[].class)).thenReturn(secondaryTags);
		Mockito.when(page.getProperties().get("datepickerarticle", String.class)).thenReturn(datePublished);
		Mockito.when(page.getProperties().get("hideDate", String.class)).thenReturn(dateHide);
		Mockito.when(page.getTitle()).thenReturn(pageTitle);
		Mockito.when(getRelatedArticles.getTagRelatedData(primaryTags)).thenReturn(primaryTag);
		Mockito.when(getRelatedArticles.getTagRelatedData(secondaryTags)).thenReturn(secondaryTag);
		
		
	}

	@Test
	public void init() {
		articleBannerModel.init();
	}

	@Test
	public void getPrimaryTagTitle() {
		articleBannerModel.getPrimaryTagTitle();
	}

	@Test
	public void getSecondaryTagTitle() {
		articleBannerModel.getSecondaryTagTitle();
	}

	@Test
	public void getPageTitle() {
		articleBannerModel.getPageTitle();
	}

	@Test
	public void getDate() {
		articleBannerModel.getDate();
	}

	@Test
	public void isHideDate() {
		articleBannerModel.isHideDate();
	}

}
