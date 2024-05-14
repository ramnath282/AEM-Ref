package com.mattel.global.core.transformers.extension;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.mattel.global.core.services.UrlShorteningService;
import com.mattel.global.core.utils.PropertyReaderUtils;

@RunWith(MockitoJUnitRunner.class)
public class ExtendedTargetHtmlTransformerFactoryTest {
  @Mock
  private PropertyReaderUtils propertyReaderUtils;

  @Mock
  private UrlShorteningService urlShorteningService;
  @InjectMocks
  private ExtendedTargetHtmlTransformerFactory extendedTargetHtmlTransformerFactory;

  @Test
  public void testCreateTransformer() throws Exception {
    Assert.assertNotNull(extendedTargetHtmlTransformerFactory.createTransformer());
  }
}
