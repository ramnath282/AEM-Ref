package com.mattel.ag.explore.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.ag.explore.core.pojos.RelatedArticlePojo;
import com.mattel.ag.explore.core.services.GetRelatedArticles;

public class FilterArticlesBasedOnTagsServletTest {
	
	SlingHttpServletRequest request;
	SlingHttpServletResponse response;
	FilterArticlesBasedOnTagsServlet filterArticlesBasedOnTagsServlet;
	GetRelatedArticles getRelatedArticles;
	ResourceResolver resolver;
    Resource resource, childResource;
    ValueMap valueMap;
	
	@Before
	public void setUp() throws IOException{
		filterArticlesBasedOnTagsServlet = new FilterArticlesBasedOnTagsServlet();
		getRelatedArticles = Mockito.mock(GetRelatedArticles.class);
		filterArticlesBasedOnTagsServlet.setGetRelatedArticles(getRelatedArticles);
		resolver = Mockito.mock(ResourceResolver.class);
        resource = Mockito.mock(Resource.class);
        valueMap= Mockito.mock(ValueMap.class);
        childResource = Mockito.mock(Resource.class);
	}
	
	@Test
	public void testDoGet() throws IOException{
		request = Mockito.mock(SlingHttpServletRequest.class);
		response = Mockito.mock(SlingHttpServletResponse.class);
		@SuppressWarnings("unchecked")
		Map<String, List<String>> querryMap = Mockito.mock(Map.class);
		@SuppressWarnings("unchecked")
		List<RelatedArticlePojo> allArticlesPojos = Mockito.mock(List.class);
		Mockito.when(getRelatedArticles.getRelatedArticles(querryMap)).thenReturn(allArticlesPojos);
		RequestPathInfo requestPathInfo = Mockito.mock(RequestPathInfo.class); 
		Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
	    Mockito.when(requestPathInfo.getSelectorString()).thenReturn("!content!ag!en!explore!articles!well-being!20-years-of-care-and-keeping");
	    Mockito.when(request.getResourceResolver()).thenReturn(resolver);
	    Mockito.when(resolver.getResource("/content/ag/en/explore/articles!well-being!20-years-of-care-and-keeping")).thenReturn(null);
		PrintWriter printWriter = Mockito.mock(PrintWriter.class);
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		filterArticlesBasedOnTagsServlet.doGet(request, response);
	}
	
	@Test
    public void testDoGetWithResource() throws IOException{
        request = Mockito.mock(SlingHttpServletRequest.class);
        response = Mockito.mock(SlingHttpServletResponse.class);
        @SuppressWarnings("unchecked")
        Map<String, List<String>> querryMap = Mockito.mock(Map.class);
        @SuppressWarnings("unchecked")
        List<RelatedArticlePojo> allArticlesPojos = Mockito.mock(List.class);
        Mockito.when(getRelatedArticles.getRelatedArticles(querryMap)).thenReturn(allArticlesPojos);
        RequestPathInfo requestPathInfo = Mockito.mock(RequestPathInfo.class); 
        Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
        Mockito.when(requestPathInfo.getSelectorString()).thenReturn("!content!ag!en!explore!articles!well-being!20-years-of-care-and-keeping");
        Mockito.when(request.getResourceResolver()).thenReturn(resolver);
        
        Mockito.when(resolver.getResource("/content/ag/en/explore/articles/well-being/20-years-of-care-and-keeping")).thenReturn(resource);
        Mockito.when(resource.getChild("jcr:content/root/featuredarticles")).thenReturn(childResource);
        Mockito.when(childResource.adaptTo(ValueMap.class)).thenReturn(valueMap);
        Mockito.when(valueMap.get("pathForArticle1", String.class)).thenReturn("ArticlePath1");
        Mockito.when(valueMap.get("pathForArticle2", String.class)).thenReturn("Articlepath2");
        Mockito.when(valueMap.get("pathForArticle3", String.class)).thenReturn("Articlepath3");
        Mockito.when(valueMap.get("maxNumofArticles", String.class)).thenReturn("5");
        List<RelatedArticlePojo> allArticlesPojosList = new ArrayList<RelatedArticlePojo>();
        RelatedArticlePojo relatedArticlePojo = new RelatedArticlePojo();
        relatedArticlePojo.setArticleID("1234");
        relatedArticlePojo.setArticleLandingHero("articleLandingheroImage");
        allArticlesPojosList.add(relatedArticlePojo);
        Mockito.when(getRelatedArticles.getRelatedArticles(querryMap)).thenReturn(allArticlesPojosList);
        
        PrintWriter printWriter = Mockito.mock(PrintWriter.class);
        Mockito.when(response.getWriter()).thenReturn(printWriter);
        filterArticlesBasedOnTagsServlet.doGet(request, response);
    }
	
	@Test
    public void testDoGetWithoutChildResource() throws IOException{
        request = Mockito.mock(SlingHttpServletRequest.class);
        response = Mockito.mock(SlingHttpServletResponse.class);
        @SuppressWarnings("unchecked")
        Map<String, List<String>> querryMap = Mockito.mock(Map.class);
        @SuppressWarnings("unchecked")
        List<RelatedArticlePojo> allArticlesPojos = Mockito.mock(List.class);
        Mockito.when(getRelatedArticles.getRelatedArticles(querryMap)).thenReturn(allArticlesPojos);
        RequestPathInfo requestPathInfo = Mockito.mock(RequestPathInfo.class); 
        Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
        Mockito.when(requestPathInfo.getSelectorString()).thenReturn("!content!ag!en!explore!articles!well-being!20-years-of-care-and-keeping");
        Mockito.when(request.getResourceResolver()).thenReturn(resolver);
        
        Mockito.when(resolver.getResource("/content/ag/en/explore/articles/well-being/20-years-of-care-and-keeping")).thenReturn(resource);
        Mockito.when(resource.getChild("jcr:content/root/featuredarticles")).thenReturn(null);
        List<RelatedArticlePojo> allArticlesPojosList = new ArrayList<RelatedArticlePojo>();
        RelatedArticlePojo relatedArticlePojo = new RelatedArticlePojo();
        relatedArticlePojo.setArticleID("1234");
        relatedArticlePojo.setArticleLandingHero("articleLandingheroImage");
        allArticlesPojosList.add(relatedArticlePojo);
        Mockito.when(getRelatedArticles.getRelatedArticles(querryMap)).thenReturn(allArticlesPojosList);
        
        PrintWriter printWriter = Mockito.mock(PrintWriter.class);
        Mockito.when(response.getWriter()).thenReturn(printWriter);
        filterArticlesBasedOnTagsServlet.doGet(request, response);
    }

	
	
}
