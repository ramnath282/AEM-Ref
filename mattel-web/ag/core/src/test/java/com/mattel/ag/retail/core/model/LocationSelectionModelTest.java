package com.mattel.ag.retail.core.model;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.ag.retail.core.pojos.StoreDetailsPojo;
import com.mattel.ag.retail.core.services.ChildPageProperties;

/**
 * @author CTS. A Model class for Location Selector
 */

public class LocationSelectionModelTest {

	LocationSelectionModel locationSelectionModel;
	ChildPageProperties childPageProperties;
	List<StoreDetailsPojo> storeDetailsPojoList;
	List<StoreDetailsPojo> localLocations;
	List<StoreDetailsPojo> internationalLocations;
	StoreDetailsPojo storeDetailsPojo;
	StoreDetailsPojo storeDetailsPojo1;

	@Before
	public void setup() {
		
		locationSelectionModel = new LocationSelectionModel();
		childPageProperties = Mockito.mock(ChildPageProperties.class);
		storeDetailsPojoList = new ArrayList<StoreDetailsPojo>();
		localLocations = new ArrayList<StoreDetailsPojo>();
		internationalLocations = new ArrayList<StoreDetailsPojo>();
		
		when(childPageProperties.getpages()).thenReturn(storeDetailsPojoList);
		
		storeDetailsPojo = new StoreDetailsPojo();
		storeDetailsPojo.setPageUrl("Page URL");
		storeDetailsPojo.setPageTitle("Title");
		storeDetailsPojo.setLocation("Location");
		storeDetailsPojo.setInternational(true);
		storeDetailsPojoList.add(storeDetailsPojo);
		
		storeDetailsPojo = new StoreDetailsPojo();
		storeDetailsPojo.setPageUrl("Page URL");
		storeDetailsPojo.setPageTitle("Title");
		storeDetailsPojo.setLocation("Location");
		storeDetailsPojo.setInternational(true);
		storeDetailsPojoList.add(storeDetailsPojo);
		internationalLocations.add(storeDetailsPojo);
		
		storeDetailsPojo1 = new StoreDetailsPojo();
		storeDetailsPojo1.setPageUrl("Page URL");
		storeDetailsPojo1.setPageTitle("Title");
		storeDetailsPojo1.setLocation("Location");
		storeDetailsPojo1.setInternational(false);
		storeDetailsPojoList.add(storeDetailsPojo1);
		localLocations.add(storeDetailsPojo1);
		
		locationSelectionModel.setChildPageProperties(childPageProperties);
		locationSelectionModel.setInternationalLocations(internationalLocations);
		locationSelectionModel.setLocalLocations(localLocations);
		locationSelectionModel.setStoreDetailsPojos(storeDetailsPojoList);
	}

	@Test
	public void initTest() {

		locationSelectionModel.init();
		locationSelectionModel.getStoreDetailsPojos();
		locationSelectionModel.getInternationalLocations();
		locationSelectionModel.getLocalLocations();
	}

}
