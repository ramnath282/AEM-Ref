package com.mattel.play.core.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CategoryFilterSlidesUtilsTest {
  CategoryFilterSlidesUtils categoryFilterSlidesUtils = new CategoryFilterSlidesUtils();

  @Before
  public void setUp() {
    final CategoryFilterSlidesUtils.Config config = Mockito
        .mock(CategoryFilterSlidesUtils.Config.class);
    Mockito.when(config.characterSlidesValueMapping()).thenReturn(new String[] { "test1" });
    Mockito.when(config.slideShowValueMapping()).thenReturn(new String[] { "test2" });
    categoryFilterSlidesUtils.activate(config);
  }

  @Test
  public void getSlideShowValueMapping() {
    Assert.assertArrayEquals(new String[] { "test2" },
        CategoryFilterSlidesUtils.getSlideShowValueMapping());
  }

  @Test
  public void testGetCharacterSlidesValueMapping() throws Exception {
    Assert.assertArrayEquals(new String[] { "test1" },
        CategoryFilterSlidesUtils.getCharacterSlidesValueMapping());
  }
}
