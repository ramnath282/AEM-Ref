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
public class ContentTab {
  private static final Logger LOGGER = LoggerFactory.getLogger(ContentTab.class);

  private static final String CONTENT = "content_";

  @Self
  private Carousel carousel;

  List<ListItem> itemList;
  List<ListItem> contentList;

  @PostConstruct
  protected void init() {
    LOGGER.info("ContentTab : init Start");
    if (Objects.nonNull(carousel)) {
      itemList = carousel.getItems();
      contentList = new ArrayList();
      for (ListItem listItem : itemList) {
        if (StringUtils.contains(listItem.getName(), CONTENT)) {
          LOGGER.debug("Item node Name :{}", listItem.getName());
          contentList.add(listItem);
        }
      }
    }
    LOGGER.info("ContentTab : init End");
  }

  public List<ListItem> getItems() {
    return contentList;
  }
}
