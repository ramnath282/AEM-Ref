package com.mattel.informational.core.filter;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.informational.core.constants.Constants;
import com.mattel.informational.core.services.NewsDetailMetadataService;

import com.mattel.informational.core.utils.InformationalConfigurationUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.engine.EngineConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterConfig;
import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import javax.servlet.FilterChain;

import java.io.IOException;
import java.util.Objects;

@Component(service = Filter.class, property = {
        "service.description" + "=Demo to filter incoming requests for news article pages",
        EngineConstants.SLING_FILTER_SCOPE + "=" + EngineConstants.FILTER_SCOPE_REQUEST,
        "service.ranking" + ":Integer=1500",
        EngineConstants.SLING_FILTER_PATTERN + "=/content/mattel/mattel-corporate/.*/news/news-detail.*" })

public class NewsArticleFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(NewsArticleFilter.class);

    @Reference
    private NewsDetailMetadataService newsDetailMetadataService;

    @Reference
    private InformationalConfigurationUtils informationalConfigurationUtils;

    @Override public void init(FilterConfig filterConfig) throws ServletException {
        // Called once when the Filter is initially registered.
        // Usually, do nothing
    }

    @Override public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        LOGGER.info("News Article Page Filter Starts");
        final SlingHttpServletRequest slingRequest = (SlingHttpServletRequest) request;
        String requestPath = slingRequest.getRequestPathInfo().getResourcePath();
        ResourceResolver resourceResolver = slingRequest.getResourceResolver();
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        String[] selectors = slingRequest.getRequestPathInfo().getSelectors();
        if (Objects.nonNull(pageManager) && selectors.length >= 2) {
            Page page = pageManager.getContainingPage(requestPath);
            LOGGER.debug("Request Page path is {}",page.getPath());
            String newsArticleSelector = selectors[0];
            LOGGER.debug("Parent page path is {}",page.getParent().getPath());
                if (page.getParent().hasChild(newsArticleSelector)) {
                    String staticPagePath = page.getParent().getPath()+ Constants.SLASH+newsArticleSelector;
                    LOGGER.debug("Static page redirect path is {}", staticPagePath);
                    request.getRequestDispatcher(staticPagePath).forward(request, response);
                } else {
                    redirectToDynamicPage(newsArticleSelector, resourceResolver, request, response, selectors[1], chain);
                }

        } else {
        	LOGGER.debug("Non matching selectors, redirecting to the incoming url path");
        	chain.doFilter(request, response);
        }

        long endTime = System.currentTimeMillis();
        LOGGER.debug("Complete Time taken is {}", endTime - startTime);
        LOGGER.info("News Article Page Filter End");
    }

    private void redirectToDynamicPage(String contentFragmentSelector, ResourceResolver resourceResolver,
            ServletRequest request, ServletResponse response, String sitekey, FilterChain chain) {
        LOGGER.info("redirectToDynamicPage method of NewsArticleFilter Start");
        String contentFragmentPath = newsDetailMetadataService.getCfPath("corp") + contentFragmentSelector;
        LOGGER.debug("Path of content fragment is {}", contentFragmentPath);
        Resource contentFragmentResource = resourceResolver.resolve(contentFragmentPath);
        try {
            if (!ResourceUtil.isNonExistingResource(contentFragmentResource)) {
                chain.doFilter(request, response);
            } else {
                String errorPageUrl = informationalConfigurationUtils.getSiteErrorPages(sitekey);
                LOGGER.debug("Static and dynamic resource not found redirecting to error page {}",errorPageUrl);
                SlingHttpServletResponse slingHttpServletResponse = (SlingHttpServletResponse)response;
                slingHttpServletResponse.setStatus(404);
                slingHttpServletResponse.sendRedirect(errorPageUrl);
            }
        } catch (IOException | ServletException e) {
            LOGGER.error("Exception in the redirectToDynamicPage method in the NewsArticleFilter {}", e.getMessage());
        }
        LOGGER.info("redirectToDynamicPage method of NewsArticleFilter End");
    }

    @Override public void destroy() {
        // Called once when the Filter is unloaded.
        // Usually, do nothing
    }
}
