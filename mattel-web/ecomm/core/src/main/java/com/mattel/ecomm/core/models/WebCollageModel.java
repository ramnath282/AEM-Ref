package com.mattel.ecomm.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ecomm.core.helper.EcommHelper;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class WebCollageModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebCollageModel.class);
	@Inject
	Resource resource;
	private String productSKUId = StringUtils.EMPTY;
	private Boolean enableWebCollage;
	private String disableWebCollageProductLevel;
	private Boolean disableWCMobile;
	String siteId = StringUtils.EMPTY;
	@SlingObject
	private SlingHttpServletRequest request;

	@PostConstruct
	protected void init() {
		LOGGER.info("WebCollageModel init method  ----> Start");
		if (null != resource && !resource.getPath().contains("/conf/")) {
			PageManager pageManager = null;
			Page page = null;
			pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
			if (null != pageManager) {
				page = pageManager.getContainingPage(resource);
				productSKUId = EcommHelper.fetchProductSKUId(request);
				Page parentProductPage = page.getAbsoluteParent(5);
				if (null != parentProductPage) {
					siteId = null != parentProductPage.getProperties().get("webCollageSiteId", String.class)
							? parentProductPage.getProperties().get("webCollageSiteId", String.class)
							: "";
					disableWebCollageProductLevel =  page.getProperties().get("webCollageProductLevelConfig", String.class);
					enableWebCollage = EcommHelper.checkBooleanProperty(parentProductPage, "enableWebCollage", false);
					disableWCMobile = EcommHelper.checkBooleanProperty(parentProductPage, "disableWCMobile", false);
				}
			}
		}
		LOGGER.info("WebCollageModel init method  ----> End");
	}

	public String getProductSKUId() {
		return productSKUId;
	}

	public void setProductSKUId(String productSKUId) {
		this.productSKUId = productSKUId;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public boolean isEnableWebCollage() {
		return enableWebCollage;
	}

	public void setEnableWebCollage(boolean enableWebCollage) {
		this.enableWebCollage = enableWebCollage;
	}

	public boolean isDisableWCMobile() {
		return disableWCMobile;
	}

	public void setDisableWCMobile(boolean disableWCMobile) {
		this.disableWCMobile = disableWCMobile;
	}

	public String getDisableWebCollageProductLevel() {
		return disableWebCollageProductLevel;
	}
	
}