package com.mattel.global.core.model;

import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mattel.global.core.services.MultifieldReader;

@Model(adaptables = Resource.class)
public class CountryDropdownModelTest {
	CountryDropdownModel countryDropdownModel;

	@Mock
	private MultifieldReader multifieldReader;

	@Mock
	private Node countryDetails;

	@Mock
	private Map<String, ValueMap> countryValueMap;

	@Mock
	Map.Entry<String, ValueMap> entry;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		countryDropdownModel = new CountryDropdownModel();
		countryDropdownModel.setCountryDetails(countryDetails);
		countryDropdownModel.setCountryDropdownList(null);
		countryDropdownModel.setMultifieldReader(multifieldReader);
		Mockito.when(multifieldReader.propertyReader(countryDetails)).thenReturn(countryValueMap);
	}

	@Test
	public void testNotNullCountryListsWhenCountryDetailsIsNotNull() throws Exception {
		Set<Entry<String, ValueMap>> entrySet = new HashSet<Entry<String, ValueMap>>();
		entrySet.add(entry);
		ValueMap valueMap = Mockito.mock(ValueMap.class);
		Mockito.when(entry.getValue()).thenReturn(valueMap);
		Mockito.when(countryValueMap.entrySet()).thenReturn(entrySet);
		countryDropdownModel.init();
		assertNotNull(countryDropdownModel.getCountryDropdownList());
	}
}
