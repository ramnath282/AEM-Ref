package com.mattel.global.core.model;

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
public class AssetModel extends AssetMetaDataDetails{
	private static final Logger LOGGER = LoggerFactory.getLogger(AssetModel.class);
	
	@Inject
	private String title;
	
	@Inject
	private String subTitle;
	
	@Inject
    private String[] selectedTags;
	
	@Inject
	@Default(booleanValues = false)
	private Boolean entrCompClickable;
	
	private String strippedTitle;
	
	private String strippedSubTitle;
	
	private String url;
	
	private String cardLinkOption;
	
	@Self
	Resource resource;
	
	@PostConstruct
	protected void init() {
		LOGGER.info("AssetModel Init Method -> Start");
		getAssetsMetaDataDetails();
		strippedTitle = GlobalUtils.removeTags(title, Constants.REMOVE_TAGS, Constants.EMPTY_ARRAY_STRING);
		strippedSubTitle = GlobalUtils.removeTags(subTitle, Constants.REMOVE_TAGS, Constants.EMPTY_ARRAY_STRING);
		if(entrCompClickable) {
		    LOGGER.debug("Entire Component Clickable {}", entrCompClickable);
			Resource ctaResource = GlobalUtils.getCtaURL(resource);
			if(Objects.nonNull(ctaResource)) {			
			CtaItemModel ctaItemModel = ctaResource.adaptTo(CtaItemModel.class);			
				url = Optional.ofNullable(ctaItemModel).map(CtaItemModel::getUrl).orElse(StringUtils.EMPTY);
				cardLinkOption=Optional.ofNullable(ctaItemModel).map(CtaItemModel::getLinkOptions).orElse(StringUtils.EMPTY);
			}		
			LOGGER.debug("URL :: {} ", url);
		}
		LOGGER.info("AssetModel Init Method -> End");
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
	
	public String[] getSelectedTags() {
	    return selectedTags;
	}
	
}
