package com.mattel.global.core.services;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.jcr.Node;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class AnalyticsDynamicDropdownServiceTest {

	AnalyticsDynamicDropdownService analyticsDynamicDropdownService;
	
	@Mock
	private MultifieldReader multifieldReader;
	
	@Mock
	ResourceResolver resolver;
	
	@Mock
	ResourceResolverFactory resolverFactory;
	
	@Mock
	Resource resource;
	
	@Mock
	Node analyticsPropNode;
		
	@Before
	public void setUp() {
		analyticsDynamicDropdownService = new AnalyticsDynamicDropdownService();
	}

	@Test
	public void testReadAnalyticsNodes(){
		resolverFactory =  Mockito.mock(ResourceResolverFactory.class);
		resolver =  Mockito.mock(ResourceResolver.class);
		resource = Mockito.mock(Resource.class);
		analyticsPropNode = Mockito.mock(Node.class);
		multifieldReader = Mockito.mock(MultifieldReader.class);
		ValueMap valueMap = Mockito.mock(ValueMap.class);
		Map<String, ValueMap> analyticsValueMap = new LinkedHashMap<>();
		analyticsDynamicDropdownService.setResolverFactory(resolverFactory);
		analyticsDynamicDropdownService.setAnalyticsPropertisPath("/content/fisher-price/language-masters/analytics-properties/jcr:content/root/analyticsprop/analyticsProperties");
		analyticsDynamicDropdownService.setMultifieldReader(multifieldReader);
		Map<String, Object> map = new HashMap<>();
		map.put(ResourceResolverFactory.SUBSERVICE, "readwriteservice");
		try {
			Mockito.when(resolverFactory.getServiceResourceResolver(map)).thenReturn(resolver);
			Mockito.when(resolver.getResource(Mockito.anyString())).thenReturn(resource);
			Mockito.when(resource.adaptTo(Node.class)).thenReturn(analyticsPropNode);
			Mockito.when(multifieldReader.propertyReader(analyticsPropNode)).thenReturn(analyticsValueMap);
			Map<String, ValueMap> propNodemap = new LinkedHashMap<>();
			propNodemap.put("test", valueMap);
			Mockito.when(multifieldReader.propertyReader(analyticsPropNode)).thenReturn(propNodemap);
		} catch (LoginException e) {
			e.printStackTrace();
		}
		analyticsDynamicDropdownService.getAnalyticsProperties();
	}
	
	@Test
	public void TestGetAnalyticsPropertyValue() {
		String fieldTypeValue ="page_type";
		String path ="path";
		resolverFactory =  Mockito.mock(ResourceResolverFactory.class);
		resolver =  Mockito.mock(ResourceResolver.class);
		analyticsPropNode = Mockito.mock(Node.class);
		resource = Mockito.mock(Resource.class);
		
		multifieldReader = Mockito.mock(MultifieldReader.class);
		analyticsDynamicDropdownService.setResolverFactory(resolverFactory);
		analyticsDynamicDropdownService.setMultifieldReader(multifieldReader);
		analyticsDynamicDropdownService.setAnalyticsPropertisPath("/content/fisher-price/language-masters/analytics-properties/jcr:content/root/analyticsprop/analyticsProperties");
		try {
			Map<String, Object> map = new HashMap<>();
			map.put(ResourceResolverFactory.SUBSERVICE, "readwriteservice");
			Mockito.when(resolverFactory.getServiceResourceResolver(map)).thenReturn(resolver);
			Mockito.when(resolver.getResource(Mockito.anyString())).thenReturn(resource);
			Mockito.when(resolver.resolve(path)).thenReturn(resource);
			Mockito.when(resource.adaptTo(Node.class)).thenReturn(analyticsPropNode);
			Map<String, ValueMap> propNodemap = new LinkedHashMap<>();
			Mockito.when(multifieldReader.propertyReader(analyticsPropNode)).thenReturn(propNodemap);
			
		} catch (LoginException e) {
		}
		analyticsDynamicDropdownService.getAnalyticsPropertyValue(fieldTypeValue, path);
	}
	
	@Test
	public void TestGetAnalyticsPropertyValueWithNullValues() {
		String fieldTypeValue ="page_type";
		String path ="path";
		resolverFactory =  Mockito.mock(ResourceResolverFactory.class);
		resolver =  Mockito.mock(ResourceResolver.class);
		analyticsPropNode = Mockito.mock(Node.class);
		resource = Mockito.mock(Resource.class);
		
		multifieldReader = Mockito.mock(MultifieldReader.class);
		analyticsDynamicDropdownService.setResolverFactory(null);
		try {
			Map<String, Object> map = new HashMap<>();
			map.put(ResourceResolverFactory.SUBSERVICE, "readwriteservice");
			Mockito.when(resolverFactory.getServiceResourceResolver(map)).thenReturn(resolver);
		} catch (LoginException e) {
		}
		analyticsDynamicDropdownService.getAnalyticsPropertyValue(fieldTypeValue, path);
	}
	
	@Test
	public void TestGetAnalyticsPropertyValueWithNullNode() {
		String fieldTypeValue ="page_type";
		String path ="path";
		resolverFactory =  Mockito.mock(ResourceResolverFactory.class);
		resolver =  Mockito.mock(ResourceResolver.class);
		analyticsPropNode = Mockito.mock(Node.class);
		resource = Mockito.mock(Resource.class);
		
		multifieldReader = Mockito.mock(MultifieldReader.class);
		analyticsDynamicDropdownService.setResolverFactory(resolverFactory);
		analyticsDynamicDropdownService.setMultifieldReader(multifieldReader);
		analyticsDynamicDropdownService.setAnalyticsPropertisPath("/content/fisher-price/language-masters/analytics-properties/jcr:content/root/analyticsprop/analyticsProperties");
		try {
			Map<String, Object> map = new HashMap<>();
			map.put(ResourceResolverFactory.SUBSERVICE, "readwriteservice");
			Mockito.when(resolverFactory.getServiceResourceResolver(map)).thenReturn(resolver);
			Mockito.when(resolver.getResource(Mockito.anyString())).thenReturn(resource);
			Mockito.when(resolver.resolve(path)).thenReturn(resource);
			Mockito.when(resource.adaptTo(Node.class)).thenReturn(null);
			Map<String, ValueMap> propNodemap = new LinkedHashMap<>();
			Mockito.when(multifieldReader.propertyReader(analyticsPropNode)).thenReturn(propNodemap);
			
		} catch (LoginException e) {
		}
		analyticsDynamicDropdownService.getAnalyticsPropertyValue(fieldTypeValue, path);
	}

}
