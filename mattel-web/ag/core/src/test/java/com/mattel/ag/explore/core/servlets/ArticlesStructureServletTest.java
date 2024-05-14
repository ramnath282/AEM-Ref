package com.mattel.ag.explore.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.mattel.ag.explore.core.pojos.TagsPojo;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.ag.explore.core.pojos.RelatedArticlePojo;
import com.mattel.ag.explore.core.services.GetRelatedArticles;

public class ArticlesStructureServletTest {
	
	ArticlesStructureServlet articlesStructureServlet;
	GetRelatedArticles getRelatedArticlesWrapper;
	RelatedArticlePojo relatedArticlePojo;
	TagsPojo TagsPojo;
	SlingHttpServletRequest request; 
	SlingHttpServletResponse response;
	PrintWriter printWriter ;
	
	@Before
	public void setUp() throws IOException{
		TagsPojo tagsPojo = new TagsPojo();
		tagsPojo.setTagName("abc");
		List<TagsPojo> cqTag  = new ArrayList<>();
		cqTag.add(tagsPojo);
		articlesStructureServlet = new ArticlesStructureServlet();
		relatedArticlePojo = new RelatedArticlePojo();
		request = Mockito.mock(SlingHttpServletRequest.class);
		response = Mockito.mock(SlingHttpServletResponse.class);
		relatedArticlePojo.setArticleCreatedDate("articleCreatedDate");
		printWriter = Mockito.mock(PrintWriter.class);
		relatedArticlePojo.setArticleDate("articleDate");
		relatedArticlePojo.setArticleDescription("articleDescription");
		relatedArticlePojo.setArticleDisplayDate("articleDisplayDate");
		relatedArticlePojo.setArticleGridImage("articleGridImage");
		relatedArticlePojo.setArticleHeroImage("articleHeroImage");
		relatedArticlePojo.setArticleID("articleID");
		relatedArticlePojo.setArticleLandingHero("articleLandingHero");
		relatedArticlePojo.setArticleTitle("articleTitle");
		relatedArticlePojo.setArticleUpdatedDate("articleUpdatedDate");
		relatedArticlePojo.setCqTag(cqTag);
		relatedArticlePojo.setDisplayhomepage("displayhomepage");
		relatedArticlePojo.setDisplaysitehome("displaysitehome");
		relatedArticlePojo.setEnableSocialMediaSharing("enableSocialMediaSharing");
		relatedArticlePojo.setHideArticleDate(Boolean.TRUE);
		relatedArticlePojo.setKeywords("keywords");
		relatedArticlePojo.setPagePath("pagePath");
		relatedArticlePojo.setPrimaryTag(cqTag);
		relatedArticlePojo.setSecondaryTag(cqTag);
		
		getRelatedArticlesWrapper = Mockito.mock(GetRelatedArticles.class);
		articlesStructureServlet.setGetRelatedArticlesWrapper(getRelatedArticlesWrapper);
		Map<String, List<String>> querryMap = new HashMap<>();
		List<String> propertyList = new ArrayList<>();
		List<RelatedArticlePojo> relatedArticlePojos = new ArrayList<RelatedArticlePojo>();
		relatedArticlePojos.add(relatedArticlePojo);
		propertyList.add("/conf/ag/settings/wcm/templates/article-template-page");
		querryMap.put(com.mattel.ag.retail.core.constants.Constants.CQ_TEMPLATE, propertyList);
		Mockito.when( getRelatedArticlesWrapper.getRelatedArticles(querryMap)).thenReturn(relatedArticlePojos);
		Mockito.when(response.getWriter()).thenReturn(printWriter);
	}
	
	@Test
	public void doGet() throws IOException{
		articlesStructureServlet.doGet(request, response);
	}

}
