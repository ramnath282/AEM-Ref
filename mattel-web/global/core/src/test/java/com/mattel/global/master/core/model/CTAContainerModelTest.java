package com.mattel.global.master.core.model;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Assert;
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

@RunWith(PowerMockRunner.class)
@PrepareForTest(GlobalUtils.class)
public class CTAContainerModelTest {

  @InjectMocks
  private CTAContainerModel ctaContainerModel;

  @Mock
  private Resource resource;

  @Before
  public void setup() throws Exception {
    PowerMockito.mockStatic(GlobalUtils.class);
    ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class);
    Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
    MemberModifier.field(CTAContainerModel.class, "backgroundImagePath").set(ctaContainerModel,
        "backgroundImagePath");
    MemberModifier.field(CTAContainerModel.class, "useInterstitial").set(ctaContainerModel,true);
    MemberModifier.field(CTAContainerModel.class, "interstitialType").set(ctaContainerModel,
        "interstitialType");
    MemberModifier.field(CTAContainerModel.class, "backgroundImagePath").set(ctaContainerModel,
        "backgroundImagePath");
    MemberModifier.field(CTAContainerModel.class, "interstitialSelection").set(ctaContainerModel,
        "interstitialSelection");
    Mockito.when(GlobalUtils.checkLink("interstitialSelection", resource)).thenReturn("");
  }

  @Test
  public void testInit() throws Exception {
    ctaContainerModel.init();
  }

  @Test
  public void testGetterSetter() throws Exception {
    Assert.assertNotNull(ctaContainerModel.getBackgroundImagePath());
    Assert.assertNotNull(ctaContainerModel.getInterstitialType());
    Assert.assertNotNull(ctaContainerModel.getUseInterstitial());
    Assert.assertNotNull(ctaContainerModel.getInterstitialSelection());
  }

}
