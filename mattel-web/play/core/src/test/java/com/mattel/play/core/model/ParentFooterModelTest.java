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

import com.mattel.play.core.pojos.ParentFooterPojo;
import com.mattel.play.core.pojos.SocialIconsPojo;
import com.mattel.play.core.services.MultifieldReader;

public class ParentFooterModelTest {
	
	ParentFooterModel parentFooterModel = new ParentFooterModel();
	Resource resource;
	Node node;
	MultifieldReader multifieldReader;
	List<ParentFooterPojo> footerTextGroupOne = new ArrayList<>();
	ParentFooterPojo parentFooterPojo = new ParentFooterPojo();
	List<SocialIconsPojo> socialIconsList = new ArrayList<>();
	SocialIconsPojo socialIconsPojo = new SocialIconsPojo();
	Map<String, ValueMap> multifieldProperty = new HashMap<>();;
	Map.Entry<String, ValueMap> entry;
	ValueMap valueMap;
	
	@SuppressWarnings("unchecked")
	@Before
    public void setUp() {
		resource = Mockito.mock(Resource.class);
		node = Mockito.mock(Node.class);
		multifieldReader = Mockito.mock(MultifieldReader.class);
		valueMap = Mockito.mock(ValueMap.class);
		entry = Mockito.mock(Map.Entry.class);
		
		multifieldProperty.put("", valueMap);
		parentFooterModel.setFooterTextGroupFour(footerTextGroupOne);
		parentFooterModel.setFooterTextGroupOne(footerTextGroupOne);
		parentFooterModel.setFooterTextGroupThree(footerTextGroupOne);
		parentFooterModel.setFooterTextGroupTwo(footerTextGroupOne);
		parentFooterModel.setMultifieldReader(multifieldReader);
		parentFooterModel.setResource(resource);
		parentFooterModel.setSocialIcons(node);
		parentFooterModel.setSocialIconsList(socialIconsList);
		parentFooterModel.setFooterLinksFour(node);
		parentFooterModel.setFooterLinksOne(node);
		parentFooterModel.setFooterLinksThree(node);
		parentFooterModel.setFooterLinksTwo(node);
		
		Mockito.when(multifieldReader.propertyReader(node)).thenReturn(multifieldProperty);
		
	}
	@Test
    public void init() {

		parentFooterModel.init();

    }
	
}
