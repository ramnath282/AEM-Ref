package com.mattel.play.core.commerce;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.adobe.cq.commerce.api.CommerceException;
import com.adobe.cq.commerce.api.CommerceQuery;
import com.adobe.cq.commerce.common.ServiceContext;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;


public class PlayCommerceServiceImplTest {
	PlayCommerceServiceImpl playCommerceServiceImpl;
	ServiceContext service;
	Resource resource;
	SlingHttpServletRequest request;
    SlingHttpServletResponse response;
    CommerceQuery cQuery;
    ResourceResolver resolver;
    PageManager pageManager;
    Page productPage;
	@Before
	public void setUp() {
		service = Mockito.mock(ServiceContext.class);
		resource = Mockito.mock(Resource.class);
		resolver = Mockito.mock(ResourceResolver.class);
		request = Mockito.mock(SlingHttpServletRequest.class);
		response = Mockito.mock(SlingHttpServletResponse.class);
		cQuery = Mockito.mock(CommerceQuery.class);
		pageManager = Mockito.mock(PageManager.class);
		productPage = Mockito.mock(Page.class);
		playCommerceServiceImpl = new PlayCommerceServiceImpl(service, resource);
		playCommerceServiceImpl.setResource(resource);
		playCommerceServiceImpl.setResolver(resolver);
		Mockito.when(resolver.getResource("")).thenReturn(resource);
		Mockito.when(resource.isResourceType("commerce/components/product")).thenReturn(true);
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(pageManager.getContainingPage(resource)).thenReturn(productPage);
	}
	@Test
	public void getCountries() throws CommerceException {
		playCommerceServiceImpl.getCountries();
    }
	@Test
    public void getCreditCardTypes() throws CommerceException  {
		playCommerceServiceImpl.getCreditCardTypes();
    }
	@Test
    public void getVoucher() throws CommerceException  {
		playCommerceServiceImpl.getVoucher("");
    }
	@Test
    public void search() throws CommerceException  {
		playCommerceServiceImpl.search(cQuery);
    }
	@Test
    public void getOrderPredicates() throws CommerceException  {
		playCommerceServiceImpl.getOrderPredicates();
    }
	@Test
    public void isAvailable()
    {
		playCommerceServiceImpl.isAvailable("");
    }
	@Test
	public void getProduct() throws CommerceException {
		playCommerceServiceImpl.getProduct("");
	}
	
	@Test
	public void testGetAvailableShippingMethods() throws CommerceException {
		playCommerceServiceImpl.getAvailableShippingMethods();
	}
	
	@Test
    public void testGetAvailablePaymentMethods() throws CommerceException {
        playCommerceServiceImpl.getAvailablePaymentMethods();
    }
}
