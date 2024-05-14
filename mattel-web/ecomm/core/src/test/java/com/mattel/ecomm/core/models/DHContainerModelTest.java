package com.mattel.ecomm.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@RunWith(PowerMockRunner.class)
public class DHContainerModelTest {

    @InjectMocks
    private DHContainerModel dhContainerModel;
    @Mock
    private Resource resource;
    @Mock
    private ResourceResolver resolver;
    @Mock
    private PageManager pageManager;
    @Mock
    private Page page;
    @Mock
    private Resource pageResource;
    @Mock
    private ValueMap valueMap;

    @SuppressWarnings("unchecked")
    @Before
    public void setup() throws Exception {
        dhContainerModel = new DHContainerModel();
        MemberModifier.field(DHContainerModel.class, "resource").set(dhContainerModel, resource);
        Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
        Mockito.when(resolver.adaptTo(PageManager.class)).thenReturn(pageManager);
    }

    @Test
    public void testDhContainerModelWithVanityUrl() throws IllegalArgumentException, IllegalAccessException {

        Mockito.when(pageManager.getContainingPage(resource)).thenReturn(page);
        Mockito.when(pageManager.getContainingPage(resource)).thenReturn(page);
        Mockito.when(page.getVanityUrl()).thenReturn("/shop/c");
        dhContainerModel.init();

        Assert.assertNotNull(dhContainerModel.getLandingPageUrl());
    }

    @Test
    public void testDhContainerModelWithoutVanityUrl() throws IllegalArgumentException, IllegalAccessException {

        Mockito.when(pageManager.getContainingPage(resource)).thenReturn(page);
        Mockito.when(pageManager.getContainingPage(resource)).thenReturn(page);
        Mockito.when(page.getVanityUrl()).thenReturn("");
        Mockito.when(page.getProperties()).thenReturn(null);
        Mockito.when(page.getContentResource()).thenReturn(pageResource);
        Mockito.when(pageResource.getValueMap()).thenReturn(valueMap);
        Mockito.when(valueMap.get("landingPagePath", String.class))
                .thenReturn("/content/ag/en/doll-hospital/basic-info");

        dhContainerModel.init();

        Assert.assertNotNull(dhContainerModel.getLandingPageUrl());
    }

    @Test
    public void testDhContainerModelWithRedirectUrl() throws IllegalArgumentException, IllegalAccessException {
        Mockito.when(pageManager.getContainingPage(resource)).thenReturn(page);
        Mockito.when(pageManager.getContainingPage(resource)).thenReturn(page);
        Mockito.when(page.getVanityUrl()).thenReturn("");
        Mockito.when(page.getProperties()).thenReturn(valueMap);
        Mockito.when(valueMap.containsKey("cq:redirectTarget")).thenReturn(true);
        Mockito.when(valueMap.get("cq:redirectTarget", String.class)).thenReturn("");
        dhContainerModel.init();

        Assert.assertNotNull(dhContainerModel.getLandingPageUrl());
    }

}
