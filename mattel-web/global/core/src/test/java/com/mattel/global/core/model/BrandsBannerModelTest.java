package com.mattel.global.core.model;

import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.jcr.Node;

import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mattel.global.core.services.MultifieldReader;

public class BrandsBannerModelTest {

  BrandsBannerModel brandsBanner;

  @Mock
  private MultifieldReader multifieldReader;

  @Mock
  private Node mattelbrands;

  @Mock
  private Map<String, ValueMap> brandsMap;

  @Mock
  Map.Entry<String, ValueMap> entry;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    brandsBanner = new BrandsBannerModel();
    brandsBanner.setBrandBanners(null);
    brandsBanner.setMattelbrands(mattelbrands);
    brandsBanner.setMultifieldReader(multifieldReader);
    Mockito.when(multifieldReader.propertyReader(mattelbrands)).thenReturn(brandsMap);

  }

  @Test
  public void testNotNullBrandBannersWhenMattelBrandIsNotNull() throws Exception {
    Set<Entry<String, ValueMap>> entrySet = new HashSet();
    entrySet.add(entry);
    ValueMap valueMap = Mockito.mock(ValueMap.class);
    Mockito.when(entry.getValue()).thenReturn(valueMap);
    Mockito.when(brandsMap.entrySet()).thenReturn(entrySet);
    brandsBanner.init();
    assertNotNull(brandsBanner.getBrandBanners());

  }

  @Test
  public void testNotNullBrandLogoLink() throws Exception {

    ValueMap valueMap = Mockito.mock(ValueMap.class);
    Mockito.when(entry.getValue()).thenReturn(valueMap);
    Mockito.when(valueMap.get("brandlogolink", String.class)).thenReturn("xyz");
    Set<Entry<String, ValueMap>> entrySet = new HashSet();
    entrySet.add(entry);
    Mockito.when(brandsMap.entrySet()).thenReturn(entrySet);
    brandsBanner.init();
    assertNotNull(brandsBanner.getBrandBanners());

  }

}
