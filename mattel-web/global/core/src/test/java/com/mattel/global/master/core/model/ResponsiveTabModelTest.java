package com.mattel.global.master.core.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class ResponsiveTabModelTest {

  @InjectMocks
  ResponsiveTabModel responsiveTabModel;

  @Before
  public void setUp() throws Exception {
    MemberModifier.field(ResponsiveTabModel.class, "xlColumns").set(responsiveTabModel, "1");
    MemberModifier.field(ResponsiveTabModel.class, "largeColumns").set(responsiveTabModel, "1");
    MemberModifier.field(ResponsiveTabModel.class, "mediumColumns").set(responsiveTabModel, "1");
    MemberModifier.field(ResponsiveTabModel.class, "smallColumns").set(responsiveTabModel, "1");
    MemberModifier.field(ResponsiveTabModel.class, "xsColumns").set(responsiveTabModel, "1");

    MemberModifier.field(ResponsiveTabModel.class, "ctaRepeat").set(responsiveTabModel, "true");
    MemberModifier.field(ResponsiveTabModel.class, "scrollMobile").set(responsiveTabModel, "true");

    MemberModifier.field(ResponsiveTabModel.class, "showMoreFeature").set(responsiveTabModel,
        "true");
    MemberModifier.field(ResponsiveTabModel.class, "showMoreRepeat").set(responsiveTabModel,
        "true");

    MemberModifier.field(ResponsiveTabModel.class, "xlInitial").set(responsiveTabModel, "1");
    MemberModifier.field(ResponsiveTabModel.class, "largeInitial").set(responsiveTabModel, "1");
    MemberModifier.field(ResponsiveTabModel.class, "mediumInitial").set(responsiveTabModel, "1");
    MemberModifier.field(ResponsiveTabModel.class, "smallInitial").set(responsiveTabModel, "1");
    MemberModifier.field(ResponsiveTabModel.class, "xsInitial").set(responsiveTabModel, "1");

    MemberModifier.field(ResponsiveTabModel.class, "xlShowMore").set(responsiveTabModel, "1");
    MemberModifier.field(ResponsiveTabModel.class, "largeShowMore").set(responsiveTabModel, "1");
    MemberModifier.field(ResponsiveTabModel.class, "mediumShowMore").set(responsiveTabModel, "1");
    MemberModifier.field(ResponsiveTabModel.class, "smallShowMore").set(responsiveTabModel, "1");
    MemberModifier.field(ResponsiveTabModel.class, "xsShowMore").set(responsiveTabModel, "1");
  }

  @Test
  public void testToVerifyResponsiveTabModel() {
    Assert.assertEquals("1", responsiveTabModel.getXlColumns());
    Assert.assertEquals("1", responsiveTabModel.getLargeColumns());
    Assert.assertEquals("1", responsiveTabModel.getMediumColumns());
    Assert.assertEquals("1", responsiveTabModel.getSmallColumns());
    Assert.assertEquals("1", responsiveTabModel.getXsColumns());

    Assert.assertEquals("true", responsiveTabModel.getCtaRepeat());
    Assert.assertEquals("true", responsiveTabModel.getScrollMobile());

    Assert.assertEquals("true", responsiveTabModel.getShowMoreFeature());
    Assert.assertEquals("true", responsiveTabModel.getShowMoreRepeat());

    Assert.assertEquals("1", responsiveTabModel.getXlInitial());
    Assert.assertEquals("1", responsiveTabModel.getLargeInitial());
    Assert.assertEquals("1", responsiveTabModel.getMediumInitial());
    Assert.assertEquals("1", responsiveTabModel.getSmallInitial());
    Assert.assertEquals("1", responsiveTabModel.getXsInitial());

    Assert.assertEquals("1", responsiveTabModel.getXlShowMore());
    Assert.assertEquals("1", responsiveTabModel.getLargeShowMore());
    Assert.assertEquals("1", responsiveTabModel.getMediumShowMore());
    Assert.assertEquals("1", responsiveTabModel.getSmallShowMore());
    Assert.assertEquals("1", responsiveTabModel.getXsShowMore());
  }

}
