package com.mattel.ag.retail.core.model;

import com.mattel.ag.retail.core.pojos.SiteNavigationPojo;
import com.mattel.ag.retail.core.services.MultifieldReader;
import javax.jcr.Node;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author CTS SiteNavigation Model to Navigation.
 */

public class SiteNavigationModelTest {
  SiteNavigationModel siteNavigationModel;
  SiteNavigationPojo siteNavigationPojo;
  ValueMap valueMap;
  Map.Entry<String, ValueMap> mapEntry;
  List<SiteNavigationPojo> siteNavigationPojos;
  Map<String, ValueMap> map = Mockito.mock(Map.class);
  Iterator<Object> iterator;
  List<Object> list;
  Set<Map.Entry<String, ValueMap>> entryset = new HashSet<>();
  MultifieldReader multifieldReader;
  Node navigationDetails;


  @Before
  public void setUp() {
    siteNavigationModel = new SiteNavigationModel();
    valueMap = Mockito.mock(ValueMap.class);
    mapEntry = Mockito.mock(Map.Entry.class);
    siteNavigationPojos = Mockito.mock(List.class);
    iterator = Mockito.mock(Iterator.class);
    list = Mockito.mock(List.class);
    siteNavigationPojo = Mockito.mock(SiteNavigationPojo.class);
    multifieldReader = Mockito.mock(MultifieldReader.class);
    navigationDetails = Mockito.mock(Node.class);
    when(list.iterator()).thenReturn(iterator);
    when(iterator.hasNext()).thenReturn(true, false);
    when(valueMap.get(Mockito.anyString(), any(String.class))).thenReturn("Any String");
    when(iterator.next()).thenReturn(map);
    when(mapEntry.getValue()).thenReturn(valueMap);
  }

  @Test
  public void navigatioDetailsIsNotNullTest() {
    Map.Entry<String, ValueMap> entry = Mockito.mock(Map.Entry.class);
    when(entry.getValue()).thenReturn(valueMap);
    entryset.add(entry);
    when(map.entrySet()).thenReturn(entryset);
    when(multifieldReader.propertyReader(any())).thenReturn(map);
    Mockito.doNothing().when(siteNavigationPojo).setTargetUrl("Target URL");
    Mockito.doNothing().when(siteNavigationPojo).setAlt("Alt");
    Mockito.doNothing().when(siteNavigationPojo).setUrl("URL");
    Mockito.doNothing().when(siteNavigationPojo).setLabel("Label");
    siteNavigationModel.setMultifieldReader(multifieldReader);
    siteNavigationModel.setNavigationDetail(navigationDetails);
    siteNavigationModel.setNavItemsList(siteNavigationPojos);
    siteNavigationModel.init();


  }

  @Test
  public void navigationDetailIsNullTest() {
    siteNavigationModel.setNavigationDetail(null);
    assertSame(null, siteNavigationModel.getNavItemsList());
  }

}
