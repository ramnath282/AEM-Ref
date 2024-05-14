package com.mattel.informational.core.models;

import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.informational.core.pojos.ItemListPojo;
import com.mattel.informational.core.services.NewsDetailMetadataService;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class NewsDetailPageModel extends InformationalPageModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(NewsDetailPageModel.class);

    @SlingObject
    SlingHttpServletRequest request;
   
    @Inject
    private Resource resource;
    
    @Inject
    private NewsDetailMetadataService newsDetailMetadataService;

    private String description;
    
    @PostConstruct
    @Override
    protected void init() {
    	super.init();
        LOGGER.info("Start of NewsDetailPageModel page Model init");
        if (Objects.nonNull(resource) && !resource.getPath().contains("/conf/") && Objects.nonNull(request)) {
        	String[] selectors = request.getRequestPathInfo().getSelectors();
            LOGGER.debug("Selectors :: {}", selectors);
        	if (Objects.nonNull(newsDetailMetadataService) && ArrayUtils.isNotEmpty(selectors)) {
        		ItemListPojo itemListPojo = newsDetailMetadataService.getFragmentDetails(selectors, "corp");
        		if(Objects.nonNull(itemListPojo)){
        			if(StringUtils.isNotBlank(itemListPojo.getTitle())){
        				title = itemListPojo.getTitle()+" | "+ getHomePageTitle(resource, true);
        			} else {
        				title = getHomePageTitle(resource, true);
        			}
        			description = itemListPojo.getDescription();
        	        seoImage = itemListPojo.getImage();
        			LOGGER.debug("ItemListPojo :: {}", itemListPojo);
        		}
        	}
        }
        LOGGER.info("End of NewsDetailPageModel page Model init");
    }

    @Override
    public void setResource(Resource resource) {
        this.resource = resource;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
