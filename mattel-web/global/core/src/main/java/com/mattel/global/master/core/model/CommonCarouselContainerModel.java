package com.mattel.global.master.core.model;

import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.core.model.v1.CtaItemModel;
import com.mattel.global.core.utils.GlobalUtils;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CommonCarouselContainerModel {
  private static final Logger LOGGER = LoggerFactory.getLogger(CommonCarouselContainerModel.class);
  private static final String TRUE = "true";
  private static final String ONE = "1";

  @Inject
  @Default(values = "1")
  private String freeFormTab;

  @Inject
  @Default(values = "1")
  private String freeFormMob;

  @Inject
  @Default(values = "3")
  private String timer;

  @Inject
  @Default(values = "1")
  private String slideToShow;

  @Inject
  @Default(values = "1")
  private String slidetoscroll;

  @Inject
  private String autoPlay;

  @Inject
  private String infinte;

  @Inject
  private boolean entrCompClickable;

  private String url;

  private String carouselLinkOption;

  @Self
  private Resource resource;

  @Inject
  private String image;

  @Inject
  private String freeform;

  private ResourceResolver resourceResolver;
  private String backgroundImagePath;

  @PostConstruct
  protected void init() {
    LOGGER.info("CommonCarouselContainerModel Init Method Start");
    Resource carouselResource = GlobalUtils.getCtaURL(resource);
    if (entrCompClickable && Objects.nonNull(carouselResource)) {
      CtaItemModel ctaModel = carouselResource.adaptTo(CtaItemModel.class);
      if (Objects.nonNull(ctaModel)) {
        url = ctaModel.getUrl();
        LOGGER.debug("URL :: {} ", url);
        carouselLinkOption = ctaModel.getLinkOptions();
        LOGGER.debug("linkOption :: {} ", carouselLinkOption);
      }
    }

    if (null != resource) {
      resourceResolver = resource.getResourceResolver();
    }
    backgroundImagePath = GlobalUtils.setBackgroundPath(resourceResolver, image);
    LOGGER.info("CommonCarouselContainerModel Init Method End");
  }

  public String getBackgroundImagePath() {
    return backgroundImagePath;
  }

  public String getInfinte() {
    return infinte;
  }

  public String getTimer() {
    return timer;
  }

  public String getSlideToShow() {
    return StringUtils.contains(freeform, TRUE) ? ONE : slideToShow;
  }

  public String getSlidetoscroll() {
    return slidetoscroll;
  }

  public String getAutoPlay() {
    return autoPlay;
  }

  public boolean getEntrCompClickable() {
    return entrCompClickable;
  }

  public String getUrl() {
    return url;
  }

  public String getCarouselLinkOption() {
    return carouselLinkOption;
  }

  public String getFreeFormMob() {
    return StringUtils.contains(freeform, TRUE) ? freeFormMob : StringUtils.EMPTY;
  }

  public String getFreeFormTab() {
    return StringUtils.contains(freeform, TRUE) ? freeFormTab : StringUtils.EMPTY;
  }

}
