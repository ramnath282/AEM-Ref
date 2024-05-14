package com.mattel.global.core.model;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.mattel.global.core.utils.GlobalUtils;
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
public class ResponsiveGridModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(ResponsiveGridModel.class);

  @Self
  private Resource resource;

  @Inject
  private String image;


  @Inject
  private String navigationLink;

  @Inject
  private String openNavLinkIn;


  private String backgroundImagePath;

  @PostConstruct
  protected void init() {
    LOGGER.info("Start of init method of ResponsiveGridModel");
    if (null != resource) {
      navigationLink = GlobalUtils.checkLink(navigationLink, resource);
      ResourceResolver resourceResolver = resource.getResourceResolver();
      backgroundImagePath = GlobalUtils.setBackgroundPath(resourceResolver,image);
    }
    LOGGER.info("End of init method of ResponsiveGridModel");
  }

  public String getBackgroundImagePath() {
    return backgroundImagePath;
  }

  public String getNavigationLink() { return navigationLink; }

  public String getOpenNavLinkIn() { return openNavLinkIn; }

}
