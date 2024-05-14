package com.mattel.ag.explore.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ag.explore.core.pojos.RelatedArticlePojo;
import com.mattel.ag.explore.core.pojos.TagsPojo;
import com.mattel.ag.explore.core.services.GetRelatedArticles;

/**
 * @author CTS.
 */

@Component(service = Servlet.class, immediate = true, property = {
		Constants.SERVICE_DESCRIPTION + "=Event Creation Servlet",
		"sling.servlet.paths=" + "/explore/data/articles.json" })

public class ArticlesStructureServlet extends SlingSafeMethodsServlet {

	public void setGetRelatedArticlesWrapper(GetRelatedArticles getRelatedArticlesWrapper) {
		this.getRelatedArticlesWrapper = getRelatedArticlesWrapper;
	}

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ArticlesStructureServlet.class);

	@Reference
	private transient GetRelatedArticles getRelatedArticlesWrapper;

	@Override
	protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws IOException {
		LOGGER.info("Start of do get in Articles structure Servlet");
		try {
			Map<String, List<String>> querryMap = new HashMap<>();
			List<String> propertyList = new ArrayList<>();
			propertyList.add("/conf/ag/settings/wcm/templates/article-template-page");
			querryMap.put(com.mattel.ag.retail.core.constants.Constants.CQ_TEMPLATE, propertyList);
			List<RelatedArticlePojo> relatedArticlePojos = getRelatedArticlesWrapper.getRelatedArticles(querryMap);
			LOGGER.debug("Pojo from Servlet is {}", relatedArticlePojos);
			JSONArray jsonArray = new JSONArray();
			JSONObject finalJson = new JSONObject();
			for (RelatedArticlePojo relatedArticlePojo : relatedArticlePojos) {
				
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", checkRelatedArticlePropExists(relatedArticlePojo.getArticleID()));
				jsonObject.put("type", "article");
				jsonObject.put("author", "");
				jsonObject.put("title", checkRelatedArticlePropExists(relatedArticlePojo.getArticleTitle()));
				jsonObject.put("subtitle", checkRelatedArticlePropExists(relatedArticlePojo.getArticleDescription()));
				jsonObject.put("keywords", checkRelatedArticlePropExists(relatedArticlePojo.getKeywords()));
				if (null == relatedArticlePojo.getVanityUrl()) {
					jsonObject.put("permalink", checkRelatedArticlePropExists(relatedArticlePojo.getPagePath()));
				} else {
					jsonObject.put("permalink", checkRelatedArticlePropExists(relatedArticlePojo.getVanityUrl()));
				}
				
				JSONObject imageJson = new JSONObject();
				if(StringUtils.isNotEmpty(relatedArticlePojo.getArticleLandingHero())){
					imageJson.put("homepage", relatedArticlePojo.getArticleLandingHero());
				}
				if(StringUtils.isNotEmpty(relatedArticlePojo.getArticleGridImage())){
					imageJson.put("related", relatedArticlePojo.getArticleGridImage());
				}
				
				jsonObject.put("image", imageJson);
				jsonObject.put("displayhomepage", checkRelatedArticlePropExists(relatedArticlePojo.getDisplayhomepage()));
				jsonObject.put("displaysitehome", checkRelatedArticlePropExists(relatedArticlePojo.getDisplaysitehome()));
				jsonObject.put("created", checkRelatedArticlePropExists(relatedArticlePojo.getArticleCreatedDate()));
				jsonObject.put("updated", checkRelatedArticlePropExists(relatedArticlePojo.getArticleCreatedDate()));
				
				List<TagsPojo> primaryTags = relatedArticlePojo.getPrimaryTag();
				List<TagsPojo> secondaryTags = relatedArticlePojo.getSecondaryTag();
				List<TagsPojo> cqTags = relatedArticlePojo.getCqTag();
				List<String> tagNames = null;
				tagNames = listRelatedArticleTags(primaryTags, secondaryTags, cqTags);
				if (!tagNames.isEmpty()) {
					jsonObject.put("tags", tagNames);
				} else {
					jsonObject.put("tags", "");
				}

				jsonArray.put(jsonObject);

			}
			finalJson.put("articles", jsonArray);
			response.setContentType("application/json");
			response.getWriter().write(finalJson.toString());
		} catch (JSONException je) {
			LOGGER.error("JSON Exception {}", je);
		}

	}
	
	private String checkRelatedArticlePropExists(String propertyVal){
		return  StringUtils.isNotEmpty(propertyVal)? propertyVal : "";
	}
	
	private List<String> listRelatedArticleTags(List<TagsPojo> primaryTags, List<TagsPojo> secondaryTags, List<TagsPojo> cqTags){
		List<String> tagNames = new ArrayList<>();
		if (primaryTags != null) {
			primaryTags.forEach(primaryTag->tagNames.add(primaryTag.getTagName()));
		}
		if (secondaryTags != null) {
			secondaryTags.forEach(secondaryTag->tagNames.add(secondaryTag.getTagName()));
		}
		if (cqTags != null) {
			cqTags.forEach(cqTag->tagNames.add(cqTag.getTagName()));
		}
		return tagNames;
	}

}
