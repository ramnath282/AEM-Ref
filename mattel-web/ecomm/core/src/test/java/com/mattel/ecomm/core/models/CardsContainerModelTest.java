package com.mattel.ecomm.core.models;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.adobe.cq.wcm.core.components.models.Carousel;
import com.adobe.cq.wcm.core.components.models.ListItem;

@RunWith(PowerMockRunner.class)
public class CardsContainerModelTest {

  @InjectMocks
  CardsContainerModel cardsContainerModel;

  @Mock
  Carousel carousel;

  @Mock
  Resource resource;

  List<ListItem> items;

  ListItem listItem;

  @SuppressWarnings("unchecked")
  @Before
  public void setUp() throws Exception {
    MemberModifier.field(CardsContainerModel.class, "carousel").set(cardsContainerModel, carousel);
    MemberModifier.field(CardsContainerModel.class, "resource").set(cardsContainerModel, resource);
    MemberModifier.field(CardsContainerModel.class, "dataXsSlider").set(cardsContainerModel,
        "dataXsSlider");
    MemberModifier.field(CardsContainerModel.class, "ctaLink").set(cardsContainerModel, "ctaLink");

    items = Mockito.mock(List.class);
  }

  @Test
  public void testInitLinkNotCategory() throws Exception {
    MemberModifier.field(CardsContainerModel.class, "link").set(cardsContainerModel, "link");
    cardsContainerModel.init();
  }

  @Test
  public void testInitLinkCategory() throws Exception {
    MemberModifier.field(CardsContainerModel.class, "link").set(cardsContainerModel, "categories");
    cardsContainerModel.init();
  }

  @Test
  public void testInitWithLinkEmpty() throws Exception {
    MemberModifier.field(CardsContainerModel.class, "link").set(cardsContainerModel, "");
    cardsContainerModel.init();
  }

  @Test
  public void testGetterSetters() {
    Assert.assertEquals("ctaLink", cardsContainerModel.getCtaLink());
    Assert.assertEquals("dataXsSlider", cardsContainerModel.getDataXsSlider());
    Mockito.when(carousel.getItems()).thenReturn(items);
    Assert.assertEquals(items, cardsContainerModel.getItems());
  }
}
