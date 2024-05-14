package com.mattel.ecomm.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.QueryBuilder;
import com.mattel.ecomm.core.interfaces.ProductGridPromoBannerService;

@Component(service = Servlet.class, immediate = true, property = {
		Constants.SERVICE_DESCRIPTION + "=Product Grid Promo Banner Configuration Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.paths=" + "/bin/getproductgridpromobannerconfig" })
public class ProductGridPromoBannerConfig extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;

	@Reference
	transient QueryBuilder queryBuilder;
	
	@Reference
	transient ProductGridPromoBannerService productGridPromoBannerService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductGridPromoBannerConfig.class);

	@Override
	protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws IOException {
		LOGGER.info("Get MEthod called");
		String currentPagePath = request.getParameter("currentPagePath");
		LOGGER.debug("currentPagePath is {}", currentPagePath);
		if (StringUtils.isNotBlank(currentPagePath)) {
			JSONObject masterJosn = productGridPromoBannerService.getProductGridPromoBannerJson(currentPagePath);
			response.setContentType("application/json");
			response.getWriter().print(masterJosn);
		}

	}
}
