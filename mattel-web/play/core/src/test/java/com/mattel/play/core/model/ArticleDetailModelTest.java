package com.mattel.play.core.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.day.cq.wcm.api.PageManager;
import com.mattel.play.core.pojos.TagsPojo;
import com.mattel.play.core.services.TileGalleryAndLandingService;

public class ArticleDetailModelTest {
	
	ArticleDetailModel articleDetailModel = new ArticleDetailModel();
	Resource resource;
	String[] primaryTags;
	PageManager pageManager;
	List<TagsPojo> pageTags = new ArrayList<>();
	TagsPojo tagPojo = new TagsPojo();
	TileGalleryAndLandingService tileGalleryAndLandingService;
	List<String> primaryTagTitle = new ArrayList<>();
	ResourceResolver resourceResolver;
	
	
	@Before
    public void setUp() {
		resource = Mockito.mock(Resource.class);
		pageManager = Mockito.mock(PageManager.class);
		tileGalleryAndLandingService = Mockito.mock(TileGalleryAndLandingService.class);
		resourceResolver = Mockito.mock(ResourceResolver.class);
		primaryTags = new String [2];
		primaryTags[0] = "Ujjwal";
		primaryTags[1] = "Deep";
		tagPojo.setTagID("");
		pageTags.add(tagPojo);
		primaryTagTitle.add("");
		
		articleDetailModel.setPrimaryTags(primaryTags);
		articleDetailModel.setPrimaryTagTitle(primaryTagTitle);
		articleDetailModel.setResource(resource);
		articleDetailModel.setTileGalleryAndLandingService(tileGalleryAndLandingService);
		
		Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
		Mockito.when(resource.getResourceResolver().adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(tileGalleryAndLandingService.getTagRelatedData(primaryTags)).thenReturn(pageTags);
		
	}
	
	@Test
    public void init() {

		articleDetailModel.init();

    }
	@Test
	public void getPrimaryTagTitle() {
		articleDetailModel.getPrimaryTagTitle();
	}
	
}
