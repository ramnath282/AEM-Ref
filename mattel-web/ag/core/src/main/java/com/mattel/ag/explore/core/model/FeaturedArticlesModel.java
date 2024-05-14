package com.mattel.ag.explore.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ag.explore.core.constants.Constants;
import com.mattel.ag.explore.core.pojos.RelatedArticlePojo;
import com.mattel.ag.explore.core.pojos.TagsPojo;
import com.mattel.ag.explore.core.services.GetRelatedArticles;
import com.mattel.ag.explore.core.services.GetResourceResolver;

/**
 * @author CTS. A Model class for Featured Articles
 */

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FeaturedArticlesModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(FeaturedArticlesModel.class);
	List<String> articlePaths = new ArrayList<>();
	Map<String, List<String>> queryDataMap = new HashMap<>();
	List<RelatedArticlePojo> articles = new ArrayList<>();
	List<String> displayOnPageValues = new ArrayList<>();
	
	@Inject
	private GetRelatedArticles getArticles;

	@Inject
	private String pathForArticle1;
	@Inject
	private String pathForArticle2;
	@Inject
	private String pathForArticle3;
	@Inject
	private String maxNumofArticles;
	@Inject
	private GetResourceResolver resolver;

	@PostConstruct
	protected void init() {
		LOGGER.info("Featured Articles Model Start");
		if (null != pathForArticle1) {
			articlePaths.add(pathForArticle1);
		}
		if (null != pathForArticle2) {
			articlePaths.add(pathForArticle2);
		}
		if (null != pathForArticle3) {
			articlePaths.add(pathForArticle3);
		}
		LOGGER.debug("articlePaths:{}", articlePaths);
		if (!articlePaths.isEmpty()) {
			LOGGER.debug("list articlePaths is not empty");
			articles = getArticlesForPath(articlePaths);
		} else {
			// call default path method
			articlePaths.add(maxNumofArticles != null ? maxNumofArticles : "3");
			queryDataMap.put(Constants.DEFAULT_LIMIT, articlePaths);
			displayOnPageValues.add("false");
			queryDataMap.put(Constants.DISPLAY_HOME_PAGE, displayOnPageValues);
			LOGGER.debug("Map to be sent from Related article is {}", queryDataMap);
			articles = getArticles.getRelatedArticles(queryDataMap);
		}
		LOGGER.debug("sorted articles:{}", articles);

		LOGGER.info("Featured Articles Model End");
	}

	/**
	 * @param articlePaths
	 * @return
	 */
	private List<RelatedArticlePojo> getArticlesForPath(List<String> articlePaths) {
		LOGGER.info("getArticlesForPath Start");
		ResourceResolver resourceResolver = resolver.getResourceResolver();
		List<RelatedArticlePojo> getArticlesForPath = new ArrayList<>();
		for (String articlePath : articlePaths) {
			Resource resource = resourceResolver.getResource(articlePath + "/jcr:content");
			if (null != resource) {
				ValueMap valueMap = resource.getValueMap();
				RelatedArticlePojo relatedArticlePojo = new RelatedArticlePojo();
				if (null != valueMap.get(Constants.TITLE, String.class)) {
					relatedArticlePojo.setArticleTitle(valueMap.get(Constants.TITLE, String.class));
				}
				if (null != valueMap.get(Constants.ARTICLE_DESCRIPTION, String.class)) {
					relatedArticlePojo.setArticleDescription(valueMap.get(Constants.ARTICLE_DESCRIPTION, String.class));
				}
				if (null != valueMap.get(Constants.PRIMARY_TAGS, String[].class)) {
					List<TagsPojo> ptagsPojoList = getArticles
							.getTagRelatedData(valueMap.get(Constants.PRIMARY_TAGS, String[].class));
					relatedArticlePojo.setPrimaryTag(ptagsPojoList);
				}
				Resource res = resolver.getResourceResolver()
						.getResource(articlePath + "/jcr:content/root/articlebannercompone");
				setImagePath(res, relatedArticlePojo);
				setVanityUrl(valueMap, relatedArticlePojo, articlePath);

				if (null != valueMap.get(Constants.ARTICLE_SOCIAL_SHARING, String.class)) {
					relatedArticlePojo
							.setEnableSocialMediaSharing(valueMap.get(Constants.ARTICLE_SOCIAL_SHARING, String.class));
				}
				getArticlesForPath.add(relatedArticlePojo);
			}
		}
		LOGGER.info("getArticlesForPath end");
		return getArticlesForPath;
	}

	/**
	 *
	 * @param valueMap
	 * @param relatedArticlePojo
	 * @param articlePath
	 */
	private void setVanityUrl(ValueMap valueMap, RelatedArticlePojo relatedArticlePojo, String articlePath) {
		if (valueMap.containsKey("sling:vanityPath")) {
			relatedArticlePojo.setPagePath(valueMap.get("sling:vanityPath", String.class));
		} else {
			relatedArticlePojo.setPagePath(articlePath + ".html");
		}

	}

	/**
	 * @param res
	 * @param relatedArticlePojo
	 */
	private void setImagePath(Resource res, RelatedArticlePojo relatedArticlePojo) {
		LOGGER.info("setImagePath start");
		if (res != null) {
			LOGGER.debug("Resource Not Null");
			ValueMap vMap = res.getValueMap();
			LOGGER.debug("ValueMap is {}", vMap);
			String articleHeroImagePath = vMap.get("articleHeroImagePath", String.class);
			String articleGridImagepath = vMap.get("articleGridImagePath", String.class);
			String arttilceLandingHeroImagePath = vMap.get("articleLandingHeroImagePath", String.class);
			relatedArticlePojo.setArticleHeroImage(articleHeroImagePath);
			relatedArticlePojo.setArticleGridImage(articleGridImagepath);
			relatedArticlePojo.setArticleLandingHero(arttilceLandingHeroImagePath);
		}
		LOGGER.info("setImagePath end");
	}

	/**
	 * @return articles
	 */
	public List<RelatedArticlePojo> getArticles() {
		return articles;
	}

	public void setQueryDataMap(Map<String, List<String>> queryDataMap) {
		this.queryDataMap = queryDataMap;
	}

	public void setPathForArticle1(String pathForArticle1) {
		this.pathForArticle1 = pathForArticle1;
	}

	public void setPathForArticle2(String pathForArticle2) {
		this.pathForArticle2 = pathForArticle2;
	}

	public void setPathForArticle3(String pathForArticle3) {
		this.pathForArticle3 = pathForArticle3;
	}

	public void setResolver(GetResourceResolver resolver) {
		this.resolver = resolver;
	}
	
	public void setGetArticles(GetRelatedArticles getArticles) {
		this.getArticles = getArticles;
	}
}
