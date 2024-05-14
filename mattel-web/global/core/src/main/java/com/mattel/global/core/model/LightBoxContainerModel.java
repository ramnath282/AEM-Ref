package com.mattel.global.core.model;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class LightBoxContainerModel {
  private static final Logger LOGGER = LoggerFactory.getLogger(LightBoxContainerModel.class);

	@Self
    private Resource resource;
	private String backgroundImagePath;
	@Inject
	private String image;
	@Inject
    private String enableCookies;
	@Inject
    private String cookieName;
	@Inject
    private String cookieExpiry;

	@PostConstruct
	protected void init() {
		LOGGER.info("FormContainerModel init Start");
		if (null != resource) {
		    final ResourceResolver resourceResolver = resource.getResourceResolver();
			LOGGER.debug("image path set is {}", image);
			setBackgroundPath(resourceResolver);
			LOGGER.debug("enableCookies value is {}", enableCookies);
			LOGGER.debug("cookieName is: {} and Cookie Expiry is: {}", cookieName,cookieExpiry);
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

    public String getEnableCookies() {
        return enableCookies;
    }

    public void setEnableCookies(String enableCookies) {
        this.enableCookies = enableCookies;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public String getCookieExpiry() {
        return cookieExpiry;
    }

    public void setCookieExpiry(String cookieExpiry) {
        this.cookieExpiry = cookieExpiry;
    }
	
}
