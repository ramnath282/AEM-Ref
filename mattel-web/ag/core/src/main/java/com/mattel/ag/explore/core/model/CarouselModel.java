package com.mattel.ag.explore.core.model;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ag.explore.core.pojos.CarouselPojo;
import com.mattel.ag.explore.core.utils.PathUtils;
import com.mattel.ag.retail.core.services.MultifieldReader;

/**
 * @author CTS. A Model class for Carousel
 */

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CarouselModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(CarouselModel.class);
	List<CarouselPojo> carouselDetailsList = new ArrayList<>();
	CarouselPojo carouselFeaturesData = new CarouselPojo();
	@Inject
	private Node carouselDetails;
	@Inject
	private MultifieldReader multifieldReader;
	@Inject
	private Resource resource;

	private String falseValue = "false";

	public MultifieldReader getMultifieldReader() {
		return multifieldReader;
	}

	public void setMultifieldReader(MultifieldReader multifieldReader) {
		this.multifieldReader = multifieldReader;
	}

	public Node getCarouselDetails() {
		return carouselDetails;
	}

	public void setCarouselDetails(Node carouselDetails) {
		this.carouselDetails = carouselDetails;
	}

	@PostConstruct
	protected void init() {
		LOGGER.info("Carousel Model Init Start");

		if (carouselDetails != null) {
			Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(carouselDetails);
			for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
				CarouselPojo carouselPojo = new CarouselPojo();
				LOGGER.debug("Image Url{}", entry.getValue().get("imgUrl", String.class));
				carouselPojo.setImgUrl(entry.getValue().get("imgUrl", String.class));
				LOGGER.debug("Image Alternative Text{}", entry.getValue().get("imageAlttext", String.class));
				carouselPojo.setImageAlttext(entry.getValue().get("imageAlttext", String.class));
				LOGGER.debug("Description {}", entry.getValue().get("description", String.class));
				carouselPojo.setDescription(entry.getValue().get("description", String.class));
				LOGGER.debug("carouselDetails {} ", carouselDetails);
				carouselDetailsList.add(carouselPojo);
			}
		}

		if (null != resource) {
			carouselFeatures();
		}

		LOGGER.info("Carousel Model Init end");

	}

	private void carouselFeatures() {
		ValueMap valueMap = resource.getValueMap();
		CarouselPojo carouselPojo = new CarouselPojo();
		if (null != valueMap.get("slideScroll", String.class)) {
			carouselPojo.setSlideScroll(valueMap.get("slideScroll", String.class));
		} else {
			carouselPojo.setSlideScroll("1");
		}
		if (null != valueMap.get("centerMode", String.class)) {
			carouselPojo.setCenterMode(valueMap.get("centerMode", String.class));
		} else {
			carouselPojo.setCenterMode(falseValue);
		}
		if (null != valueMap.get("autoPlay", String.class)) {
			carouselPojo.setAutoPlay(valueMap.get("autoPlay", String.class));
		} else {
			carouselPojo.setAutoPlay(falseValue);
		}
		if (null != valueMap.get("arrows", String.class)) {
			carouselPojo.setArrows(valueMap.get("arrows", String.class));
		} else {
			carouselPojo.setArrows(falseValue);
		}
		if (null != valueMap.get("slideShow", String.class)) {
			carouselPojo.setSlideShow(valueMap.get("slideShow", String.class));
		} else {
			carouselPojo.setSlideShow("1");
		}
		if (null != valueMap.get("dots", String.class)) {
			carouselPojo.setDots(valueMap.get("dots", String.class));
		} else {
			carouselPojo.setDots(falseValue);
		}
		if (null != valueMap.get("seeEverythingLink", String.class)) {
			carouselPojo.setExternal(PathUtils.isExternal(valueMap.get("seeEverythingLink", String.class)));
		}
		carouselFeaturesData = carouselPojo;
		setCarouselFeaturesData(carouselPojo);
	}

	public CarouselPojo getCarouselFeaturesData() {
		return carouselFeaturesData;
	}

	public void setCarouselFeaturesData(CarouselPojo carouselFeaturesData) {
		this.carouselFeaturesData = carouselFeaturesData;
	}

	public List<CarouselPojo> getCarouselDetailsList() {
		return carouselDetailsList;
	}

	public void setCarouselDetailsList(List<CarouselPojo> carouselDetailsList) {
		this.carouselDetailsList = carouselDetailsList;
	}

}
