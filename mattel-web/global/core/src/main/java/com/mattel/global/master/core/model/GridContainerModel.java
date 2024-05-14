package com.mattel.global.master.core.model;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.via.ResourceSuperType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.wcm.core.components.models.Carousel;
import com.adobe.cq.wcm.core.components.models.ListItem;
import com.mattel.global.core.utils.GlobalUtils;

@Model(adaptables = { Resource.class,
    SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class GridContainerModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(GridContainerModel.class);

  @Self
  @Via(type = ResourceSuperType.class)
  private Carousel carousel;

  @Inject
  @Via("resource")
  private String dataXsSlider;

  @Inject
  @Via("resource")
  private String link;

  @Self
  @Via("resource")
  private Resource resource;

  private String ctaLink;

  @PostConstruct
  protected void init() {
    LOGGER.debug("Cards container Init Method - Start");
    if (StringUtils.isNotBlank(link)) {
      ctaLink = GlobalUtils.checkLink(link, resource);
    }
  }

  public String getCtaLink() {
    return ctaLink;
  }

  public String getDataXsSlider() {
    return dataXsSlider;
  }

  public List<ListItem> getItems() {
    return carousel.getItems();
  }
}
