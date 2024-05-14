package com.mattel.global.core.model.v2;

import java.util.Objects;

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

/**
 * @author CTS
 *
 */
@Model(adaptables = {
    Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ParallaxModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(ParallaxModel.class);

  @Inject
  private String title;

  @Inject
  private String description;

  @Self
  private Resource resource;

  @Inject
  private String image;

  private ResourceResolver resourceResolver;

  private String backgroundImagePath;

  @PostConstruct
  protected void init() {
    LOGGER.info("Start of init method of ParallaxModel");
    if (Objects.nonNull(resource)) {
      resourceResolver = resource.getResourceResolver();
      setBackgroundPath();
    }

    LOGGER.info("End of init method of ParallaxModel");
  }

  /**
   * method is to provide set original background image path
   */
  private void setBackgroundPath() {
    if (Objects.nonNull(resourceResolver)) {
      Resource imageResource = resourceResolver.resolve(image + "/jcr:content/renditions/original");
      if (!StringUtils.isEmpty(imageResource.getPath())) {
        backgroundImagePath = imageResource.getPath();
      }
    }
    LOGGER.debug("background Image Path :{}", backgroundImagePath);
  }

  public String getBackgroundImagePath() {
    return backgroundImagePath;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

}
