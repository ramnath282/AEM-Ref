package com.mattel.ag.retail.core.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.mattel.ag.retail.core.services.SearchEvents;

/**
 * @author CTS
 *
 */
@Component(service = Servlet.class, immediate = true, property = {
		Constants.SERVICE_DESCRIPTION + "=Search Event Servlet", "sling.servlet.paths=" + "/bin/getsearchevents" })
public class SearchEventServlet extends SlingSafeMethodsServlet {

	@Reference
	private transient SearchEvents searchEvents;

	public SearchEvents getSearchEvents() {
		return searchEvents;
	}

	public void setSearchEvents(SearchEvents searchEvents) {
		this.searchEvents = searchEvents;
	}

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(SearchEventServlet.class);

	/* (non-Javadoc)
	 * @see org.apache.sling.api.servlets.SlingSafeMethodsServlet#doGet(org.apache.sling.api.SlingHttpServletRequest, org.apache.sling.api.SlingHttpServletResponse)
	 */
	@Override
	protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws IOException {
		LOGGER.info("SearchEventServlet: doGet started");
		String json = "";
		List<DisplayEventPojo> list;
		Map<String,String> searchFilters = new HashMap<>();
		String eventId = request.getParameter("eventId");
		if(null != eventId){
			searchFilters.put("eventId", eventId);
		}
		String eventTitle = request.getParameter("eventTitle");
		if(null != eventTitle){
			searchFilters.put("eventTitle", eventTitle);
		}
		String storeName = request.getParameter("storeName");
		if(null != storeName){
			searchFilters.put("storeName", storeName);
		}
		String keywords = request.getParameter("keywords");
		if(null != keywords){
			searchFilters.put("keywords", keywords);
		}
		String storeTag = request.getParameter("storeTag");
		if(null != storeTag){
			searchFilters.put("storeTag", storeTag);
		}
		list = searchEvents.getSearchResults(searchFilters);
		LOGGER.debug("final list for events is:{}", list);
		Gson gson = new Gson();
		json = gson.toJson(list);
		response.setContentType("application/json");
		response.getWriter().write(json);
		LOGGER.info("SearchEventServlet: doGet started");
	}

}
