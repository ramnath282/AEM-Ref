package com.mattel.ecomm.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SocialMediaModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(SocialMediaModel.class);

	@Inject
	Resource resource;
	private String addthisInlineId = StringUtils.EMPTY;
	private String addThisPubId = StringUtils.EMPTY;
	

	@PostConstruct
	protected void init() {
		LOGGER.info("SocialMediaModel init method  ----> Start");
		if (null != resource) {
			PageManager pageManager = null;
			Page page = null;
			pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
			if (null != pageManager) {
				page = pageManager.getContainingPage(resource);
				Page parentProductPage = page.getAbsoluteParent(5);
				if (null != parentProductPage) {
					addThisPubId = null != parentProductPage.getProperties().get("addThisPubId", String.class)
							? parentProductPage.getProperties().get("addThisPubId", String.class)
							: "";
					addthisInlineId = null != parentProductPage.getProperties().get("addthisInlineId", String.class)
							? parentProductPage.getProperties().get("addthisInlineId", String.class)
							: "";
				}
			}
		}
		LOGGER.info("SocialMediaModel init method  ----> End");
	}

	public String getAddthisInlineId() {
		return addthisInlineId;
	}

	public void setAddthisInlineId(String addthisInlineId) {
		this.addthisInlineId = addthisInlineId;
	}

	public String getAddThisPubId() {
		return addThisPubId;
	}

	public void setAddThisPubId(String addThisPubId) {
		this.addThisPubId = addThisPubId;
	}

	
}
