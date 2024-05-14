package com.mattel.ag.explore.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.Template;
import com.mattel.ag.explore.core.pojos.RelatedArticlePojo;
import com.mattel.ag.explore.core.services.GetRelatedArticles;

@RunWith(PowerMockRunner.class)
public class RelatedArticleServletTest {

	RelatedArticleServlet relatedArticleServlet;
	GetRelatedArticles getRelatedArticlesWrapper;
	SlingHttpServletRequest request;
	SlingHttpServletResponse response;
	ResourceResolver resolver;
	Resource resource;
	PageManager pageManager;
	Page page;
	ValueMap valueMap;
	Template pageTemplate;
	

	@Before
	public void setUp() throws IOException {
		relatedArticleServlet = new RelatedArticleServlet();
		getRelatedArticlesWrapper = Mockito.mock(GetRelatedArticles.class);
		relatedArticleServlet.setGetRelatedArticlesWrapper(getRelatedArticlesWrapper);
		resolver = Mockito.mock(ResourceResolver.class);
		resource = Mockito.mock(Resource.class);
		pageManager = Mockito.mock(PageManager.class);
		page = Mockito.mock(Page.class);
		valueMap = Mockito.mock(ValueMap.class);
		pageTemplate = Mockito.mock(Template.class);
	}

	@Test
	public void testDoGet() throws IOException {
		request = Mockito.mock(SlingHttpServletRequest.class);
		response = Mockito.mock(SlingHttpServletResponse.class);
		@SuppressWarnings("unchecked")
		Map<String, List<String>> queryDataMap = Mockito.mock(Map.class);
		@SuppressWarnings("unchecked")
		List<RelatedArticlePojo> list = Mockito.mock(List.class);
		RelatedArticlePojo relatedArticlePojo = new RelatedArticlePojo();
		relatedArticlePojo.setPagePath("pagePath");
	    relatedArticlePojo.setArticleGridImage("ArticleImage");
	    list.add(relatedArticlePojo);
		PrintWriter printWriter = Mockito.mock(PrintWriter.class);
		
		RequestPathInfo requestPathInfo = Mockito.mock(RequestPathInfo.class); 
		Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		Mockito.when(requestPathInfo.getSelectorString()).thenReturn("!content!ag!en!explore!articles!well-being!20-years-of-care-and-keeping");
		Mockito.when(request.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.getResource("/content/ag/en/explore/articles/well-being/20-years-of-care-and-keeping")).thenReturn(resource);
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(pageManager.getContainingPage(resource)).thenReturn(page);
		Mockito.when(resource.adaptTo(Page.class)).thenReturn(page);
		
		Mockito.when(page.getTemplate()).thenReturn(pageTemplate);
	    Mockito.when(pageTemplate.getPath()).thenReturn("/conf/ag/settings/wcm/templates/article-template-landing-page");
	    String[] primaryTags = {"AG","EN"};
	    String[] secondaryTags ={"secondaryTag1","secondaryTag2"};
		Mockito.when(page.getProperties()).thenReturn(valueMap);
		Mockito.when( page.getProperties().get("primaryTags", String[].class)).thenReturn(primaryTags);
        Mockito.when(page.getProperties().get("secondaryTags", String[].class)).thenReturn(secondaryTags);
		Mockito.when(getRelatedArticlesWrapper.getRelatedArticles(queryDataMap)).thenReturn(list);
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		relatedArticleServlet.doGet(request, response);
	}

}
