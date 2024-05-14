package com.mattel.ag.explore.core.model;

import com.mattel.ag.explore.core.utils.PropertyReaderUtils;
import javax.annotation.PostConstruct;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CTS.
 */

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SocialShareTemplateModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(SocialShareTemplateModel.class);
	@OSGiService
	PropertyReaderUtils propertyReaderUtils;

	private String facebookUrl;
	private String twitterUrl;
	private String pinterestUrl;

	@PostConstruct
	protected void init() {
		LOGGER.info("Start of init method of Social Share Template Model Class");
		facebookUrl = propertyReaderUtils.getFacebookUrl();
		twitterUrl = propertyReaderUtils.getTwitterUrl();
		pinterestUrl = propertyReaderUtils.getPinterestUrl();
		LOGGER.info("End of init method of Social Share Template Model Class");
	}

	public String getTwitterUrl() {
		return twitterUrl;
	}

	public String getPinterestUrl() {
		return pinterestUrl;
	}

	public String getFacebookUrl() {
		return facebookUrl;
	}

	public void setPropertyReaderUtils(PropertyReaderUtils propertyReaderUtils) {
		this.propertyReaderUtils = propertyReaderUtils;
	}
}
