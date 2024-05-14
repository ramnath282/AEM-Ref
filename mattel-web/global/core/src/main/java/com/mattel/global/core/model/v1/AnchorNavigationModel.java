package com.mattel.global.core.model.v1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.core.utils.GlobalUtils;

@Model(adaptables = {
    Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class AnchorNavigationModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(AnchorNavigationModel.class);

  @Inject
  private String logoAlt;

  @Inject
  private String logoNavUrl;

  @Inject
  private String navigationLogo;

  private List<AnchorModel> anchorModelList;

  @Self
  private Resource resource;

  @Inject
  private String bgType;

  @Inject
  private String backgroundImage;

  @Inject
  private String backgroundColor;
  
  @Inject
  private String mobileSubNavMenuText;

  /**
   * The init method to process anchor navigation details
   */

  @PostConstruct
  protected void init() {
    LOGGER.info("AnchorNavigationModel init method  ----> Start");

    if (Objects.nonNull(resource)) {
      LOGGER.debug("Resource::{}", resource);
      populateAnchorNavigationDetail();
    }

    LOGGER.info("AnchorNavigationModel init method  ----> End");
  }

  private void populateAnchorNavigationDetail() {
    anchorModelList = new ArrayList<AnchorModel>();
    Iterator<Resource> resourceList = resource.listChildren();

    while (resourceList.hasNext()) {
      Resource contentResource = resourceList.next();
      LOGGER.debug("Resource type::{}", contentResource.getResourceType());
      if (StringUtils.containsIgnoreCase(contentResource.getResourceType(), "/ctaItem")) {
        AnchorModel anchorModel = contentResource.adaptTo(AnchorModel.class);
        if (Objects.nonNull(anchorModel)) {
          LOGGER.debug("Anchor ID::{}", anchorModel.getAnchorID());
          LOGGER.debug("Anchor Name::{}", anchorModel.getAnchorName());
          LOGGER.debug("Anchor Navigation Ur::{}", anchorModel.getAnchorNavUrl());

          anchorModelList.add(anchorModel);
        }
      }

    }
  }

  public List<AnchorModel> getAnchorModelList() {
    return anchorModelList;
  }

  public String getLogoAlt() {
    return logoAlt;
  }

  public String getLogoNavUrl() {
    return GlobalUtils.checkLink(logoNavUrl, resource);
  }

  public String getNavigationLogo() {
    return navigationLogo;
  }

  public String getBgType() {
    return bgType;
  }

  public String getBackgroundImage() {
    return backgroundImage;
  }

  public String getBackgroundColor() {
    return backgroundColor;
  }

  public void setResource(Resource resource) {
    this.resource = resource;
  }
  
  public String getMobileSubNavMenuText() {
      return mobileSubNavMenuText;
  }
}
