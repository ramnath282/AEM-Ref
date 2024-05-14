package com.mattel.ag.explore.core.model;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ag.explore.core.constants.Constants;
import com.mattel.ag.explore.core.pojos.RelatedArticlePojo;
import com.mattel.ag.explore.core.pojos.TagsPojo;
import com.mattel.ag.explore.core.services.GetRelatedArticles;
import javax.annotation.PostConstruct;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@Model(adaptables = Resource.class)
public class ArticleLandingPageModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(ArticleLandingPageModel.class);

	@OSGiService
	GetRelatedArticles getRelatedArticles;

	@Self
	Resource resource;

	private String primaryTagTitle;
	private String title;
	private String description;
	private String articleBannerImagePath;
	private String pagePath;
	private String socialMediaEnabled;

	@PostConstruct
	protected void init() {
		LOGGER.info("Article Landing Page Init Starts");
		if (null != resource) {
			PageManager pageManager = null;
			Page page = null;
			List<String> primaryTagList = new ArrayList<>();
			List<String> secondaryTagList = new ArrayList<>();
			List<String> limit = new ArrayList<>();
			pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
			if (null != pageManager) {
				page = pageManager.getContainingPage(resource);
				String[] primaryTags = page.getProperties().get(Constants.PRIMARY_TAGS, String[].class);
				String[] secondaryTags = page.getProperties().get(Constants.SECONDARY_TAGS, String[].class);
				if (null != primaryTags) {
					Collections.addAll(primaryTagList, primaryTags);
				}
				if (null != secondaryTags) {
					Collections.addAll(secondaryTagList, secondaryTags);
				}
				limit.add("1");
				LOGGER.debug("Primary Tags are {}", primaryTagList);
				LOGGER.debug("Secondary tags list is {}", secondaryTagList);
				Map<String, List<String>> listMap = new HashMap<>();
				listMap.put(Constants.PRIMARY_TAGS, primaryTagList);
				listMap.put(Constants.DEFAULT_LIMIT, limit);
				List<RelatedArticlePojo> relatedArticlePojos = getRelatedArticles.getRelatedArticles(listMap);
				if (!relatedArticlePojos.isEmpty()) {
					setValues(relatedArticlePojos);
					LOGGER.info("Article Landing Page Init Ends");
				}
			}
		}

	}
	private void setValues(List<RelatedArticlePojo> relatedArticlePojos) {
		LOGGER.debug("Related Articles pojos are {}", relatedArticlePojos);
		RelatedArticlePojo relatedArticlePojo = relatedArticlePojos.get(0);
		List<TagsPojo> tagsPojos = relatedArticlePojo.getPrimaryTag();
		LOGGER.debug("Tags Pojo returned from Service are {}", tagsPojos);
		if (!tagsPojos.isEmpty()) {
			TagsPojo tagsPojo = tagsPojos.get(0);
			LOGGER.debug("Tags Pojo is {}", tagsPojo);
			primaryTagTitle = tagsPojo.getTagName();
		}
		title = relatedArticlePojo.getArticleTitle();
		description = relatedArticlePojo.getArticleDescription();
		articleBannerImagePath = relatedArticlePojo.getArticleLandingHero();
		if (null == relatedArticlePojo.getVanityUrl()) {
			pagePath = relatedArticlePojo.getPagePath();
		} else {
			pagePath = relatedArticlePojo.getVanityUrl();
		}
		socialMediaEnabled = relatedArticlePojo.getEnableSocialMediaSharing();

	}

	public String getDescription() {
		return description;
	}

	public String getPrimaryTagTitle() {
		return primaryTagTitle;
	}

	public String getTitle() {
		return title;
	}

	public String getArticleBannerImagePath() {
		return articleBannerImagePath;
	}

	public String getPagePath() {
		return pagePath;
	}

	public String getSocialMediaEnabled() {
		return socialMediaEnabled;
	}

	public void setGetRelatedArticles(GetRelatedArticles getRelatedArticles) {
		this.getRelatedArticles = getRelatedArticles;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}
}
