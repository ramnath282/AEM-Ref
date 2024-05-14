package com.mattel.ecomm.coreservices.core.pojos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class DHTreatmentServiceAttributesTest {

	@InjectMocks
	private DHTreatmentServiceAttributes dhTreatmentServiceAttributes;

	@Before
	public void setUp() throws Exception {
		dhTreatmentServiceAttributes=new DHTreatmentServiceAttributes();
		dhTreatmentServiceAttributes.setComparable(true);
		dhTreatmentServiceAttributes.setDisplayable(true);
		dhTreatmentServiceAttributes.setFacetable(true);
		dhTreatmentServiceAttributes.setIdentifier("dhServiceId");
		dhTreatmentServiceAttributes.setName("name");
		dhTreatmentServiceAttributes.setSearchable(true);
		dhTreatmentServiceAttributes.setUniqueID("1234");
		dhTreatmentServiceAttributes.setStoreDisplay(true);
		dhTreatmentServiceAttributes.setUsage("descriptive");
		List<Map<String, String>> values=new ArrayList<>();
		HashMap<String, String> value = new HashMap<>();
		value.put("price", "0");
		values.add(value);
		dhTreatmentServiceAttributes.setValues(values);
	}

	@Test
	public void testGetItems() {
		Assert.assertNotNull(dhTreatmentServiceAttributes.toString());
		Assert.assertEquals("name", dhTreatmentServiceAttributes.getName());
		Assert.assertEquals(true, dhTreatmentServiceAttributes.getFacetable());
		Assert.assertEquals(true, dhTreatmentServiceAttributes.getDisplayable());
		Assert.assertEquals("dhServiceId", dhTreatmentServiceAttributes.getIdentifier());
	}

}
