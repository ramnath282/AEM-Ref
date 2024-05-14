package com.mattel.global.master.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.wcm.core.components.models.Carousel;
import com.adobe.cq.wcm.core.components.models.ListItem;

@Model(adaptables = { Resource.class,
    SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CTATab {
  private static final Logger LOGGER = LoggerFactory.getLogger(CTATab.class);

  private static final String CTA = "cta_";

  @Self
  private Carousel carousel;

  List<ListItem> itemList;
  List<ListItem> ctaList;

  @PostConstruct
  protected void init() {
    LOGGER.info("CTATab : init Start");
    if (Objects.nonNull(carousel)) {
      itemList = carousel.getItems();
      ctaList = new ArrayList();
      for (ListItem listItem : itemList) {
        if (StringUtils.contains(listItem.getName(), CTA)) {
          LOGGER.debug("Item node Name :{}", listItem.getName());
          ctaList.add(listItem);
        }
      }
    }
    LOGGER.info("CTATab : init End");
  }

  public List<ListItem> getItems() {
    return ctaList;
  }
}
