package com.mattel.global.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.core.pojo.CountryDropdownPojo;
import com.mattel.global.core.services.MultifieldReader;

@Model(adaptables = Resource.class)
public class CountryDropdownModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(CountryDropdownModel.class);

	@Inject
	@Optional
	private MultifieldReader multifieldReader;

	@Inject
	@Optional
	private Node countryDetails;

	private List<CountryDropdownPojo> countryDropdownList;

	/**
	 * init method.
	 */
	@PostConstruct
	protected void init() {
		LOGGER.info("Init Method - Start");
		if (countryDetails != null) {
			countryDropdownList = listOfCounties();
			LOGGER.debug("Calling Comparator for sorting");
			countryDropdownList.sort((c1, c2) -> c1.getCountryName().compareTo(c2.getCountryName()));
		}
		LOGGER.info("Init Method - End");
	}

	private List<CountryDropdownPojo> listOfCounties() {
		LOGGER.info("listOfCounties Method -> Start");
		Map<String, ValueMap> brandsMap = multifieldReader.propertyReader(countryDetails);
		List<CountryDropdownPojo> countyDropdownDetails = new ArrayList<>();
		for (Map.Entry<String, ValueMap> entry : brandsMap.entrySet()) {
			CountryDropdownPojo countryDropdownPojo = new CountryDropdownPojo();
			countryDropdownPojo.setCountryName(entry.getValue().get("countryName", String.class));
			countryDropdownPojo.setCountrySiteUrl(entry.getValue().get("countrySiteUrl", String.class));
			LOGGER.debug("Pojo of Country Dropdown Item {}", countryDropdownPojo);
			countyDropdownDetails.add(countryDropdownPojo);
		}
		LOGGER.info("listOfCounties Method -> End");
		return countyDropdownDetails;
	}

	public List<CountryDropdownPojo> getCountryDropdownList() {
		return countryDropdownList;
	}

	public void setCountryDetails(Node countryDetails) {
		this.countryDetails = countryDetails;
	}

	public void setCountryDropdownList(List<CountryDropdownPojo> countryDropdownList) {
		this.countryDropdownList = countryDropdownList;
	}

	public void setMultifieldReader(MultifieldReader multifieldReader) {
		this.multifieldReader = multifieldReader;
	}

}
