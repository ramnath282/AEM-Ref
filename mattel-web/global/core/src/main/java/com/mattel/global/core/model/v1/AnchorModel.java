package com.mattel.global.core.model.v1;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import com.mattel.global.core.utils.GlobalUtils;
import com.mattel.global.master.core.constants.Constants;

@Model(adaptables = {
    Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class AnchorModel {
  private static final String HASH = "#";
  @Inject
  @Named("linkText")
  private String anchorName;

  @Inject
  @Named("anchorID")
  private String anchorID;

  @Inject
  @Named("linkURL")
  private String anchorNavUrl;

  @Inject
  @Named("linkAltText")
  private String anchorNavAltText;

  @Inject
  @Named("linkOptions")
  private String linkOptions;

  @Inject
  @Named("trackThisCTA")
  private String trackThisCTA;

  @Inject
  @Named("trackingText")
  private String trackingText;

  @Self
  private Resource resource;

  public String getAnchorName() {
    return GlobalUtils.removeTags(anchorName, Constants.REMOVE_TAGS, Constants.EMPTY_ARRAY_STRING);
  }

  public String getAnchorID() {
    if (StringUtils.isNotBlank(anchorID) && anchorID.charAt(0) != '#') {
      anchorID = HASH + anchorID;
    }
    return anchorID;
  }

  public String getAnchorNavUrl() {

    return GlobalUtils.checkLink(anchorNavUrl, resource);
  }

  public void setAnchorNavUrl(String anchorNavUrl) {
    this.anchorNavUrl = anchorNavUrl;
  }

  public String getAnchorNavAltText() {
    return anchorNavAltText;
  }

  public String getLinkOptions() {
    return linkOptions;
  }

  public String getTrackThisCTA() {
    return trackThisCTA;
  }

  public String getTrackingText() {
    return trackingText;
  }

}
