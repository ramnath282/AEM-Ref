package com.mattel.global.core.model.v1;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
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

@PrepareForTest(GlobalUtils.class)
@RunWith(PowerMockRunner.class)
public class CtaItemModelTest {

  @InjectMocks
  CtaItemModel ctaItemModel;

  @Mock
  Resource resource;

  @Before
  public void setup() throws IllegalArgumentException, IllegalAccessException {
    PowerMockito.mockStatic(GlobalUtils.class);
    MemberModifier.field(CtaItemModel.class, "linkURL").set(ctaItemModel, "#");
    MemberModifier.field(CtaItemModel.class, "linkText").set(ctaItemModel, "<p>linkText/<p>");
    MemberModifier.field(CtaItemModel.class, "useInterstitial").set(ctaItemModel, true);
    Mockito.when(GlobalUtils.getUrl("linkURL", resource)).thenReturn("#");

    Map<String, String> interstitialDetail = new HashMap<>();
    interstitialDetail.put("interstitialPath", "interstitialPath");
    interstitialDetail.put("interstitialType", "interstitialType");
    interstitialDetail.put("interstitialResType", "interstitialResType");
    Mockito.when(GlobalUtils.getInterstitialDetails(resource)).thenReturn(interstitialDetail);
  }

  @Test
  public void testInit() throws IllegalArgumentException, IllegalAccessException {
    Resource parentResource = Mockito.mock(Resource.class);
    Mockito.when(resource.getParent()).thenReturn(parentResource);
    Mockito.when(GlobalUtils.isOnlyCTA(parentResource)).thenReturn(true);

    ValueMap valueMap = Mockito.mock(ValueMap.class);
    Mockito.when(parentResource.getValueMap()).thenReturn(valueMap);
    Mockito.when(valueMap.get("useInterstitial", Boolean.class)).thenReturn(true);
    Mockito.when(GlobalUtils.isFirstCTA(parentResource, resource)).thenReturn(true);
    MemberModifier.field(CtaItemModel.class, "useInterstitial").set(ctaItemModel, true);
    ctaItemModel.init();
  }

  @Test
  public void testInit_1() throws IllegalArgumentException, IllegalAccessException {
    Resource parentResource = Mockito.mock(Resource.class);
    Mockito.when(resource.getParent()).thenReturn(parentResource);
    Mockito.when(GlobalUtils.isOnlyCTA(parentResource)).thenReturn(true);

    ValueMap valueMap = Mockito.mock(ValueMap.class);
    Mockito.when(parentResource.getValueMap()).thenReturn(valueMap);
    Mockito.when(valueMap.get("useInterstitial", Boolean.class)).thenReturn(true);
    Mockito.when(GlobalUtils.isFirstCTA(parentResource, resource)).thenReturn(true);
    MemberModifier.field(CtaItemModel.class, "useInterstitial").set(ctaItemModel, false);
    ctaItemModel.init();
  }

  @Test
  public void testInit_2() throws IllegalArgumentException, IllegalAccessException {
    Mockito.when(resource.getParent()).thenReturn(null);
    ctaItemModel.init();
  }

  @Test
  public void testInit_3() throws IllegalArgumentException, IllegalAccessException {
    Mockito.when(resource.getParent()).thenReturn(null);
    ValueMap valueMap = Mockito.mock(ValueMap.class);
    Mockito.when(valueMap.get("useInterstitial", Boolean.class)).thenReturn(true);
    MemberModifier.field(CtaItemModel.class, "useInterstitial").set(ctaItemModel, false);
    ctaItemModel.init();
  }

  @Test
  public void testGetters() throws IllegalArgumentException, IllegalAccessException {
    MemberModifier.field(CtaItemModel.class, "url").set(ctaItemModel, "url");
    MemberModifier.field(CtaItemModel.class, "interstitialType").set(ctaItemModel,
        "interstitialType");
    MemberModifier.field(CtaItemModel.class, "interstitialSelectionFragmentPath").set(ctaItemModel,
        "interstitialSelectionFragmentPath");
    MemberModifier.field(CtaItemModel.class, "isFirstCTA").set(ctaItemModel, true);
    MemberModifier.field(CtaItemModel.class, "interstitialPath").set(ctaItemModel,
        "interstitialPath");
    MemberModifier.field(CtaItemModel.class, "interstitialResTypeName").set(ctaItemModel,
        "interstitialResTypeName");

    Assert.assertNotNull(ctaItemModel.getUrl());
    Assert.assertNotNull(ctaItemModel.getInterstitialType());
    Assert.assertNotNull(ctaItemModel.getInterstitialSelectionFragmentPath());
    Assert.assertNotNull(ctaItemModel.getIsFirstCTA());
    Assert.assertNotNull(ctaItemModel.getInterstitialPath());
    Assert.assertNotNull(ctaItemModel.getInterstitialResTypeName());
  }

}
