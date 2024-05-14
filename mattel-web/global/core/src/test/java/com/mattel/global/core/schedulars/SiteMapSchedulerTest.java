package com.mattel.global.core.schedulars;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.mattel.global.core.services.GetResourceResolver;
import com.mattel.global.core.services.SiteMapService;
import com.mattel.global.core.utils.PropertyUtils;
import com.mattel.global.core.utils.SiteMapGeneratorUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PropertyUtils.class)
public class SiteMapSchedulerTest {

  @InjectMocks
  private SiteMapScheduler siteMapScheduler;

  @Mock
  private GetResourceResolver getResourceResolver;

  @Mock
  SiteMapGeneratorUtils siteMapGeneratorUtils;

  @Mock
  SiteMapService siteMapService;

  @Before
  public void setup() throws Exception {
    PowerMockito.mockStatic(PropertyUtils.class);
    ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class);
    Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(resourceResolver);
    Mockito.when(resourceResolver.getUserID()).thenReturn("admin");
    String[] brandSiteMapConfig = new String[] { "brand1" };
    Mockito.when(siteMapGeneratorUtils.getBrandSiteMapDetails()).thenReturn(brandSiteMapConfig);
    Map<String, String> brandDetailMap = new HashMap<>();
    brandDetailMap.put("rootSitemapPath", "arg1");
    brandDetailMap.put("siteRootPath", "arg2");
    Mockito.when(PropertyUtils.getBrandDetails("brand1")).thenReturn(brandDetailMap);
    Resource rootPathResource = Mockito.mock(Resource.class);
    Mockito.when(resourceResolver.getResource("arg1")).thenReturn(rootPathResource);
    Asset asset = Mockito.mock(Asset.class);
    Mockito.when(rootPathResource.adaptTo(Asset.class)).thenReturn(asset);
    Session session = Mockito.mock(Session.class);
    Mockito.when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
    Rendition originalRendition = Mockito.mock(Rendition.class);
    Mockito.when(asset.getOriginal()).thenReturn(originalRendition);
    String testString = "test-string";
    InputStream stream = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));
    Mockito.when(originalRendition.getStream()).thenReturn(stream);
    Document doc = Mockito.mock(Document.class);
    Mockito.when(siteMapService.prepareXmlDocument("test-string")).thenReturn(doc);
    NodeList locales = Mockito.mock(NodeList.class);
    Mockito.when(doc.getElementsByTagName("url")).thenReturn(locales);
    Mockito.when(locales.getLength()).thenReturn(1);
    Element locElement = Mockito.mock(Element.class);
    Mockito.when(locales.item(Mockito.anyInt())).thenReturn(locElement);
    Mockito.when(locElement.getNodeType()).thenReturn(org.w3c.dom.Node.ELEMENT_NODE);  
    NodeList urlList = Mockito.mock(NodeList.class);
    Mockito.when(locElement.getElementsByTagName("loc")).thenReturn(urlList);
    Element urlElement = Mockito.mock(Element.class);
    Mockito.when((Element) urlList.item(0)).thenReturn(urlElement);
    NodeList loctextList = Mockito.mock(NodeList.class);
    Mockito.when(urlElement.getChildNodes()).thenReturn(loctextList);
    Node item1 = Mockito.mock(Node.class);
    Mockito.when(loctextList.item(0)).thenReturn(item1);
    Mockito.when(item1.getNodeValue()).thenReturn("item1Value");
    NodeList productPathNodes = Mockito.mock(NodeList.class);
    Mockito.when(locElement.getElementsByTagName("path")).thenReturn(productPathNodes);
    Mockito.when(productPathNodes.getLength()).thenReturn(1);
    Node prodNodeItem1 = Mockito.mock(Node.class);
    Mockito.when(productPathNodes.item(0)).thenReturn(prodNodeItem1);
    Mockito.when(prodNodeItem1.getTextContent()).thenReturn("context-string");
  }

  @Test
  public void testInit() throws Exception {
    siteMapScheduler.run();
  }
}
