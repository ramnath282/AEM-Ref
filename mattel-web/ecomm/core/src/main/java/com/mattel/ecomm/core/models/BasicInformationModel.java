package com.mattel.ecomm.core.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ecomm.core.pojos.BasicInformationPojo;
import com.mattel.ecomm.core.services.MultifieldReader;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BasicInformationModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicInformationModel.class);
    List<BasicInformationPojo> keyList = new ArrayList<>();
    @Inject
    private Node dolls;

    @Inject
    private MultifieldReader multifieldReader;

    @Inject
    private String type;

    @Inject
    private String[] eyeColorCategories;

    @Inject
    private String[] hairColorCategories;

    @Self
    private Resource resource;

    private String dollType;
    private String treatmentSkuId;

    List<Tag> tagObjList = null;

    @PostConstruct
    protected void init() {
        LOGGER.info("Basic Information Model Init Start new {}", type);
        if (!Objects.isNull(dolls)) {
            final Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(dolls);
            for (final Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
                final BasicInformationPojo basicInformationPojo = new BasicInformationPojo();
                String[] value = entry.getValue().get("dollsCategories", String[].class);
                basicInformationPojo.setDollImage(entry.getValue().get("dollImage", String.class));
                basicInformationPojo.setDollDescription(entry.getValue().get("dollDescription", String.class));
                basicInformationPojo.setHelperPopOverTextTreatment(
                        entry.getValue().get("helperPopOverTextTreatment", String.class));
                basicInformationPojo.setHelperTextTreatment(entry.getValue().get("helperTextTreatment", String.class));
                basicInformationPojo.setVideoLinkTreatment(entry.getValue().get("videoLinkTreatment", String.class));
                
                basicInformationPojo.setTreatmentLinkType(entry.getValue().get("treatmentLinkType", String.class));
                basicInformationPojo.setTreatmentVideoType(entry.getValue().get("treatmentVideoType", String.class));
                LOGGER.debug("value of doll decription{}", value);
                LOGGER.debug("value of frm gettag{}", getTagObjectList(value));
                basicInformationPojo.setDollsCategories(getTagObjectList(value));

                keyList.add(basicInformationPojo);
            }
        }
        checkWellnessUserAttributes();
        LOGGER.info("Basic Information Model Init End");
    }

    private void checkWellnessUserAttributes() {
        LOGGER.info("Basic Information Model checkWellnessUserAttributes Start");
        ResourceResolver resourceResolver = resource.getResourceResolver();
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
        if (Objects.nonNull(pageManager) && Objects.nonNull(tagManager)) {
            Page page = pageManager.getContainingPage(resource);
            readPageProperty(page, tagManager);
        }
        LOGGER.info("Basic Information Model checkWellnessUserAttributes End");
    }

    /**
     * The method reads the page property to get the wellness dolltype and
     * treatment id
     * 
     * @param page
     * @param tagManager
     */
    private void readPageProperty(Page page, TagManager tagManager) {
        LOGGER.info("Basic Information Model readPageProperty Start");
        Resource pageResource = page.getContentResource();
        if (Objects.nonNull(pageResource)) {
            ValueMap valueMap = pageResource.getValueMap();
            dollType = getTagTitle(valueMap.get("dollsType", String.class), tagManager);
            treatmentSkuId = getTagTitle(valueMap.get("treatmentProductId", String.class), tagManager);
            LOGGER.debug("Doll Type for wellness user -{}, treatment Sku Id for wellness user{}", dollType,
                    treatmentSkuId);
        }
        LOGGER.info("Basic Information Model readPageProperty End");
    }

    /**
     * The method fetch the tag title from authored tag field
     * 
     * @param propertyValue
     * @param tagManager
     */
    private String getTagTitle(String propertyValue, TagManager tagManager) {
        LOGGER.info("Basic Information Model getTagTitle Start");
        String tagTitle = "";
        if (StringUtils.isNotEmpty(propertyValue)) {
            Tag tagObject = tagManager.resolve(propertyValue);
            if (Objects.nonNull(tagObject)) {
                tagTitle = tagObject.getTitle();
                LOGGER.debug("Tagtitle {}", tagTitle);
            }
        }
        LOGGER.info("Basic Information Model getTagTitle End");
        return tagTitle;
    }

    /**
     * The method create the list of tagObject
     * 
     * @param tagStringList
     * @return tagObjList
     */
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

    /**
     * The method create a taglist based on screen category Type
     * 
     * @param categoryType
     * @param eyeColorCategories2
     * @return tagList
     */
    private List<Tag> getCategoryTagList(String categoryType, String[] dollScreenCategories) {
        List<Tag> tagList = null;
        if (type.equals(categoryType)) {
            tagList = this.getTagObjectList(dollScreenCategories);
        }
        return tagList;
    }

    public List<Tag> getEyeColorTagList() {
        return getCategoryTagList("eyeColorScreen", eyeColorCategories);
    }

    public List<Tag> getHairColorTagList() {
        return getCategoryTagList("hairColorScreen", hairColorCategories);
    }

    public Node getDolls() {
        return dolls;
    }

    public void setDolls(Node dolls) {
        this.dolls = dolls;
    }

    public MultifieldReader getMultifieldReader() {
        return multifieldReader;
    }

    public void setMultifieldReader(MultifieldReader multifieldReader) {
        this.multifieldReader = multifieldReader;
    }

    public List<BasicInformationPojo> getKeyList() {
        return keyList;
    }

    public void setKeyList(List<BasicInformationPojo> keyList) {
        this.keyList = keyList;
    }

    public String getDollType() {
        return dollType;
    }

    public void setDollType(String dollType) {
        this.dollType = dollType;
    }

    public String getTreatmentSkuId() {
        return treatmentSkuId;
    }

    public void setTreatmentSkuId(String treatmentSkuId) {
        this.treatmentSkuId = treatmentSkuId;
    }

}
