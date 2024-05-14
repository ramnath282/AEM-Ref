package com.mattel.global.core.model;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class LightBoxContainerModelTest {
    @InjectMocks
    private LightBoxContainerModel lightBoxContainerModel;

    @Mock
    Resource resource;

    String image = "/context/dam/fp-dam/image-path";

    @Mock
    Resource imageres;

    @Mock
    ResourceResolver resolver;

    @Test
    public void testInit() throws IllegalArgumentException, IllegalAccessException {
        MemberModifier.field(LightBoxContainerModel.class, "resource").set(lightBoxContainerModel, resource);
        MemberModifier.field(LightBoxContainerModel.class, "image").set(lightBoxContainerModel, image);
        Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
        Mockito.when(resolver.resolve(image+"/jcr:content/renditions/original")).thenReturn(imageres);
        Mockito.when(imageres.getPath()).thenReturn("/context/dam/fp-dam/image-path/jcr:content/renditions/original");
        lightBoxContainerModel.init();

    }

    @Test
    public void testGetBackgroundImagePath() {
        lightBoxContainerModel.setCookieExpiry("cookieExpiry");
        lightBoxContainerModel.setCookieName("cookieName");
        lightBoxContainerModel.setEnableCookies("enableCookies");
        lightBoxContainerModel.getBackgroundImagePath();
        lightBoxContainerModel.getCookieExpiry();
        lightBoxContainerModel.getCookieName();
        lightBoxContainerModel.getEnableCookies();

    }
}