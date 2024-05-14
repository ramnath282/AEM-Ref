package com.mattel.play.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.play.core.pojos.HeaderPojo;
import com.mattel.play.core.pojos.PlaySitePojo;
import com.mattel.play.core.services.MultifieldReader;

public class HeaderModelTest {

	
	HeaderModel headerModel = new HeaderModel();
	Node mainNavDetail;
	MultifieldReader multifieldReader;
	PlaySitePojo playSiteLinks;
	HashMap<String, ValueMap> multifieldProperty;
	List<PlaySitePojo> playSiteList;
	ValueMap valueMap;
	List<HeaderPojo> mainNavList = new ArrayList<>();
	Resource resource;
	
	@Before
    public void setUp() {
		
		mainNavDetail = Mockito.mock(Node.class);
		valueMap = Mockito.mock(ValueMap.class);
		playSiteLinks = new PlaySitePojo();
		multifieldReader = Mockito.mock(MultifieldReader.class);
		resource = Mockito.mock(Resource.class);
		headerModel.setMultifieldReader(multifieldReader);
		headerModel.setMultifieldReader(multifieldReader);
		headerModel.setMainNavDetail(mainNavDetail);
		playSiteLinks.setSiteLabel("siteLabel");
		playSiteLinks.setSiteLink("siteLlink");
		playSiteLinks.setSiteTarget("siteTarget");
		mainNavList = new ArrayList<>();
		playSiteList = new ArrayList<>();
		
		//headerModel.setPlaySiteDetail(playSiteDetail);
		headerModel.setMainNavDetail(mainNavDetail);
		headerModel.setResource(resource);
		
		valueMap = Mockito.mock(ValueMap.class);
		multifieldProperty = new HashMap<>();
		multifieldProperty.put("siteLabel", valueMap);
		multifieldProperty.put("siteLlink", valueMap);
		multifieldProperty.put("siteTarget", valueMap);
		multifieldProperty.put("link", valueMap);
		multifieldProperty.put("targetUrl", valueMap);
		multifieldProperty.put("label", valueMap);
		
		Mockito.when(multifieldReader.propertyReader(mainNavDetail)).thenReturn(multifieldProperty);
		playSiteList.add(playSiteLinks);         

    }

    @Test
    public void init() {

    	headerModel.init();

    }
    @Test
    public void getMainNavList() {
		headerModel.getMainNavList();
	}
    @Test
    public void getGlobalUrl() {
    	headerModel.getGlobalUrl();
	}
    
    
}
