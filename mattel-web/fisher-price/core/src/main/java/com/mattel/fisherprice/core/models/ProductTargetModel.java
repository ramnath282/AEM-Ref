package com.mattel.fisherprice.core.models;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.fisherprice.core.utils.FisherPriceConfigurationUtils;

@Model(adaptables = SlingHttpServletRequest.class)
public class ProductTargetModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductTargetModel.class);
	
	private String atPropertyTargetFP;

	@PostConstruct
	protected void init() {
		LOGGER.info("ProductTargetModel init() starts here");
		atPropertyTargetFP = FisherPriceConfigurationUtils.getAtPropertyTargetFP();
		LOGGER.info("ProductTargetModel init() ends here");
	}
	
	public String getAtPropertyTargetFP() {
			return atPropertyTargetFP;
		}
	 
}
