package com.mattel.global.master.core.model;

import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.core.utils.GlobalUtils;
import com.mattel.global.master.core.constants.Constants;


@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AccordionItemsModel extends BaseAccordionModel {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AccordionItemsModel.class);

    @Inject
    private Resource resource;

    @Inject
    @Via("resource")
    private String trackThisCta;

    @Inject
    @Via("resource")
    private String trackingText;

    @Inject
    @Via("resource")
    private String anchorName;

    @Inject
    @Via("resource")
    private String trackThisAnchor;

    @Inject
    @Via("resource")
    private String trackingTextAnchor;

    String ctaCloseText;
    String ctaOpenText;

    @PostConstruct
    protected void init() {
        LOGGER.debug("Accordion Items Model Init Method - Start");
        if(Objects.nonNull(resource)){
            Resource parentResource = resource.getParent();
            if(Objects.nonNull(parentResource)){
            final ValueMap nodeProperties = parentResource.getValueMap();
            ctaOpenText = nodeProperties.get("ctaOpenText", String.class);
            ctaCloseText = nodeProperties.get("ctaCloseText", String.class);
            }
        }
    }

    public String getCtaCloseText() {
        return GlobalUtils.removeTags(ctaCloseText,Constants.REMOVE_TAGS, Constants.EMPTY_ARRAY_STRING);
    }

    public String getCtaOpenText() {
        return GlobalUtils.removeTags(ctaOpenText, Constants.REMOVE_TAGS, Constants.EMPTY_ARRAY_STRING);
    }

    public String getTrackThisCta() { return trackThisCta; }

    public String getTrackingText() { return trackingText; }

    public String getAnchorName() { return anchorName; }

    public String getTrackThisAnchor() { return trackThisAnchor; }

    public String getTrackingTextAnchor() { return trackingTextAnchor; }
}
