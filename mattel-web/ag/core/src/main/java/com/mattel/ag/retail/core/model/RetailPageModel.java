package com.mattel.ag.retail.core.model;

import com.mattel.ag.retail.core.utils.PropertyReaderUtils;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class RetailPageModel {

  @Inject
  private PropertyReaderUtils propertyReaderUtils;

  private String tealiumUrl;
  private static final Logger LOGGER = LoggerFactory.getLogger(RetailPageModel.class);

  @PostConstruct
  protected void init(){
    LOGGER.info("Start of Retail Page Model");
    tealiumUrl = propertyReaderUtils.getTealiumUrl();
    LOGGER.info("End of Retail Page Model");

  }

  public String getTealiumUrl() {
    return tealiumUrl;
  }
}
