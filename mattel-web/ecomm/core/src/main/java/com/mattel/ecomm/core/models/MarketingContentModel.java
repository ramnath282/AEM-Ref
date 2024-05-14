package com.mattel.ecomm.core.models;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.core.interfaces.MarketingContentProviderService;

/**
 * @author CTS. A Model class for Marketing Content
 */
@Model(adaptables = { Resource.class,
	    SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MarketingContentModel {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MarketingContentModel.class);

	@Inject
	MarketingContentProviderService marketingContentProviderService;

	@Inject
	@Via("resource")
	@Default(values="default")
	private String position;

	@SlingObject
	SlingHttpServletRequest request;

	private List<String> experienceFragmentPath;
	
	@PostConstruct
	protected void init() {
		final String[] selectors = request.getRequestPathInfo().getSelectors();
		final String siteKey;
		final String partNumber;
		if (selectors.length >= 2 && !StringUtils.isEmpty(selectors[0])
				&& !StringUtils.isEmpty(selectors[1])) {
			siteKey = selectors[0];
			partNumber = selectors[1];
			experienceFragmentPath = marketingContentProviderService.getContentFromTags(siteKey, partNumber,position);
			LOGGER.debug("Experience Fragments: {}",experienceFragmentPath);
		}
	}

	/**
	 * @return the experienceFragmentPath
	 */
	public List<String> getExperienceFragmentPath() {
		return experienceFragmentPath;
	}
	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

}
