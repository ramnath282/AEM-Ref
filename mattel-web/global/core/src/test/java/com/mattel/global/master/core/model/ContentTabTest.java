package com.mattel.global.master.core.model;

import java.util.ArrayList;
import java.util.List;

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
public class ContentTabTest {

  @InjectMocks
  ContentTab contentTab;

  @Mock
  Carousel carousel;

  @Mock
  ListItem listItem;

  List<ListItem> items;

  @Before
  public void setUp() throws Exception {
    items = new ArrayList();
    items.add(listItem);
    items.add(listItem);
    MemberModifier.field(ContentTab.class, "carousel").set(contentTab, carousel);
    Mockito.when(carousel.getItems()).thenReturn(items);
    items = Mockito.mock(List.class);
  }

  @Test
  public void testToVerifycontentTabForValidItemList() {
    Mockito.when(listItem.getName()).thenReturn("content_12345");
    contentTab.init();
    contentTab.getItems();
  }

  @Test
  public void testToVerifycontentTabForInvalidValidIItemList() {
    Mockito.when(listItem.getName()).thenReturn("cta12345");
    contentTab.init();
    contentTab.getItems();
  }

  @Test
  public void testToVerifycontentTabForInvalidResource()
      throws IllegalArgumentException, IllegalAccessException {
    MemberModifier.field(ContentTab.class, "carousel").set(contentTab, null);
    contentTab.init();
    contentTab.getItems();
  }

}
