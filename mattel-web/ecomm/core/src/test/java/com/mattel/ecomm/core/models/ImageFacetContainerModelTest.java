package com.mattel.ecomm.core.models;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.adobe.cq.wcm.core.components.models.Carousel;
import com.adobe.cq.wcm.core.components.models.ListItem;

@RunWith(MockitoJUnitRunner.class)
public class ImageFacetContainerModelTest {

  @Mock
  Carousel carousel;

  @Mock
  ListItem listItem;

  @InjectMocks
  ImageFacetContainerModel imageFacetContainerModel;

  private List<ListItem> lis = new ArrayList<>();

  @Test
  public void test_init() {
    lis.add(listItem);
    when(carousel.getItems()).thenReturn(lis);
    imageFacetContainerModel.init();
    assertEquals(lis, imageFacetContainerModel.getItems());
  }
}
