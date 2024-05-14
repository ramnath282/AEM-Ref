package com.mattel.ecomm.core.models;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.core.utils.EcommConfigurationUtils;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MixedMediaViewerModel {
  private static final Logger LOGGER = LoggerFactory.getLogger(MixedMediaViewerModel.class);

  private String scenesevenServerurl;
  private String scenesevenContenturl;
  private String scenesevenVideoserverurl;

  @PostConstruct
  protected void init() {
    LOGGER.info("MixedMediaViewerModel Init Start");

    scenesevenServerurl = EcommConfigurationUtils.getScenesevenServerUrl();
    scenesevenContenturl = EcommConfigurationUtils.getScenesevenContentUrl();
    scenesevenVideoserverurl = EcommConfigurationUtils.getScenesevenVideoserverUrl();

    LOGGER.info("MixedMediaViewerModel Init End");
  }

  public String getScenesevenServerurl() {
    return scenesevenServerurl;
  }

  public String getScenesevenContenturl() {
    return scenesevenContenturl;
  }

  public String getScenesevenVideoserverurl() {
    return scenesevenVideoserverurl;
  }

}
