package com.mattel.global.master.core.model;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {
    Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class AnchorCTAModel {

  @Inject
  private String anchorName;

  @Inject
  private String ctaTrack;

  @Inject
  private String trackingText;

  @Inject
  private String linkOptions;

  @Inject
  private String fillColor;

  @Inject
  private String linkText;

  @Inject
  private String linkAltText;

  public String getAnchorName() {
    return anchorName;
  }

  public String getCtaTrack() {
    return ctaTrack;
  }

  public String getTrackingText() {
    return trackingText;
  }

  public String getLinkOptions() {
    return linkOptions;
  }

  public String getFillColor() {
    return fillColor;
  }

  public String getLinkText() {
    return linkText;
  }

  public String getLinkAltText() {
    return linkAltText;
  }

}
