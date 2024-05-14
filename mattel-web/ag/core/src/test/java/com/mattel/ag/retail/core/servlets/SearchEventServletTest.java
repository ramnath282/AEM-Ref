package com.mattel.ag.retail.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.search.PredicateGroup;
import com.mattel.ag.retail.core.services.SearchEvents;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PredicateGroup.class)
public class SearchEventServletTest {

	SearchEvents searchEvents;
	SearchEventServlet searchEventServlet;
	SlingHttpServletRequest request;
	SlingHttpServletResponse response;

	@Before
	public void setUp() {
		searchEventServlet = new SearchEventServlet();
		searchEvents = Mockito.mock(SearchEvents.class);
		searchEventServlet.setSearchEvents(searchEvents);
	}

	@Test
	public void testDoGetLocations() throws ServletException, IOException {
		request = Mockito.mock(SlingHttpServletRequest.class);
		response = Mockito.mock(SlingHttpServletResponse.class);
		RequestPathInfo requestPathInfo = Mockito.mock(RequestPathInfo.class);
		PrintWriter printWriter = Mockito.mock(PrintWriter.class);
		Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		Mockito.when(requestPathInfo.getSelectorString()).thenReturn("locations");
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		searchEventServlet.doGet(request, response);
	}

	@Test
	public void testDoGetEventId() throws ServletException, IOException {
		request = Mockito.mock(SlingHttpServletRequest.class);
		response = Mockito.mock(SlingHttpServletResponse.class);
		RequestPathInfo requestPathInfo = Mockito.mock(RequestPathInfo.class);
		PrintWriter printWriter = Mockito.mock(PrintWriter.class);
		Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		Mockito.when(requestPathInfo.getSelectorString()).thenReturn("eventid");
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		searchEventServlet.doGet(request, response);
	}

}
