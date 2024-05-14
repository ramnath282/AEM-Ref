package com.mattel.ag.explore.core.servlets;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.google.gson.Gson;
import com.mattel.ag.explore.core.pojos.RelatedArticlePojo;
import com.mattel.ag.explore.core.services.GetRelatedArticles;
import javax.servlet.Servlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

import static com.mattel.ag.explore.core.constants.Constants.ARTICLE_LANDING_TEMPLATE_PATH;
import static com.mattel.ag.explore.core.constants.Constants.PRIMARY_TAGS;
import static com.mattel.ag.explore.core.constants.Constants.SECONDARY_TAGS;

/**
 * @author CTS RelatedArticleServlet.
 */
@Component(service = Servlet.class, immediate = true, property = {
		Constants.SERVICE_DESCRIPTION + "=Related Article Servlet",
		"sling.servlet.paths=" + "/bin/getrelatedarticles" })

public class RelatedArticleServlet extends SlingSafeMethodsServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(RelatedArticleServlet.class);
	@Reference
	private transient GetRelatedArticles getRelatedArticlesWrapper;

	@Override
	protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws IOException {

		LOGGER.info("RelatedArticleServlet: doGet started");
		String json = "";
		List<RelatedArticlePojo> list = new ArrayList<>();
		Page page = null;
		String template = null;
		Map<String, List<String>> queryDataMap = new HashMap<>();
		String selector = request.getRequestPathInfo().getSelectorString();
		if(null !=selector){
			String pagePath = selector.replaceAll("!", "/");
			List<String> allTagsList = getTagList(request , pagePath);
			LOGGER.debug("All Tags are:{}", allTagsList);
			queryDataMap.put("allTags", allTagsList);
			list = getRelatedArticlesWrapper.getRelatedArticles(queryDataMap);
			Resource resource = request.getResourceResolver().getResource(pagePath);
			if (null != resource) {
				page = resource.adaptTo(Page.class);
			}
			if (null != page) {
				template = page.getTemplate().getPath();
				LOGGER.debug("Template path is {}", template);
			}
			if (ARTICLE_LANDING_TEMPLATE_PATH.equals(template)) {
				removeDuplicateArticle(list, page, resource);
			}
			for (int i = 0; i < list.size(); i++) {
                String path = pagePath.concat(".html");
				if (path.equals(list.get(i).getPagePath())) {
					list.remove(i);
				}
			}
			LOGGER.debug("Page path request is {}", pagePath);
			LOGGER.debug("final list for events is:{}", list);
		}
		Gson gson = new Gson();
		json = gson.toJson(list);
		response.setContentType("application/json");
		response.getWriter().write(json);
		LOGGER.info("EventCreationServlet: doGet end");
	}

	private void removeDuplicateArticle(List<RelatedArticlePojo> list, Page page, Resource resource) {
		int i = 0;
		int j = 0;
		while (i < list.size() && j < 1) {
			String pagePath1 = list.get(i).getPagePath();
			Resource pageresource = resource.getResourceResolver().resolve(pagePath1);
			Page listFromPage = pageresource.adaptTo(Page.class);
			if (null != listFromPage) {
				String mainPrimaryTags = page.getProperties().get(PRIMARY_TAGS, String.class);
				String primaryTag = listFromPage.getProperties().get(PRIMARY_TAGS, String.class);
				if (null != primaryTag && primaryTag.equalsIgnoreCase(mainPrimaryTags) && j < 1) {
					String removedPage = list.get(i).getPagePath();
					LOGGER.debug(removedPage);
					list.remove(i);
					j++;
				}
				i++;
			}
		}
	}

	private List<String> getTagList(SlingHttpServletRequest request, String pagePath) {
		LOGGER.info("Start of getTagList() method in RelatedArticleServlet");
		List<String> tagList = new ArrayList<>();
		ResourceResolver resolver = request.getResourceResolver();
		Resource resource = resolver.getResource(pagePath);
		if(null != resource){
			PageManager pageManager = null;
			Page page = null;
			pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
			if (null != pageManager) {
				page = pageManager.getContainingPage(resource);
				String[] primaryTags = page.getProperties().get(PRIMARY_TAGS, String[].class);
				String[] secondaryTags = page.getProperties().get(SECONDARY_TAGS, String[].class);
				if(null != primaryTags){
					addTagsToList(tagList,primaryTags);
				}
				if(null != secondaryTags){
					addTagsToList(tagList,secondaryTags);
				}
				LOGGER.debug("primaryTags is {}",primaryTags);
				LOGGER.debug("secondaryTags is {}",secondaryTags);
			}
		}
		LOGGER.info("End of getTagList() method in RelatedArticleServlet");
		return tagList;
	}
	private void addTagsToList(List<String> tagList, String[] primaryTags) {
		LOGGER.info("Start of addTagsToList() method in RelatedArticleServlet");
		for (int i = 0; i < primaryTags.length; i++) {
			tagList.add(primaryTags[i]);
		}
		LOGGER.debug("Final list of tags is {}",tagList);
		LOGGER.info("End of addTagsToList() method in RelatedArticleServlet");
	}
	public void setGetRelatedArticlesWrapper(GetRelatedArticles getRelatedArticlesWrapper) {
		this.getRelatedArticlesWrapper = getRelatedArticlesWrapper;
	}
}
