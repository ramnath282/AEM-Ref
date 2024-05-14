package com.mattel.global.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.core.utils.GlobalUtils;
import com.mattel.global.core.pojo.InformationalFooterLinkPojo;
import com.mattel.global.core.pojo.SocialIconsPojo;
import com.mattel.global.core.services.MultifieldReader;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class InformationalFooterModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(InformationalFooterModel.class);

	@Self
	Resource resource; 

	@Inject
	private Node footerLinksOne;

	@Inject
	private Node socialIcons;

	@Inject
	private Node footerLinksTwo;

	@Inject
	private Node footerLinksThree;

	@Inject
	private Node footerLinksFour;

	@Inject
	private MultifieldReader multifieldReader;

	private static final String LINKTEXT = "linkText";
	private static final String LINKTARGET= "linkTarget";
	private static final String ALWAYSENGLISH= "alwaysEnglish";
	private static final String LINKURL = "linkURL";    
	private static final String ICONS= "icons";
	private static final String SOCIALLINKURL = "socialLinkURL";    

	private List<InformationalFooterLinkPojo> footerTextGroupOne = new ArrayList<>();
	private List<InformationalFooterLinkPojo> footerTextGroupTwo = new ArrayList<>();
	private List<InformationalFooterLinkPojo> footerTextGroupThree = new ArrayList<>();
	private List<InformationalFooterLinkPojo> footerTextGroupFour = new ArrayList<>();	
	private List<SocialIconsPojo> socialIconsList = new ArrayList<>();	

	/**
	 * The init method to fetch the list of footer link details.
	 */

	@PostConstruct
	public void init () {
		LOGGER.info("init() method of InformationalFooterModel class --> started");
		if (Objects.nonNull(resource)) {
			fetchFooterLinksByNodes();
		}		
		if (Objects.nonNull(socialIcons) && Objects.nonNull(resource)) {
			Map<String, ValueMap> multifieldProperty;
			multifieldProperty = multifieldReader.propertyReader(socialIcons);
			if (Objects.nonNull(multifieldProperty)) {
				for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
					SocialIconsPojo socialIconsDetail = new SocialIconsPojo();
					socialIconsDetail.setIcons(entry.getValue().get(InformationalFooterModel.ICONS, StringUtils.EMPTY));
					socialIconsDetail.setLinkText(entry.getValue().get(InformationalFooterModel.LINKTEXT, StringUtils.EMPTY));
					socialIconsDetail.setLinkTarget(entry.getValue().get(InformationalFooterModel.LINKTARGET, StringUtils.EMPTY));
					socialIconsDetail.setAlwaysEnglish(entry.getValue().get(InformationalFooterModel.ALWAYSENGLISH, StringUtils.EMPTY));
					socialIconsDetail.setSocialLinkURL(GlobalUtils.checkLink(entry.getValue().get(InformationalFooterModel.SOCIALLINKURL, StringUtils.EMPTY), resource));					
					socialIconsList.add(socialIconsDetail);
					LOGGER.debug("socialIconsList is {}", socialIconsList);
				}
			}
		}
		LOGGER.info("init() method of InformationalFooterModel class --> ended");
	}

	private void fetchFooterLinksByNodes() {
		LOGGER.info("fetchFooterLinksByNodes() method of InformationalFooterModel class --> started");
		if (Objects.nonNull(footerLinksOne)) {
			footerTextGroupOne = fetchFooterLinks(footerLinksOne);
		}
		if (Objects.nonNull(footerLinksTwo)) {
			footerTextGroupTwo = fetchFooterLinks(footerLinksTwo);
		}
		if (Objects.nonNull(footerLinksThree)) {
			footerTextGroupThree = fetchFooterLinks(footerLinksThree);
		}
		if (Objects.nonNull(footerLinksFour)) {
			footerTextGroupFour = fetchFooterLinks(footerLinksFour);
		}
		LOGGER.info("fetchFooterLinksByNodes() method of InformationalFooterModel class --> ended");
	}

	private List<InformationalFooterLinkPojo> fetchFooterLinks(Node footerLinkNode) {
		LOGGER.info("fetchFooterLinks() method of InformationalFooterModel class --> started");
		Map<String, ValueMap> multifieldProperty;
		List<InformationalFooterLinkPojo> groupLinks = new ArrayList<>();
		multifieldProperty = multifieldReader.propertyReader(footerLinkNode);
		if(Objects.nonNull(multifieldProperty)) {
			for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
				InformationalFooterLinkPojo footerLink = new InformationalFooterLinkPojo();
				footerLink.setLinkText(entry.getValue().get(InformationalFooterModel.LINKTEXT, StringUtils.EMPTY));
				footerLink.setLinkURL(GlobalUtils.checkLink(entry.getValue().get(InformationalFooterModel.LINKURL, StringUtils.EMPTY), resource));
				footerLink.setLinkTarget(entry.getValue().get(InformationalFooterModel.LINKTARGET, StringUtils.EMPTY));
				footerLink.setAlwaysEnglish(entry.getValue().get(InformationalFooterModel.ALWAYSENGLISH, StringUtils.EMPTY));
				LOGGER.debug("footerLink is {}", footerLink);
				groupLinks.add(footerLink);
			}
		}
		LOGGER.debug("groupLinks is {}", groupLinks);
		LOGGER.info("fetchFooterLinks() method of InformationalFooterModel class --> ended");
		return groupLinks;
	}

	public List<InformationalFooterLinkPojo> getFooterTextGroupOne() {
		return footerTextGroupOne;
	}

	public List<InformationalFooterLinkPojo> getFooterTextGroupTwo() {
		return footerTextGroupTwo;
	}

	public List<InformationalFooterLinkPojo> getFooterTextGroupThree() {
		return footerTextGroupThree;
	}

	public List<InformationalFooterLinkPojo> getFooterTextGroupFour() {
		return footerTextGroupFour;
	}

	public List<SocialIconsPojo> getSocialIconsList() {
		return socialIconsList;
	}
}
