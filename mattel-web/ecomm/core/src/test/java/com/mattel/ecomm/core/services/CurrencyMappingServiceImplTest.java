package com.mattel.ecomm.core.services;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.mattel.ecomm.coreservices.core.constants.Constant;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyMappingServiceImplTest {
	@InjectMocks
	CurrencyMappingServiceImpl currencyMappingServiceImpl;
	@Mock
	private Resource resource;
	@Mock
	private ResourceResolver resourceResolver;
	@Mock
	MultifieldReader multifieldReader;
	String currencyType;
	Node currencyNode;
	Resource currencyResource;
	Map<String, ValueMap> currencyLinkedHashMap;
	Map.Entry<String, ValueMap> mapEntry;
	ValueMap valMap;
	Object cType;
	Object cValue;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		currencyType = "USD";
		currencyResource = resource;
		currencyNode = Mockito.mock(Node.class);
		currencyLinkedHashMap = new HashMap<>();
		mapEntry = Mockito.mock(Map.Entry.class);
		valMap = Mockito.mock(ValueMap.class);
		currencyLinkedHashMap.put("", valMap);
		cType = "USD";
		cValue = "&30;";
		Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
		Mockito.when(resource.getResourceResolver().getResource(Constant.CURRENCY_MAPPING)).thenReturn(resource);
		Mockito.when(currencyResource.adaptTo(Node.class)).thenReturn(currencyNode);
		Mockito.when(multifieldReader.propertyReader(currencyNode)).thenReturn(currencyLinkedHashMap);
		Mockito.when(valMap.get("currencyType")).thenReturn(cType);
		Mockito.when(valMap.get("currencyCode")).thenReturn(cValue);

	}

	@Test
	public void getCurrencyDetails() {
		currencyMappingServiceImpl.getCurrencyDetails(currencyType, resource);
	}

	@Test
	public void getCurrencyDetailsResource() {
		currencyMappingServiceImpl.getCurrencyDetails(resource);
	}
}
