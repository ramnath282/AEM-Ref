package com.mattel.global.master.core.model;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.injectorspecific.Self;

public class CTABaseModel {
	
	@Inject
	@Default(booleanValues = false)
	protected Boolean trackThisCTA;
	
	@Inject
	@Default(values = StringUtils.EMPTY)
	protected String trackingText;
	
	@Inject
	@Default(values = "newTab")
	protected String linkOptions;
	
	@Inject
	@Default(booleanValues = false)
	protected Boolean useInterstitial;
	
	@Inject
	@Default(values = StringUtils.EMPTY)
	protected String interstitialPath;
	
	@Inject
	@Default(values = StringUtils.EMPTY)
	protected String interstitialSelectionIndItem;
	
	@Inject
	@Default(values = StringUtils.EMPTY)
	protected String fillColor;
	
	@Inject
	@Default(values = StringUtils.EMPTY)
	protected String linkText;
	
	@Inject
	@Default(values = StringUtils.EMPTY)
	protected String linkAltText;
	
	@Self
	protected Resource resource;	
	
	
	public Boolean getTrackThisCTA() {
		return trackThisCTA;
	}

	public String getTrackingText() {
		return trackingText;
	}

	public String getLinkOptions() {
		return linkOptions;
	}

	public Boolean getUseInterstitial() {
		return useInterstitial;
	}

	public String getInterstitialSelectionIndItem() {
		return interstitialSelectionIndItem;
	}

	public String getInterstitialPath() {
		return interstitialPath;
	}

	public String getFillColor() {
		return fillColor;
	}

	public String getLinkText() {
		return linkText;
	}

	public String getLinkAltText() {
		return linkAltText;
	}

}
