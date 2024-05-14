package com.mattel.ag.retail.core.model;

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

import com.mattel.ag.retail.core.pojos.PersonalShopperFormPojo;
import com.mattel.ag.retail.core.services.MultifieldReader;
import com.mattel.ag.retail.core.utils.PropertyReaderUtils;

/**
 * @author CTS. A Model class for Footer Links
 */

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class PersonalShopperModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonalShopperModel.class);
	List<PersonalShopperFormPojo> locationsList = new ArrayList<>();
	List<PersonalShopperFormPojo> contactOptionList = new ArrayList<>();
	List<PersonalShopperFormPojo> firstVisitOptionList = new ArrayList<>();
	@Inject
	private Node locations;
	@Inject
	private Node contactOptions;
	@Inject
	private Node firstVisitOptions;
	@Inject
	private MultifieldReader multifieldReader;
	@Inject
	private PropertyReaderUtils propertyReaderUtils;

	@PostConstruct
	protected void init() {

		LOGGER.info("Personal Shopper Model Start");
		if (locations != null) {
			locationsList = getLocationList(locations);
		}
		if (contactOptions != null) {
			contactOptionList = getContactsList(contactOptions);
		}
		if (firstVisitOptions != null) {
			firstVisitOptionList = getVisitList(firstVisitOptions);
		}
		LOGGER.info("Personal Shopper Model End");
	}

	private List<PersonalShopperFormPojo> getVisitList(Node firstVisitOptions) {
		String firstVisitOption = "firstVisitOption";
		Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(firstVisitOptions);
		for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
			PersonalShopperFormPojo personalShopperFormPojo = new PersonalShopperFormPojo();
			if (entry.getValue().containsKey(firstVisitOption)) {
				LOGGER.debug("First Visit Option{}", entry.getValue().get(firstVisitOption, String.class));
				personalShopperFormPojo.setFirstVisitOption(entry.getValue().get(firstVisitOption, String.class));
				firstVisitOptionList.add(personalShopperFormPojo);
			}
			LOGGER.debug("firstVisitOptionList{} ", firstVisitOptionList);

		}
		return firstVisitOptionList;
	}

	private List<PersonalShopperFormPojo> getContactsList(Node contactOptions) {
		String contactOption = "contactOption";
		Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(contactOptions);
		for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
			PersonalShopperFormPojo personalShopperFormPojo = new PersonalShopperFormPojo();
			if (entry.getValue().containsKey(contactOption)) {
				LOGGER.debug("Contact Option{}", entry.getValue().get(contactOption, String.class));
				personalShopperFormPojo.setContactOption(entry.getValue().get(contactOption, String.class));
				contactOptionList.add(personalShopperFormPojo);
			}
			LOGGER.debug("contactOptionList{} ", contactOptionList);
		}
		return contactOptionList;
	}

	private List<PersonalShopperFormPojo> getLocationList(Node locations) {
		String location = "location";
		Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(locations);
		for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
			PersonalShopperFormPojo personalShopperFormPojo = new PersonalShopperFormPojo();
			if (entry.getValue().containsKey(location)) {
				LOGGER.debug("Location{}", entry.getValue().get(location, String.class));
				personalShopperFormPojo.setLocation(entry.getValue().get(location, String.class));
				locationsList.add(personalShopperFormPojo);
			}
			LOGGER.debug("locationsList{} ", locationsList);
		}
		return locationsList;
	}

	/**
	 * @return This method return list of PersonalShopperFormPojo
	 */
	public List<PersonalShopperFormPojo> getLocationsList() {
		return locationsList;
	}

	public List<PersonalShopperFormPojo> getContactOptionList() {
		return contactOptionList;
	}

	public List<PersonalShopperFormPojo> getFirstVisitOptionList() {
		return firstVisitOptionList;
	}

	public String getPersonalShopperFormBaseURL() {
		return propertyReaderUtils.getPersonalShopperBaseURL();
	}

	public void setLocations(Node locations) {
		this.locations = locations;
	}

	public void setContactOptions(Node contactOptions) {
		this.contactOptions = contactOptions;
	}

	public void setFirstVisitOptions(Node firstVisitOptions) {
		this.firstVisitOptions = firstVisitOptions;
	}

	public void setMultifieldReader(MultifieldReader multifieldReader) {
		this.multifieldReader = multifieldReader;
	}
}
