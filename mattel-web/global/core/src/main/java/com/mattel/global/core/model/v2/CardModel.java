package com.mattel.global.core.model.v2;

import java.util.Objects;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.core.model.v1.CtaItemModel;
import com.mattel.global.core.utils.GlobalUtils;
import com.mattel.global.master.core.constants.Constants;
import com.mattel.global.master.core.model.AssetMetaDataDetails;

@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CardModel extends AssetMetaDataDetails{
	private static final Logger LOGGER = LoggerFactory.getLogger(CardModel.class);

	@Inject
	private String title;

	@Inject
	private String subTitle;

	@Inject
	@Default(booleanValues = false)
	private Boolean entrCompClickable;

	private String strippedTitle;

	private String strippedSubTitle;

	private String url;

	private String cardLinkOption;

	private String ctaTrackingText;

	private String sectionTitleAnalytics = StringUtils.EMPTY;

	@Self
	Resource resource;

	@PostConstruct
	protected void init() {
		LOGGER.info("Card Model V2 Init Method Start");
		getAssetsMetaDataDetails();
		strippedTitle = GlobalUtils.removeTags(title, Constants.REMOVE_TAGS, Constants.EMPTY_ARRAY_STRING);
		if(StringUtils.isNotBlank(strippedTitle)){
			sectionTitleAnalytics =  strippedTitle.replaceAll("\\<.*?\\>", "");
		}

		strippedSubTitle = GlobalUtils.removeTags(subTitle, Constants.REMOVE_TAGS, Constants.EMPTY_ARRAY_STRING);
		if(entrCompClickable) {
			Resource ctaResource = GlobalUtils.getCtaURL(resource);
			if(Objects.nonNull(ctaResource)) {
			CtaItemModel ctaItemModel = ctaResource.adaptTo(CtaItemModel.class);
				url = Optional.ofNullable(ctaItemModel).map(CtaItemModel::getUrl).orElse(StringUtils.EMPTY);
				cardLinkOption=Optional.ofNullable(ctaItemModel).map(CtaItemModel::getLinkOptions).orElse(StringUtils.EMPTY);
				ctaTrackingText=Optional.ofNullable(ctaItemModel).map(CtaItemModel::getTrackingText).orElse(StringUtils.EMPTY);
			}
			LOGGER.info("URL :: {} ", url);
		}
		LOGGER.info("Card Model V2 Init Method End");
	}

	public String getStrippedTitle() {
		return strippedTitle;
	}

	public String getStrippedSubTitle() {
		return strippedSubTitle;
	}

	public String getUrl() {
		return url;
	}

	public String getCardLinkOption() {
	    return cardLinkOption;
	}
    public String getCtaTrackingText() {
        return ctaTrackingText;
    }

    public String getSectionTitleAnalytics() {
        return sectionTitleAnalytics;
    }

}
