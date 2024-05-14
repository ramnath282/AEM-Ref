package com.mattel.ecomm.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
//import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
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

import com.mattel.ecomm.core.interfaces.CategoryNavigationService;
import com.mattel.ecomm.core.interfaces.ContentNavigationService;
import com.mattel.ecomm.coreservices.core.constants.Constant;

@Component(service = Servlet.class, immediate = true, property = {
		Constants.SERVICE_DESCRIPTION + "=Category Navigation Links Json",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/getNavigation" })
public class SiteNavigationServlet extends SlingSafeMethodsServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(SiteNavigationServlet.class);
	//transient Resource resource;
	//private transient List<JSONObject> navigationLinks = new LinkedList<>();
	@Reference
	transient CategoryNavigationService categoryNavService;
	@Reference
	transient ContentNavigationService contentNavService;
	//String device = StringUtils.EMPTY;

	@Override
	protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("doGet of CategoryNavigationLinks Servlet -> Start");
		//navigationLinks.clear();
		String currentNodePath = request.getParameter("currentPath") + Constant.HEADER_RESPONSIVEGRID_NAME;
		String device = Optional.ofNullable(request.getParameter("deviceType")).orElse(StringUtils.EMPTY);
		try {
			ResourceResolver resolver = request.getResourceResolver();
			List<JSONObject> navigationLinks = new ArrayList<>();
			Resource resource = resolver.getResource(currentNodePath);
			if (null != resource) {
				Iterator<Resource> childResources = resource.listChildren();
				while (childResources.hasNext()) {
					Resource childResource = childResources.next();
					String resourceType = childResource.getResourceType();
					checkResourceType(device, childResource, navigationLinks, resourceType, request);
				}
			}
			if ("mobile".equals(device)) {
				orderFinalJSONResponse(navigationLinks);
			}
			prepareJson(response, navigationLinks);
		} catch (NullPointerException e) {
			LOGGER.error("Null PointerException Occured {} ", e);
		} catch (JSONException e) {
			LOGGER.error("JSONException Occured {} ", e);
		}
		LOGGER.debug("doGet of CategoryNavigationLinks Servlet -> End");
	}

	public void setCategoryNavService(CategoryNavigationService categoryNavService) {
		this.categoryNavService = categoryNavService;
	}

	public void setContentNavService(ContentNavigationService contentNavService) {
		this.contentNavService = contentNavService;
	}

	/**
	 *
	 * @param device
	 * @param childResource
	 * @param navigationLinks
	 * @param resourceType
	 * @param request
	 */
	private void checkResourceType(String device, Resource childResource, List<JSONObject> navigationLinks,
			String resourceType, SlingHttpServletRequest request) {
		LOGGER.info("checkResourceType -> Start");
		JSONObject tempObj;
		if (resourceType.contains(Constant.CATEGORY_NAV_RESOURCE_TYPE)) {
			tempObj = categoryNavService.processNavLinks(device, childResource, request);
			if (null != tempObj) {
				navigationLinks.add(tempObj);
				LOGGER.debug("checkResourceType if CATEGORY_NAV_RESOURCE_TYPE tempObj {}", tempObj);
			}
		} else if (resourceType.contains(Constant.OTHER_NAV_RESOURCE_TYPE)) {
			LOGGER.debug("checkResourceType contentNavigation");
			tempObj = contentNavService.processNavLinks(device, childResource);
			if (null != tempObj) {
				navigationLinks.add(tempObj);
				LOGGER.debug("checkResourceType contentNavigation tempObj {}", tempObj);
			}

		}
		LOGGER.info("checkResourceType -> End");
	}

	private void orderFinalJSONResponse(List<JSONObject> navigationLinks) {
		Collections.sort(navigationLinks, (p1, p2) -> {
			try {
				boolean b1 = p1.getBoolean("linkHeaderText");
				boolean b2 = p2.getBoolean("linkHeaderText");
				return Boolean.compare(b2, b1);
			} catch (JSONException e) {
				LOGGER.error("JSONException Occured {} ", e);
			}
			return 0;
		});
	}

	/**
	 * Method to prepare the JSON Response
	 * 
	 * @param response
	 * @param navigationLinks 
	 * @param tagLength
	 * @throws JSONException
	 * @throws IOException
	 */
	private void prepareJson(SlingHttpServletResponse response, List<JSONObject> navigationLinks) throws JSONException, IOException {
		LOGGER.debug("prepareJson of Category Navigation Links---> start");
		JSONObject obj = new JSONObject();
		obj.put("navigationLinks", navigationLinks);
		response.setContentType("application/json");
		response.getWriter().print(obj);
		LOGGER.debug("prepareJson of Category Navigation Links---> end");
	}
}
