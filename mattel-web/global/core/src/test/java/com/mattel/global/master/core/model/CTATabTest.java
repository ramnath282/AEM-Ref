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
public class CTATabTest {

  @InjectMocks
  CTATab ctaTab;

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
    MemberModifier.field(CTATab.class, "carousel").set(ctaTab, carousel);
    Mockito.when(carousel.getItems()).thenReturn(items);
    items = Mockito.mock(List.class);
  }

  @Test
  public void testToVerifyCTATabForValidItemList() {
    Mockito.when(listItem.getName()).thenReturn("CTA12345");
    ctaTab.init();
    ctaTab.getItems();
  }

  @Test
  public void testToVerifyCTATabForInvalidValidIItemList() {
    Mockito.when(listItem.getName()).thenReturn("Content12345");
    ctaTab.init();
    ctaTab.getItems();
  }

  @Test
  public void testToVerifyCTATabForInvalidResource()
      throws IllegalArgumentException, IllegalAccessException {
    MemberModifier.field(CTATab.class, "carousel").set(ctaTab, null);
    ctaTab.init();
    ctaTab.getItems();
  }

}
