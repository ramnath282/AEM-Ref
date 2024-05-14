package com.mattel.global.master.core.model;

import org.apache.sling.api.resource.Resource;
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

import com.mattel.global.core.model.v1.CtaItemModel;
import com.mattel.global.core.utils.GlobalUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GlobalUtils.class)
public class CommonCarouselContainerModelTest {

  @InjectMocks
  private CommonCarouselContainerModel commonCarouselContainerModel;

  @Mock
  private Resource resource;

  @Before
  public void setUp() throws Exception {
    PowerMockito.mockStatic(GlobalUtils.class);
    MemberModifier.field(CommonCarouselContainerModel.class, "entrCompClickable")
        .set(commonCarouselContainerModel, true);

    Resource carouselResource = Mockito.mock(Resource.class);
    Mockito.when(GlobalUtils.getCtaURL(resource)).thenReturn(carouselResource);
    CtaItemModel ctaModel = Mockito.mock(CtaItemModel.class);
    Mockito.when(carouselResource.adaptTo(CtaItemModel.class)).thenReturn(ctaModel);
    Mockito.when(ctaModel.getUrl()).thenReturn("ctaurl");

    MemberModifier.field(CommonCarouselContainerModel.class, "timer")
        .set(commonCarouselContainerModel, "4");
    MemberModifier.field(CommonCarouselContainerModel.class, "slideToShow")
        .set(commonCarouselContainerModel, "4");
    MemberModifier.field(CommonCarouselContainerModel.class, "slidetoscroll")
        .set(commonCarouselContainerModel, "4");
    MemberModifier.field(CommonCarouselContainerModel.class, "autoPlay")
        .set(commonCarouselContainerModel, "true");
    MemberModifier.field(CommonCarouselContainerModel.class, "infinte")
        .set(commonCarouselContainerModel, "true");
    MemberModifier.field(CommonCarouselContainerModel.class, "freeform")
    .set(commonCarouselContainerModel, "true");
    MemberModifier.field(CommonCarouselContainerModel.class, "carouselLinkOption")
    .set(commonCarouselContainerModel, "carouselLinkOption");
    MemberModifier.field(CommonCarouselContainerModel.class, "url")
    .set(commonCarouselContainerModel, "url");
  }

  @Test
  public void testInit() throws Exception {
    commonCarouselContainerModel.init();
  }

  @Test
  public void testGetterSetters() {
    commonCarouselContainerModel.getBackgroundImagePath();
    commonCarouselContainerModel.getCarouselLinkOption();
    commonCarouselContainerModel.getEntrCompClickable();
    commonCarouselContainerModel.getFreeFormMob();
    commonCarouselContainerModel.getFreeFormTab();
    commonCarouselContainerModel.getUrl();

    commonCarouselContainerModel.getTimer();
    commonCarouselContainerModel.getSlideToShow();
    commonCarouselContainerModel.getSlidetoscroll();
    commonCarouselContainerModel.getAutoPlay();
    commonCarouselContainerModel.getInfinte();
  }

}
