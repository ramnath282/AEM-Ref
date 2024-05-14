package com.mattel.ag.retail.core.model;

import com.mattel.ag.retail.core.pojos.SiteNavigationPojo;
import com.mattel.ag.retail.core.services.MultifieldReader;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author CTS SiteNavigation Model to Navigation.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SiteNavigationModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(SiteNavigationModel.class);
  @Inject
  private Node navigationDetail;
  @Inject
  private MultifieldReader multifieldReader;
  private List<SiteNavigationPojo> navItemsList;

  /**
   * The init method.
   */
  @PostConstruct
  protected void init() {
    LOGGER.info("SiteNavigationModel init start");
    if (navigationDetail != null) {
      LOGGER.debug("Navigation Detail is not null");
      Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(navigationDetail);
      navItemsList = new ArrayList<>();
      for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
        SiteNavigationPojo navigationAgItem = new SiteNavigationPojo();
        navigationAgItem.setLabel(entry.getValue().get("label", String.class));
        navigationAgItem.setUrl(entry.getValue().get("url", String.class));
        navigationAgItem.setAlt(entry.getValue().get("alt", String.class));
        navigationAgItem.setTargetUrl(entry.getValue().get("targetUrl", String.class));
        LOGGER.debug("Site Navigation Pojo {}", navigationAgItem);
        navItemsList.add(navigationAgItem);
        LOGGER.debug("Nav item List is {}", navItemsList);
      }

    }
    LOGGER.info("SiteNavigationModel init end");

  }

  /**
   * @return This method return list of SiteNavigationBean
   */
  public List<SiteNavigationPojo> getNavItemsList() {
    return navItemsList;
  }

  public void setNavItemsList(List<SiteNavigationPojo> navItemsList) {
    this.navItemsList = navItemsList;
  }

  public void setMultifieldReader(MultifieldReader multifieldReader) {
    this.multifieldReader = multifieldReader;
  }

  public void setNavigationDetail(Node navigationDetail) {
    this.navigationDetail = navigationDetail;
  }
}

