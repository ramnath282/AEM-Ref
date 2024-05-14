package com.mattel.ag.explore.core.model;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ag.explore.core.pojos.CommonPojo;
import com.mattel.ag.explore.core.utils.PathUtils;



/**
 * @author CTS. A Model class for Instagram Banner.
 */

@Model(adaptables = Resource.class , defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class InstagramBannerModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(InstagramBannerModel.class);
	@Inject
	private String imgUrl;
	@Inject
	private String ctaLink;
	CommonPojo commonPojo = new CommonPojo();

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getCtaLink() {
		return ctaLink;
	}

	public void setCtaLink(String ctaLink) {
		this.ctaLink = ctaLink;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	@PostConstruct
	protected void init() {
		LOGGER.info("Hero Image Banner Model init Start");
		if(null != imgUrl){
			commonPojo.setExternal(PathUtils.isExternal(imgUrl));}
		if(null != ctaLink){
			commonPojo.setCtaExternal(PathUtils.isExternal(ctaLink));}
		LOGGER.info("Hero Image Banner Model init End");
	}
}