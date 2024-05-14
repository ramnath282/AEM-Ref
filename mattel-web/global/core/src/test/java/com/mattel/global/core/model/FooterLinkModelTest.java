package com.mattel.global.core.model;

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

import com.mattel.global.core.pojo.FooterLinkPojo;
import com.mattel.global.core.services.MultifieldReader;
public class FooterLinkModelTest {
	FooterLinkModel footerlinkModel;
	FooterLinkPojo footerlinkPojo;
	ValueMap valueMap;
	Map.Entry<String, ValueMap> mapEntry;
	List<FooterLinkPojo> footerLinkPojo;
	Map<String, ValueMap> map = Mockito.mock(Map.class);
	Iterator<Object> iterator;
	List<Object> list;
	Set<Map.Entry<String, ValueMap>> entryset = new HashSet<>();
	MultifieldReader multifieldReader;
	Node linkDetails;

	@Before
	public void setUp(){
		footerlinkModel=new FooterLinkModel();
		valueMap = Mockito.mock(ValueMap.class);
		mapEntry = Mockito.mock(Map.Entry.class);
		footerLinkPojo = Mockito.mock(List.class);
		iterator = Mockito.mock(Iterator.class);
		list = Mockito.mock(List.class);
		footerlinkPojo = Mockito.mock(FooterLinkPojo.class);
		multifieldReader = Mockito.mock(MultifieldReader.class);
		linkDetails = Mockito.mock(Node.class);
		
		when(list.iterator()).thenReturn(iterator);
		when(iterator.hasNext()).thenReturn(true, false);
		when(valueMap.get(Mockito.anyString(), any(String.class))).thenReturn("Any String");
		when(iterator.next()).thenReturn(map);
		when(mapEntry.getValue()).thenReturn(valueMap);
	}

	@Test
	public void testWhenLinkDetailsNotNull() {
		Map.Entry<String, ValueMap> entry = Mockito.mock(Map.Entry.class);
		when(entry.getValue()).thenReturn(valueMap);
		entryset.add(entry);
		when(map.entrySet()).thenReturn(entryset);
		when(multifieldReader.propertyReader(any())).thenReturn(map);
		Mockito.doNothing().when(footerlinkPojo).setAlt("Alt text");
		Mockito.doNothing().when(footerlinkPojo).setExternal(false);
		Mockito.doNothing().when(footerlinkPojo).setLabel("Label");
		Mockito.doNothing().when(footerlinkPojo).setTargetUrl("/content/mattelweb/crm/some/path");
		Mockito.doNothing().when(footerlinkPojo).setUrl("/content/mattelweb/crm/some/path");
		footerlinkModel.setMultifieldReader(multifieldReader);
		footerlinkModel.setLinkDetails(linkDetails);
		footerlinkModel.setFooterLinkItems(footerLinkPojo);
		footerlinkModel.setCommonPojo(footerlinkModel.commonPojo);
		footerlinkModel.setImgUrl("/content/dam/some/path");
		footerlinkModel.init();
	}
	
	@Test
	public void testWhenImagePathIsNull() {
		footerlinkModel.setImgUrl(null);
		assertSame(null, footerlinkModel.getImgUrl());
	}
	
	@Test
	public void testWhenLinkDetailsNull() {
		Map.Entry<String, ValueMap> entry = Mockito.mock(Map.Entry.class);
		when(entry.getValue()).thenReturn(valueMap);
		entryset.add(entry);
		when(map.entrySet()).thenReturn(entryset);
		when(multifieldReader.propertyReader(any())).thenReturn(map);
		footerlinkModel.setMultifieldReader(multifieldReader);
		footerlinkModel.setLinkDetails(null);
		footerlinkModel.init();
		
	}
	
	@Test
	public void testWhenListIsNull() {
		footerlinkModel.setFooterLinkItems(null);
		assertSame(null, footerlinkModel.getFooterLinkItemsList());
		
	}
}
