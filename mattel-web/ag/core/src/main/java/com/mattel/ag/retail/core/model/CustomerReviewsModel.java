package com.mattel.ag.retail.core.model;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ag.retail.core.utils.PropertyReaderUtils;

/**
 * @author CTS. A Model class for Customer review Model
 */

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class CustomerReviewsModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerReviewsModel.class);

	@Inject
	private PropertyReaderUtils propertyReaderUtils;

	@PostConstruct
	protected void init() {
		LOGGER.info("CustomerReviewsModel Start");
		LOGGER.info("CustomerReviewsModel End");
	}

	/**
	 * @return
	 */
	public String getBazaarVoiceBaseURL() {
		return propertyReaderUtils.getBazaarVoiceBaseURL();
	}

	public void setPropertyReaderUtils(PropertyReaderUtils propertyReaderUtils) {
		this.propertyReaderUtils = propertyReaderUtils;
	}
	
}
