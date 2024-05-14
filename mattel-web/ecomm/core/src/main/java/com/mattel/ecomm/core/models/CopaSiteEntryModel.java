package com.mattel.ecomm.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.core.services.MultifieldReader;
import com.mattel.ecomm.core.utils.EcomUtil;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

@Model(adaptables = {
    Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CopaSiteEntryModel {
  private static final Logger LOGGER = LoggerFactory.getLogger(CopaSiteEntryModel.class);

  @Inject
  private String shopCtaLink;

  @Inject
  private String playCtaLink;

  @Self
  private Resource resource;
  
  @Inject
  private Node excludedPages;

  @Inject
  private Node excludedPageKeywords;
  
  @Inject
  private MultifieldReader multifieldReader;
  
  private String excudeKeywords;
  private String excudePages;

  /**
   * init method.
   */
  @PostConstruct
  protected void init() {
    LOGGER.info("CopaSiteEntryModel Init Method - Start");
    if (StringUtils.isNotBlank(shopCtaLink)) {
      shopCtaLink = EcomUtil.checkLink(shopCtaLink, resource);
      LOGGER.debug("CopaSiteEntryModel  getShopCtaLink URL{}", shopCtaLink);
    }
    if (StringUtils.isNotBlank(playCtaLink)) {
      playCtaLink = EcomUtil.checkLink(playCtaLink, resource);
      LOGGER.debug("CopaSiteEntryModel  getPlayCtaLink URL{}", playCtaLink);
    }
    excudePages = getPropertyValues(excludedPages, "pagePath",resource);
    excudeKeywords = getPropertyValues(excludedPageKeywords, "keywords",resource);
    LOGGER.info("CopaSiteEntryModel Init Method - End");
  }
  
  /**
   * @param node
   * @param propertyName
   * @return
   * 
   *    Common method to get exclude keywords / exclude pages / secondary subscription Id's
   */
  private String getPropertyValues(Node node, String propertyName, Resource resource){
    LOGGER.info("getExcludeData -> START");
    String excludeString = "";
    if(Objects.nonNull(node)){
      Map<String, ValueMap> excludeMap = multifieldReader.propertyReader(node);
      ArrayList<String> excludeList = new ArrayList<>();
      excludeList = EcomUtil.setExcludeList(excludeMap, propertyName, excludeList, resource);
      excludeString = String.join(",", excludeList);
    }
    LOGGER.debug("Exclude {} : {}", propertyName, excludeString);
    LOGGER.info("getExcludeData -> END");
    return excludeString;
  }
  
  /**
   * @return the multifieldReader
   */
  public MultifieldReader getMultifieldReader() {
    return multifieldReader;
  }

  /**
   * @param multifieldReader
   *          the multifieldReader to set
   */
  public void setMultifieldReader(MultifieldReader multifieldReader) {
    this.multifieldReader = multifieldReader;
  }
  
  public String getShopCtaLink() {
    return shopCtaLink;
  }

  public String getPlayCtaLink() {
    return playCtaLink;
  }
  
  public String getExcudeKeywords() {
    return excudeKeywords;
  }

  public String getExcudePages() {
    return excudePages;
  }

}
