package com.mattel.ag.retail.core.model;

import com.mattel.ag.retail.core.pojos.FooterLinks;
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
 * @author CTS. A Test Model class for Footer Links
 */


public class FooterLinksModelTest {

  FooterLinksModel footerlinkModel;
  FooterLinks footerlinkPojo;
  ValueMap valueMap;
  Map.Entry<String, ValueMap> mapEntry;
  List<FooterLinks> footerLinkPojo;
  Map<String, ValueMap> map = Mockito.mock(Map.class);
  Iterator<Object> iterator;
  List<Object> list;
  Set<Map.Entry<String, ValueMap>> entryset = new HashSet<>();
  MultifieldReader multifieldReader;
  Node linkDetails;


  @Before
  public void setUp() {
    footerlinkModel = new FooterLinksModel();
    valueMap = Mockito.mock(ValueMap.class);
    mapEntry = Mockito.mock(Map.Entry.class);
    footerLinkPojo = Mockito.mock(List.class);
    iterator = Mockito.mock(Iterator.class);
    list = Mockito.mock(List.class);
    footerlinkPojo = Mockito.mock(FooterLinks.class);
    multifieldReader = Mockito.mock(MultifieldReader.class);
    linkDetails = Mockito.mock(Node.class);
    when(list.iterator()).thenReturn(iterator);
    when(iterator.hasNext()).thenReturn(true, false);
    when(valueMap.get(Mockito.anyString(), any(String.class))).thenReturn("Any String");
    when(iterator.next()).thenReturn(map);
    when(mapEntry.getValue()).thenReturn(valueMap);
  }

  @Test
  public void footerLinksNodeisNotNull() {
    Map.Entry<String, ValueMap> entry = Mockito.mock(Map.Entry.class);
    when(entry.getValue()).thenReturn(valueMap);
    entryset.add(entry);
    when(map.entrySet()).thenReturn(entryset);
    when(multifieldReader.propertyReader(any())).thenReturn(map);
    Mockito.doNothing().when(footerlinkPojo).setLinkText("Any String");
    footerlinkModel.setFooterLinks(linkDetails);
    footerlinkModel.setFooterLinksList(footerLinkPojo);
    footerlinkModel.setMultifieldReader(multifieldReader);
    footerlinkModel.init();

  }

  @Test
  public void footerLinksNodeisNull() {

    footerlinkModel.setFooterLinks(null);
    assertSame(null, footerlinkModel.getFooterLinksList());

  }


}
