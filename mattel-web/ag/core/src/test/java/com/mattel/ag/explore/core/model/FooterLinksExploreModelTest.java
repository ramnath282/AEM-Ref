package com.mattel.ag.explore.core.model;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jcr.Node;

import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.ag.explore.core.pojos.FooterLinksExplorePojo;
import com.mattel.ag.retail.core.services.MultifieldReader;


public class FooterLinksExploreModelTest {
	FooterLinksExploreModel footerlinkModel;
	FooterLinksExplorePojo footerlinkPojo;
	ValueMap valueMap;
	Map.Entry<String, ValueMap> mapEntry;
	List<FooterLinksExplorePojo> footerLinkPojo;
	Map<String, ValueMap> map = Mockito.mock(Map.class);
	Iterator<Object> iterator;
	List<Object> list;
	Set<Map.Entry<String, ValueMap>> entryset = new HashSet<>();
	MultifieldReader multifieldReader;
	Node helpFooterLinks;
	Node hearFooterLinks;
	Node visitFooterLinks;
	Node cantFindFooterLinks;
	
	@Before
	public void setUp(){
		footerlinkModel=new FooterLinksExploreModel();
		valueMap = Mockito.mock(ValueMap.class);
		mapEntry = Mockito.mock(Map.Entry.class);
		footerLinkPojo = Mockito.mock(List.class);
		iterator = Mockito.mock(Iterator.class);
		list = Mockito.mock(List.class);
		footerlinkPojo = Mockito.mock(FooterLinksExplorePojo.class);
		multifieldReader = Mockito.mock(MultifieldReader.class);
		helpFooterLinks = Mockito.mock(Node.class);
		hearFooterLinks = Mockito.mock(Node.class);
		visitFooterLinks = Mockito.mock(Node.class);
		cantFindFooterLinks = Mockito.mock(Node.class);
		
		when(list.iterator()).thenReturn(iterator);
		when(iterator.hasNext()).thenReturn(true, false);
		when(valueMap.get(Mockito.anyString(), any(String.class))).thenReturn("Any String");
		when(iterator.next()).thenReturn(map);
		when(mapEntry.getValue()).thenReturn(valueMap);
	}
	
	@Test
	public void testWhenHelpFooterLinksNotNull() {
		Map.Entry<String, ValueMap> entry = Mockito.mock(Map.Entry.class);
		when(entry.getValue()).thenReturn(valueMap);
		entryset.add(entry);
		when(map.entrySet()).thenReturn(entryset);
		when(multifieldReader.propertyReader(any())).thenReturn(map);
		Mockito.doNothing().when(footerlinkPojo).setHelpRenderOption("Help Render Option");
		Mockito.doNothing().when(footerlinkPojo).setHelpLinkText("Help Link Text");
		Mockito.doNothing().when(footerlinkPojo).setHelpLinkUrl("Help Link URL");
		footerlinkModel.setMultifieldReader(multifieldReader);
		footerlinkModel.setHelpFooterLinks(helpFooterLinks);
		footerlinkModel.setHelpFooterLinksList(footerLinkPojo);
		footerlinkModel.init();
	}
	@Test
	public void testWhenVisitFooterLinksNotNull() {
		Map.Entry<String, ValueMap> entry = Mockito.mock(Map.Entry.class);
		when(entry.getValue()).thenReturn(valueMap);
		entryset.add(entry);
		when(map.entrySet()).thenReturn(entryset);
		when(multifieldReader.propertyReader(any())).thenReturn(map);
		Mockito.doNothing().when(footerlinkPojo).setVisitRenderOption("Visit Render Option");
		Mockito.doNothing().when(footerlinkPojo).setVisitLinkText("Visit Link Text");
		Mockito.doNothing().when(footerlinkPojo).setVisitLinkUrl("Visit Link URL");
		footerlinkModel.setMultifieldReader(multifieldReader);
		footerlinkModel.setVisitFooterLinks(visitFooterLinks);
		footerlinkModel.setVisitFooterLinksList(footerLinkPojo);
		footerlinkModel.init();
	}
	@Test
	public void testWhenCantFindFooterLinksNotNull() {
		Map.Entry<String, ValueMap> entry = Mockito.mock(Map.Entry.class);
		when(entry.getValue()).thenReturn(valueMap);
		entryset.add(entry);
		when(map.entrySet()).thenReturn(entryset);
		when(multifieldReader.propertyReader(any())).thenReturn(map);
		Mockito.doNothing().when(footerlinkPojo).setFindRenderOption("Find Render Option");
		Mockito.doNothing().when(footerlinkPojo).setCantFindLinkText("CantFind Link Text");
		Mockito.doNothing().when(footerlinkPojo).setCantFindLinkUrl("CantFind Link URL");
		footerlinkModel.setMultifieldReader(multifieldReader);
		footerlinkModel.setCantFindFooterLinks(cantFindFooterLinks);
		footerlinkModel.setCantFindFooterLinksList(footerLinkPojo);
		footerlinkModel.init();
	}
	@Test
	public void testWhenHearFooterLinksNotNull() {
		Map.Entry<String, ValueMap> entry = Mockito.mock(Map.Entry.class);
		when(entry.getValue()).thenReturn(valueMap);
		entryset.add(entry);
		when(map.entrySet()).thenReturn(entryset);
		when(multifieldReader.propertyReader(any())).thenReturn(map);
		Mockito.doNothing().when(footerlinkPojo).setHearRenderOption("Hear Render Option");
		Mockito.doNothing().when(footerlinkPojo).setHearLinkText("Hear Link Text");
		Mockito.doNothing().when(footerlinkPojo).setHearLinkUrl("Hear Link URL");
		footerlinkModel.setMultifieldReader(multifieldReader);
		footerlinkModel.setHearFooterLinks(hearFooterLinks);
		footerlinkModel.setHearFooterLinksList(footerLinkPojo);
		footerlinkModel.init();
	}
	
