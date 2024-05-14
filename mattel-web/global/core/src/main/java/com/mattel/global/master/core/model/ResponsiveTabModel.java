package com.mattel.global.master.core.model;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {
    Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ResponsiveTabModel {

  @Inject
  private String xlColumns;
  @Inject
  private String largeColumns;
  @Inject
  private String mediumColumns;
  @Inject
  private String smallColumns;
  @Inject
  private String xsColumns;
  @Inject
  private String showMoreFeature;
  @Inject
  private String showMoreRepeat;

  @Inject
  private String ctaRepeat;
  @Inject
  private String scrollMobile;
  @Inject
  private String xlInitial;
  @Inject
  private String largeInitial;
  @Inject
  private String mediumInitial;
  @Inject
  private String smallInitial;
  @Inject
  private String xsInitial;

  @Inject
  private String xlShowMore;
  @Inject
  private String largeShowMore;
  @Inject
  private String mediumShowMore;
  @Inject
  private String smallShowMore;
  @Inject
  private String xsShowMore;

  public String getXlColumns() {
    return xlColumns;
  }

  public String getLargeColumns() {
    return largeColumns;
  }

  public String getMediumColumns() {
    return mediumColumns;
  }

  public String getSmallColumns() {
    return smallColumns;
  }

  public String getXsColumns() {
    return xsColumns;
  }

  public String getShowMoreFeature() {
    return showMoreFeature;
  }

  public String getShowMoreRepeat() {
    return showMoreRepeat;
  }

  public String getCtaRepeat() {
    return ctaRepeat;
  }

  public String getScrollMobile() {
    return scrollMobile;
  }

  public String getXlInitial() {
    return xlInitial;
  }

  public String getLargeInitial() {
    return largeInitial;
  }

  public String getMediumInitial() {
    return mediumInitial;
  }

  public String getSmallInitial() {
    return smallInitial;
  }

  public String getXsInitial() {
    return xsInitial;
  }

  public String getXlShowMore() {
    return xlShowMore;
  }

  public String getLargeShowMore() {
    return largeShowMore;
  }

  public String getMediumShowMore() {
    return mediumShowMore;
  }

  public String getSmallShowMore() {
    return smallShowMore;
  }

  public String getXsShowMore() {
    return xsShowMore;
  }

}
