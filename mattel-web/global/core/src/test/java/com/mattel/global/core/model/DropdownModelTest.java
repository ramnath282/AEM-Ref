package com.mattel.global.core.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.jcr.Node;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.mattel.global.core.services.AnalyticsDynamicDropdownService;
import com.mattel.global.core.services.MultifieldReader;

public class DropdownModelTest {

	DropdownModel dropdownModel;

	@Mock
	SlingHttpServletRequest request;

	@Mock
	private MultifieldReader multifieldReader;

	@Mock
	AnalyticsDynamicDropdownService analyticsDynamicDropdown;

	@Mock
	private Node anyNode;

	@Mock
	ResourceResolver resolver;
	
	@Mock
	Resource resource;
	
	@Mock
	Iterator<String> itr;
	
	@Before
	public void setUp() {
		//MockitoAnnotations.initMocks(this);
		analyticsDynamicDropdown = Mockito.mock(AnalyticsDynamicDropdownService.class);
		dropdownModel = new DropdownModel();
		dropdownModel.setAnalyticsDynamicDropdown(analyticsDynamicDropdown);
	}
	
	@Test
	public void testInit(){
		List<String> attrs = new LinkedList<>();
		attrs.add("locale_id");
		attrs.add("page_type");
		attrs.add("company_division");
		request =  Mockito.mock(SlingHttpServletRequest.class);
		dropdownModel.setRequest(request);
		resolver = Mockito.mock(ResourceResolver.class);
		resource = Mockito.mock(Resource.class);
		Mockito.when(request.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.getResource("TestString")).thenReturn(resource);
		Mockito.when(resource.adaptTo(Node.class)).thenReturn(anyNode);
		Mockito.when(analyticsDynamicDropdown.getAnalyticsProperties()).thenReturn(attrs);
		dropdownModel.init();
	}
	
	@Test
	public void testInitWithRequestNull(){
		dropdownModel.setRequest(null);
		dropdownModel.init();
	}

}