	@Test
	public void testWhenHelpFooterLinksNull() {
		Map.Entry<String, ValueMap> entry = Mockito.mock(Map.Entry.class);
		when(entry.getValue()).thenReturn(valueMap);
		entryset.add(entry);
		when(map.entrySet()).thenReturn(entryset);
		when(multifieldReader.propertyReader(any())).thenReturn(map);
		footerlinkModel.setMultifieldReader(multifieldReader);
		footerlinkModel.setHelpFooterLinksList(null);
		footerlinkModel.init();
		
	}
	@Test
	public void testWhenHearFooterLinksNull() {
		Map.Entry<String, ValueMap> entry = Mockito.mock(Map.Entry.class);
		when(entry.getValue()).thenReturn(valueMap);
		entryset.add(entry);
		when(map.entrySet()).thenReturn(entryset);
		when(multifieldReader.propertyReader(any())).thenReturn(map);
		footerlinkModel.setMultifieldReader(multifieldReader);
		footerlinkModel.setHearFooterLinksList(null);
		footerlinkModel.init();
		
	}
	@Test
	public void testWhenVisitFooterLinksNull() {
		Map.Entry<String, ValueMap> entry = Mockito.mock(Map.Entry.class);
		when(entry.getValue()).thenReturn(valueMap);
		entryset.add(entry);
		when(map.entrySet()).thenReturn(entryset);
		when(multifieldReader.propertyReader(any())).thenReturn(map);
		footerlinkModel.setMultifieldReader(multifieldReader);
		footerlinkModel.setVisitFooterLinksList(null);
		footerlinkModel.init();
		
	}
	@Test
	public void testWhenCantFindFooterLinksNull() {
		Map.Entry<String, ValueMap> entry = Mockito.mock(Map.Entry.class);
		when(entry.getValue()).thenReturn(valueMap);
		entryset.add(entry);
		when(map.entrySet()).thenReturn(entryset);
		when(multifieldReader.propertyReader(any())).thenReturn(map);
		footerlinkModel.setMultifieldReader(multifieldReader);
		footerlinkModel.setCantFindFooterLinksList(null);
		footerlinkModel.init();
		
	}
	@Test
	public void testWhenHelpFooterLinksListIsNull() {
		footerlinkModel.setHelpFooterLinksList(null);
		assertSame(null, footerlinkModel.getHelpFooterLinksList());
		
	}
	@Test
	public void testWhenHearFooterLinksListIsNull() {
		footerlinkModel.setHearFooterLinksList(null);
		assertSame(null, footerlinkModel.getHearFooterLinksList());
		
	}
	@Test
	public void testWhenVisitFooterLinksListIsNull() {
		footerlinkModel.setVisitFooterLinksList(null);
		assertSame(null, footerlinkModel.getVisitFooterLinksList());
		
	}
	@Test
	public void testWhenCantFindFooterLinksListIsNull() {
		footerlinkModel.setCantFindFooterLinksList(null);
		assertSame(null, footerlinkModel.getCantFindFooterLinksList());
		
	}
}
