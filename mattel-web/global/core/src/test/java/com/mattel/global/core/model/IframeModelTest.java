package com.mattel.global.core.model;

import static org.junit.Assert.assertSame;

import org.junit.Before;

import org.junit.Test;


public class IframeModelTest {

    IframeModel iframeModel;

    @Before
    public void setUp() throws IllegalArgumentException,IllegalAccessException {
        iframeModel = new IframeModel();

    }


    @Test
    public void testIframe() {
        iframeModel.setBackgroundColor("backgroundColor");
        assertSame("backgroundColor", iframeModel.getBackgroundColor());
        iframeModel.setBackgroundImage("backgroundImage");
        assertSame("backgroundImage", iframeModel.getBackgroundImage());
        iframeModel.setDesktopHeight("desktopHeight");
        assertSame("desktopHeight", iframeModel.getDesktopHeight());
        iframeModel.setDesktopWidth("desktopWidth");
        assertSame("desktopWidth", iframeModel.getDesktopWidth());
        iframeModel.setMobileLandscapeHeight("mobileLandscapeHeight");
        assertSame("mobileLandscapeHeight", iframeModel.getMobileLandscapeHeight());
        iframeModel.setMobileLandscapeWidth("mobileLandscapeWidth");
        assertSame("mobileLandscapeWidth", iframeModel.getMobileLandscapeWidth());
        iframeModel.setMobileLandscapeSpacing("mobileLandscapeSpacing");
        assertSame("mobileLandscapeSpacing", iframeModel.getMobileLandscapeSpacing());
        iframeModel.setMobilePortraitHeight("mobilePortraitHeight");
        assertSame("mobilePortraitHeight", iframeModel.getMobilePortraitHeight());
        iframeModel.setMobilePortraitWidth("mobilePortraitWidth");
        assertSame("mobilePortraitWidth", iframeModel.getMobilePortraitWidth());
        iframeModel.setMobilePortraitSpacing("mobilePortraitSpacing");
        assertSame("mobilePortraitSpacing", iframeModel.getMobilePortraitSpacing());
        iframeModel.setLinkAltText("linkAltText");
        assertSame("linkAltText", iframeModel.getLinkAltText());
        iframeModel.setLinkUrl("linkUrl");
        assertSame("linkUrl", iframeModel.getLinkUrl());
        iframeModel.setLinkAltText("linkAltText");
        assertSame("linkAltText", iframeModel.getLinkAltText());
        iframeModel.setLinkOptions("linkOptions");
        assertSame("linkOptions", iframeModel.getLinkOptions());
        iframeModel.setLinkText("linkText");
        assertSame("linkText", iframeModel.getLinkText());
        iframeModel.setSourceUrl("sourceUrl");
        assertSame("sourceUrl", iframeModel.getSourceUrl());
        iframeModel.setDynamicDimentions("dynamicDimentions");
        assertSame("dynamicDimentions", iframeModel.getDynamicDimentions());
        iframeModel.init();
    }

}
