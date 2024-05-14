package com.mattel.global.core.model;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jcr.Node;

import org.apache.sling.api.resource.ValueMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.commons.Externalizer;
import com.mattel.global.core.pojo.CarouselPojo;
import com.mattel.global.core.services.MultifieldReader;
import com.mattel.global.core.utils.PathUtils;
@RunWith(PowerMockRunner.class)
@PrepareForTest(PathUtils.class)
public class CarouselModelTest {
	CarouselModel carouselModel;
	CarouselPojo carouselPojo;
	
	ValueMap valueMap;
	Map.Entry<String, ValueMap> mapEntry;
	List<CarouselPojo> carouselItemList;
	Map<String, ValueMap> map = Mockito.mock(Map.class);
	Iterator<Object> iterator;
	List<Object> list;
	Set<Map.Entry<String, ValueMap>> entryset = new HashSet<>();
	MultifieldReader multifieldReader;
	Node carouselList;
	
	Externalizer externalizer;
	
	@Before
	public void setUp(){
		carouselModel = new CarouselModel();
		carouselPojo = Mockito.mock(CarouselPojo.class);
		valueMap = Mockito.mock(ValueMap.class);
		mapEntry = Mockito.mock(Map.Entry.class);
		carouselItemList = Mockito.mock(List.class);
		iterator = Mockito.mock(Iterator.class);
		list = Mockito.mock(List.class);
		carouselPojo = Mockito.mock(CarouselPojo.class);
		multifieldReader = Mockito.mock(MultifieldReader.class);
		externalizer = Mockito.mock(Externalizer.class);
		
		when(list.iterator()).thenReturn(iterator);
		when(iterator.hasNext()).thenReturn(true, false);
		when(valueMap.get(Mockito.anyString(), any(String.class))).thenReturn("Any String");
		when(iterator.next()).thenReturn(map);
		when(mapEntry.getValue()).thenReturn(valueMap);
	}
	
	@Test
	public void test(){
		Map.Entry<String, ValueMap> entry = Mockito.mock(Map.Entry.class);
		when(entry.getValue()).thenReturn(valueMap);
		entryset.add(entry);
		when(map.entrySet()).thenReturn(entryset);
		when(multifieldReader.propertyReader(any())).thenReturn(map);
		carouselList = Mockito.mock(Node.class);
		
		PowerMockito.mockStatic(PathUtils.class);
		
		PowerMockito.when(PathUtils.isExternal(Mockito.anyString())).thenReturn(Boolean.TRUE);
		
		Mockito.doNothing().when(carouselPojo).setCtaAltText("ctaAltText");
		Mockito.doNothing().when(carouselPojo).setCtaLabel("ctaLabel");
		Mockito.doNothing().when(carouselPojo).setCtaLink("/content/somePath");
		Mockito.doNothing().when(carouselPojo).setCtaPositioning("ctaPositioning");
		Mockito.doNothing().when(carouselPojo).setCtaRenderoption("ctaRenderoption");
		Mockito.doNothing().when(carouselPojo).setCtaStyling("ctaStyling");
		//Mockito.doNothing().when(carouselPojo).setExternal(true);
		Mockito.doNothing().when(carouselPojo).setHeading("heading");
		Mockito.doNothing().when(carouselPojo).setImage("/conetnt/dam/image");
		Mockito.doNothing().when(carouselPojo).setImageAlttext("imageAlttext");
		
		Mockito.doNothing().when(carouselPojo).setOverlayHeading("true");
		Mockito.doNothing().when(carouselPojo).setOverlaySubHeading("overlayHeading");
		Mockito.doNothing().when(carouselPojo).setShowArrow("true");
		Mockito.doNothing().when(carouselPojo).setShowPagination("showPagination");
		Mockito.doNothing().when(carouselPojo).setSubHeading("subHeading");
		Mockito.doNothing().when(carouselPojo).setTextBackground("textBackground");
		Mockito.doNothing().when(carouselPojo).setTextPositioning("textPositioning");
		
		carouselModel.setExternalizer(externalizer);
		carouselModel.setMultifieldReader(multifieldReader);
		carouselModel.setCarouselList(carouselList);
		carouselModel.init();
		Assert.assertSame(1, carouselModel.getCarouselItemsList().size());
		
	}
	
	@Test 
	public void testCarouselItemsList(){
	  List<CarouselPojo> carouselItemList = new ArrayList<>();	  
	  carouselModel.setCarouselItemList(carouselItemList);
	  Assert.assertSame(carouselItemList, carouselModel.getCarouselItemsList());
	  
	}
	
}
