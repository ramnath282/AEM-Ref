package com.mattel.ag.retail.core.model;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ag.retail.core.constants.Constants;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

/**
 * @author CTS. A Model class for Store Locator Banner
 */

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class StoreLocatorBannerModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(StoreLocatorBannerModel.class);
	@Self
	Resource resource;
	private String storeLocation;

	@PostConstruct
	protected void init() {
		LOGGER.info("Init method in StoreLocatorBannerModel start");
		try {

			PageManager pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
			Page page = null;
			ValueMap valueMap = null;
			if (pageManager != null) {
				LOGGER.debug("PageManager Not Null");
				page = pageManager.getContainingPage(resource);
			}
			if (page != null) {
				LOGGER.debug("page Not Null");
				valueMap = page.getProperties();
			}
			if (valueMap != null) {
				LOGGER.debug("ValueMap is {}", valueMap);
				String template = valueMap.get("cq:template").toString();
				LOGGER.debug("template for store page template is:{} ", template);
				if (template.equalsIgnoreCase(Constants.STOREPAGETEMPLATEPATH)) {
					if (valueMap.get("storeLocation").toString() != null) {
						storeLocation = valueMap.get("storeLocation").toString();
					}
					LOGGER.debug("storeLocation for store page template is:{} ", storeLocation);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in init method{}", e.getMessage());

		}
		LOGGER.info("Init method in StoreLocatorBannerModel end");
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public String getStoreLocation() {
		return storeLocation;
	}
}
