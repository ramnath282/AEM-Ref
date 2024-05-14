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
import com.mattel.ecomm.core.interfaces.ProductService;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class RelatedSearches {
	private static final Logger LOGGER = LoggerFactory.getLogger(RelatedSearches.class);
	@SlingObject
	private SlingHttpServletRequest request;

	@Inject
	Resource resource;

	@Inject
	private ProductService productService;
	private Boolean defaultValue = false;
	private Boolean enableRelatedSearches = defaultValue;
	private String relatedSearchTitle = StringUtils.EMPTY;

	/**
	 * The init method to fetch the product information from Commerce
	 */
	@PostConstruct
	protected void init() {
		LOGGER.info("PDPModel init method  ----> Start");
		if (null != resource && !resource.getPath().contains("/conf/")) {
			PageManager pageManager = null;
			Page page = null;
			Page parentProductPage = null;
			pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
			if (null != pageManager) {
				page = productService.getCurrentPagePath(pageManager, resource, request);
				if (null != page) {
					parentProductPage = page.getAbsoluteParent(5);
					if (null != parentProductPage)
						relatedSearchTitle = EcommHelper.productPageProperties(parentProductPage, "relSearchTitle");
					enableRelatedSearches = EcommHelper.checkBooleanProperty(parentProductPage, "releatedSearches",
							defaultValue);
				}

			}
		}
	}

	public Boolean getEnableRelatedSearches() {
		return enableRelatedSearches;
	}

	public String getRelatedSearchTitle() {
		return relatedSearchTitle;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

}
