package com.mattel.ecomm.core.models;

import com.mattel.ecomm.core.services.MultifieldReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class PLPFacetsLeftNavModelTest {

  @InjectMocks
  private PLPFacetsLeftNavModel plpFacetsLeftNavModel;
  @Mock
  private Resource resource;

  MultifieldReader multifieldReader;
  Node excludedFacets;
  Map<String, ValueMap> multifieldProperty;
  ValueMap valueMap;
  Map.Entry<String, ValueMap> entry;


  @SuppressWarnings("unchecked")
  @Before
  public void setup() throws Exception {
    entry = Mockito.mock(Entry.class);
    valueMap = Mockito.mock(ValueMap.class);
    multifieldReader = Mockito.mock(MultifieldReader.class);
    excludedFacets = Mockito.mock(Node.class);
    multifieldProperty = new HashMap<>();
    multifieldProperty.put("document", valueMap);
    multifieldProperty.put("document", valueMap);
    Mockito.when(entry.getValue()).thenReturn(valueMap);
    plpFacetsLeftNavModel.setMultifieldReader(multifieldReader);
    plpFacetsLeftNavModel.setExcludedFacets(excludedFacets);
  }

  @Test
  public void testInit() throws Exception {
    Mockito.when(entry.getValue()).thenReturn(valueMap);
    Mockito.when(multifieldReader.propertyReader(excludedFacets)).thenReturn(multifieldProperty);
    Mockito.when(entry.getValue().get("key", String.class))
        .thenReturn("Hair Color");
    plpFacetsLeftNavModel.init();
    Assert.assertNotNull(plpFacetsLeftNavModel.getExcludedFacets());
  }

}
