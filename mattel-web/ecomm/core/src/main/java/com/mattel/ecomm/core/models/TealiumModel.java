package com.mattel.ecomm.core.models;

import com.mattel.ecomm.core.utils.EcommConfigurationUtils;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = { Resource.class,
    SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TealiumModel {
  private String tealiumUrl;
  
  @PostConstruct
  protected void init() {
    tealiumUrl = EcommConfigurationUtils.getTealiumUrl();
  }

  public String getTealiumUrl() {
    return tealiumUrl;
  }

  public void setTealiumUrl(String tealiumUrl) {
    this.tealiumUrl = tealiumUrl;
  }
}
