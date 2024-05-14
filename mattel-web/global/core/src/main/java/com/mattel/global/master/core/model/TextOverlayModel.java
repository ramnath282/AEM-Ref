package com.mattel.global.master.core.model;

import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.core.model.v1.CtaItemModel;
import com.mattel.global.core.utils.GlobalUtils;


@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TextOverlayModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(TextOverlayModel.class);

  @Inject
  private String backgroundColor;

  @Inject
  private String backgroundColorMob;

  @Inject
  private String title;

  @Inject
  private String description;

  @Inject
  private String subTitle;

  @Inject
  private boolean entrCompClickable;

  private String ctaurl;

  private String linkOption;

  @Self
  private Resource resource;

  @PostConstruct
  protected void init() {
    LOGGER.info("Text Overlay Init Method Start");
    Resource ctaResource = GlobalUtils.getCtaURL(resource);
    if (entrCompClickable && Objects.nonNull(ctaResource)) {
      CtaItemModel model = ctaResource.adaptTo(CtaItemModel.class);
      if (Objects.nonNull(model)) {
        ctaurl = model.getUrl();
        linkOption=model.getLinkOptions();
        LOGGER.debug("URL :: {} ", ctaurl);
        LOGGER.debug("linkOption :: {} ", linkOption);
      }
    }
    LOGGER.info("Text Overlay Init Method End");
  }

  public String getTitle() { return title; }

  public String getDescription() { return description; }

  public String getSubTitle() { return subTitle; }

  public String getBackgroundColor() {
    return backgroundColor;
  }

  public String getBackgroundColorMob() {
    return backgroundColorMob;
  }

  public boolean getEntrCompClickable() {
    return entrCompClickable;
  }

  public String getCtaurl() {
    return ctaurl;
  }
  
  public String getLinkOption() {
    return linkOption;
  }
}
