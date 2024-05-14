package com.mattel.ecomm.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.jcr.RepositoryException;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.ecomm.core.interfaces.CurrencyMappingService;
import com.mattel.ecomm.core.pojos.CurrencyMappingPojo;

@RunWith(PowerMockRunner.class)
public class CurrencyMappingServletTest {
	CurrencyMappingServlet currencyMappingServlet;
	@Mock
	SlingHttpServletRequest request;
	@Mock
	SlingHttpServletResponse response;
	@Mock
	ResourceResolver resolver;
	@Mock
	Resource resource;
	@Mock
	CurrencyMappingService currencyMappingService;
	List<CurrencyMappingPojo> currencyMapList;
	PrintWriter printWriter;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws RepositoryException, IOException {
		currencyMappingServlet = new CurrencyMappingServlet();
		currencyMapList = new LinkedList<>();
		printWriter = Mockito.mock(PrintWriter.class);
		currencyMappingServlet.setCurrencyMappingService(currencyMappingService);
		Mockito.when(request.getResourceResolver()).thenReturn(resolver);
		Mockito.when(request.getPathInfo()).thenReturn("pathInfo");
		Mockito.when(resolver.getResource(request.getPathInfo())).thenReturn(resource);
		Mockito.when(currencyMappingService.getCurrencyDetails(resource)).thenReturn(currencyMapList);
		Mockito.when(response.getWriter()).thenReturn(printWriter);
	}

	@Test
	public void doGetTest() throws IOException, ServletException {
		currencyMappingServlet.doGet(request, response);
	}
}
