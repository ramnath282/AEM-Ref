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

import com.mattel.play.core.pojos.MallTourPagePojo;
import com.mattel.play.core.services.MultifieldReader;

public class MallTourPageModelTest {
	
	MallTourPageModel mallTourPageModel;
	MultifieldReader multifieldReader;
	Node mallDetailList;
	List<MallTourPagePojo> mallTourPageList;
	ValueMap valueMap;
	MallTourPagePojo mallTourPagePojo;
	Map<String, ValueMap> multifieldProperty;
	Resource resource;
	
	@Before
	public void setUp()
	{
		mallTourPageModel = new MallTourPageModel();
		mallDetailList = Mockito.mock(Node.class);
		multifieldReader = Mockito.mock(MultifieldReader.class);
		valueMap = Mockito.mock(ValueMap.class);
		resource = Mockito.mock(Resource.class);
		mallTourPageList = new ArrayList<>();
		mallTourPagePojo = new MallTourPagePojo();
		multifieldProperty = new HashMap<>();
		multifieldProperty.put("dateDetails", valueMap);
		multifieldProperty.put("locationDetails", valueMap);
		multifieldProperty.put("linctaButtonTex", valueMap);
		mallTourPageModel.setMallDetailList(mallDetailList);
		mallTourPageModel.setMultifieldReader(multifieldReader);
		mallTourPageModel.setResource(resource);
		mallTourPagePojo.setAwalysEnglish("alwaysEnglish");
		mallTourPagePojo.setCtaButtonText("ctaButtonTex");
		mallTourPagePojo.setCtaButtonUrl("ctaUrl");
		mallTourPagePojo.setDateDetails("dateDetails");
		mallTourPagePojo.setLocationDetails("locationDetails");
		mallTourPagePojo.setMallTourTarget("mallTarget");
		mallTourPageList.add(mallTourPagePojo);
		mallTourPageModel.setMallTourPageList(mallTourPageList);
		Mockito.when(multifieldReader.propertyReader(mallDetailList)).thenReturn(multifieldProperty);
		
	}
	@Test
	public void init()
	{
		mallTourPageModel.init();
	}
	@Test
	public void getMallTourPageList() {
		mallTourPageModel.getMallTourPageList();
	}
	
}
