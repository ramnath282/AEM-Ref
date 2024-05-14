package com.mattel.global.master.core.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.dam.api.Asset;
import com.mattel.global.master.core.constants.Constants;
import com.mattel.global.master.core.enums.ImageAltTextMapping;

public class AssetMetaDataDetails {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AssetMetaDataDetails.class);
	
	@Inject
	private String image;
	
	@Inject
	@Default(values = StringUtils.EMPTY)
	private String imageAltText;
	
	@Inject
	private String mobileImage;
	
	@Inject
	@Default(values = StringUtils.EMPTY)
	private String mobileImageAltText;
	
	@Inject
	private String hoverImage;
	
	@Inject
	@Default(values = StringUtils.EMPTY)
	private String hoverImageAltText;
	
	
	@Inject
	private String mobileHoverImage;
	
	@Inject
	@Default(values = StringUtils.EMPTY)
	private String mobileHoverImageAltText;	
	
	@Self
	private Resource resource;	
	
	Map<String,String> imageDetails;
	
	Map<String,String> imageAltTextDetails;
		
	protected void getAssetsMetaDataDetails() {	
		LOGGER.info("AssetMetaDataDetails -> getAssetsMetaDataDetails Method Start");
		imageDetails = new HashMap<>();
		imageAltTextDetails = new HashMap<>();
		imageDetails.put(Constants.IMAGE, image);
		imageDetails.put(Constants.MOBILE_IMAGE, mobileImage);
		imageDetails.put(Constants.HOVER_IMAGE, hoverImage);
		imageDetails.put(Constants.MOBILE_HOVER_IMAGE, mobileHoverImage);		
		
		imageAltTextDetails.put(Constants.IMAGE_ALT_TEXT, imageAltText);
		imageAltTextDetails.put(Constants.MOBILE_IMAGE_ALT_TEXT, mobileImageAltText);
		imageAltTextDetails.put(Constants.HOVER_IMG_ALT_TEXT, hoverImageAltText);
		imageAltTextDetails.put(Constants.MOBILE_HOVER_IMG_ALT_TEXT, mobileHoverImageAltText);		
		
		imageAltTextDetails.entrySet().stream().filter(e -> e.getValue().isEmpty()).forEach(imageAltTextDetail -> {			
			String imagekey = ImageAltTextMapping.valueOf(imageAltTextDetail.getKey().toUpperCase()).getAltTextMapping();
			String imagePath = imageDetails.get(imagekey);
			LOGGER.debug("Image Path :: {}",imagePath);
			Resource assetRes = resource.getResourceResolver().getResource(imagePath);
			if(Objects.nonNull(assetRes)) {		
				ModifiableValueMap map = resource.adaptTo(ModifiableValueMap.class);
				Asset asset = assetRes.adaptTo(Asset.class);				
				String altText = Optional.ofNullable(asset.getMetadataValue(Constants.DC_ALT_TEXT)).orElse(StringUtils.EMPTY);
				if(StringUtils.isNotBlank(altText) && StringUtils.isNotBlank(imageAltTextDetail.getKey())) {
					LOGGER.debug("Image Alt Text :: {}",altText);
					map.put(imageAltTextDetail.getKey(),altText);
				}
				try {
					resource.getResourceResolver().commit();
				} catch (PersistenceException e1) {
					LOGGER.error("Error :: {}",e1);					
				}
			}else {
				LOGGER.debug("Image Unavailable :: {}",imagePath);
			}
			
		});
		LOGGER.info("AssetMetaDataDetails -> getAssetsMetaDataDetails Method End");
	}
}
