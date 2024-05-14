package com.mattel.ag.explore.core.model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ag.explore.core.pojos.TagsPojo;
import com.mattel.ag.explore.core.services.GetRelatedArticles;

/**
 * @author CTS. A Model class for Featured Articles
 */

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FilterArticlesModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(FilterArticlesModel.class);
	List<TagsPojo> tagsAuthored = new ArrayList<>();

	@Self
	private Resource resource;
	@Inject
	private GetRelatedArticles relatedArticlesService;

	@PostConstruct
	protected void init() {
		LOGGER.info("Filter Articles Model Start");
		if (null != resource) {
			ValueMap valueMap = resource.getValueMap();
			if (null != valueMap.get("filterTags", String[].class)) {
				String[] filterTags = valueMap.get("filterTags", String[].class);

				if (null != filterTags) {
					tagsAuthored = relatedArticlesService.getTagRelatedData(filterTags);
				}
			}
		}
		LOGGER.info("Filter Articles Model End");
	}

	/**
	 * @return tagsAuthored
	 */
	public List<TagsPojo> getTagsAuthored() {
		return tagsAuthored;
	}

	public void setTagsAuthored(List<TagsPojo> tagsAuthored) {
		this.tagsAuthored = tagsAuthored;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public void setRelatedArticlesService(GetRelatedArticles relatedArticlesService) {
		this.relatedArticlesService = relatedArticlesService;
	}

}
