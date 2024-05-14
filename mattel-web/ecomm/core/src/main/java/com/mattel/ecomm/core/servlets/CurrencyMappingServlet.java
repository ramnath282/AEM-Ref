package com.mattel.ecomm.core.servlets;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.core.interfaces.CurrencyMappingService;
import com.mattel.ecomm.core.pojos.CurrencyMappingPojo;

@Component(service = Servlet.class, immediate = true, property = {
		Constants.SERVICE_DESCRIPTION + "=Currency Type and Symbol Codes Json",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/getCurrencyMapList" })
public class CurrencyMappingServlet extends SlingSafeMethodsServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyMappingServlet.class);
	transient Resource resource;
	@Reference
	transient CurrencyMappingService currencyMappingService;
	private transient List<CurrencyMappingPojo> currencyMapList = new LinkedList<>();

	@Override
	protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("doGet of Currency Mapping Servlet -> Start");
		try {
			ResourceResolver resolver = request.getResourceResolver();
			resource = resolver.getResource(request.getPathInfo());
			if (null != resource) {
				currencyMapList = currencyMappingService.getCurrencyDetails(resource);
			}
			prepareJson(response);
		} catch (NullPointerException e) {
			LOGGER.error("Null PointerException Occured {} ", e);
		} catch (JSONException e) {
			LOGGER.error("JSONException Occured {} ", e);
		} catch (RepositoryException e) {
			LOGGER.error("Repository Exception Occured {} ", e);
		}
	}

	/**
	 * Method to prepare the JSON Response
	 * 
	 * @param response
	 * @param tagLength
	 * @throws JSONException
	 * @throws IOException
	 */
	private void prepareJson(SlingHttpServletResponse response) throws JSONException, IOException {
		LOGGER.debug("prepareJson of CurrencyMappingServlet---> start");
		JSONObject obj = new JSONObject();
		obj.put("currencyMap", currencyMapList);
		response.setContentType("application/json");
		response.getWriter().print(obj);
		LOGGER.debug("prepareJson of CurrencyMappingServlet---> end");
	}

	public void setCurrencyMappingService(CurrencyMappingService currencyMappingService) {
		this.currencyMappingService = currencyMappingService;
	}

}
