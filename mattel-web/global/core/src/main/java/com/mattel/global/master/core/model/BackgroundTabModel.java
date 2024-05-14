package com.mattel.global.master.core.model;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {
    Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BackgroundTabModel {

  @Inject
  private String backgroundOption;

  @Inject
  private String backgroundColor;

  @Inject
  private String customMobile;

  @Inject
  private String tileImage;

  @Inject
  private String imageLarge;

  @Inject
  private String tileOption;

  @Inject
  private String imagebg;

  
  @PostConstruct
  protected void init() {
	/**
   * default Init method
   *
   */
  }

  public String getBackgroundOption() {
    return backgroundOption;
  }

  public String getBackgroundColor() {
    return backgroundColor;
  }

  public String getCustomMobile() {
    return customMobile;
  }

  public String getTileImage() {
    return tileImage;
  }

  public String getImageLarge() {
    return imageLarge;
  }

  public String getTileOption() {
    return tileOption;
  }

  public String getImagebg() {
    return imagebg;
  }

}
