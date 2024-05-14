package com.mattel.global.core.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class CtaModelTest {
  
  @InjectMocks
  private CtaModel ctaModel;

  @Test
  public void testIsLinkStatus() throws IllegalArgumentException, IllegalAccessException {
    MemberModifier.field(CtaModel.class, "ctaUrl").set(ctaModel, "/content/dam/image.jpeg");
    ctaModel.isLinkStatus();
  }
}
