package com.mattel.ecomm.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.jcr.RepositoryException;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.wcm.api.Page;
import com.mattel.ecomm.core.interfaces.CategoryNavigationService;
import com.mattel.ecomm.core.interfaces.ContentNavigationService;
import com.mattel.ecomm.core.utils.EcommConfigurationUtils;

@RunWith(PowerMockRunner.class)
public class SiteNavigationServletTest {
	SiteNavigationServlet siteNavigationServlet;
	@Mock
	SlingHttpServletRequest request;
	@Mock
	SlingHttpServletResponse response;
	@Mock
	EcommConfigurationUtils ecommConfigurationUtils;
	@Mock
	ResourceResolver resolver;
	@Mock
	Resource resource;
	@Mock
	Page _page;
	@Mock
	CategoryNavigationService categoryNavService;
	@Mock
	ContentNavigationService contentNavService;
	String currentNodePath;
	Iterator<Resource> childResources;
	Resource childResource;
	String device = "mobile";
	JSONObject tempObj;
	PrintWriter printWriter;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp()
			throws RepositoryException, JSONException, IllegalArgumentException, IllegalAccessException, IOException {
		siteNavigationServlet = new SiteNavigationServlet();
		Mockito.when(request.getParameter("currentPath"))
				.thenReturn("/content/experience-fragments/fisher-price/en/header/master/jcr:content/root/siteheader");
		currentNodePath = "/content/experience-fragments/fisher-price/en/header/master/jcr:content/root/siteheader/navLinks";
		childResources = Mockito.mock(Iterator.class);
		childResource = resource;
		tempObj = Mockito.mock(JSONObject.class);
		printWriter = Mockito.mock(PrintWriter.class);
		siteNavigationServlet.setCategoryNavService(categoryNavService);
		siteNavigationServlet.setContentNavService(contentNavService);
		Mockito.when(request.getParameter("deviceType")).thenReturn(device);
		Mockito.when(request.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.getResource(currentNodePath)).thenReturn(resource);
		Mockito.when(resource.listChildren()).thenReturn(childResources);
		Mockito.when(childResources.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);
		Mockito.when(childResources.next()).thenReturn(childResource);
		Mockito.when(childResource.getResourceType())
				.thenReturn("mattel/ecomm/fisher-price/components/content/categoryNavigation")
				.thenReturn("mattel/ecomm/fisher-price/components/content/contentNavigation");
		Mockito.when(categoryNavService.processNavLinks(device, childResource, request)).thenReturn(tempObj);
		Mockito.when(contentNavService.processNavLinks(device, childResource)).thenReturn(tempObj);
		Mockito.when(response.getWriter()).thenReturn(printWriter);
	}

	@Test
	public void doGetTest() throws IOException, ServletException {
		siteNavigationServlet.doGet(request, response);
	}
}
