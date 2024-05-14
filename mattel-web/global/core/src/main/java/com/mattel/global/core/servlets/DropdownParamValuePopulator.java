package com.mattel.global.core.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.mattel.global.core.services.AnalyticsDynamicDropdownService;

/**
 * @author CTS
 *
 */
@Component(service = Servlet.class, immediate = true, property = {
		Constants.SERVICE_DESCRIPTION + "=Populate values dropdown based on type selected",
		"sling.servlet.paths=" + "/bin/populateAnalyticsAttrValues" })
public class DropdownParamValuePopulator extends SlingAllMethodsServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(DropdownParamValuePopulator.class);
	
	@Reference
	private transient AnalyticsDynamicDropdownService analyticsDynamicDropdown;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.sling.api.servlets.SlingSafeMethodsServlet#doGet(org.apache.
	 * sling.api.SlingHttpServletRequest,
	 * org.apache.sling.api.SlingHttpServletResponse)
	 */
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.info("doGet -> Start");
		RequestParameter requestParameter = request.getRequestParameter("contentPath");
		String path = "";
		if (requestParameter != null) {
			LOGGER.debug("Request Parameter : {}", requestParameter);
			path = requestParameter.toString().split("=")[1] + "/jcr:content/analyticsProperties";
		}
		LOGGER.debug("Content Path : {}", path);

		RequestParameter fieldTypeValue = request.getRequestParameter("fieldType");
		LOGGER.debug("Field Type Value : {}", fieldTypeValue);

		Map<String, String> attrValueList = null;
		if (fieldTypeValue != null) {
			attrValueList = analyticsDynamicDropdown.getAnalyticsPropertyValue(fieldTypeValue.toString(), path);
		}
		LOGGER.debug("Analytics Propert Attr Values : {} ", attrValueList);
		Gson gson = new Gson();
		String json = gson.toJson(attrValueList);
		response.setContentType("application/json");
		response.getWriter().write(json);
		LOGGER.info("doGet -> End");
	}

	public void setAnalyticsDynamicDropdown(AnalyticsDynamicDropdownService analyticsDynamicDropdown) {
		this.analyticsDynamicDropdown = analyticsDynamicDropdown;
	}
	
}
