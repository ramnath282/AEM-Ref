package com.mattel.global.core.sitemap;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

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
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true, service = Servlet.class, property = {
    "sling.servlet.extensions=" + "xml", "sling.servlet.selectors=" + "genericsitemap",
    "sling.servlet.methods="
        + HttpConstants.METHOD_GET, }, configurationPid = "com.mattel.global.core.sitemap.PathSiteMapServlet")
public class PathSiteMapServlet extends SlingSafeMethodsServlet {
  private static final long serialVersionUID = 7555527618331620922L;
  private static final Logger LOGGER = LoggerFactory.getLogger(PathSiteMapServlet.class);
  @Reference
  transient GenericSiteMapService genericSiteMapService;
  @Reference
  transient GenericSiteMapGeneratorConfig siteMapGeneratorConfig;

  @Override
  protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
      throws ServletException, IOException {
    final ResourceResolver resourceResolver = request.getResourceResolver();
    final PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
    if (Objects.nonNull(pageManager)) {
      final Page page = pageManager.getContainingPage(request.getResource());
      final SiteConfig siteConfig = siteMapGeneratorConfig
          .getSiteMapConfigByRelPath(page.getPath());

      if (Objects.nonNull(siteConfig)) {
        PathSiteMapServlet.LOGGER.debug("Generating site map for page path: {},\n siteConfig: {}",
            new Object[] { page.getPath(), siteConfig });
        response.setContentType(request.getContentType());
        genericSiteMapService.buildSiteMap(response.getWriter(), resourceResolver, siteConfig);
      } else {
        PathSiteMapServlet.LOGGER.error(
            "No site map configuration found for path: {}, unable to generate site map.",
            page.getPath());
        response.sendError(HttpURLConnection.HTTP_NOT_FOUND,
            "No site map configuration found for given page");
      }
    } else {
      PathSiteMapServlet.LOGGER.error("Unable to find page resource");
      response.sendError(HttpURLConnection.HTTP_NOT_FOUND, "Unable to find page resource");
    }
  }
}
