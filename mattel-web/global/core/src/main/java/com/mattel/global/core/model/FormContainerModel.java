package com.mattel.global.core.model;

import com.mattel.global.core.utils.GlobalUtils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.commons.lang.StringUtils;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FormContainerModel {
  private static final Logger LOGGER = LoggerFactory.getLogger(FormContainerModel.class);
	@Inject
	private String thankYouPageURL;
	@Self
  private Resource resource;
	private String backgroundImagePath;
	private ResourceResolver resourceResolver;
	@Inject
	private String image;

	@PostConstruct
	protected void init() {
		LOGGER.info("FormContainerModel init Start");
		if (null != resource) {
			resourceResolver = resource.getResourceResolver();
			thankYouPageURL = GlobalUtils.checkLink(thankYouPageURL, resource);
			LOGGER.debug("image path set is {}", image);
			setBackgroundPath(resourceResolver);
		}
		LOGGER.info("FormContainerModel init End");
	}

	/**
	 * method is to provide set original background image path
	 * @param resolver
	 */
	private void setBackgroundPath(ResourceResolver resolver) {
		LOGGER.info("FormContainerModel setBackgroundPath Start");
		if (null != resolver) {
			Resource imageResource = resolver.resolve(image + "/jcr:content/renditions/original");
			if(!StringUtils.isEmpty(imageResource.getPath())) {
				backgroundImagePath = imageResource.getPath();
				LOGGER.debug("backgroundImagePath path set is {}", backgroundImagePath);
			}
		}
		LOGGER.info("FormContainerModel setBackgroundPath End");
	}

	public String getBackgroundImagePath() {
		return backgroundImagePath;
	}

	public String getThankYouPageURL() {
    return thankYouPageURL;
  }

  public void setThankYouPageURL(String thankYouPageURL) {
    this.thankYouPageURL = thankYouPageURL;
  }

}
