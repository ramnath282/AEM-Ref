package com.mattel.global.core.model.v2;

import static org.junit.Assert.assertEquals;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.global.core.utils.GlobalUtils;

/**
 * @author CTS
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(GlobalUtils.class)
public class HeroImageBannerModelTest {

  @InjectMocks
  HeroImageBannerModel heroImageBannerModel;

  @Mock
  private Resource resource;

  @Before
  public void setup() throws Exception {
    MemberModifier.field(HeroImageBannerModel.class, "image").set(heroImageBannerModel,
        "/imageUrl");
    MemberModifier.field(HeroImageBannerModel.class, "linkUrl").set(heroImageBannerModel,
        "/linkUrl");

    PowerMockito.mockStatic(GlobalUtils.class);
    ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class);
    Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
    Resource imageResource = Mockito.mock(Resource.class);
    Mockito.when(resourceResolver.resolve(Mockito.anyString())).thenReturn(imageResource);
    Mockito.when(imageResource.getPath()).thenReturn("/testResourcePath");
    MemberModifier.field(HeroImageBannerModel.class, "backgroundImagePath")
        .set(heroImageBannerModel, "/backgroundImagePath");
  }

  @Test
  public void testInit() throws Exception {
    heroImageBannerModel.init();
  }

  @Test
  public void testGetterSetter() {
    heroImageBannerModel.getBackgroundImagePath();
    heroImageBannerModel.getLinkUrl();
    assertEquals("/backgroundImagePath", heroImageBannerModel.getBackgroundImagePath());
    assertEquals("/linkUrl", heroImageBannerModel.getLinkUrl());
  }
}
