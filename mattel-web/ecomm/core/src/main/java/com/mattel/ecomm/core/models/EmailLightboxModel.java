package com.mattel.ecomm.core.models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.core.services.MultifieldReader;
import com.mattel.ecomm.core.utils.EcomUtil;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class EmailLightboxModel extends BasePageModel{

  @Self
  private Resource resource;

  private Map<String, String> genderOptionsList;
  private Map<String, String> relationOptionsList;
  private String cmEndpoint;
  private String cmKey;
  private String secondarySubscriptionIds;

  @Inject
  private Node excludedPages;

  @Inject
  private Node excludedPageKeywords;

  @Inject
  private Node genderOptions;

  @Inject
  private Node relationOptions;

  @Inject
  private Node secondarySubscription;
  
  @Inject
  String privacyLinkUrl;

  @Inject
  private MultifieldReader multifieldReader;

  @Inject
  private PropertyReaderService propertyReaderService;

 
  private String excudeKeywords;
  private String excudePages;

  private static final Logger LOGGER = LoggerFactory.getLogger(EmailLightboxModel.class);
  private static final String EXCLUDE_KEYWORD_PROP = "keywords";
  private static final String EXCLUDE_PAGE_PROP = "pagePath";
  private static final String SECONDARY_SUBSCRIPTION_ID = "id";
  
  private static final String GENDER_KEY = "selectedGender";
  private static final String GENDER_LABEL = "genderLabel";
  private static final String RELATION_KEY = "selectedRelation";
  private static final String RELATION_LABEL = "relationLabel";
  
  @PostConstruct
  protected void init(){
    LOGGER.info("EmailLightBox init method -> START {}", resource);
    
    excudePages = getPropertyValues(excludedPages, EmailLightboxModel.EXCLUDE_PAGE_PROP,resource);
    excudeKeywords = getPropertyValues(excludedPageKeywords, EmailLightboxModel.EXCLUDE_KEYWORD_PROP,resource);
    secondarySubscriptionIds = getPropertyValues(secondarySubscription, EmailLightboxModel.SECONDARY_SUBSCRIPTION_ID,resource);
    genderOptionsList = getGenderRelationData(genderOptions, EmailLightboxModel.GENDER_KEY, EmailLightboxModel.GENDER_LABEL);
    relationOptionsList = getGenderRelationData(relationOptions, EmailLightboxModel.RELATION_KEY, EmailLightboxModel.RELATION_LABEL);    
   
    cmEndpoint = propertyReaderService.getConsumerInfoAPIEndpoint();
    cmKey = propertyReaderService.getConsumerInfoAPIKey();
    
    LOGGER.info("EmailLightBox init method -> END");
  }
  
  /**
   * @param node
   * @param propertyName1
   * @param propertyName2
   * @return
   * 
   *    Common method to get gender data / relation data
   */
  private Map<String, String> getGenderRelationData(Node node, String propertyName1, String propertyName2){
    LOGGER.info("getGenderRelationData -> START");
    Map<String, String> dataMap = new LinkedHashMap<>();
    if(Objects.nonNull(node)){
      Map<String, ValueMap> propertyMap = multifieldReader.propertyReader(node);
      for (Map.Entry<String, ValueMap> entry : propertyMap.entrySet()) {
        String key = entry.getValue().get(propertyName1, String.class);
        String label = entry.getValue().get(propertyName2, String.class);
        dataMap.put(key, label);
        LOGGER.debug("propertyName2 : {}", label);
      }
    }
    LOGGER.info("getGenderRelationData -> END");
    return dataMap;
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
      excludeList = EcomUtil.setExcludeList(excludeMap,propertyName,excludeList,resource);
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

  public Map<String, String> getGenderOptionsList() {
    return genderOptionsList;
  }

  public void setGenderOptionsList(Map<String, String> genderOptionsList) {
    this.genderOptionsList = genderOptionsList;
  }

  public Map<String, String> getRelationOptionsList() {
    return relationOptionsList;
  }

  public void setRelationOptionsList(Map<String, String> relationOptionsList) {
    this.relationOptionsList = relationOptionsList;
  }

  public String getCmEndpoint() {
    return cmEndpoint;
  }

  public void setCmEndpoint(String cmEndpoint) {
    this.cmEndpoint = cmEndpoint;
  }

  public String getCmKey() {
    return cmKey;
  }

  public void setCmKey(String cmKey) {
    this.cmKey = cmKey;
  }

  public String getSecondarySubscriptionIds() {
    return secondarySubscriptionIds;
  }

  @Override
  public boolean isDisableClientLibs() {
      return checkClientLibsSelector(request);
    }
   public String getPrivacyLinkUrl() {
    if(!StringUtils.isEmpty(privacyLinkUrl)){
        return EcomUtil.checkLink(privacyLinkUrl, resource);
    }
    return "#";
  }

  public String getExcudeKeywords() {
    return excudeKeywords;
  }

  public String getExcudePages() {
    return excudePages;
  }

}
