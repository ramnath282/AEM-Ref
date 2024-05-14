package com.mattel.global.core.model;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.jcr.Node;

import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mattel.global.core.services.MultifieldReader;

public class CustomerInfoModelTest {
	CustomerInfoModel customerInfoModel;
	@Mock
	private MultifieldReader multifieldReader;
	
	@Mock
	private Map<String, ValueMap> idValueMap;
	
	@Mock
	private Node secondarySubscription
	;
	@Mock
	Map.Entry<String, ValueMap> entry;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		customerInfoModel = new CustomerInfoModel();
		customerInfoModel.setSecondarySubscription(secondarySubscription);
		customerInfoModel.setMultifieldReader(multifieldReader);
		Mockito.when(multifieldReader.propertyReader(secondarySubscription)).thenReturn(idValueMap);
	}
	
	@Test
	public void testSubscriptionNodeNull(){
		Set<Entry<String, ValueMap>> entrySet = new HashSet<Entry<String, ValueMap>>();
		entrySet.add(entry);
		ValueMap valueMap = Mockito.mock(ValueMap.class);
		Mockito.when(entry.getValue()).thenReturn(valueMap);
		Mockito.when(idValueMap.entrySet()).thenReturn(entrySet);
		customerInfoModel.init();
	}

}
