package com.mattel.ecomm.core.services;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import org.junit.Assert;

@RunWith(MockitoJUnitRunner.class)
public class GetResourceResolverTest {

  @Mock
  private ResourceResolverFactory resourceResolverFactory;
  @InjectMocks
  GetResourceResolver getResourceResolver;
  @Mock
  private ResourceResolver resourceResolver;

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testGetResourceResolver() throws LoginException {
    Mockito.when(resourceResolverFactory.getServiceResourceResolver(Mockito.any()))
        .thenReturn(resourceResolver);
    Assert.assertNotNull(getResourceResolver.getResourceResolver());
  }

  @Test
  public void testGetResourceResolver_withLoginException() throws LoginException {
    Mockito.when(resourceResolverFactory.getServiceResourceResolver(Mockito.any()))
        .thenThrow(LoginException.class);
    getResourceResolver.getResourceResolver();
  }

  @Test
  public void testGetResourceResolver_withNull() throws LoginException {
    Mockito.when(resourceResolverFactory.getServiceResourceResolver(Mockito.any()))
        .thenReturn(null);
    getResourceResolver.getResourceResolver();
  }

}
