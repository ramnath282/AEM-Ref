package com.mattel.global.core.model;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL) public class VideoModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(VideoModel.class);

  @Inject
  private String embedurl;

  private String videoUrl = StringUtils.EMPTY;

  @PostConstruct protected void init() {
    LOGGER.info("VideoModel init method  :: Start");
    if (StringUtils.isNotBlank(embedurl)) {
      if (StringUtils.contains(embedurl, "?")) {
        videoUrl = embedurl.concat("&enablejsapi=1");
      } else {
        videoUrl = embedurl.concat("?enablejsapi=1");
      }
    }
    LOGGER.debug("Final embed url is {}", videoUrl);
    LOGGER.info("VideoModel init method  :: End");
  }

  public String getVideoUrl() {
    return videoUrl;
  }
}
