package com.mattel.global.core.model;

import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public interface SocialModel {

  @Inject
  List<CategoryConfiguration> getCategoryConfiguration();

  /**
   * CategoryConfiguration- To map all multifield categories authored via
   * dialog.
   * 
   */
  @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
  interface CategoryConfiguration {
    @Inject
    String getCategoryType();

    @Inject
    List<SocialConfiguration> getSocialConfiguration();
  }

  /**
   * SocialConfiguration- To map all multifield social link details authored via
   * dialog.
   * 
   */
  @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
  interface SocialConfiguration {
    @Inject
    String getLinkLabel();

    @Inject
    String getLinkName();

    @Inject
    String getLinkUrl();
  }

}
