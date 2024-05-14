package com.mattel.fisherprice.core.models;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.fisherprice.core.constants.Constants;
import com.mattel.fisherprice.core.pojos.EntityParametersPojo;
import com.mattel.fisherprice.core.services.MultifieldReader;
import com.mattel.fisherprice.core.utils.FisherPriceUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Model(adaptables = { SlingHttpServletRequest.class,
    Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL) public class TargetRecommendationModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(TargetRecommendationModel.class);

    @Inject @Via("resource") private String mboxValue;
    private String atProperty;
    @SlingObject private SlingHttpServletRequest request;
    private String componentId;
	@Inject @Via("resource") @Default(values="false") private String isItProductMbox;
    private String paramsString;
    @Inject @Via("resource") private Node entityParameters;
    @Inject private MultifieldReader multifieldReader;
    @Self @Via("resource") private Resource resource;
    List<EntityParametersPojo> entityParametersList = new ArrayList<>();
    PageManager pageManager;

    @PostConstruct protected void init() {
        pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
        if(Objects.nonNull(pageManager)) {
          LOGGER.info("TargetRecommendationModel init method start");
          Page page = pageManager.getContainingPage(resource);
          atProperty = FisherPriceUtils.getInheritedProperty(page,"atProperty");
          LOGGER.debug("Mbox value is  {}", mboxValue);
          componentId = (String) request.getAttribute(Constants.COMPONENT_ID);
          LOGGER.debug("componentId:::> {}", componentId);
          if (StringUtils.isNotBlank(componentId)) {
              int componentIdCount = Integer.parseInt(componentId);
              ++componentIdCount;
              this.componentId = Integer.toString(componentIdCount);
              request.setAttribute(Constants.COMPONENT_ID, this.componentId);
          } else {
              request.setAttribute(Constants.COMPONENT_ID, Integer.toString(0));
              this.componentId = "0";
          }
          LOGGER.debug("TargetRecommendationModel ::componentId {}", componentId);
          setCustomEntityParameters();
        }
        LOGGER.info("TargetRecommendationModel init method end");
    }

  /**
   * This method will create a list of pojo with multifield property authored
   *
   */
    private void setCustomEntityParameters() {
        LOGGER.info("TargetRecommendationModel setCustomEntityParameters method start");
        StringBuilder stringBuilder = new StringBuilder();
        String parameterString;
        EntityParametersPojo parametersPojo = new EntityParametersPojo();
        if (null != pageManager) {
            Page containingPage = pageManager.getContainingPage(resource);
            ValueMap pageProperties = containingPage.getProperties();
			LOGGER.debug("Value of is product mbox is {}", isItProductMbox);
          if (null != pageProperties.get("articleId", String.class) && isItProductMbox
              .equalsIgnoreCase("false")) {
                parametersPojo.setArticleId(pageProperties.get("articleId", String.class));
                stringBuilder.append("'entity.id'" + ":" + "'" + parametersPojo.getArticleId() + "'");
                LOGGER.debug("Article Id is set to {}", parametersPojo.getArticleId());
            }
          parametersPojo.setAtProperty(FisherPriceUtils.getInheritedProperty(containingPage,"atProperty"));
            LOGGER.debug("At Property is set to {}", parametersPojo.getAtProperty());
            if (stringBuilder.length() != 0) {
                stringBuilder
                    .append(",'at_property'" + ":" + "'" + parametersPojo.getAtProperty() + "',");
            } else {
                stringBuilder
                    .append("'at_property'" + ":" + "'" + parametersPojo.getAtProperty() + "',");
            }
            entityParametersList.add(parametersPojo);

        }
        if (null != entityParameters) {
            LOGGER.debug("entityParameters Multifield is authored ");
            Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(entityParameters);
            for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
                EntityParametersPojo entityParametersPojo = new EntityParametersPojo();
                LOGGER.debug("Entity Key {}", entry.getValue().get("entityKey", String.class));
                entityParametersPojo.setEntityKey(entry.getValue().get("entityKey", String.class));
                LOGGER.debug("Entity Value {}", entry.getValue().get("entityValue", String.class));
                entityParametersPojo.setEntityValue(entry.getValue().get("entityValue", String.class));
                entityParametersList.add(entityParametersPojo);
                stringBuilder.append("'entity." + entityParametersPojo.getEntityKey() + "'" + ":" + "'"
                    + entityParametersPojo.getEntityValue() + "',");
            }
        }
        LOGGER.debug("List final version in {}", entityParametersList);
        parameterString = stringBuilder.length() > 0 ? stringBuilder.toString() : "";
        paramsString = parameterString.substring(0, parameterString.length() - 1);
        LOGGER.debug("params string is {}", paramsString);
        LOGGER.info("Target Recommendation setCustomEntityParameters method end");
    }



  public String getMboxValue() {
    return mboxValue;
  }

    public String getAtProperty() {
        return atProperty;
    }

    public Node getEntityParameters() {
        return entityParameters;
    }

    public String getParamsString() {
        return paramsString;
    }

    public String getComponentId() {
        return componentId;
    }

    public List<EntityParametersPojo> getEntityParametersList() {
        return entityParametersList;
    }
}
