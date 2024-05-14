package com.mattel.global.core.sitemap;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Objects;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = Servlet.class, immediate = true, property = {
    Constants.SERVICE_DESCRIPTION + "=Generic Site Map Servlet",
    "sling.servlet.methods=" + HttpConstants.METHOD_GET,
    "sling.servlet.paths=" + "/bin/genericsitemap", "sling.servlet.extensions=" + "xml" })
public class SiteKeySiteMapServlet extends SlingSafeMethodsServlet {
  private static final Logger LOGGER = LoggerFactory.getLogger(SiteKeySiteMapServlet.class);
  private static final long serialVersionUID = -2254709067443484786L;
  @Reference
  transient GenericSiteMapGeneratorConfig siteMapGeneratorConfig;
  @Reference
  transient GenericSiteMapService genericSiteMapService;

  @Override
  protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
      throws ServletException, IOException {
	LOGGER.info("SiteKeySiteMapServlet doGet -> Start");
    final ResourceResolver resourceResolver = request.getResourceResolver();
    final String[] selectors = request.getRequestPathInfo().getSelectors();
    final String siteKey = selectors[0];
    final SiteConfig siteConfig = siteMapGeneratorConfig.getSiteMapConfig(siteKey);

    if (Objects.nonNull(siteConfig)) {
      SiteKeySiteMapServlet.LOGGER.debug("Generating site map for siteKey: {},\n siteConfig: {}",
          new Object[] { siteKey, siteConfig });
      response.setStatus(200);
      response.setContentType("application/xhtml+xml");
      genericSiteMapService.buildSiteMap(response.getWriter(), resourceResolver, siteConfig);
    } else {
      SiteKeySiteMapServlet.LOGGER.error(
          "No site map configuration found for siteKey: {}, unable to generate site map.", siteKey);
      response.sendError(HttpURLConnection.HTTP_NOT_FOUND, "No site map configuration found");
    }
    LOGGER.info("SiteKeySiteMapServlet doGet -> End");
  }
}
