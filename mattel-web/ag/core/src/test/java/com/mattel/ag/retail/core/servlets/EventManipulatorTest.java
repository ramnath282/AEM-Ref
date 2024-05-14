package com.mattel.ag.retail.core.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;

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
import com.mattel.ag.retail.core.pojos.EventPojo;
import com.mattel.ag.retail.core.services.EventsToolService;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PredicateGroup.class)
public class EventManipulatorTest {
	
	EventsToolService eventsToolService;
	EventManipulator eventManipulator;
	SlingHttpServletRequest request;
	SlingHttpServletResponse response;
	
	
	@Before
	public void setUp() {
		eventManipulator = new EventManipulator();
		eventsToolService = Mockito.mock(EventsToolService.class);
		eventManipulator.setEventsToolService(eventsToolService);
	}
	
	@Test
	public void testDoGetLocations() throws ServletException, IOException{
		request = Mockito.mock(SlingHttpServletRequest.class);
		response = Mockito.mock(SlingHttpServletResponse.class);
		RequestPathInfo requestPathInfo = Mockito.mock(RequestPathInfo.class); 
		PrintWriter printWriter = Mockito.mock(PrintWriter.class);
		Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		Mockito.when(requestPathInfo.getSelectorString()).thenReturn("locations");
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		eventManipulator.doGet(request, response);
	}
	
	@Test
	public void testDoGetEventId() throws ServletException, IOException{
		request = Mockito.mock(SlingHttpServletRequest.class);
		response = Mockito.mock(SlingHttpServletResponse.class);
		RequestPathInfo requestPathInfo = Mockito.mock(RequestPathInfo.class); 
		PrintWriter printWriter = Mockito.mock(PrintWriter.class);
		Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		Mockito.when(requestPathInfo.getSelectorString()).thenReturn("eventid");
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		eventManipulator.doGet(request, response);
	}
	
	@Test
	public void testDoPostCreateOrUpdate() throws ServletException, IOException{
		request = Mockito.mock(SlingHttpServletRequest.class);
		response = Mockito.mock(SlingHttpServletResponse.class);
		EventPojo eventPojo = Mockito.mock(EventPojo.class);
		String test = "{'eventId':'11','eventTitle':'DVSADVSDV','eventDescription':'dcfSAC','reservationRequired':false,'minAge':'0','keywords':'KEY1, KEY11, jhakds, adsda, fewfew, wefwqefe, FQFsw','locationDetails':[{'locationDateDetails':[{'eventDate':'01-01-2019','scheduleDescription':'','additionalDateInfo':'','eventStartTime':'04:00:am','eventEndTime':'07:00:am'}],'storeName':'denver','storeTag':'ag:retail/storesList/denver','zomatoURL':'','pricingAmount':'','pricingOption':'person','gratuityRequired':false}]}";
		Reader inputString = new StringReader(test);
		BufferedReader bufferedReader = new BufferedReader(inputString);
		RequestPathInfo requestPathInfo = Mockito.mock(RequestPathInfo.class); 
		PrintWriter printWriter = Mockito.mock(PrintWriter.class);
		Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		Mockito.when(requestPathInfo.getSelectorString()).thenReturn("create");
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		Mockito.when(request.getReader()).thenReturn(bufferedReader);
		Mockito.when(eventsToolService.createEvent(eventPojo, "create")).thenReturn("11");
		eventManipulator.doPost(request, response);
	}
	
	@Test
	public void testDoPostDataToUpdate() throws ServletException, IOException{
		request = Mockito.mock(SlingHttpServletRequest.class);
		response = Mockito.mock(SlingHttpServletResponse.class);
		String test = "{ 'eventId' : '11' } ";
		Reader inputString = new StringReader(test);
		BufferedReader bufferedReader = new BufferedReader(inputString);
		RequestPathInfo requestPathInfo = Mockito.mock(RequestPathInfo.class); 
		PrintWriter printWriter = Mockito.mock(PrintWriter.class);
		Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		Mockito.when(requestPathInfo.getSelectorString()).thenReturn("datatoupdate");
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		Mockito.when(request.getReader()).thenReturn(bufferedReader);
		eventManipulator.doPost(request, response);
	}
	
	@Test
	public void testDoPostDelete() throws ServletException, IOException{
		request = Mockito.mock(SlingHttpServletRequest.class);
		response = Mockito.mock(SlingHttpServletResponse.class);
		String test = "{ 'eventId' : '11' } ";
		Reader inputString = new StringReader(test);
		BufferedReader bufferedReader = new BufferedReader(inputString);
		RequestPathInfo requestPathInfo = Mockito.mock(RequestPathInfo.class); 
		PrintWriter printWriter = Mockito.mock(PrintWriter.class);
		Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		Mockito.when(requestPathInfo.getSelectorString()).thenReturn("delete");
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		Mockito.when(request.getReader()).thenReturn(bufferedReader);
		eventManipulator.doPost(request, response);
	}
}


