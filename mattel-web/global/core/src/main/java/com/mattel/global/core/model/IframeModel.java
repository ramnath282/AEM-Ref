package com.mattel.global.core.model;

import javax.inject.Inject;

import javax.annotation.PostConstruct;


import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import com.mattel.global.core.utils.GlobalUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.injectorspecific.Self;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL) public class IframeModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(IframeModel.class);

    @Inject
    private String backgroundImage;

    @Inject
    private String backgroundColor;

    @Inject
    private String sourceUrl;

    @Inject
    private String desktopHeight;

    @Inject
    private String desktopWidth;

    @Inject
    private String dynamicDimentions;

    @Inject
    private String mobilePortraitHeight;

    @Inject
    private String mobilePortraitWidth;

    @Inject
    private String mobileLandscapeHeight;

    @Inject
    private String linkUrl;

    @Inject
    private String linkText;

    @Inject
    private String linkOptions;

    @Inject
    private String mobilePortraitSpacing;

    @Inject
    private String mobileLandscapeSpacing;

    @Inject
    private String mobileLandscapeWidth;

    @Inject
    private String linkAltText;

    @Self
    private Resource resource;

    @PostConstruct protected void init() {
        LOGGER.info("Start of Init method in the Iframe Model");
            linkUrl = GlobalUtils.checkLink(linkUrl, resource);
        LOGGER.info("End of Init method in the Iframe Model");
    }

    public String getMobilePortraitSpacing() {
        return mobilePortraitSpacing;
    }

    public void setMobilePortraitSpacing(String mobilePortraitSpacing) {
        this.mobilePortraitSpacing = mobilePortraitSpacing;
    }

    public String getMobileLandscapeSpacing() {
        return mobileLandscapeSpacing;
    }

    public void setMobileLandscapeSpacing(String mobileLandscapeSpacing) {
        this.mobileLandscapeSpacing = mobileLandscapeSpacing;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getLinkText() {
        return linkText;
    }

    public void setLinkText(String linkText) {
        this.linkText = linkText;
    }

    public String getLinkOptions() {
        return linkOptions;
    }

    public void setLinkOptions(String linkOptions) {
        this.linkOptions = linkOptions;
    }

    public String getLinkAltText() {
        return linkAltText;
    }

    public void setLinkAltText(String linkAltText) {
        this.linkAltText = linkAltText;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getDesktopHeight() {
        return desktopHeight;
    }

    public void setDesktopHeight(String desktopHeight) {
        this.desktopHeight = desktopHeight;
    }

    public String getDesktopWidth() {
        return desktopWidth;
    }

    public void setDesktopWidth(String desktopWidth) {
        this.desktopWidth = desktopWidth;
    }

    public String getDynamicDimentions() {
        return dynamicDimentions;
    }

    public void setDynamicDimentions(String dynamicDimentions) {
        this.dynamicDimentions = dynamicDimentions;
    }

    public String getMobilePortraitHeight() {
        return mobilePortraitHeight;
    }

    public void setMobilePortraitHeight(String mobilePortraitHeight) {
        this.mobilePortraitHeight = mobilePortraitHeight;
    }

    public String getMobilePortraitWidth() {
        return mobilePortraitWidth;
    }

    public void setMobilePortraitWidth(String mobilePortraitWidth) {
        this.mobilePortraitWidth = mobilePortraitWidth;
    }

    public String getMobileLandscapeHeight() {
        return mobileLandscapeHeight;
    }

    public void setMobileLandscapeHeight(String mobileLandscapeHeight) {
        this.mobileLandscapeHeight = mobileLandscapeHeight;
    }

    public String getMobileLandscapeWidth() {
        return mobileLandscapeWidth;
    }

    public void setMobileLandscapeWidth(String mobileLandscapeWidth) {
        this.mobileLandscapeWidth = mobileLandscapeWidth;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

}
