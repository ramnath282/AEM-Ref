package com.mattel.global.master.core.model;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;

@Model(adaptables = {
    Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class AppItemModel {

  @Inject
  private String selectApp;

  @Inject
  private String relativeAppPath;
  @Inject
  private String ctaTrack;
  @Inject
  private String trackingText;
  @Inject
  private String linkOptions;
  @Inject
  private String useInterstitial;
  @Inject
  private String interstitialPath;
  @Inject
  private String fillColor;
  @Inject
  private String linkText;
  @Inject
  private String linkAltText;

  public String getSelectApp() {
    return selectApp;
  }

  public String getRelativeAppPath() {
    return relativeAppPath;
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

  public String getUseInterstitial() {
    return useInterstitial;
  }

  public String getInterstitialPath() {
    return interstitialPath;
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
