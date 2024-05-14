package com.mattel.play.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.day.cq.dam.api.Asset;
import com.mattel.play.core.pojos.PromoCarouselPojo;
import com.mattel.play.core.pojos.VideoTile;
import com.mattel.play.core.services.MultifieldReader;
import com.mattel.play.core.services.VideoGalleryService;

public class PromoCarouselModelTest {

	PromoCarouselModel promoCarouselModel;
	MultifieldReader multifieldReader;
	Node promcarousel;
	int transitionTime;
	List<PromoCarouselPojo> promoCarouselList;
	Map<String, ValueMap> multifieldProperty;
	PromoCarouselPojo carouselLinks;
	ValueMap valueMap;
	Map.Entry<String, ValueMap> entry;
	SlingHttpServletRequest request;
	ResourceResolver resolver;
	Resource resource;
	Asset videoAsset;
	VideoTile videoDetail;
	VideoGalleryService videoGalleryService;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		promoCarouselModel = new PromoCarouselModel();
		multifieldReader = Mockito.mock(MultifieldReader.class);
		promcarousel = Mockito.mock(Node.class);
		valueMap = Mockito.mock(ValueMap.class);
		entry = Mockito.mock(Map.Entry.class);
		request = Mockito.mock(SlingHttpServletRequest.class);
		resolver = Mockito.mock(ResourceResolver.class);
		resource = Mockito.mock(Resource.class);
		videoAsset = Mockito.mock(Asset.class);
		videoGalleryService = Mockito.mock(VideoGalleryService.class);
		videoDetail = new VideoTile();
		promoCarouselList = new ArrayList <>();
		multifieldProperty = new HashMap<>();
		carouselLinks = new PromoCarouselPojo();
		promoCarouselModel.setMultifieldReader(multifieldReader);
		promoCarouselModel.setPromcarousel(promcarousel);
		promoCarouselModel.setResource(resource);
		promoCarouselModel.setSlingHttpServletRequest(request);
		promoCarouselModel.setVideoGalleryService(videoGalleryService);
		multifieldProperty.put("", valueMap);
		Mockito.when(multifieldReader.propertyReader(promcarousel)).thenReturn(multifieldProperty);
		Mockito.when(entry.getValue()).thenReturn(valueMap);
		Mockito.when(entry.getValue().get("crlImage", String.class)).thenReturn("videos");
		Mockito.when(request.getResourceResolver()).thenReturn(resolver);
		Mockito.when(request.getResourceResolver().getResource("videos")).thenReturn(resource);
		Mockito.when(resource.adaptTo(Asset.class)).thenReturn(videoAsset);
		Mockito.when(videoGalleryService.prepareVideoTile(resource)).thenReturn(videoDetail);
		
	}
	@Test
	public void init() {
		promoCarouselModel.init();
	}
	
	@Test
	public void getPromoCarouselList() {
		promoCarouselModel.getPromoCarouselList();
	}
	@Test
	public void getTransitionTime() {
		promoCarouselModel.getTransitionTime();
	}
	
}
