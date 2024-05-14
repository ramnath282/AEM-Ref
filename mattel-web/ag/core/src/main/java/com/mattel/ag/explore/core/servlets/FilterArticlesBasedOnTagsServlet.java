package com.mattel.ag.explore.core.servlets;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.mattel.ag.explore.core.pojos.RelatedArticlePojo;
import com.mattel.ag.explore.core.services.GetRelatedArticles;

/**
 * @author CTS.
 */

@Component(service = Servlet.class, immediate = true, property = {
		Constants.SERVICE_DESCRIPTION + "=Get Filtered Articles Servlet",
		"sling.servlet.paths=" + "/explore/data/filteredArticles"
})

public class FilterArticlesBasedOnTagsServlet extends SlingSafeMethodsServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(FilterArticlesBasedOnTagsServlet.class);

	@Reference
	private transient GetRelatedArticles getRelatedArticles;


	@Override
	protected void doGet(final SlingHttpServletRequest request, final
	SlingHttpServletResponse response) throws IOException {
		LOGGER.info("doGet method in FilterArticlesBasedOnTagsServlet Start");
		String json;
		Map<String, List<String>> querryMap = new HashMap<>();
		List<String> propertyList = new ArrayList<>();
		propertyList.add("");
		querryMap.put("", propertyList);
		String pagePath;
		String selector = request.getRequestPathInfo().getSelectorString();
		pagePath = selector != null ? selector.replaceAll("!", "/") : "";
		ResourceResolver resolver = request.getResourceResolver();
		Resource resource = resolver.getResource(pagePath);
		List<String> featuredArticles = new ArrayList<>();
		List<RelatedArticlePojo> finalList;
		if (null != resource) {
			Resource resource1 = resource.getChild("jcr:content/root/featuredarticles");
			if (null != resource1) {
				ValueMap valueMap = resource1.adaptTo(ValueMap.class);
				if (null != valueMap) {
					getFeaturedArticleList(featuredArticles, valueMap);
					String maxNumofArticles = valueMap.get("maxNumofArticles", String.class);
					int count = null != maxNumofArticles ? Integer.parseInt(maxNumofArticles) : 3;
					List<RelatedArticlePojo> allArticlesPojos = getRelatedArticles.getRelatedArticles(querryMap);
					finalList = getRelatedArticles.removeDuplicateArticles(featuredArticles, count, allArticlesPojos);
				} else {
					finalList = getRelatedArticles.getRelatedArticles(querryMap);
				}
			} else {
				finalList = getRelatedArticles.getRelatedArticles(querryMap);
			}
		} else {
			finalList = getRelatedArticles.getRelatedArticles(querryMap);
		}
		LOGGER.debug("Filtered articles are {}", finalList);
		Gson gson = new Gson();
		json = gson.toJson(finalList);
		response.setContentType("application/json");
		response.getWriter().write(json);
		LOGGER.info("doGet method in FilterArticlesBasedOnTagsServlet End");
	}

	private void getFeaturedArticleList(List<String> featuredArticles, ValueMap valueMap) {
		LOGGER.info("Start of getFeaturedArticleList() method in FilterArticlesBasedOnTagsServlet");
		String article1 = valueMap.get("pathForArticle1", String.class);
		if (null != article1) {
			featuredArticles.add(article1);
		}
		String article2 = valueMap.get("pathForArticle2", String.class);
		if (null != article2) {
			featuredArticles.add(article2);
		}
		String article3 = valueMap.get("pathForArticle3", String.class);
		if (null != article3) {
			featuredArticles.add(article3);
		}
	}

	public void setGetRelatedArticles(GetRelatedArticles getRelatedArticles) {
		this.getRelatedArticles = getRelatedArticles;
	}
}
