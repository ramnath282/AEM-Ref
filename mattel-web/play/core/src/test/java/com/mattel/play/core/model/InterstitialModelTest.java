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

import com.mattel.play.core.pojos.InterstitialPojo;
import com.mattel.play.core.services.MultifieldReader;

public class InterstitialModelTest {
	
	InterstitialModel interstitialModel;
	Node interstitialDetailList;
	MultifieldReader multifieldReader;
	Map<String, ValueMap> multifieldProperty;
	ValueMap valueMap;
	InterstitialPojo interstitialDetail;
	List<InterstitialPojo> interstitialDetailsList;
	Resource resource;
	
	@Before
	public void setUp()
	{
		
		interstitialModel = new InterstitialModel();
		interstitialDetailList = Mockito.mock(Node.class);
		multifieldReader = Mockito.mock(MultifieldReader.class);
		valueMap = Mockito.mock(ValueMap.class);
		resource = Mockito.mock(Resource.class);
		multifieldProperty = new HashMap<>();
		multifieldProperty.put("interstitialLogo", valueMap);
		multifieldProperty.put("interstitialLogoAlt", valueMap);
		multifieldProperty.put("interstitialUrl", valueMap);
		multifieldProperty.put("interstitialTarget", valueMap);
		interstitialModel.setInterstitialDetailList(interstitialDetailList);
		interstitialModel.setMultifieldReader(multifieldReader);
		interstitialModel.setResource(resource);
		interstitialDetail = new InterstitialPojo();
		Mockito.when(multifieldReader.propertyReader(interstitialDetailList)).thenReturn(multifieldProperty);
		interstitialDetail.setInterstitialLogoAlt("interstitialLogoAlt");
		interstitialDetail.setInterstitialLogoSrc("interstitialLogo");
		interstitialDetail.setInterstitialTarget("interstitialTarget");
		interstitialDetail.setInterstitialUrl("interstitialUrl");
		interstitialDetailsList = new ArrayList<>();
		interstitialDetailsList.add(interstitialDetail);
		
		
	}
	@Test
	public void init() {

		interstitialModel.init();
	}
	@Test
	public void setInterstitialDetailsList() {
		interstitialModel.setInterstitialDetailsList(interstitialDetailsList);
	}
	@Test
	public void getInterstitialDetailsList() {
		interstitialModel.getInterstitialDetailsList();
	}

}
