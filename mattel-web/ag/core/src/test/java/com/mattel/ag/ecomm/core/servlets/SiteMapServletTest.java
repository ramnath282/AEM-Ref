package com.mattel.ag.ecomm.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ag.ecomm.core.servlets.SiteMapServlet.SiteMapConfig;

@RunWith(PowerMockRunner.class)
public class SiteMapServletTest {

  @InjectMocks
  private SiteMapServlet siteMapServlet;
  @Mock
  private SlingHttpServletResponse response;
  @Mock
  private SlingHttpServletRequest request;
  
  @Mock
  ResourceResolver resourceResolver;
  
  @Mock
  PageManager pageManager;
  
  @Mock
  Resource resource;
  
  @Mock
  Page page;
  
  @Mock
  PrintWriter writer;
  
  @Mock
  ValueMap valueMap;
  
  @Mock
  Externalizer externalizer;
  
  @Mock
  PageFilter pageFilter;
  
  @Mock
  Iterator<Page> childrenItr;
  
  @Mock
  Page child;
  
  @Mock
  SiteMapConfig config;

  @Before
  public void setUp() throws Exception {
    String[] strArr = new String[]{};
    List<String> lst = new ArrayList<>();
    MemberModifier.field(SiteMapServlet.class, "externalizerDomain").set(siteMapServlet, "agdomainqa");
    MemberModifier.field(SiteMapServlet.class, "productdetailsProperty").set(siteMapServlet, "/var/commerce/products/ag/en");
    MemberModifier.field(SiteMapServlet.class, "productrootpathProperty").set(siteMapServlet, "/shop/p");
    MemberModifier.field(SiteMapServlet.class, "productdetailsPriority").set(siteMapServlet, "1");
    MemberModifier.field(SiteMapServlet.class, "productrootpathFrequency").set(siteMapServlet, "daily");
    MemberModifier.field(SiteMapServlet.class, "includeInheritValue").set(siteMapServlet, true);
    MemberModifier.field(SiteMapServlet.class, "includeLastModified").set(siteMapServlet, true);
    MemberModifier.field(SiteMapServlet.class, "changefreqProperties").set(siteMapServlet, strArr);
    MemberModifier.field(SiteMapServlet.class, "priorityProperties").set(siteMapServlet, strArr);
    MemberModifier.field(SiteMapServlet.class, "damAssetProperty").set(siteMapServlet, "");
    MemberModifier.field(SiteMapServlet.class, "damAssetTypes").set(siteMapServlet, lst);
    MemberModifier.field(SiteMapServlet.class, "excludeFromSiteMapProperty").set(siteMapServlet,
        "");
    MemberModifier.field(SiteMapServlet.class, "extensionlessUrls").set(siteMapServlet, false);
    MemberModifier.field(SiteMapServlet.class, "removeTrailingSlash").set(siteMapServlet, false);

    Mockito.when(request.getResponseContentType()).thenReturn("application/xml");
    Mockito.when(request.getResourceResolver()).thenReturn(resourceResolver);
    Mockito.when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
    Mockito.when(request.getResource()).thenReturn(resource);
    Mockito.when(pageManager.getContainingPage(request.getResource())).thenReturn(page);
    Mockito.when(response.getWriter()).thenReturn(writer);
    Mockito.when(page.getProperties()).thenReturn(valueMap);
    Mockito.when(valueMap.get(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(false);
    Mockito.when(page.getPath()).thenReturn("/content/ag/en/shop");
    Mockito.when(resourceResolver.map(Mockito.anyString())).thenReturn("/content/ag/en/shop");
    Mockito.when(page.getVanityUrl()).thenReturn("/shop");
    Mockito.when(page.listChildren(Mockito.any(PageFilter.class),Mockito.anyBoolean())).thenReturn(childrenItr);
    Mockito.when(childrenItr.hasNext()).thenReturn(false);
    Mockito.when(request.getRequestURI()).thenReturn("/content/ag/en/shop.sitemap.xml");
  }

  @Test
  public void testInit(){
    siteMapServlet.activate(config);
  }
  
  @Test
  public void testDoGetSlingHttpServletRequestSlingHttpServletResponse()
      throws ServletException, IOException {
    siteMapServlet.doGet(request, response);
  }

}
