package com.mattel.global.master.core.model;

import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.core.utils.GlobalUtils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;

@Model(adaptables = {Resource.class} ,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CTAContainerModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(CTAContainerModel.class);

	@Inject
	private Boolean useInterstitial;

	@Inject
	private String interstitialType;

	@Inject
	private String interstitialSelection;

	@Self
	private Resource resource;

	@Inject
	private String image;

	private String backgroundImagePath;


	@PostConstruct
	protected void init() {
		LOGGER.info("CTAContainerModel Init Method ---> Started");
		if (null != resource) {
			ResourceResolver resourceResolver = resource.getResourceResolver();
			backgroundImagePath = GlobalUtils.setBackgroundPath(resourceResolver,image);
		}
		LOGGER.info("CTAContainerModel Init Method ---> Ended");
	}

	public String getBackgroundImagePath() {
		return backgroundImagePath;
	}	

	public Boolean getUseInterstitial() {
		return useInterstitial;
	}

	public String getInterstitialType() {
		return interstitialType;
	}

	public String getInterstitialSelection() {
		return GlobalUtils.checkLink(interstitialSelection, resource);
	}

}
