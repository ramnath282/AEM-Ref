package com.mattel.global.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.jcr.Node;

import org.apache.sling.api.resource.ValueMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.adobe.granite.ui.components.Value;
import com.mattel.global.core.pojo.ImageBlockWithTextPojo;
import com.mattel.global.core.services.MultifieldReader;

public class ImageBlockWithTextModelTest {
  ImageBlockWithTextModel imageBlockWithTextModel;

  @Mock
  private Node textImageDetails;

  @Mock
  private MultifieldReader multifieldReader;

  @Mock
  Map<String, ValueMap> hashMap;

 
  @Mock
  Map.Entry<String, ValueMap> entry;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    imageBlockWithTextModel = new ImageBlockWithTextModel();
    imageBlockWithTextModel.setMultifieldReader(multifieldReader);
    imageBlockWithTextModel.setTextImageDetails(textImageDetails);

  }

  @Test
  public void testWithNotNullImageDetail() {

    Mockito.when(multifieldReader.propertyReader(textImageDetails)).thenReturn(hashMap);
    ValueMap valueMap = Mockito.mock(ValueMap.class);
    Set<Entry<String, ValueMap>> entrySet = new HashSet();
    entrySet.add(entry);

    Mockito.when(hashMap.entrySet()).thenReturn(entrySet);
    Mockito.when(entry.getValue()).thenReturn(valueMap);
    Mockito.when( entry.getValue().get("ctaurl", String.class)).thenReturn("/content/crm");

    imageBlockWithTextModel.init();
    Assert.assertSame(1, imageBlockWithTextModel.getImageWithTextPojos().size());

  }

}
