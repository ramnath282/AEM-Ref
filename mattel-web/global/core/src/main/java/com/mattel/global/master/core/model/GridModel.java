package com.mattel.global.master.core.model;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.core.utils.GlobalUtils;
import com.mattel.global.master.core.constants.Constants;

/**
 * @author CTS
 *
 */
@Model(adaptables = {
    Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class GridModel extends ResponsiveTabModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(GridModel.class);

  @Inject
  private String linkURL;

  @Inject
  private String linkText;

  @Inject
  private String showMoreText;

  @Inject
  private String showLessText;

  @Self
  private Resource resource;

  @Inject
  private String image;

  private String backgroundImagePath;

  @PostConstruct
  protected void init() {
    LOGGER.info("Start of init method of GridModel");
    linkURL = GlobalUtils.checkLink(linkURL, resource);
    if (null != resource) {
      ResourceResolver resourceResolver = resource.getResourceResolver();
      backgroundImagePath = GlobalUtils.setBackgroundPath(resourceResolver,image);
    }
    LOGGER.info("End of init method of GridModel");
  }

  public String getBackgroundImagePath() {
    return backgroundImagePath;
  }

  public String getLinkURL() {
    return linkURL;
  }

  public String getShowMoreText() {
    return GlobalUtils.removeTags(showMoreText, Constants.REMOVE_TAGS,
        Constants.EMPTY_ARRAY_STRING);
  }

  public String getShowLessText() {
    return GlobalUtils.removeTags(showLessText, Constants.REMOVE_TAGS,
        Constants.EMPTY_ARRAY_STRING);
  }

  public String getLinkText() {
    return GlobalUtils.removeTags(linkText, Constants.REMOVE_TAGS, Constants.EMPTY_ARRAY_STRING);
  }
}
