package com.mattel.ag.retail.core.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.io.IOUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.mattel.ag.retail.core.pojos.EventPojo;
import com.mattel.ag.retail.core.pojos.LocationDateDetailsPojo;
import com.mattel.ag.retail.core.pojos.LocationDetailsPojo;
import com.mattel.ag.retail.core.pojos.LocationPrepopulationPojo;
import com.mattel.ag.retail.core.services.EventsToolService;

@Component(service = Servlet.class, immediate = true, property = {
		Constants.SERVICE_DESCRIPTION + "= Event Creation Servlet", "sling.servlet.paths=" + "/bin/events" })
public class EventManipulator extends SlingAllMethodsServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(EventManipulator.class);

	@Reference
	private transient EventsToolService eventsToolService;

	@Override
	protected void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws IOException {
		LOGGER.info("EventManipulator: doPost started");
		Gson gson = new Gson();
		String selector = request.getRequestPathInfo().getSelectorString();
		if (null != selector) {
			String eventResponceData = "";
			BufferedReader bufferedReader = request.getReader();
			String eventData = IOUtils.toString(bufferedReader);
			LOGGER.debug("Event Data : {} ", eventData);
			if ("create".equals(selector) || "update".equals(selector)) {
				LOGGER.debug("{} Event Start", selector);
				EventPojo eventPojo = gson.fromJson(eventData, EventPojo.class);
				LOGGER.debug("Event ID : {} ", eventPojo.getEventId());
				String eventId = eventsToolService.createEvent(eventPojo, selector);
				Map<String, String> createEventResponce = new HashMap<>();
				createEventResponce.put("permanentEventId", eventId);
				eventResponceData = gson.toJson(createEventResponce);
				LOGGER.debug("{} Event End", selector);
			}
			else if ("delete".equals(selector)) {
				LOGGER.debug("Delete Event start");
				@SuppressWarnings("unchecked")
				Map<String, String> map = gson.fromJson(eventData, Map.class);
				eventsToolService.deleteEvent(map.get("eventNodePath"));
				Map<String, String> deleteResponce = new HashMap<>();
				deleteResponce.put("deleteResponce", "Event Deleted successfully..!!!");
				eventResponceData = gson.toJson(deleteResponce);
				LOGGER.debug("Delete Event end");
			} else {
				LOGGER.debug("Datatoupdate start");
				@SuppressWarnings("unchecked")
				Map<String, String> map = gson.fromJson(eventData, Map.class);
				String evenId = map.get("eventId");
				EventPojo responceEventPojo = eventsToolService.getEventTobeUpdated(evenId);
				eventResponceData = gson.toJson(responceEventPojo);
				LOGGER.debug("Datatoupdate end");
			}
			response.setContentType(com.mattel.ag.retail.core.constants.Constants.CONTENT_TYPE_JSON);
			response.getWriter().write(eventResponceData);
		}
		LOGGER.info("EventManipulator: doPost end");
	}

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.info("Do Get EventManipulator Start");
		Gson gson = new Gson();
		String selector = request.getRequestPathInfo().getSelectorString();
		if (null != selector) {
			String eventResponceData = "";
			if ("locations".equals(selector)) {
				LOGGER.debug("locations json creation start");
				List<LocationPrepopulationPojo> allStores = eventsToolService.getAllAgStoreTags();
				Map<String, List<LocationPrepopulationPojo>> allStoresMap = new HashMap<>();
				allStoresMap.put("locations", allStores);
				eventResponceData = gson.toJson(allStoresMap);
				LOGGER.debug("Loation Map : {} ", eventResponceData);
				LOGGER.debug("locations json creation end");
			}
			else{
				LOGGER.debug("eventid prepopulation json creation start");
				int maxCount = eventsToolService.getMaxCount();
				LOGGER.debug("Max Count : {} ", maxCount);

				EventPojo eventPojo = new EventPojo();
				List<LocationDetailsPojo> locationDetailsLst = new ArrayList<>();
				List<LocationDateDetailsPojo> locationDateDetailsLst = new ArrayList<>();
				LocationDetailsPojo locationDetailsPojo = new LocationDetailsPojo();
				LocationDateDetailsPojo locationDateDetailsPojo = new LocationDateDetailsPojo();
				LOGGER.debug("Created EventPojo, LocationDetailsPojo, LocationDateDetailsPojo");
				
				setEventLevelData(eventPojo,maxCount + 1);
				setLocationLevelData(locationDetailsPojo);
				setDateLevelData(locationDateDetailsPojo);
				
				locationDateDetailsLst.add(locationDateDetailsPojo);
				locationDetailsPojo.setLocationDateDetails(locationDateDetailsLst);
				locationDetailsLst.add(locationDetailsPojo);
				eventPojo.setLocationDetails(locationDetailsLst);
				eventResponceData = gson.toJson(eventPojo);
				LOGGER.debug("eventid prepopulation json : {} ", eventResponceData);
				LOGGER.debug("eventid prepopulation json creation end");
			}
			response.setContentType(com.mattel.ag.retail.core.constants.Constants.CONTENT_TYPE_JSON);
			response.getWriter().write(eventResponceData);
		}
		LOGGER.info("Do Get EventManipulator End");
	}

	private void setEventLevelData(EventPojo eventPojo, int eventId) {
		eventPojo.setEventId(Integer.toString(eventId));
		eventPojo.setEventTitle("");
		eventPojo.setEventDescription("");
		eventPojo.setMinAge("0");
		eventPojo.setReservationRequired("false");
		eventPojo.setKeywords("");
	}

	private void setLocationLevelData(LocationDetailsPojo locationDetailsPojo) {
		locationDetailsPojo.setStoreTag("");
		locationDetailsPojo.setZomatoURL("");
		locationDetailsPojo.setPricingOption("");
		locationDetailsPojo.setCostInfo("");
		locationDetailsPojo.setPricingAmount("");
		locationDetailsPojo.setStoreName("");
		locationDetailsPojo.setGratuityRequired("false");
	}

	private void setDateLevelData(LocationDateDetailsPojo locationDateDetailsPojo) {
		locationDateDetailsPojo.setScheduleDescription("");
		locationDateDetailsPojo.setEventStartTime("00:00:am");
		locationDateDetailsPojo.setEventEndTime("00:00:am");
		locationDateDetailsPojo.setEventDate("");
		locationDateDetailsPojo.setAdditionalDateInfo("");
	}

	public void setEventsToolService(EventsToolService eventsToolService) {
		this.eventsToolService = eventsToolService;
	}

}
