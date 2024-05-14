package com.mattel.ag.explore.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.day.cq.wcm.api.Page;
import com.mattel.ag.explore.core.pojos.RelatedArticlePojo;
import com.mattel.ag.explore.core.services.GetRelatedArticles;

/**
 * @author CTS UpdateVanityUrlServlet.
 */
@Component(service = Servlet.class, immediate = true, property = {
		Constants.SERVICE_DESCRIPTION + "=Update Vanity Url Servlet",
		"sling.servlet.paths=" + "/bin/updatevanityUrls" })

public class UpdateVanityUrlServlet extends SlingSafeMethodsServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateVanityUrlServlet.class);
	String propertyName = "sling:vanityPath";
	String backSlash = "/";
	@Reference
	private transient GetRelatedArticles getRelatedArticlesWrapper;

	@Override
	protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws IOException {

		LOGGER.info("UpdateVanityUrlServlet: doGet started");
		List<RelatedArticlePojo> articlePageList;
		Page page = null;
		Node node = null;

		Map<String, List<String>> queryDataMap = new HashMap<>();
		List<String> contentPath = new ArrayList<>();
		contentPath.add("/content/ag/en/explore");
		queryDataMap.put("path", contentPath);
		articlePageList = getRelatedArticlesWrapper.getRelatedArticles(queryDataMap);
		LOGGER.debug("articlePageList {}", articlePageList);
		for (RelatedArticlePojo articlePage : articlePageList) {
			String pagePath = articlePage.getPagePath();
			LOGGER.debug("pagePath {}", pagePath);
			ResourceResolver resourceResolver = null;
			Resource resource = request.getResourceResolver().getResource(pagePath + "/jcr:content");
			Resource resource1 = request.getResourceResolver().getResource(pagePath);
			resourceResolver = request.getResourceResolver();
			if (null != resource && null != resource1) {
				LOGGER.debug("Resource is null");
				page = resource1.adaptTo(Page.class);
				node = resource.adaptTo(Node.class);
				if (null != page) {
					updateVanityUrl(page, node, resourceResolver);
				}
			}

		}
		LOGGER.info("UpdateVanityUrlServlet: doGet end");

	}

	/**
	 * The purpose of this method is to update the vanity Url of all the
	 * Articles under certain Path
	 * 
	 * @param page
	 * @param node
	 * @param resourceResolver
	 * @param valueMap
	 * @throws PersistenceException
	 */
	private void updateVanityUrl(Page page, Node node, ResourceResolver resourceResolver) throws PersistenceException {
		String pageName = page.getName();
		LOGGER.debug("pageName {}", pageName);
		String vanityPagePath = "/explore/articles/" + pageName + backSlash;

		if (null != node) {
			try {
				node.setProperty("sling:vanityPath", vanityPagePath);
				resourceResolver.commit();
			} catch (RepositoryException e) {
				LOGGER.debug("Property cannot be updated", e);
			}
		}

		LOGGER.debug("Vanity Url Successfully updated for {}", pageName);
	}

	public void setGetRelatedArticlesWrapper(GetRelatedArticles getRelatedArticlesWrapper) {
		this.getRelatedArticlesWrapper = getRelatedArticlesWrapper;
	}

}
