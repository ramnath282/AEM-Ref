package com.mattel.global.master.core.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class AppItemModelTest {

	@InjectMocks
	private AppItemModel appItemModel;

	@Before
	public void setup() throws IllegalArgumentException, IllegalAccessException {
		MemberModifier.field(AppItemModel.class, "selectApp").set(appItemModel, "selectApp");
		MemberModifier.field(AppItemModel.class, "relativeAppPath").set(appItemModel, "relativeAppPath");
		MemberModifier.field(AppItemModel.class, "ctaTrack").set(appItemModel, "ctaTrack");
		MemberModifier.field(AppItemModel.class, "trackingText").set(appItemModel, "trackingText");
		MemberModifier.field(AppItemModel.class, "linkOptions").set(appItemModel, "linkOptions");
		MemberModifier.field(AppItemModel.class, "useInterstitial").set(appItemModel, "useInterstitial");
		MemberModifier.field(AppItemModel.class, "interstitialPath").set(appItemModel, "interstitialPath");
		MemberModifier.field(AppItemModel.class, "fillColor").set(appItemModel, "fillColor");
		MemberModifier.field(AppItemModel.class, "linkText").set(appItemModel, "linkText");
		MemberModifier.field(AppItemModel.class, "linkAltText").set(appItemModel, "linkAltText");
	}
	
	@Test
	public void testGetters(){
		Assert.assertNotNull(appItemModel.getSelectApp());
		Assert.assertNotNull(appItemModel.getRelativeAppPath());
		Assert.assertNotNull(appItemModel.getCtaTrack());
		Assert.assertNotNull(appItemModel.getTrackingText());
		Assert.assertNotNull(appItemModel.getLinkOptions());
		Assert.assertNotNull(appItemModel.getUseInterstitial());
		Assert.assertNotNull(appItemModel.getInterstitialPath());
		Assert.assertNotNull(appItemModel.getFillColor());
		Assert.assertNotNull(appItemModel.getLinkText());
		Assert.assertNotNull(appItemModel.getLinkAltText());
	}
}
