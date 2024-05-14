package com.mattel.play.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.play.core.pojos.RetailerPojo;
import com.mattel.play.core.services.MultifieldReader;
import com.mattel.play.core.services.TileGalleryAndLandingService;

public class RetailerModelTest {

	RetailerModel retailerModel;
	Node retailerDetailList;
	MultifieldReader multifieldReader;
	List<RetailerPojo> retailerDetailsList = new ArrayList<>();
	Map<String, ValueMap> multifieldProperty;
	Resource resource;
	TileGalleryAndLandingService tileGalleryAndLandingService;
	
	@Before
	public void setUp() {
		retailerModel = new RetailerModel();
		retailerDetailList = Mockito.mock(Node.class);
		resource = Mockito.mock(Resource.class);
		multifieldReader = Mockito.mock(MultifieldReader.class);
		tileGalleryAndLandingService = Mockito.mock(TileGalleryAndLandingService.class);
		multifieldProperty = new HashMap <>();
		retailerModel.setMultifieldReader(multifieldReader);
		retailerModel.setRetailerDetailList(retailerDetailList);
		retailerModel.setRetailerDetailsList(retailerDetailsList);
		retailerModel.setTileGalleryAndLandingService(tileGalleryAndLandingService);
		retailerModel.setResource(resource);
		Mockito.when(multifieldReader.propertyReader(retailerDetailList)).thenReturn(multifieldProperty);
		Mockito.when(tileGalleryAndLandingService.fetchRetailerDetails(multifieldProperty,resource)).thenReturn(retailerDetailsList);
		
		
	}
	@Test
	public void init() {
		retailerModel.init();
	}
	@Test
	public void getRetailerDetailsList() {
		retailerModel.getRetailerDetailsList();
	}
}
