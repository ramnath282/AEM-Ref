package com.mattel.play.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.play.core.pojos.SubNavigationPojo;
import com.mattel.play.core.services.MultifieldReader;

public class SubNavigationTest {

	SubNavigation subNavigation;
	Node subBrandList;
	MultifieldReader multifieldReader;
	Resource resource;
	List<SubNavigationPojo> subBrandsList = new ArrayList<>();
	Map<String, ValueMap> multifieldProperty = new HashMap<>();
	ValueMap valueMap;

	@Before
	public void setUp() {
		subNavigation = new SubNavigation();
		subBrandList = Mockito.mock(Node.class);
		multifieldReader = Mockito.mock(MultifieldReader.class);
		resource = Mockito.mock(Resource.class);
		valueMap = Mockito.mock(ValueMap.class);
		multifieldProperty.put("", valueMap);
		subNavigation.setMultifieldReader(multifieldReader);
		subNavigation.setResource(resource);
		subNavigation.setSubBrandList(subBrandList);
		subNavigation.setSubBrandsList(subBrandsList);
		Mockito.when(multifieldReader.propertyReader(subBrandList)).thenReturn(multifieldProperty);
	}

	@Test
	public void init() {
		subNavigation.init();
	}

	@Test
	public void getSubBrandsList() {
		subNavigation.getSubBrandsList();
	}
}
