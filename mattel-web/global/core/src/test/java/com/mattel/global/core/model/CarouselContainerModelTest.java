package com.mattel.global.core.model;

import com.adobe.cq.wcm.core.components.models.Carousel;
import com.adobe.cq.wcm.core.components.models.ListItem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

@RunWith(PowerMockRunner.class)
public class CarouselContainerModelTest {

  @InjectMocks
  CarouselContainerModel carouselContainerModel;
  @Mock
  Carousel carousel;

  List<ListItem> items;

  @SuppressWarnings("unchecked")
  @Before
  public void setUp() throws Exception {
    MemberModifier.field(CarouselContainerModel.class, "carousel").set(carouselContainerModel,
        carousel);

    MemberModifier.field(CarouselContainerModel.class, "outerArrows").set(carouselContainerModel,
        true);
    MemberModifier.field(CarouselContainerModel.class, "dots").set(carouselContainerModel, true);
    MemberModifier.field(CarouselContainerModel.class, "arrows").set(carouselContainerModel, true);
    MemberModifier.field(CarouselContainerModel.class, "autoPlay").set(carouselContainerModel,
        true);
    MemberModifier.field(CarouselContainerModel.class, "centerMode").set(carouselContainerModel,
        true);

    MemberModifier.field(CarouselContainerModel.class, "slidetoscroll").set(carouselContainerModel,
        "slidetoscroll");
    MemberModifier.field(CarouselContainerModel.class, "slideToShow").set(carouselContainerModel,
        "slideToShow");
    MemberModifier.field(CarouselContainerModel.class, "slideRotationSpeed").set(carouselContainerModel,
            "slideRotationSpeed");
    MemberModifier.field(CarouselContainerModel.class, "textPosition").set(carouselContainerModel,
        "textPosition");
    MemberModifier.field(CarouselContainerModel.class, "description").set(carouselContainerModel,
        "description");
    MemberModifier.field(CarouselContainerModel.class, "title").set(carouselContainerModel,
        "title");
    items = Mockito.mock(List.class);
    Mockito.when(carousel.getItems()).thenReturn(items);
  }

  @Test
  public void testGetters() {
    Assert.assertEquals(true, carouselContainerModel.isArrows());
    Assert.assertEquals(true, carouselContainerModel.isOuterArrows());
    Assert.assertEquals(true, carouselContainerModel.isAutoPlay());
    Assert.assertEquals(true, carouselContainerModel.isCenterMode());
    Assert.assertEquals(true, carouselContainerModel.isDots());

    Assert.assertEquals("description", carouselContainerModel.getDescription());
    Assert.assertEquals("slidetoscroll", carouselContainerModel.getSlidetoscroll());
    Assert.assertEquals("slideToShow", carouselContainerModel.getSlideToShow());
    Assert.assertEquals("slideRotationSpeed", carouselContainerModel.getSlideRotationSpeed());
    Assert.assertEquals("textPosition", carouselContainerModel.getTextPosition());
    Assert.assertEquals("title", carouselContainerModel.getTitle());
    Assert.assertEquals(items, carouselContainerModel.getItems());
  }
}
