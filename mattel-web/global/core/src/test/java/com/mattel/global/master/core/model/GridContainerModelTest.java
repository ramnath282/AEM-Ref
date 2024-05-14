package com.mattel.global.master.core.model;

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
public class GridContainerModelTest {

  @InjectMocks
  GridContainerModel gridContainerModel;

  @Mock
  Carousel carousel;

  @Mock
  Resource resource;

  List<ListItem> items;

  ListItem listItem;

  @SuppressWarnings("unchecked")
  @Before
  public void setUp() throws Exception {
    MemberModifier.field(GridContainerModel.class, "carousel").set(gridContainerModel, carousel);
    MemberModifier.field(GridContainerModel.class, "resource").set(gridContainerModel, resource);
    MemberModifier.field(GridContainerModel.class, "dataXsSlider").set(gridContainerModel,
        "dataXsSlider");
    MemberModifier.field(GridContainerModel.class, "ctaLink").set(gridContainerModel, "ctaLink");

    items = Mockito.mock(List.class);
  }

  @Test
  public void testInitLinkNotCategory() throws Exception {
    MemberModifier.field(GridContainerModel.class, "link").set(gridContainerModel, "link");
    gridContainerModel.init();
  }

  @Test
  public void testInitLinkCategory() throws Exception {
    MemberModifier.field(GridContainerModel.class, "link").set(gridContainerModel, "categories");
    gridContainerModel.init();
  }

  @Test
  public void testInitWithLinkEmpty() throws Exception {
    MemberModifier.field(GridContainerModel.class, "link").set(gridContainerModel, "");
    gridContainerModel.init();
  }

  @Test
  public void testGetterSetters() {
    Assert.assertEquals("ctaLink", gridContainerModel.getCtaLink());
    Assert.assertEquals("dataXsSlider", gridContainerModel.getDataXsSlider());
    Mockito.when(carousel.getItems()).thenReturn(items);
    Assert.assertEquals(items, gridContainerModel.getItems());
  }
}
