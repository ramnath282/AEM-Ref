package com.mattel.global.core.sitemap;

import com.day.cq.commons.Externalizer;
import com.mattel.global.core.services.GetResourceResolver;

import java.io.StringWriter;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import io.wcm.testing.mock.aem.junit.AemContext;

@RunWith(MockitoJUnitRunner.class)
public class GenericSiteMapServiceTest {
  @Rule
  public final AemContext context = new AemContext(ResourceResolverType.JCR_MOCK);
  @Mock
  private Externalizer externalizer;
  @InjectMocks
  private GenericSiteMapService genericSiteMapService;

  private MockSlingHttpServletRequest request;

  GenericSiteMapGeneratorConfig genericSiteMapGeneratorConfig;
  
  SiteConfig siteConfig;

  @Before
  public void setUp() throws Exception {
    context.load().json(getClass().getResourceAsStream("SiteMapServlet.json"), "/content/ag");
    context.load().json(getClass().getResourceAsStream("Products.json"), "/var/commerce/ag/en");
    context.load().binaryFile(getClass().getResourceAsStream("product.csv"), "/content/dam/ag_en/nodes.txt");
    request = new MockSlingHttpServletRequest(context.resourceResolver(), context.bundleContext()) {
        @Override
        public String getResponseContentType() {
            return "text/xml";
        }
    };

    
    request.setResource(context.resourceResolver().getResource("/content/ag/en"));
    Mockito.when(externalizer.externalLink(Mockito.eq(context.resourceResolver()), Mockito.eq("agdomainstage"), Mockito.anyString())).then(i -> "http://mstg.americangirl.com" + i.getArgument(2));
    GenericSiteMapGeneratorConfig.Config config = Mockito.mock(GenericSiteMapGeneratorConfig.Config.class);
    ResourceResolver rr = Mockito.mock(ResourceResolver.class);
    String schemaPath = "ag_en:/content/dam/ag-dam/ag-global-dam/parent-site-dam";
    Mockito.when(config.schemaPath()).thenReturn(schemaPath);
    GetResourceResolver getResourceResolver = Mockito.mock(GetResourceResolver.class);
    Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(rr);
    Mockito.when(rr.getResource(schemaPath)).thenReturn(null);
    genericSiteMapGeneratorConfig = new GenericSiteMapGeneratorConfig();
    genericSiteMapGeneratorConfig.setGetResourceResolver(getResourceResolver);
    genericSiteMapGeneratorConfig.activate(config);
    siteConfig=  genericSiteMapGeneratorConfig.getSiteMapConfigByRootPath("/content/ag/en");
  }

  @Test
  public void testBuildSiteMap() throws Exception {
    StringWriter sw = new StringWriter();
    genericSiteMapService.buildSiteMap(sw, request.getResourceResolver(), siteConfig);
    //Assert.assertTrue(StringUtils.isNotEmpty(sw.toString()));
    System.out.println(sw.toString());
  }
}
