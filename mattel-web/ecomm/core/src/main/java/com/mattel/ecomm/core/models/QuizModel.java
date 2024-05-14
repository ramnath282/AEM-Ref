package com.mattel.ecomm.core.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.mattel.ecomm.core.services.GTEmailTnsConfigService;

/**
 * @author CTS. A Model class for Carousel
 */

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class QuizModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(QuizModel.class);

  @Inject
  private String type;

  @Inject
  private String[] occasionCategories;

  @Inject
  private String[] attributesCategories;

  @Inject
  private String[] aspirationsCategories;
  
  @Inject
  GTEmailTnsConfigService gtEmailTnsConfigService;

  @Self
  private Resource resource;
  
  JSONObject tnsConfigDetails = null;
  List<Tag> tagObjList = null;

  @PostConstruct
  protected void init() throws JSONException {
    LOGGER.info("Quiz Model Init Start new {}",type);
    tnsConfigDetails = gtEmailTnsConfigService.getJSONValues();
  }
  
  public String getTNSConfigDetails() {
	  return tnsConfigDetails.toString();
  }
  
  private List<Tag> getTagObjectList(String[] tagStringList) {
    ResourceResolver resourceResolver = resource.getResourceResolver();
     TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
      if (Objects.nonNull(tagManager)) {
        tagObjList = new ArrayList<>();
        for (String tagString : tagStringList) {
          Tag tagObject = tagManager.resolve(tagString);
          tagObjList.add(tagObject);
        }
      }
    return tagObjList;
  }

  public List<Tag> getOccasionTagList() {
    List<Tag> tagList = null;
    if ("occasionSectionConfig".equals(type)) {
      tagList = this.getTagObjectList(occasionCategories);

    }

    return tagList;
  }

  public List<Tag> getAttributesTagList() {
    List<Tag> tagList = null;
    if ("attributesSectionConfig".equals(type)) {
      tagList = this.getTagObjectList(attributesCategories);

    }
    return tagList;
  }

  public List<Tag> getAspirationTagList() {
    List<Tag> tagList = null;
    if ("aspirationsSectionConfig".equals(type)) {
      tagList = this.getTagObjectList(aspirationsCategories);

    }

    return tagList;
  }

}
