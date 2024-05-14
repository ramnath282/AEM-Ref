package com.mattel.global.core.model.v1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.global.core.utils.GlobalUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GlobalUtils.class)
public class AnchorModelTest {

	@InjectMocks
	private AnchorModel anchorModel;
	
	@Before
	public void setup() throws IllegalArgumentException, IllegalAccessException{
		MemberModifier.field(AnchorModel.class, "anchorName").set(anchorModel, "anchorName");
		MemberModifier.field(AnchorModel.class, "anchorID").set(anchorModel, "anchorID");
		MemberModifier.field(AnchorModel.class, "anchorNavUrl").set(anchorModel, "anchorNavUrl");
    MemberModifier.field(AnchorModel.class, "anchorNavAltText").set(anchorModel, "anchorNavAltText");
    MemberModifier.field(AnchorModel.class, "linkOptions").set(anchorModel, "linkOptions");
    MemberModifier.field(AnchorModel.class, "trackThisCTA").set(anchorModel, "trackThisCTA");
    MemberModifier.field(AnchorModel.class, "trackingText").set(anchorModel, "trackingText");
	}
	
	@Test
	public void testGetters(){
		anchorModel.setAnchorNavUrl("setAnchorNavUrl");
		Assert.assertNotNull(anchorModel.getAnchorID());
		Assert.assertNotNull(anchorModel.getAnchorName());
    Assert.assertNotNull(anchorModel.getAnchorNavAltText());
    Assert.assertNotNull(anchorModel.getAnchorNavUrl());
    Assert.assertNotNull(anchorModel.getLinkOptions());
    Assert.assertNotNull(anchorModel.getTrackingText());
    Assert.assertNotNull(anchorModel.getTrackThisCTA());
	}
}
