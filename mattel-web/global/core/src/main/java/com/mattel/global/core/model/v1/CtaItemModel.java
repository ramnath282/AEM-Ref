package com.mattel.global.core.model.v1;

import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.core.utils.GlobalUtils;
import com.mattel.global.master.core.constants.Constants;
import com.mattel.global.master.core.model.CTABaseModel;

@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CtaItemModel extends CTABaseModel{

	private static final Logger LOGGER = LoggerFactory.getLogger(CtaItemModel.class);

	@Inject
	@Default(values = StringUtils.EMPTY)
	private String linkURL;

	private String url;

	private String interstitialType;

	private String interstitialSelectionFragmentPath;

	private String interstitialResTypeName;

	private Boolean isFirstCTA = false;

	Boolean useInterstitialParent = false;

	@PostConstruct
	protected void init() {
		Resource parentResource = null;
		LOGGER.info("CTA ITEM MODEL :: Init Method Start");
		url = GlobalUtils.getUrl(linkURL,resource);
		parentResource =  resource.getParent();
		Map<String,String> interstitialDetail;

		if(Boolean.FALSE.equals(useInterstitial) && Objects.nonNull(parentResource)) {
			useInterstitialParent = parentResource.getValueMap().get("useInterstitial",Boolean.class);
			LOGGER.debug("useInterstitial from Parent :: {}",useInterstitialParent);
			isFirstCTA = GlobalUtils.isFirstCTA(parentResource,resource);
			LOGGER.debug("isFirstCTA is :: {}",isFirstCTA);
			if(Boolean.TRUE.equals(useInterstitialParent) && Boolean.TRUE.equals(isFirstCTA)) {
				interstitialDetail=	GlobalUtils.getInterstitialDetails(parentResource);
				if(Boolean.FALSE.equals(interstitialDetail.isEmpty())) {
					interstitialPath = interstitialDetail.get(Constants.INTERSTITIAL_PATH);
					interstitialType = interstitialDetail.get(Constants.INTERSTITIAL_TYPE);
					interstitialResTypeName = interstitialDetail.get(Constants.INTERSTITIAL_RESOURCETYPE);
				}
			}
		}

		LOGGER.debug("useInterstitial :: {}",useInterstitial);
		linkText = GlobalUtils.removeTags(linkText, Constants.REMOVE_TAGS, Constants.EMPTY_ARRAY_STRING);
		LOGGER.debug("Button Text :: {}",linkText);
		LOGGER.info("CTA ITEM MODEL :: Init Method End");
	}

	public String getUrl() {
		return url;
	}

	public String getInterstitialType() {
		return interstitialType;
	}

	public String getInterstitialSelectionFragmentPath() {
		return interstitialSelectionFragmentPath;
	}

	public Boolean getIsFirstCTA() {
		return isFirstCTA;
	}

	public Boolean getUseInterstitialParent() {
		return useInterstitialParent;
	}

	public String getInterstitialResTypeName() {
		return interstitialResTypeName;
	}
	
}
