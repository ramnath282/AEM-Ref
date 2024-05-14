package com.mattel.ag.retail.core.model;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jcr.Node;

import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.ag.retail.core.services.MultifieldReader;
import com.mattel.ag.retail.core.utils.PropertyReaderUtils;

public class PersonalShopperModelTest {
	
	PersonalShopperModel personalShopperModel;
	
	Node locations;
	Node contactOptions;
	Node firstVisitOptions;
	MultifieldReader multifieldReader;
	PropertyReaderUtils propertyReaderUtils;
	Map<String, ValueMap> multifieldProperty;
	Set<Map.Entry<String, ValueMap>> entryset = new HashSet<>();
	Map<String, ValueMap> map = Mockito.mock(Map.class);
	Iterator<Object> iterator;
	List<Object> list;
	
	ValueMap valueMap;

	@Before
	public void setUp() {
		locations = Mockito.mock(Node.class);
		contactOptions = Mockito.mock(Node.class);
		firstVisitOptions = Mockito.mock(Node.class);
		multifieldReader = Mockito.mock(MultifieldReader.class);
		propertyReaderUtils = Mockito.mock(PropertyReaderUtils.class);
		
		personalShopperModel = new PersonalShopperModel();
		personalShopperModel.setMultifieldReader(multifieldReader);
		valueMap = Mockito.mock(ValueMap.class);
		list = Mockito.mock(List.class);
		when(list.iterator()).thenReturn(iterator);
	}
	
	@Test
	public void testGetLocationListNotNUll(){
		personalShopperModel.setLocations(locations);
		Map.Entry<String, ValueMap> entry = Mockito.mock(Map.Entry.class);
		when(entry.getValue()).thenReturn(valueMap);
		entryset.add(entry);
		when(map.entrySet()).thenReturn(entryset);
		when(multifieldReader.propertyReader(any())).thenReturn(map);
		when(entry.getValue().containsKey("location")).thenReturn(true);
		personalShopperModel.init();
	}
	
	
	@Test
	public void testGetContactsListNotNUll(){
		personalShopperModel.setContactOptions(contactOptions);
		Map.Entry<String, ValueMap> entry = Mockito.mock(Map.Entry.class);
		when(entry.getValue()).thenReturn(valueMap);
		entryset.add(entry);
		when(map.entrySet()).thenReturn(entryset);
		when(multifieldReader.propertyReader(any())).thenReturn(map);
		when(entry.getValue().containsKey("contactOption")).thenReturn(true);
		personalShopperModel.init();
	}
	
	@Test
	public void testGetFirstVisitOptionsNotNUll(){
		personalShopperModel.setFirstVisitOptions(firstVisitOptions);
		Map.Entry<String, ValueMap> entry = Mockito.mock(Map.Entry.class);
		when(entry.getValue()).thenReturn(valueMap);
		entryset.add(entry);
		when(map.entrySet()).thenReturn(entryset);
		when(multifieldReader.propertyReader(any())).thenReturn(map);
		when(entry.getValue().containsKey("firstVisitOption")).thenReturn(true);
		personalShopperModel.init();
	}
	
	@Test
	public void testAllGettes(){
		personalShopperModel.getLocationsList();
		personalShopperModel.getContactOptionList();
		personalShopperModel.getFirstVisitOptionList();
	}
	
	@Test
	public void testGetLocationListNUll(){
		personalShopperModel.setLocations(null);
		personalShopperModel.init();
	}
	@Test
	public void testGetContactsListNUll(){
		personalShopperModel.setContactOptions(null);
		personalShopperModel.init();
	}
	@Test
	public void testGetFirstVisitOptionsNUll(){
		personalShopperModel.setFirstVisitOptions(null);
		personalShopperModel.init();
	}
}
