package com.mattel.play.core.utils;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class VideosDamLanguageMappingTest {
  @Test
  public void testActivate() throws Exception {
    final VideosDamLanguageMapping impl = new VideosDamLanguageMapping();
    final VideosDamLanguageMapping.Config config = Mockito
        .mock(VideosDamLanguageMapping.Config.class);
    final String[] mappings = new String[] { "test" };

    Mockito.when(config.languageMapping()).thenReturn(mappings);
    impl.activate(config);
    Assert.assertArrayEquals(mappings, VideosDamLanguageMapping.getLanguageMapping());
  }
}
