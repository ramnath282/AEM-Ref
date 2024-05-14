package com.mattel.ag.retail.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ag.retail.core.pojos.FooterLinks;
import com.mattel.ag.retail.core.services.MultifieldReader;

/**
 * @author CTS. A Model class for Footer Links
 */

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class FooterLinksModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(FooterLinksModel.class);
  List<FooterLinks> footerLinksList;
  @Inject
  private Node footerLinks;
  @Inject
  private MultifieldReader multifieldReader;

  @PostConstruct
  protected void init() {

    LOGGER.info("Footer Links Model Start");
    if (footerLinks != null) {
      LOGGER.debug("calling multifield service");
      footerLinksList = new ArrayList<>();
      Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(footerLinks);
      for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
        FooterLinks footerLinksPojo = new FooterLinks();
        LOGGER.debug("Link texts{}", entry.getValue().get("linkText", String.class));
        footerLinksPojo.setLinkText(entry.getValue().get("linkText", String.class));
        LOGGER.debug("Footerlinks{} ", footerLinks);
        footerLinksList.add(footerLinksPojo);
      }
    }

    LOGGER.info("Init method in FooterLinkModel end");
  }

  /**
   * @return This method return list of FooterLinksPojo
   */
  public List<FooterLinks> getFooterLinksList() {
    return footerLinksList;
  }

  public void setMultifieldReader(MultifieldReader multifieldReader) {
    this.multifieldReader = multifieldReader;
  }

  public void setFooterLinks(Node footerLinks) {
    this.footerLinks = footerLinks;
  }

  public void setFooterLinksList(List<FooterLinks> footerLinksList) {
    this.footerLinksList = footerLinksList;
  }
}
