package com.mattel.ecomm.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.core.utils.EcomUtil;

@Model(adaptables = {
    Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class RelatedProductModel {
  private static final Logger LOGGER = LoggerFactory.getLogger(RelatedProductModel.class);
  @Inject
  private String popularRecordsTitle;

  @Inject
  private String landingPageUrl;

  @Inject
  private String relatedArticleHeading;

  @Inject
  private String relatedProductHeading;

  @Inject
  private String articlePageUrl;

  @Self
  private Resource resource;

  @PostConstruct
  protected void init() {
    LOGGER.info("RelatedProductModel Init Method - Start");
    if (StringUtils.isNotBlank(landingPageUrl)) {
      landingPageUrl = EcomUtil.checkLink(landingPageUrl, resource);
      LOGGER.debug("RelatedProductModel landingPageUrl - {}", landingPageUrl);
    }

    if (StringUtils.isNotBlank(articlePageUrl)) {
      articlePageUrl = EcomUtil.checkLink(articlePageUrl, resource);
      LOGGER.debug("RelatedProductModel articlePageUrl - {}", articlePageUrl);
    }
    LOGGER.info("RelatedProductModel Init Method - end");
  }

  public String getPopularRecordsTitle() {
    return popularRecordsTitle;
  }

  public String getLandingPageUrl() {
    return landingPageUrl;
  }

  public String getRelatedArticleHeading() {
    return relatedArticleHeading;
  }

  public String getRelatedProductHeading() {
    return relatedProductHeading;
  }

  public String getArticlePageUrl() {
    return articlePageUrl;
  }

}
