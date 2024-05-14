package com.mattel.ag.ecomm.core.model;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ag.retail.core.utils.PropertyReaderUtils;

@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SharedSearchModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(SharedSearchModel.class);

	@OSGiService
	private PropertyReaderUtils propertyReaderUtils;
	@Inject
	private String searchTarget;

	@Inject
	private String topResult;
	@Inject
	private String related;
	@Inject
	private String popular;
	@Inject
	private String article;

	@Inject
	private String categories;
	@Inject
	private String characterLimit;
	@Inject
	private String suggestionLimit;

	private String snpAccountUrl;
	private String typeAheadAccountUrl;
	private String snpParams;
	private String targetSnpUrl;

	@PostConstruct
	protected void init() {
		LOGGER.info("Start of init method of SharedSearchModel");

		if (propertyReaderUtils != null) {
			snpAccountUrl = propertyReaderUtils.getSnpAccountURLs();
			typeAheadAccountUrl = propertyReaderUtils.getTypeAheadAccountURLs();
			snpParams = propertyReaderUtils.getSnpParams();
			targetSnpUrl = propertyReaderUtils.getTargetSnpUrl();
		}
		LOGGER.info("End of init method of SharedSearchModel");

	}

	public String getSnpAccountUrl() {
		return snpAccountUrl;
	}

	public void setSnpAccountUrl(String snpAccountUrl) {
		this.snpAccountUrl = snpAccountUrl;
	}

	public String getTypeAheadAccountUrl() {
		return typeAheadAccountUrl;
	}

	public void setTypeAheadAccountUrl(String typeAheadAccountUrl) {
		this.typeAheadAccountUrl = typeAheadAccountUrl;
	}

	public PropertyReaderUtils getPropertyReaderUtils() {
		return propertyReaderUtils;
	}

	public String getSearchTarget() {
		return searchTarget;
	}

	public String getTopResult() {
		return topResult;
	}

	public String getRelated() {
		return related;
	}

	public String getPopular() {
		return popular;
	}

	public String getArticle() {
		return article;
	}

	public String getCharacterLimit() {
		return characterLimit;
	}

	public String getSuggestionLimit() {
		return suggestionLimit;
	}

	public String getCategories() {
		return categories;
	}

	public String getSnpParams() {
		return snpParams;
	}

	public String getTargetSnpUrl() {
		return targetSnpUrl;
	}
}