/**
 * 
 */
package com.mattel.global.master.core.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class AnchorCTAModelTest {
  @InjectMocks
  private AnchorCTAModel anchorCTAModel;

  @Before
  public void setup() throws Exception {
    MemberModifier.field(AnchorCTAModel.class, "anchorName").set(anchorCTAModel, "anchorName");
    MemberModifier.field(AnchorCTAModel.class, "ctaTrack").set(anchorCTAModel, "ctaTrack");
    MemberModifier.field(AnchorCTAModel.class, "trackingText").set(anchorCTAModel, "trackingText");
    MemberModifier.field(AnchorCTAModel.class, "linkOptions").set(anchorCTAModel, "linkOptions");
    MemberModifier.field(AnchorCTAModel.class, "fillColor").set(anchorCTAModel, "fillColor");
    MemberModifier.field(AnchorCTAModel.class, "linkText").set(anchorCTAModel, "linkText");
    MemberModifier.field(AnchorCTAModel.class, "linkAltText").set(anchorCTAModel, "linkAltText");

  }

  @Test
  public void testToVerifyAnchorCTAModel() {
    assertEquals("anchorName", anchorCTAModel.getAnchorName());
    assertEquals("ctaTrack", anchorCTAModel.getCtaTrack());
    assertEquals("trackingText", anchorCTAModel.getTrackingText());
    assertEquals("linkOptions", anchorCTAModel.getLinkOptions());
    assertEquals("fillColor", anchorCTAModel.getFillColor());
    assertEquals("linkText", anchorCTAModel.getLinkText());
    assertEquals("linkAltText", anchorCTAModel.getLinkAltText());
  }

}
