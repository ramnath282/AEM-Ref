package com.mattel.ecomm.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.core.services.ConsumerLoyaltyRewardsConfig;

/**
 * @author CTS
 *
 */
@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class LoyaltyRewardsDrawerModel extends BasePageModel {

	@Self
	@Via("resource")
	private Resource resource;

	@SlingObject
	SlingHttpServletRequest slinghttprequest;

	@Inject
	private ConsumerLoyaltyRewardsConfig consumerLoyaltyRewardsConfig;

	private String apiFailureCallCount;
	private String localStorageExpiry;

	private static final Logger LOGGER = LoggerFactory.getLogger(LoyaltyRewardsDrawerModel.class);

	@PostConstruct
	protected void init() {
		LoyaltyRewardsDrawerModel.LOGGER.info("LoyalityRewardsDrawerModel init method - Start");
		LoyaltyRewardsDrawerModel.LOGGER.debug("LoyalityRewardsDrawerModel resource is {}", resource);
		apiFailureCallCount = consumerLoyaltyRewardsConfig.getApiFailureCallCount();
		localStorageExpiry = consumerLoyaltyRewardsConfig.getLocalStorageExpiry();
		LoyaltyRewardsDrawerModel.LOGGER.debug("apiFailureCallCount is : {} localStorageExpiry time is : {}",
				apiFailureCallCount, localStorageExpiry);
		LoyaltyRewardsDrawerModel.LOGGER.info("LoyalityRewardsDrawerModel init method - End");
	}

	/**
	 * @return the consumerLoyaltyConfig
	 */
	public ConsumerLoyaltyRewardsConfig getConsumerLoyaltyRewardsConfig() {
		return consumerLoyaltyRewardsConfig;
	}

	public String getApiFailureCallCount() {
		return apiFailureCallCount;
	}

	public String getLocalStorageExpiry() {
		return localStorageExpiry;
	}

	@Override
	public boolean isDisableClientLibs() {
		return checkClientLibsSelector(slinghttprequest);
	}

}
