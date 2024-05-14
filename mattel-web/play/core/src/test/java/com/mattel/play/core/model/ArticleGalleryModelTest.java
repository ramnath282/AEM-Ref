package com.mattel.play.core.model;

import java.util.LinkedList;
import java.util.List;

import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.play.core.pojos.ArticlePojo;
import com.mattel.play.core.services.TileGalleryAndLandingService;

public class ArticleGalleryModelTest {
	
	ArticleGalleryModel articleGalleryModel = new ArticleGalleryModel();
	
	Resource resource;
	PageManager pageManager;
	ResourceResolver resourceResolver;
	Page page = null;
	TagManager tagManager;
	Tag galleryTag;
	TileGalleryAndLandingService tileGalleryAndLandingService;
	List<ArticlePojo> categoryArticleList = new LinkedList<>();
	ArticlePojo articlePojo = new ArticlePojo();
	String[] pages;
	
	@Before
    public void setUp() {
		resource = Mockito.mock(Resource.class);
		resourceResolver = Mockito.mock(ResourceResolver.class);
		pageManager = Mockito.mock(PageManager.class);
		page = Mockito.mock(Page.class);
		tileGalleryAndLandingService = Mockito.mock(TileGalleryAndLandingService.class);
		tagManager = Mockito.mock(TagManager.class);
		galleryTag = Mockito.mock(Tag.class);
		
		
		pages = new String [2];
		pages[0] = "ujjwal";
		pages[1] = "deep";
		articlePojo.setAlwaysEnglish("");
		categoryArticleList.add(articlePojo);
		articleGalleryModel.setResource(resource);
		articleGalleryModel.setTileGalleryAndLandingService(tileGalleryAndLandingService);
		articleGalleryModel.setGalleryCategory("");
		articleGalleryModel.setHomePagePath("");
		articleGalleryModel.setPages(pages);
		
		Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
		Mockito.when(resource.getResourceResolver().adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(pageManager.getContainingPage(resource)).thenReturn(page);
		Mockito.when(page.getAbsoluteParent(5)).thenReturn(page);
		Mockito.when(page.getAbsoluteParent(5).getPath()).thenReturn("");
		Mockito.when(tagManager.resolve("")).thenReturn(galleryTag);
		Mockito.when(galleryTag.getTagID()).thenReturn("");
		Mockito.when(resource.getResourceResolver().adaptTo(TagManager.class)).thenReturn(tagManager);
		Mockito.when(tileGalleryAndLandingService.getArticleTilesByDate("", "", resource.getResourceResolver())).thenReturn(categoryArticleList);
		Mockito.when(tileGalleryAndLandingService.getArticleTilesByDate("", null, resource.getResourceResolver())).thenReturn(categoryArticleList);
		
	}
	
	@Test
    public void init() {

		articleGalleryModel.init();

    }
	@Test
	public void getCategoryArticleList() {
		articleGalleryModel.getCategoryArticleList();
	}
	@Test
	public void getByDateArticleList() {
		articleGalleryModel.getByDateArticleList();
	}
	@Test
	public void getManualAuthorArticleList() throws RepositoryException {
		articleGalleryModel.getManualAuthorArticleList();
	}
	
	
}
