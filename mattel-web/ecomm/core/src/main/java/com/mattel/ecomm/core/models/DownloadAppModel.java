package com.mattel.ecomm.core.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.core.pojos.DownloadAppPojo;
import com.mattel.ecomm.core.services.MultifieldReader;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DownloadAppModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(DownloadAppModel.class);
	@Inject
	@Optional
	private MultifieldReader multifieldReader;

	@Inject
	@Optional
	private Node downloadImage;

	@Self
	Resource resource;

	List<DownloadAppPojo> downloadImageGalleryList;

	@PostConstruct
	protected void init() {
		LOGGER.debug("init method of DownloadAppModel start");
		if (downloadImage != null && null != resource && !resource.getPath().contains("/conf/")) {
			downloadImageGalleryList = new ArrayList<>();
			Map<String, ValueMap> multifieldProperty;
			multifieldProperty = multifieldReader.propertyReader(downloadImage);
			for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
				DownloadAppPojo downloadfieldList = new DownloadAppPojo();
				downloadfieldList.setThumbnailImage(entry.getValue().get("thumbnailImage", String.class));
				downloadfieldList.setThumbnailTitle(entry.getValue().get("thumbnailTitle", String.class));
				downloadfieldList.setThumbnailDescription(entry.getValue().get("thumbnailDescription", String.class));
				downloadfieldList.setAltTextThumbnail(entry.getValue().get("altTexTthumbnail", String.class));
				downloadfieldList.setDownloadCtaLabel(entry.getValue().get("downloadCtaLabel", String.class));
				downloadfieldList.setAlwaysEnglish(entry.getValue().get("alwaysEnglish", String.class));
				downloadfieldList
						.setInterstitialAndroidUrl(entry.getValue().get("interstitialAndroidUrl", String.class));
				downloadfieldList.setInterstitialIosUrl(entry.getValue().get("interstitialIosUrl", String.class));

				String openCtaLink = entry.getValue().get("openCtaLinksIn", String.class);
				LOGGER.debug("openCtaLink value of DownloadAppModel is {}", openCtaLink);
				downloadImageGalleryList.add(downloadfieldList);
				LOGGER.debug("downloadImageGalleryList size of DownloadImageGalleryModel is {}",
						downloadImageGalleryList.size());
			}

		}
		LOGGER.debug("init method of DownloadAppModel end");
	}

	public void setMultifieldReader(MultifieldReader multifieldReader) {
		this.multifieldReader = multifieldReader;
	}

	public void setDownloadImage(Node downloadImage) {
		this.downloadImage = downloadImage;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public List<DownloadAppPojo> getDownloadImageGalleryList() {
		return downloadImageGalleryList;
	}

	public void setDownloadImageGalleryList(List<DownloadAppPojo> downloadImageGalleryList) {
		this.downloadImageGalleryList = downloadImageGalleryList;
	}

}
