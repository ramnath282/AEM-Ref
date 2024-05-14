package com.mattel.ag.retail.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.mattel.ag.retail.core.pojos.DisplayEventPojo;
import com.mattel.ag.retail.core.services.DisplayStoreEvents;

/**
 * @author CTS
 *
 */
@Component(service = Servlet.class, immediate = true, property = {
		Constants.SERVICE_DESCRIPTION + "=Event Creation Servlet", "sling.servlet.paths=" + "/bin/getevents" })
public class EventCreationServlet extends SlingSafeMethodsServlet {

	@Reference
	private transient DisplayStoreEvents displayStoreEvents;

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(EventCreationServlet.class);

	@Override
	protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws IOException {

		LOGGER.info("EventCreationServlet: doGet started");
		String json = "";
		List<DisplayEventPojo> list = new ArrayList<>();

		String currentStoreTag = request.getParameter("currentPageName");
		LOGGER.debug("current page tag is:{}", currentStoreTag);
		if (null != currentStoreTag) {
			list = displayStoreEvents.getEventsData(currentStoreTag);
			LOGGER.debug("final list for events is:{}", list);
		}

		Gson gson = new Gson();
		json = gson.toJson(list);
		response.setContentType("application/json");
		response.getWriter().write(json);
		LOGGER.info("EventCreationServlet: doGet end");

	}

	public DisplayStoreEvents getDisplayStoreEvents() {
		return displayStoreEvents;
	}

	public void setDisplayStoreEvents(DisplayStoreEvents displayStoreEvents) {
		this.displayStoreEvents = displayStoreEvents;
	}
}
