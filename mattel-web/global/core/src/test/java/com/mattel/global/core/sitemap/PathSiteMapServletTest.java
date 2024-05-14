package com.mattel.global.core.sitemap;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@RunWith(MockitoJUnitRunner.class)
public class PathSiteMapServletTest {
  @InjectMocks
  PathSiteMapServlet pathSiteMapServlet;

  @Mock
  GenericSiteMapService genericSiteMapService;

  @Mock
  GenericSiteMapGeneratorConfig siteMapGeneratorConfig;

  SlingHttpServletRequest request;
  SlingHttpServletResponse response;
  SiteConfig siteConfig;
  Page page;
  ResourceResolver resourceResolver;

  @Before
  public void init() {
    request = Mockito.mock(SlingHttpServletRequest.class);
    response = Mockito.mock(SlingHttpServletResponse.class);

    resourceResolver = Mockito.mock(ResourceResolver.class);
    Mockito.when(request.getResourceResolver()).thenReturn(resourceResolver);
    PageManager pageManager = Mockito.mock(PageManager.class);
    Mockito.when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
    Resource resource = Mockito.mock(Resource.class);
    Mockito.when(request.getResource()).thenReturn(resource);
    page = Mockito.mock(Page.class);
    Mockito.when(pageManager.getContainingPage(request.getResource())).thenReturn(page);
    Mockito.when(page.getPath()).thenReturn("page path");
    siteConfig = Mockito.mock(SiteConfig.class);

  }

  @Test
  public void testGet_with_SiteConfig_NotNull() throws ServletException, IOException {
    Mockito.when(siteMapGeneratorConfig.getSiteMapConfigByRelPath(page.getPath()))
        .thenReturn(siteConfig);
    pathSiteMapServlet.doGet(request, response);
  }

  @Test
  public void testGet_with_SiteConfig_Null() throws ServletException, IOException {
    Mockito.when(siteMapGeneratorConfig.getSiteMapConfigByRelPath(page.getPath())).thenReturn(null);
    pathSiteMapServlet.doGet(request, response);
  }

  @Test
  public void testGet_with_PageManager_Null() throws ServletException, IOException {
    Mockito.when(resourceResolver.adaptTo(PageManager.class)).thenReturn(null);
    pathSiteMapServlet.doGet(request, response);
  }

}
