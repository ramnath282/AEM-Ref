package com.mattel.ag.explore.core.servlets;

import java.io.IOException;
import java.util.Iterator;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ag.explore.core.services.GetResourceResolver;
import com.mattel.ag.explore.core.utils.SiteMapServletConfiguration;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PageFilter.class)
public class SiteMapGeneratorServletTest {
	
	SiteMapGeneratorServlet siteMapGeneratorServlet;
	SlingHttpServletRequest request; 
	SlingHttpServletResponse response;
	SiteMapServletConfiguration siteMapServletConfiguration;
	ResourceResolver resourceResolver;
	GetResourceResolver getResourceResolver;
	PageManager pageManager;
	Page page;
	Iterator<Page> pages;
	PageFilter pageFilter;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		siteMapGeneratorServlet = new SiteMapGeneratorServlet();
		request = Mockito.mock(SlingHttpServletRequest.class);
		response = Mockito.mock(SlingHttpServletResponse.class);
		siteMapServletConfiguration = Mockito.mock(SiteMapServletConfiguration.class);
		resourceResolver = Mockito.mock(ResourceResolver.class);
		getResourceResolver = Mockito.mock(GetResourceResolver.class);
		pageManager = Mockito.mock(PageManager.class);
		page = Mockito.mock(Page.class);
		pages = Mockito.mock(Iterator.class);
		PowerMockito.mockStatic(PageFilter.class);
		pageFilter = Mockito.mock(PageFilter.class);
		siteMapGeneratorServlet.setGetResourceResolver(getResourceResolver);
		siteMapGeneratorServlet.setSiteMapServletConfiguration(siteMapServletConfiguration);
		
	}
	@Test
	public void doGet() throws IOException {
		//PageFilter filter = Mockito.mock(PageFilter.class);
		Mockito.when(siteMapServletConfiguration.getMyChangeFreqProperties()).thenReturn("");
		Mockito.when(siteMapServletConfiguration.getMyPriorityProperties()).thenReturn("");
		Mockito.when(siteMapServletConfiguration.getRootPath()).thenReturn("");
		Mockito.when(siteMapServletConfiguration.getDomain()).thenReturn("");
		Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(resourceResolver);
		Mockito.when(resourceResolver.adaptTo(PageManager.class)).thenReturn(null);
		//Mockito.when(pageManager.getPage("")).thenReturn(page);
		//Mockito.when(page.listChildren(filter, true)).thenReturn(pages);
		siteMapGeneratorServlet.doGet(request, response);
	}
	
}
