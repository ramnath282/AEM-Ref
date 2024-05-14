package com.mattel.ag.explore.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ag.explore.core.services.GetResourceResolver;
import com.mattel.ag.explore.core.utils.SiteMapServletConfiguration;

/**
 * @author CTS RelatedArticleServlet.
 */
@Component(service = Servlet.class, immediate = true, property = {
		Constants.SERVICE_DESCRIPTION + "=SiteMap Generator Servlet", "sling.servlet.methods=" + "GET",
		"sling.servlet.paths=" + "/explore/getSiteMapUrls", "sling.servlet.extensions=" + "xml",
		"sling.servlet.selectors=" + "sitemap" })

public class SiteMapGeneratorServlet extends SlingSafeMethodsServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(SiteMapGeneratorServlet.class);
	private static final String CLASS_NAME = SiteMapGeneratorServlet.class.getSimpleName();

	@Reference
	private transient SiteMapServletConfiguration siteMapServletConfiguration;
	@Reference
	private transient GetResourceResolver getResourceResolver;

	public void setSiteMapServletConfiguration(SiteMapServletConfiguration siteMapServletConfiguration) {
		this.siteMapServletConfiguration = siteMapServletConfiguration;
	}

	@Override
	protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws IOException {
		LOGGER.info(CLASS_NAME, "Start doGet()");
		LOGGER.debug("siteMapServletConfiguration : {}", siteMapServletConfiguration);
		response.setContentType("text/xml;charset=UTF-8");
		String changefreqProperties = siteMapServletConfiguration.getMyChangeFreqProperties();
		String priorityProperties = siteMapServletConfiguration.getMyPriorityProperties();
		String rootPath = siteMapServletConfiguration.getRootPath();
		String domain = siteMapServletConfiguration.getDomain();

    try (ResourceResolver resourceResolver = getResourceResolver.getResourceResolver()) {
		PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
		List<Page> pageList = new ArrayList<>();
		if (pageManager != null) {
			Page homepage = pageManager.getPage(rootPath);
			Iterator<Page> pages = homepage.listChildren(new PageFilter(), true);
			while (pages.hasNext()) {
				Page childPage = pages.next();
				ValueMap valueMap = childPage.getProperties();
				if (valueMap.get("valueMap") == null) {
					pageList.add(childPage);
				}

			}
			LOGGER.debug("changefreqProperties: {}", changefreqProperties);
			LOGGER.debug("priorityProperties: {}", priorityProperties);
			response.setContentType("text/xml");
			PrintWriter writer = response.getWriter();
			writer.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
			writer.append("<urlset" + " " + "xmlns=" + "\"https://www.sitemaps.org/schemas/sitemap/0.9\"" + " "
					+ "xmlns:xsi=" + "\"https://www.w3.org/2001/XMLSchema-instance\"" + " " + "xsi:schemaLocation="
					+ "\"https://www.sitemaps.org/schemas/sitemap/0.9" + " "
					+ "https://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd\"" + ">");
			for (Page page : pageList) {
				String pagePath = null;
				String vanityPath = page.getProperties().get("sling:vanityPath", String.class);
				LOGGER.debug("vanityPath : {}", vanityPath);
				if (null == vanityPath) {
					pagePath = page.getPath();
					pagePath = pagePath.replace(rootPath, "");
				} else {
					pagePath = vanityPath;
				}

				writer.append("<url>");
				writer.append("<loc>").append(domain + pagePath).append("</loc>");
				writer.append("<changefreq>").append(changefreqProperties).append("</changefreq>");
				writer.append("<priority>").append(priorityProperties).append("</priority>");
				writer.append("</url>");
			}

			writer.append("</urlset>");
			LOGGER.info(CLASS_NAME, "End doGet()");
		}
    }
	}

	public void setGetResourceResolver(GetResourceResolver getResourceResolver) {
		this.getResourceResolver = getResourceResolver;
	}

}
