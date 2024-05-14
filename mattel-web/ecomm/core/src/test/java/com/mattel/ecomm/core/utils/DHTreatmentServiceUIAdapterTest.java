package com.mattel.ecomm.core.utils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mattel.ecomm.coreservices.core.pojos.DHService;
import com.mattel.ecomm.coreservices.core.pojos.DHTreatmentServiceAttributes;
import com.mattel.ecomm.coreservices.core.pojos.Price;

public class DHTreatmentServiceUIAdapterTest {

	private DHService dhService=new DHService();
	
	@Before
	public void setUp() {
		List<DHTreatmentServiceAttributes> attributes = new ArrayList<>();
		DHTreatmentServiceAttributes serviceAttributes = new DHTreatmentServiceAttributes();
		serviceAttributes.setName("name");
		serviceAttributes.setComparable(true);
		serviceAttributes.setDisplayable(true);
		serviceAttributes.setIdentifier("123213");
		serviceAttributes.setSearchable(true);
		serviceAttributes.setSequence("1");
		serviceAttributes.setStoreDisplay(true);
		serviceAttributes.setUniqueID("123123213");
		serviceAttributes.setUsage("usage");
		serviceAttributes.setValues(null);
		attributes.add(serviceAttributes);
		dhService.setAttributes(attributes );
		dhService.setBuyable("true");
		dhService.setCatalogEntryTypeCode("124");
		dhService.setHasSingleSKU(true);
		dhService.setName("TreatmentName");
		dhService.setPartNumber("12345");
		List<Price> priceList = new ArrayList<>();
		Price price = new Price();
		price.setCurrency("2");
		price.setDescription("description");
		price.setUsage("usage");
		price.setValue("212");
		priceList.add(price);
		dhService.setResourceId("7821312");
		dhService.setShortDescription("shortDescription");
		dhService.setStoreID("10657");
		dhService.setThumbnail("thumbnail");
		dhService.setUniqueID("000");
		
	}

	@Test
	public void testTransformTreatmentServiceToSignleSKU() {
		DHTreatmentServiceUIAdapter dhTreatmentServiceUIAdapter= new DHTreatmentServiceUIAdapter();
		dhTreatmentServiceUIAdapter.transformTreatmentServiceToSignleSKU(dhService);
	}

}
