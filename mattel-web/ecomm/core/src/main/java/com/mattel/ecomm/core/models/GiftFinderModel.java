package com.mattel.ecomm.core.models;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.core.pojos.GiftFinderPojo;
import com.mattel.ecomm.core.services.MultifieldReader;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class GiftFinderModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(GiftFinderModel.class);
	@Inject
	Resource resource;

	@Inject
	@Optional
	private Node ageFilterDetails;

	@Inject
	@Optional
	private Node priceFilterDetails;
	@Inject
	MultifieldReader multifieldReader;
	private List<GiftFinderPojo> ageDetailsList = new LinkedList<>();
	private List<GiftFinderPojo> priceDetailsList = new LinkedList<>();

	/**
	 * The init method to fetch the list of Retailer Details.
	 */
	@PostConstruct
	protected void init() {
		LOGGER.debug("GiftFinderModel init method ---> Start");
		if (null != resource) {
			checkAgenPriceDetails();

		}
		LOGGER.debug("GiftFinderModel init method ---> End");
	}

	private void checkAgenPriceDetails() {
		try {
			if (null != ageFilterDetails) {
				ageDetailsList = fetchFilterDetails(ageFilterDetails);
			}
			if (null != priceFilterDetails) {
				priceDetailsList = fetchFilterDetails(priceFilterDetails);
			}
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("UnsupportedEncodingException Occurred {}", e);
		}

	}

	private List<GiftFinderPojo> fetchFilterDetails(Node filterDetails) throws UnsupportedEncodingException {
		LOGGER.debug("fetchFilterDetails method ---> Start");
		List<GiftFinderPojo> filterDetailsList = new LinkedList<>();
		Map<String, ValueMap> multifieldProperty;
		multifieldProperty = multifieldReader.propertyReader(filterDetails);
		for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
			GiftFinderPojo filterDetailPojo = new GiftFinderPojo();
			filterDetailPojo.setLabel(entry.getValue().get("labelText", String.class));
			filterDetailPojo.setValue(URLEncoder.encode(entry.getValue().get("labelValue", String.class), "UTF-8"));
			filterDetailsList.add(filterDetailPojo);
		}
		LOGGER.debug("fetchFilterDetails method ---> End");
		return filterDetailsList;
	}

	public List<GiftFinderPojo> getAgeDetailsList() {
		return ageDetailsList;
	}

	public void setAgeDetailsList(List<GiftFinderPojo> ageDetailsList) {
		this.ageDetailsList = ageDetailsList;
	}

	public List<GiftFinderPojo> getPriceDetailsList() {
		return priceDetailsList;
	}

	public void setPriceDetailsList(List<GiftFinderPojo> priceDetailsList) {
		this.priceDetailsList = priceDetailsList;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public void setAgeFilterDetails(Node ageFilterDetails) {
		this.ageFilterDetails = ageFilterDetails;
	}

	public void setPriceFilterDetails(Node priceFilterDetails) {
		this.priceFilterDetails = priceFilterDetails;
	}

	public void setMultifieldReader(MultifieldReader multifieldReader) {
		this.multifieldReader = multifieldReader;
	}
}
