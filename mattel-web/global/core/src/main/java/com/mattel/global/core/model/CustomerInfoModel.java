package com.mattel.global.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.core.services.MultifieldReader;
import com.mattel.global.core.utils.PropertyReaderUtils;

/**
 * @author CTS
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CustomerInfoModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerInfoModel.class);
  @Inject
  PropertyReaderUtils formConfigurations;
  @Inject
  private Node secondarySubscription;
  
  private String secondarySubscriptionId;
  @Inject
  private MultifieldReader multifieldReader;
 
  
  
  public void setFormConfigurations(PropertyReaderUtils formConfigurations) {
	this.formConfigurations = formConfigurations;
}

@PostConstruct
  protected void init() {
    LOGGER.info("Init method of CustomerInfoModel start");
    
    
    if (secondarySubscription != null) {
      LOGGER.debug("SecondarySubscriptionId node is not null");
      Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(secondarySubscription);
      List secondarySubscriptionIdList = new ArrayList<String>();
      for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
        secondarySubscriptionIdList.add(entry.getValue().get("id", String.class));
       
      }
      LOGGER.debug("secondarySubscriptionIdList size is {}",secondarySubscriptionIdList.size());
      secondarySubscriptionId=StringUtils.join(secondarySubscriptionIdList, ',');
      
      LOGGER.debug("secondarySubscriptionIdStr is {}",secondarySubscriptionId);
    }
    
    LOGGER.info("Init method of CustomerInfoModel end");

  }

  /**
   * @return APIBaseURL
   */
  public String getAPIBaseURL() {
    return formConfigurations.getApiBaseUrl();
  }
  
  /**
   * @return SecondarySubscriptionId
   */
  public String getSecondarySubscriptionId() {
    return secondarySubscriptionId;
  }
  
  public void setMultifieldReader(MultifieldReader multifieldReader) {
    this.multifieldReader = multifieldReader;
  }
  
  public void setSecondarySubscription(Node secondarySubscription) {
		this.secondarySubscription = secondarySubscription;
  }
  
  public String getCrmformApiKey() {
    return formConfigurations.getCrmformApiKey();
  }
}
