package com.mattel.ecomm.coreservices.core.pojos;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class DHServiceTest {

	@InjectMocks
	private DHService dhService;

	@Before
	public void setUp() throws Exception {
		dhService = new DHService();
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

		dhService.setAttributes(attributes);
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
		dhService.setPrice(priceList);
		dhService.setResourceId("7821312");
		dhService.setShortDescription("shortDescription");
		dhService.setStoreID("10657");
		dhService.setThumbnail("thumbnail");
		dhService.setUniqueID("000");
	}

	@Test
	public void testGetItems() {
		Assert.assertEquals("true", dhService.getBuyable());
		Assert.assertNotNull(dhService.getAttributes());
		Assert.assertEquals("124", dhService.getCatalogEntryTypeCode());
		Assert.assertEquals(true, dhService.getHasSingleSKU());
		Assert.assertEquals("TreatmentName", dhService.getName());
		Assert.assertEquals("12345", dhService.getPartNumber());
		Assert.assertNotNull(dhService.getPrice());
		Assert.assertEquals("7821312", dhService.getResourceId());
		Assert.assertEquals("shortDescription", dhService.getShortDescription());
		Assert.assertEquals("10657", dhService.getStoreID());
		Assert.assertEquals("thumbnail", dhService.getThumbnail());
		Assert.assertEquals("000", dhService.getUniqueID());
	}

}
