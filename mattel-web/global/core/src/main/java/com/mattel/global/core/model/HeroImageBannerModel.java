package com.mattel.global.core.model;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.core.utils.PathUtils;

/**
 * @author CTS. A Model class for Store Map Component.
 */

@Model(adaptables = Resource.class , defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeroImageBannerModel {
  private static final Logger LOGGER = LoggerFactory.getLogger(HeroImageBannerModel.class);
  @Inject
  private String imgUrl;
  @Inject
  private String ctaLink;

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public String getCtaLink() {
    return ctaLink;
  }

  public void setCtaLink(String ctaLink) {
    this.ctaLink = ctaLink;
  }

  public String getImgUrl() {
    return imgUrl;
  }
  
  @PostConstruct
  protected void init() {
    LOGGER.info("Hero Image Banner Model init Start");
    if(null != imgUrl && !PathUtils.isExternal(imgUrl)){
        imgUrl = imgUrl + ".html";
    }
    if(null != ctaLink && !PathUtils.isExternal(ctaLink)){
        ctaLink = ctaLink + ".html";
    }
    LOGGER.info("Hero Image Banner Model init End");
  }
}