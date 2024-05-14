package com.mattel.global.core.services;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class GetResourceResolverTest {

  @InjectMocks
  private GetResourceResolver getResourceResolver;

  @Mock
  private ResourceResolverFactory resourceResolverFactory;

  @Before
  public void setup() throws LoginException {
    ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class);
    Map<String, Object> usermap = new HashMap<>();
    usermap.put(ResourceResolverFactory.SUBSERVICE, "readwriteservice");
    Mockito.when(resourceResolverFactory.getServiceResourceResolver(Mockito.anyMap()))
        .thenReturn(resourceResolver);
  }

  @Test
  public void testGetResourceResolver() {
    getResourceResolver.getResourceResolver();
  }

}
