package com.mattel.ecomm.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.mattel.ecomm.core.utils.EcomUtil;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class QuickViewModel {

  @Inject
  Resource resource;
  private String affirmInfoPagePath;

  private static final Logger LOGGER = LoggerFactory.getLogger(QuickViewModel.class);

  @PostConstruct
  protected void init() {
    LOGGER.info("QuickViewModel -> Start");

    if(resource != null) {
      //Get valuemap properties from resource
      ValueMap properties = resource.adaptTo(ValueMap.class);
      if (properties != null) {
        String affirmInfoPageProperty = properties.get("affirmLearnMoreLink", String.class);
        affirmInfoPagePath = EcomUtil.getPageLink(affirmInfoPageProperty, resource);
        LOGGER.debug("affirmInfoPagePath in QuickViewModel is {}", affirmInfoPagePath);
      }
    }
    LOGGER.info("QuickViewModel -> End");
  }

  public String getAffirmInfoPagePath() {
    return affirmInfoPagePath;
  }

}


