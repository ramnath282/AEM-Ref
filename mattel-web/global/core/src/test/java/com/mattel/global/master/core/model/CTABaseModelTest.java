package com.mattel.global.master.core.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class CTABaseModelTest {

  @InjectMocks
  CTABaseModel ctaBaseModel;

  @Before
  public void setup() throws IllegalArgumentException, IllegalAccessException {
    MemberModifier.field(CTABaseModel.class, "trackThisCTA").set(ctaBaseModel, true);
    MemberModifier.field(CTABaseModel.class, "trackingText").set(ctaBaseModel, "trackingText");
    MemberModifier.field(CTABaseModel.class, "linkOptions").set(ctaBaseModel, "newwindow");
    MemberModifier.field(CTABaseModel.class, "useInterstitial").set(ctaBaseModel, true);
    MemberModifier.field(CTABaseModel.class, "interstitialSelectionIndItem").set(ctaBaseModel,
        "interstitialSelectionIndItem");
    MemberModifier.field(CTABaseModel.class, "fillColor").set(ctaBaseModel, "fillColor");
    MemberModifier.field(CTABaseModel.class, "linkText").set(ctaBaseModel, "linkText");
    MemberModifier.field(CTABaseModel.class, "linkAltText").set(ctaBaseModel, "linkAltText");
  }

  @Test
  public void testGetterSetter() {
    Assert.assertNotNull(ctaBaseModel.getTrackThisCTA());
    Assert.assertNotNull(ctaBaseModel.getTrackingText());
    Assert.assertNotNull(ctaBaseModel.getLinkOptions());
    Assert.assertNotNull(ctaBaseModel.getUseInterstitial());
    Assert.assertNotNull(ctaBaseModel.getInterstitialSelectionIndItem());
    Assert.assertNotNull(ctaBaseModel.fillColor);
    Assert.assertNotNull(ctaBaseModel.getLinkText());
    Assert.assertNotNull(ctaBaseModel.getLinkAltText());
  }

}
