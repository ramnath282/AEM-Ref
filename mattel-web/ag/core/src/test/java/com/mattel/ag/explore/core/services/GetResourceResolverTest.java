package com.mattel.ag.explore.core.services;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GetResourceResolverTest {

    @InjectMocks
    private GetResourceResolver getResourceResolver;

    @Mock
    private ResourceResolverFactory resourceResolverFactory;
    @Mock
    private ResourceResolver resourceResolver;

    @Test
    public void testGetResourceResolver() throws LoginException {
        Mockito.when(resourceResolverFactory.getServiceResourceResolver(Mockito.any())).thenReturn(resourceResolver);
        Assert.assertNotNull(getResourceResolver.getResourceResolver());
    }

    @Test
    public void testGetResourceResolver_withNull() throws LoginException {
        Mockito.when(resourceResolverFactory.getServiceResourceResolver(Mockito.any())).thenReturn(null);
        Assert.assertNull(getResourceResolver.getResourceResolver());
    }

    @Test
    public void testGetResourceResolverWithException() throws LoginException {
        Mockito.when(resourceResolverFactory.getServiceResourceResolver(Mockito.any())).thenThrow(LoginException.class);
        getResourceResolver.getResourceResolver();
    }

}
