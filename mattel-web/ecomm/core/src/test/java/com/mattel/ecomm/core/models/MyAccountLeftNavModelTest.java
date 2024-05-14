package com.mattel.ecomm.core.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.jcr.Node;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ecomm.core.pojos.LeftNavLinkPojo;
import com.mattel.ecomm.core.services.MultifieldReader;

public class MyAccountLeftNavModelTest {
	SlingHttpServletRequest request;
	MyAccountLeftNavModel myAccountLeftNavModel;
	Node accountDetailsList;
	Node orderDetailsList;
	MultifieldReader multifieldReader;
	List<LeftNavLinkPojo> accountDetailList = new ArrayList<>();
	List<LeftNavLinkPojo> orderDetailList = new ArrayList<>();
	List<LeftNavLinkPojo> tempLinkList = new ArrayList<>();
	Map<String, ValueMap> multifieldProperty;
	Resource resource;
	LeftNavLinkPojo leftNavLinkPojo;
	LeftNavLinkPojo leftNavLink;
	ResourceResolver resourceResolver;
	String pageLink;
	PageManager pageManager;
	ValueMap valueMap;
	Resource currentResource;
	Page currentPage;
	String navigationTitle = "";
	String pageName = "ag";
	String currentPageLink;
	Map.Entry<String, ValueMap> entry;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		resource = Mockito.mock(Resource.class);
		currentResource = Mockito.mock(Resource.class);
		request = Mockito.mock(SlingHttpServletRequest.class);
		resourceResolver = Mockito.mock(ResourceResolver.class);
		leftNavLink = new LeftNavLinkPojo();
		currentPage = Mockito.mock(Page.class);
		myAccountLeftNavModel = new MyAccountLeftNavModel();
		accountDetailsList = Mockito.mock(Node.class);
		orderDetailsList = Mockito.mock(Node.class);
		multifieldReader = Mockito.mock(MultifieldReader.class);
		valueMap = Mockito.mock(ValueMap.class);
		pageManager = Mockito.mock(PageManager.class);
		entry = Mockito.mock(Entry.class);
		multifieldProperty = new HashMap<>();
		multifieldProperty.put("page", valueMap);
		multifieldProperty.put("page", valueMap);
		multifieldProperty.put("page", valueMap);
		leftNavLinkPojo = new LeftNavLinkPojo();
		pageLink = "/content/ag/shop";
		currentPageLink=pageLink;
		myAccountLeftNavModel.setMultifieldReader(multifieldReader);
		myAccountLeftNavModel.setAccountDetailsList(accountDetailsList);
		myAccountLeftNavModel.setOrderDetailsList(orderDetailsList);
		myAccountLeftNavModel.setAccountDetailList(accountDetailList);
		myAccountLeftNavModel.setOrderDetailList(orderDetailList);
		myAccountLeftNavModel.setResource(currentResource);
		myAccountLeftNavModel.setRequest(request);
		leftNavLink.setCurrentPage(false);
		leftNavLink.setPageLink(pageLink);
		leftNavLink.setPageName(pageName);
		
		entry.setValue(valueMap);
		Mockito.when(request.getPathInfo()).thenReturn(pageLink);
		Mockito.when(multifieldReader.propertyReader(accountDetailsList)).thenReturn(multifieldProperty);
		Mockito.when(multifieldReader.propertyReader(orderDetailsList)).thenReturn(multifieldProperty);
		Mockito.when(resourceResolver.getResource("/content/pageLink")).thenReturn(currentResource);
		Mockito.when(currentResource.getResourceResolver()).thenReturn(resourceResolver);
		Mockito.when(currentResource.getResourceResolver().adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(pageManager.getPage("/content/pageLink")).thenReturn(currentPage);
		Mockito.when(currentPage.getTitle()).thenReturn(navigationTitle);
		Mockito.when(entry.getValue()).thenReturn(valueMap);
		Mockito.when(entry.getValue().get("pageTitle", String.class)).thenReturn("pageTitle");
		Mockito.when(entry.getValue().get("pageLink", String.class)).thenReturn("/content/pageLink");
		Mockito.when(request.getResourceResolver()).thenReturn(resourceResolver);
		
		Mockito.when(currentPage.getNavigationTitle()).thenReturn(navigationTitle);
		Mockito.when(resourceResolver.map("/content/pageLink")).thenReturn("/content/pageLink");
		
	}

	@Test
	public void init() {
		myAccountLeftNavModel.init();
	}

	@Test
	public void getAccountDetailList() {
		myAccountLeftNavModel.getAccountDetailList();
	}

	@Test
	public void getOrderDetailList() {
		myAccountLeftNavModel.getOrderDetailList();
	}

}
