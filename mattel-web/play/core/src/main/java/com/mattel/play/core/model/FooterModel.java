package com.mattel.play.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.RequestAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.play.core.helper.PlayHelper;
import com.mattel.play.core.pojos.FooterPojo;
import com.mattel.play.core.services.MultifieldReader;
import com.mattel.play.core.utils.PlaySiteConfigurationUtils;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FooterModel {

	@Inject
	@Via("resource")
	@Optional
	private Node footerLinks;

	@Inject
	@Via("resource")
	@Optional
	private String logoURL;

	@Inject
	@Optional
	private MultifieldReader multifieldReader;
	
	@RequestAttribute(name = "url")
	String pagePath;
	
	@Inject
	Resource resource;
	
	private boolean isCountryDropdownExcluded;

	List<FooterPojo> footerLinksList = new ArrayList<>();

	private static final Logger LOGGER = LoggerFactory.getLogger(FooterModel.class);

	/**
	 * The init method to fetch the list of footer link details.
	 */
	@PostConstruct
	protected void init() {

		LOGGER.info("Footer Links Model Start");
		if (footerLinks != null && multifieldReader != null && null!=resource) {
			Map<String, ValueMap> multifieldProperty;
			multifieldProperty = multifieldReader.propertyReader(footerLinks);
			for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
				FooterPojo footerLink = new FooterPojo();
				footerLink.setLinkText(entry.getValue().get("linkText", String.class));
				footerLink.setLinkURL(PlayHelper.checkLink(entry.getValue().get("linkURL", String.class),resource));
				footerLink.setLinkTarget(entry.getValue().get("linkTarget", String.class));
				footerLink.setAlwaysEnglish(entry.getValue().get("alwaysEnglish", String.class));
				footerLinksList.add(footerLink);
				LOGGER.debug("footerLinksList size of FooterModel is {}",footerLinksList.size());
			}
		}
		if(null!=pagePath){
			isCountryDropdownExcluded = checkCountryDropdownIsExcluded();
		}
		LOGGER.info("Init method in FooterLinkModel end");
	}

	private boolean checkCountryDropdownIsExcluded() {
		String[] excludedBrands = PlaySiteConfigurationUtils.getExcludedBrandsCountryDropdown();
		if (null != excludedBrands && excludedBrands.length > 0) {
			for (String brand : excludedBrands) {
				if(brand.equalsIgnoreCase(PlayHelper.getBrandName(pagePath))){
					return true;
				}
			}
		}
		return false;
	}

	public void setFooterLinksList(List<FooterPojo> footerLinksList) {
		this.footerLinksList = footerLinksList;
	}

	public void setFooterLinks(Node footerLinks) {
		this.footerLinks = footerLinks;
	}

	public void setMultifieldReader(MultifieldReader multifieldReader) {
		this.multifieldReader = multifieldReader;
	}

	/**
	 * @return This method return list of FooterLinksPojo
	 */
	public List<FooterPojo> getFooterLinksList() {
		return footerLinksList;
	}

	/**
	 * @return This method return list of FooterLinksPojo
	 */
	public String getLogoURL() {
		return PlayHelper.checkLink(logoURL,resource);
	}

	/**
	 * @return This method return isCountryDropdownExcluded flag
	 */
	public boolean isCountryDropdownExcluded() {
		return isCountryDropdownExcluded;
	}

	public void setCountryDropdownExcluded(boolean isCountryDropdownExcluded) {
		this.isCountryDropdownExcluded = isCountryDropdownExcluded;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}

}
