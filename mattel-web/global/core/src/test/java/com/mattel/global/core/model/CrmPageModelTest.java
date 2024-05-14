package com.mattel.global.core.model;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;

import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.google.gson.JsonObject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;


import com.mattel.global.core.services.MultifieldReader;
import com.mattel.global.core.utils.PropertyReaderUtils;

@RunWith(PowerMockRunner.class)
public class CrmPageModelTest {
	@InjectMocks
	CrmPageModel crmPageModel;
	@Mock
	MultifieldReader multifieldReader;
	@Mock
	PropertyReaderUtils propertyReaderUtils;
	@Mock
	Resource resource;
	@Mock
	Resource analyticsNodeResource;
	@Mock
	Node analyticsNode; 
	@Mock
	HierarchyNodeInheritanceValueMap inheritanceValueMap;
	
	ValueMap valueMap;
	
	private String scriptUrl = "https://script/url/";
	private String categoryName = "categoryName";
	String pathOfResource="";  

	@Before
	public void setup() throws IllegalArgumentException, IllegalAccessException {
		MockitoAnnotations.initMocks(this);
		crmPageModel = new CrmPageModel();
		crmPageModel.setMultifieldReader(multifieldReader);
		crmPageModel.setPropertyReaderUtils(propertyReaderUtils);
		crmPageModel.setResource(resource);
		crmPageModel.setScriptUrl("");
		crmPageModel.setPropertiesJson("");
		crmPageModel.setCategoryName(categoryName);
		Mockito.when(resource.getPath()).thenReturn(pathOfResource);
		ResourceResolver resourceResolver= Mockito.mock(ResourceResolver.class);
		Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
		Mockito.when(resource.getResourceResolver().getResource(pathOfResource+"/analyticsProperties")).thenReturn(analyticsNodeResource);
		Mockito.when(analyticsNodeResource.adaptTo(Node.class)).thenReturn(analyticsNode);
		Map<String, ValueMap> stringValueMapLinkedHashMap = new HashMap<>();
		valueMap = new ValueMapDecorator(new HashMap<>());
		valueMap.put("propertyName", "propertyName");
		valueMap.put("propertyValue", "propertyValue");
		stringValueMapLinkedHashMap.put("valueMap", valueMap);
		Mockito.when(resource.adaptTo(Node.class)).thenReturn(analyticsNode);
		Mockito.when(multifieldReader.propertyReader(analyticsNode)).thenReturn(stringValueMapLinkedHashMap);
		Mockito.when(propertyReaderUtils.getScriptUrl()).thenReturn(scriptUrl);
	    Mockito.when(new HierarchyNodeInheritanceValueMap(resource)).thenReturn(inheritanceValueMap);
		Mockito.when(inheritanceValueMap.getInherited("categoryName", String.class)).thenReturn(categoryName);
	}

	@Test
	public void init() throws Exception {
		crmPageModel.init();
		crmPageModel.getCategoryName();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("propertyName", "propertyValue");
        String propertiesJson = jsonObject.toString();
		assertEquals(scriptUrl, crmPageModel.getScriptUrl());
		assertEquals(propertiesJson, crmPageModel.getPropertiesJson());
	}

	@Test
	public void nullChecks() throws Exception {
		valueMap.put("propertyName", null);
		crmPageModel.init();	
		valueMap.put("propertyName", "propertyName");	
		valueMap.put("propertyValue", null);
		crmPageModel.init();		
		Mockito.when(analyticsNodeResource.adaptTo(Node.class)).thenReturn(null);
		crmPageModel.init();		
		Mockito.when(resource.getResourceResolver().getResource(pathOfResource+"/analyticsProperties")).thenReturn(null);
		crmPageModel.init();
		assertEquals(scriptUrl, crmPageModel.getScriptUrl());
		crmPageModel.setResource(null);
		crmPageModel.init();
	}

}
