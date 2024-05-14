package com.mattel.global.core.model;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CTS
 *
 */
@Model(adaptables = Resource.class ,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ButtonDetails {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ButtonDetails.class);
	
	@Inject	
	@Named("text")
	private String text;	

	@Inject	
	private String link;
	
	@Inject
	private String ctaType;
	
	@Inject
	private String ctaStyle;
	
	@Inject
	private String ctaLabel;
	
	@Inject
	private String ctaLink;
	
	@Inject
	private String linkOptions;
	
	@PostConstruct
	protected void init() {	    
		LOGGER.info("Inside buttonDetails");
	}
	
	public String getText() {
		return text;
	}

	public String getLink() {
		return link;
	}	

	public void setLink(String link) {
		this.link = link;
	}

	public String getCtaStyle() { return ctaStyle; }
	

	public String getCtaType() { return ctaType; }
	

	public String getCtaLabel() {
		return ctaLabel;
	}	

	public String getCtaLink() {
		return ctaLink;
	}

	public void setCtaLink(String ctaLink) {
		this.ctaLink = ctaLink;
	}
	
	public String getLinkOptions() {
		return linkOptions;
	}

	public void setLinkOptions(String linkOptions) {
		this.linkOptions = linkOptions;
	}	
	
}
