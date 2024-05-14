package com.mattel.ecomm.core.services;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * @author CTS. Service for configuration Consumer Loyalty API.
 */
@Component(service = ConsumerLoyaltyRewardsConfig.class, immediate = true)
@Designate(ocd = ConsumerLoyaltyRewardsConfig.Config.class)
public class ConsumerLoyaltyRewardsConfig {

  private String apiFailureCallCount;
  private String localStorageExpiry;

  @Activate
  public void activate(final Config config) {
    buildConfigs(config);
  }

  private void buildConfigs(Config config) {
	apiFailureCallCount = config.apiFailureCallCount();
    localStorageExpiry = config.localStorageExpiry();
  }

  @ObjectClassDefinition(name = "Consumer Loyalty And Drawer Configurations")
  public @interface Config {
    @AttributeDefinition(name = "API Failure Call Count", description = "Please enter no of times api calls should be made when first API call fails")
    String apiFailureCallCount();

    @AttributeDefinition(name = "Local Storage Expiry (In Minutes) for Loyalty and Rewards Data", description = "Please enter Local Storage Expiry for Loyalty and Rewards Data")
    String localStorageExpiry();

  }

  public String getApiFailureCallCount() {
    return apiFailureCallCount;
  }

  public String getLocalStorageExpiry() {
	return localStorageExpiry;
  }
  
}
