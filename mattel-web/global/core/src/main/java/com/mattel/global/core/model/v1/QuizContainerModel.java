package com.mattel.global.core.model.v1;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.core.utils.GlobalUtils;

@Model(adaptables = {
    Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class QuizContainerModel {
  private static final Logger LOGGER = LoggerFactory.getLogger(QuizContainerModel.class);

  @Inject
  private String viewAllLinkURL;

  @Self
  private Resource resource;

  private String viewAllLink;

  @PostConstruct
  protected void init() {
    LOGGER.info("QuizContainer Init -> Start");
    viewAllLink = GlobalUtils.checkLink(viewAllLinkURL, resource);
    LOGGER.info("QuizContainer Init -> End");
  }

  public String getViewAllLink() {
    return viewAllLink;
  }

}
