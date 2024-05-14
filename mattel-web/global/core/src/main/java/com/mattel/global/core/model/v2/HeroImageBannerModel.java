package com.mattel.global.core.model.v2;

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

import com.mattel.global.core.utils.GlobalUtils;

/**
 * @author CTS
 *
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeroImageBannerModel {
  private static final Logger LOGGER = LoggerFactory.getLogger(HeroImageBannerModel.class);
  private static final String IMAGE_ORIGINAL_RENDITION_PATH = "/jcr:content/renditions/original";
  @Self
  private Resource resource;

  @Inject
  private String image;

  @Inject
  private String linkUrl;

  private ResourceResolver resourceResolver;
  private String backgroundImagePath;

  /**
   * initialization
   */
  @PostConstruct
  protected void init() {
    LOGGER.info("Init -> Start");
    if (null != resource) {
      linkUrl = GlobalUtils.checkLink(linkUrl, resource);
      LOGGER.debug("Link URL : {}", linkUrl);
      resourceResolver = resource.getResourceResolver();
    }
    setBackgroundPath();
    LOGGER.info("End -> End");
  }

  /**
   * method is to provide set original background image path
   */
  private void setBackgroundPath() {
    LOGGER.info("Start -> setBackgroundPath");
    if (null != resourceResolver && StringUtils.isNotBlank(image)) {
      Resource imageResource = resourceResolver
          .resolve(image + HeroImageBannerModel.IMAGE_ORIGINAL_RENDITION_PATH);
      if (!StringUtils.isEmpty(imageResource.getPath())) {
        backgroundImagePath = imageResource.getPath();
      }
    }
    LOGGER.debug("Background Image Path : {}", backgroundImagePath);
    LOGGER.info("End -> setBackgroundPath");
  }

  public String getLinkUrl() {
    return linkUrl;
  }

  public String getBackgroundImagePath() {
    return backgroundImagePath;
  }
}
