package com.mattel.ecomm.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.wcm.api.PageManager;
import com.mattel.ecomm.core.interfaces.ProductService;

@RunWith(PowerMockRunner.class)
public class RelatedSearchesTest {
  @InjectMocks
  private RelatedSearches relatedSearches;

  @Mock
  SlingHttpServletRequest request;

  @Mock
  Resource resource;

  @Mock
  ProductService productService;

  PageManager pageManager;
  ResourceResolver resourceResolver;

  @Before
  public void setUp() throws Exception {

    pageManager = Mockito.mock(PageManager.class);
    resourceResolver = Mockito.mock(ResourceResolver.class);

    MemberModifier.field(RelatedSearches.class, "request").set(relatedSearches, request);
    MemberModifier.field(RelatedSearches.class, "resource").set(relatedSearches, resource);
    MemberModifier.field(RelatedSearches.class, "productService").set(relatedSearches,
        productService);

    Mockito.when(resource.getPath()).thenReturn("resourcepath");
    Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
    Mockito.when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
    Mockito.when(productService.getCurrentPagePath(pageManager, resource, request))
        .thenReturn(null);
  }

  @Test
  public void testInit() {
    relatedSearches.init();
  }

  @Test
  public void testGetters() {
    Assert.assertEquals(false, relatedSearches.getEnableRelatedSearches());
    Assert.assertEquals("", relatedSearches.getRelatedSearchTitle());
  }

}
