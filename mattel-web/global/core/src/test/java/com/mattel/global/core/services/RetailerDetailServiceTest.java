package com.mattel.global.core.services;

import java.util.Iterator;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;

@RunWith(PowerMockRunner.class)
public class RetailerDetailServiceTest {

  @InjectMocks
  private RetailerDetailService retailerDetailService;
  
  @Mock
  private ResourceResolverFactory resolverFactory;
  
  @Before
  public void setup() throws Exception {
    ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class);
    Mockito.when(resolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenReturn(resourceResolver);
    Resource retailerResource = Mockito.mock(Resource.class);
    Mockito.when(resourceResolver.resolve(Mockito.anyString())).thenReturn(retailerResource);
    Mockito.when(retailerResource.getPath()).thenReturn("retailer-path");
    ContentFragment retailerContentFragment = Mockito.mock(ContentFragment.class);
    Mockito.when(retailerResource.adaptTo(ContentFragment.class)).thenReturn(retailerContentFragment );
    Mockito.when(retailerContentFragment.getName()).thenReturn("retailer-experience-fragment-name");
    @SuppressWarnings("unchecked")
    Iterator<ContentElement> retailerElements = Mockito.mock(Iterator.class);
    Mockito.when(retailerContentFragment.getElements()).thenReturn(retailerElements);
    Mockito.when(retailerElements.hasNext()).thenReturn(true, false);
    ContentElement retailerElement = Mockito.mock(ContentElement.class);
    Mockito.when(retailerElements.next()).thenReturn(retailerElement);
    Mockito.when(retailerElement.getName()).thenReturn("element-name");
    Mockito.when(retailerElement.getContent()).thenReturn("element-content");
  }
  
  @Test
  public void testGetRetailerDetails(){
    retailerDetailService.getRetailerDetails("retailer");
  }
  
}
