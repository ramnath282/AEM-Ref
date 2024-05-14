package com.mattel.informational.core.filter;

import java.io.IOException;

import javax.jcr.RepositoryException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.informational.core.services.NewsDetailMetadataService;
import com.mattel.informational.core.utils.InformationalConfigurationUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ResourceUtil.class})
public class NewsArticleFilterTest {

    @InjectMocks
    NewsArticleFilter newsArticleFilter;
    @Mock
    SlingHttpServletRequest slingHttpServletRequest;
    @Mock
    SlingHttpServletResponse response;
    @Mock
    FilterChain chain;
    @Mock
    RequestPathInfo requestPathInfo;
    @Mock
    PageManager pagemanager;
    @Mock
    private RequestDispatcher reqDispatcher;
    @Mock
    ResourceResolver resourceResolver;
    @Mock
    Page page;
    @Mock
    Page newPage;
    @Mock
    FilterConfig filterConfig;
    @Mock
    Resource resource;
    
    @Mock
    NewsDetailMetadataService newsDetailMetadataService;
    
    @Mock
    InformationalConfigurationUtils informationalConfigurationUtils; 
    
    
    public void setUp() throws RepositoryException {
        final String[] selector = new String[3];
        selector[0] = "test-news-article-static-page";
        selector[1] = "corporate_en-us";
        String value = "/content/mattel/mattel-corporate/news/news-detail.test-news-article-static-page.corporate_en-us.html";
        Mockito.when(slingHttpServletRequest.getRequestPathInfo()).thenReturn(requestPathInfo);
        Mockito.when(requestPathInfo.getSelectors()).thenReturn(selector);
        Mockito.when(requestPathInfo.getResourcePath()).thenReturn(value);
        Mockito.when(slingHttpServletRequest.getResourceResolver()).thenReturn(resourceResolver);
        Mockito.when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pagemanager);
        Mockito.when(pagemanager.getContainingPage(value)).thenReturn(page);
        Mockito.when(page.getPath()).thenReturn("/content/mattel/mattel-corporate/news/news-detail");
        Mockito.when(page.getParent()).thenReturn(newPage);
        Mockito.when(newsDetailMetadataService.getCfPath("corp")).thenReturn("/content/dam/mattel");
        Mockito.when(resourceResolver.resolve(Mockito.anyString())).thenReturn(resource);
        PowerMockito.mockStatic(ResourceUtil.class);
        Mockito.when(!ResourceUtil.isNonExistingResource(resource)).thenReturn(true);
        Mockito.when(informationalConfigurationUtils.getSiteErrorPages("corporate_en-us")).thenReturn("/content/mattel/mattel-corporate/us/en-us/error.html");
        Mockito.when(slingHttpServletRequest.getRequestDispatcher(Mockito.anyString())).thenReturn(reqDispatcher);
    }
    
    @Test
    public void doFilterWhenStaticPageNotAvailable() throws IOException, ServletException, RepositoryException {
    	setUp();
    	Mockito.when(newPage.hasChild(Mockito.anyString())).thenReturn(false);
        newsArticleFilter.init(filterConfig);
        newsArticleFilter.doFilter(slingHttpServletRequest, response, chain);
        newsArticleFilter.destroy();
    }

    @Test
    public void doFilterWhenStaticPageNonMatchingFilter() throws IOException, ServletException, RepositoryException {
    	String value = "/editor.html/content/mattel/mattel-corporate/news/news-detail.html";
        Mockito.when(slingHttpServletRequest.getRequestPathInfo()).thenReturn(requestPathInfo);
        Mockito.when(requestPathInfo.getSelectors()).thenReturn(new String[]{});
        Mockito.when(requestPathInfo.getResourcePath()).thenReturn(value);
        Mockito.when(slingHttpServletRequest.getResourceResolver()).thenReturn(resourceResolver);
        Mockito.when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pagemanager);
        Mockito.doNothing().when(chain).doFilter(slingHttpServletRequest, response);
        newsArticleFilter.init(filterConfig);
        newsArticleFilter.doFilter(slingHttpServletRequest, response, chain);
        newsArticleFilter.destroy();
    }
    
    @Test
    public void doFilterWhenStaticPageAvailable() throws IOException, ServletException, RepositoryException {
    	setUp();
        Mockito.when(!ResourceUtil.isNonExistingResource(Mockito.any())).thenReturn(false);
        Mockito.when(newPage.hasChild(Mockito.anyString())).thenReturn(true);
        newsArticleFilter.init(filterConfig);
        newsArticleFilter.doFilter(slingHttpServletRequest, response, chain);
        newsArticleFilter.destroy();
    }
    
}
