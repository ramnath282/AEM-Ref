package com.mattel.ecomm.core.models;

import java.util.Objects;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@Model(adaptables = { Resource.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DHContainerModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(DHContainerModel.class);
	@Self
    private Resource resource;
	
	private String landingPageUrl;

	@PostConstruct
	protected void init() {
		LOGGER.info("DHTreatmentViewModel init method Start");
		landingPageUrl = readLandingPagePath();
		LOGGER.info("DHTreatmentViewModel init method end");
	}


    /**
     * The method reads the landing page path
     * 
     * @return landingPagePath
     */
    private String readLandingPagePath() {
        LOGGER.info("DHContainerModel readLandingPagePath method start");
        String landingPagePath = "";
        if (Objects.nonNull(resource)) {
            ResourceResolver resourceResolver = resource.getResourceResolver();
            PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
            if (Objects.nonNull(pageManager)) {
                Page page = pageManager.getContainingPage(resource);
                landingPagePath = readPageProperty(page);
            }
         LOGGER.info("DHContainerModel readLandingPagePath method end");
        }
        return landingPagePath;
    }
    
    /**
     * The method reads properties from page
     * 
     * @return pagePath
     */
    private String readPageProperty(Page page) {
        LOGGER.info("DHContainerModel readPageProperty Start");
        String pagePath = "";
        if (StringUtils.isNotBlank(page.getVanityUrl())) {
            pagePath = page.getVanityUrl();
        } else if (null != page.getProperties() && page.getProperties().containsKey("cq:redirectTarget")) {
            pagePath = page.getProperties().get("cq:redirectTarget", String.class);
        } else {
            Resource pageResource = page.getContentResource();
            if (Objects.nonNull(pageResource)) {
                ValueMap valueMap = pageResource.getValueMap();
                pagePath = valueMap.get("landingPagePath", String.class);
            }
        }
        LOGGER.info("DHContainerModel readPageProperty End");
        return pagePath;
    }
	
    public String getLandingPageUrl() {
        return landingPageUrl;
    }
	
}