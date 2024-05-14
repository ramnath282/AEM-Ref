package com.mattel.global.core.model;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.google.gson.JsonObject;
import com.mattel.global.core.services.MultifieldReader;
import com.mattel.global.core.utils.PropertyReaderUtils;

/**
 * @author CTS.
 * Model for CRM Analytics Attributes Defined in Page Properties.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CrmPageModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(CrmPageModel.class);

  @Inject
  private MultifieldReader multifieldReader;

  @Inject
  private PropertyReaderUtils propertyReaderUtils;

  private String categoryName;

  @Self
  private Resource resource;

  private String propertiesJson;
  private String scriptUrl;

  @PostConstruct
  protected void init() {

    LOGGER.info("Start of CRM page Model init");
    Optional.ofNullable(resource).ifPresent(res -> {
      try {
        Resource analyticsNodeResource = res.getResourceResolver().getResource
            (res.getPath() + "/analyticsProperties");
        Node analyticsNode = null;
        Map<String, ValueMap> stringValueMapLinkedHashMap = new HashMap<>();
        JsonObject jsonObject = new JsonObject();
        if (analyticsNodeResource != null) {
          analyticsNode = analyticsNodeResource.adaptTo(Node.class);
          if (null != analyticsNode) {
            LOGGER.debug("Analytics path node is {}", analyticsNode.getPath());
            stringValueMapLinkedHashMap = multifieldReader.propertyReader(analyticsNode);
          }
          LOGGER.debug("Map from Multifield reader is {}", stringValueMapLinkedHashMap);
	      for (Map.Entry<String, ValueMap> mapEntry : stringValueMapLinkedHashMap.entrySet()) {
				Object propName = mapEntry.getValue().get("propertyName");
				Object propVal = mapEntry.getValue().get("propertyValue");
				if (null != propName && null != propVal) {
					jsonObject.addProperty(propName.toString(), propVal.toString());
				}
		  }
          LOGGER.debug("JSON Object is {}", jsonObject);

          propertiesJson = jsonObject.toString();
          LOGGER.info("End of CRM page Model init");
        }
        scriptUrl = propertyReaderUtils.getScriptUrl();
        InheritanceValueMap inheritanceValueMap = new HierarchyNodeInheritanceValueMap(res);
        categoryName = Optional.ofNullable(inheritanceValueMap.getInherited("categoryName", String.class)).orElse(StringUtils.EMPTY);
        LOGGER.debug("Category Name is {}", categoryName);
      } catch (RepositoryException e) {
        LOGGER.error("Exception from Crm page Model", e);
      }
    });
  }

  public String getCategoryName() { return categoryName; }

  public String getPropertiesJson() {
    return propertiesJson;
  }

  public String getScriptUrl() { return scriptUrl; }

  public void setMultifieldReader(MultifieldReader multifieldReader) {
    this.multifieldReader = multifieldReader;
  }

  public void setPropertiesJson(String propertiesJson) {
    this.propertiesJson = propertiesJson;
  }

  public void setResource(Resource resource) {
    this.resource = resource;
  }

  public void setPropertyReaderUtils(PropertyReaderUtils propertyReaderUtils) {
    this.propertyReaderUtils = propertyReaderUtils;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public void setScriptUrl(String scriptUrl) {
    this.scriptUrl = scriptUrl;
  }
}