package com.mattel.ag.explore.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.day.cq.wcm.api.Page;
import com.mattel.ag.explore.core.pojos.RelatedArticlePojo;
import com.mattel.ag.explore.core.services.GetRelatedArticles;

public class UpdateVanityUrlServletTest {
	
	UpdateVanityUrlServlet updateVanityUrlServlet;
	SlingHttpServletRequest request; 
	SlingHttpServletResponse response;
	Page page;
	Resource resource;
	ResourceResolver resourceResolver;
	Node node;
	GetRelatedArticles getRelatedArticlesWrapper;	
	Map<String, List<String>> queryDataMap = new HashMap<>();
	List<String> contentPath = new ArrayList<>();
	RelatedArticlePojo relatedArticle = new RelatedArticlePojo();
	List<RelatedArticlePojo> relatedPojoList = new ArrayList<>();
	
	@Before
	public void setUp() {
		updateVanityUrlServlet = new UpdateVanityUrlServlet();
		request = Mockito.mock(SlingHttpServletRequest.class);
		response = Mockito.mock(SlingHttpServletResponse.class);
		page = Mockito.mock(Page.class);
		resourceResolver = Mockito.mock(ResourceResolver.class);
		resource = Mockito.mock(Resource.class);
		node = Mockito.mock(Node.class);
		getRelatedArticlesWrapper = Mockito.mock(GetRelatedArticles.class);
		contentPath.add("/content/ag/en/explore");
		queryDataMap.put("path", contentPath);
		relatedArticle.setArticleDescription("desc");
		relatedArticle.setArticleTitle("title");
		relatedArticle.setPagePath("");
		relatedPojoList.add(relatedArticle);
		relatedPojoList.add(relatedArticle);
		
		updateVanityUrlServlet.setGetRelatedArticlesWrapper(getRelatedArticlesWrapper);
		
		Mockito.when(getRelatedArticlesWrapper.getRelatedArticles(queryDataMap)).thenReturn(relatedPojoList);
		Mockito.when(request.getResourceResolver()).thenReturn(resourceResolver);
		Mockito.when(request.getResourceResolver().getResource("" + "/jcr:content")).thenReturn(resource);
		Mockito.when(request.getResourceResolver().getResource("")).thenReturn(resource);
		Mockito.when(resource.adaptTo(Page.class)).thenReturn(page);
		Mockito.when(resource.adaptTo(Node.class)).thenReturn(node);
		
	}
	@Test
	public void doGet() throws IOException {
		updateVanityUrlServlet.doGet(request, response);
	}
}
