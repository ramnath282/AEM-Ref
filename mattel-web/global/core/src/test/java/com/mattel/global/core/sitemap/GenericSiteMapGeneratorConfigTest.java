package com.mattel.global.core.sitemap;

import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.mattel.global.core.services.GetResourceResolver;

@RunWith(MockitoJUnitRunner.class)
public class GenericSiteMapGeneratorConfigTest {
  @Mock
  private GetResourceResolver getResourceResolver;
  @InjectMocks
  private GenericSiteMapGeneratorConfig genericSiteMapGeneratorConfig;

  @Before
  public void setUp() {
    GenericSiteMapGeneratorConfig.Config config = Mockito.mock(GenericSiteMapGeneratorConfig.Config.class);
    ResourceResolver rr = Mockito.mock(ResourceResolver.class);
    String schemaPath = "ag_en:/content/dam/ag-dam/ag-global-dam/parent-site-dam";
    Mockito.when(config.schemaPath()).thenReturn(schemaPath);
    Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(rr);
    Mockito.when(rr.getResource(schemaPath)).thenReturn(null);
    genericSiteMapGeneratorConfig.activate(config);
  }

  @Test
  public void testGetSiteMapConfig() throws Exception {
    Assert.assertNotNull(genericSiteMapGeneratorConfig.getSiteMapConfig("ag_en"));
    Assert.assertNotNull(genericSiteMapGeneratorConfig.getSiteMapConfig("ag_fr"));
    Assert.assertNotNull(genericSiteMapGeneratorConfig.getSiteMapConfigByRootPath("/content/ag/en"));
    Assert.assertNotNull(genericSiteMapGeneratorConfig.getSiteMapConfigByRelPath("/content/ag/en/shop"));
  }
}
