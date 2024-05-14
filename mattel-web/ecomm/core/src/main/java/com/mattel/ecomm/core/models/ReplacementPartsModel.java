package com.mattel.ecomm.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
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
public class ReplacementPartsModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReplacementPartsModel.class);

	@Inject
	Resource resource;
	private String productSKUId = StringUtils.EMPTY;
	private Boolean enableReplacementParts = false;
	@SlingObject
	private SlingHttpServletRequest request;

	@PostConstruct
	protected void init() {
		LOGGER.info("ReplacementPartsModel init method  ----> Start");
		if (null != resource && !resource.getPath().contains("/conf/")) {
			PageManager pageManager = null;
			Page page = null;
			pageManager = request.getResourceResolver().adaptTo(PageManager.class);
			if (null != pageManager) {
				String currentPagePath = request.getPathInfo();
				int lastIndex = currentPagePath.lastIndexOf('/');
				currentPagePath = currentPagePath.substring(0, lastIndex);
				ResourceResolver currentResourceResolver = request.getResourceResolver();
				Resource currentResource = currentResourceResolver.getResource(currentPagePath);
				if (null != currentResource) {
					page = pageManager.getContainingPage(currentPagePath);
				}
				if (null != page) {
					productSKUId = EcommHelper.fetchProductSKUId(request);
					Page parentProductPage = page.getAbsoluteParent(5);
					if (null != parentProductPage) {
						enableReplacementParts = EcommHelper.checkBooleanProperty(parentProductPage,
								"enableReplacementParts", false);
					}
				}
			}
		}
		LOGGER.info("ReplacementPartsModel init method  ----> End");
	}

	public String getProductSKUId() {
		return productSKUId;
	}

	public void setProductSKUId(String productSKUId) {
		this.productSKUId = productSKUId;
	}

	public Boolean getEnableReplacementParts() {
		return enableReplacementParts;
	}

	public void setEnableReplacementParts(Boolean enableReplacementParts) {
		this.enableReplacementParts = enableReplacementParts;
	}

}
