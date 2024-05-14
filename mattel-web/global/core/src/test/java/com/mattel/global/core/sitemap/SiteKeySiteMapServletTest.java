package com.mattel.global.core.sitemap;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SiteKeySiteMapServletTest {
  
  @InjectMocks
  SiteKeySiteMapServlet siteKeySiteMapServlet;
  
  @Mock
  GenericSiteMapGeneratorConfig siteMapGeneratorConfig;
  
  @Mock
  GenericSiteMapService genericSiteMapService;
  
  SlingHttpServletRequest request;
  SlingHttpServletResponse response;
  
  @Mock
  ResourceResolver resourceResolver;
  
  @Before
  public void setup(){
    request = Mockito.mock(SlingHttpServletRequest.class);
    response = Mockito.mock(SlingHttpServletResponse.class);
    Mockito.when(request.getResourceResolver()).thenReturn(resourceResolver);
    RequestPathInfo requestInfo = Mockito.mock(RequestPathInfo.class);
    Mockito.when(request.getRequestPathInfo()).thenReturn(requestInfo);
    String[] selectors = {"selector1"};
    Mockito.when(requestInfo.getSelectors()).thenReturn(selectors);
  }
  
  @Test
  public void testGet_with_siteconfig_nonnull() throws ServletException, IOException{
    SiteConfig siteConfig = Mockito.mock(SiteConfig.class);
    Mockito.when(siteMapGeneratorConfig.getSiteMapConfig("selector1")).thenReturn(siteConfig);
    siteKeySiteMapServlet.doGet(request, response);
  }
  
  @Test
  public void testGet_with_siteconfig_null() throws ServletException, IOException{
    Mockito.when(siteMapGeneratorConfig.getSiteMapConfig("selector1")).thenReturn(null);
    siteKeySiteMapServlet.doGet(request, response);
  }

}
