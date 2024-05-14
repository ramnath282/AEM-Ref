package com.mattel.ecomm.core.models;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import com.mattel.ecomm.core.utils.EcomUtil;

@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PromoModel {

	@Self
	private Resource resource;
	
	@Inject
	private String promotinalText;

	@Inject
	private String link;

	@Inject
	private String ctaStyle;

	public String getPromotinalText() {
		if (StringUtils.isNotEmpty(promotinalText)) {
			promotinalText = promotinalText.replaceAll("<p>", "");
			promotinalText = promotinalText.replaceAll("</p>", "");
		}

		return promotinalText;
	}

	/**
	  * Deliver the vanityUrl() over the getPath() if it exists
	  * @return link final URL for the authored path
	 */
	public String getLink() {
		return EcomUtil.checkLink(link, resource);
	}

	public String getCtaStyle() {
		return ctaStyle;
	}

}
