package com.mattel.play.core.model;

import java.util.HashMap;
import java.util.List;

import javax.jcr.Node;

import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.play.core.pojos.AccordionPojo;
import com.mattel.play.core.services.MultifieldReader;

public class AccordionModelTest {

	AccordionModel accordionModel = new AccordionModel();
	MultifieldReader multifieldReader;
	AccordionPojo accordionPojo;
	Node accordian;
	List<AccordionPojo> accordianList;
	ValueMap valueMap;
	HashMap<String, ValueMap> multifieldProperty;

	@Before
	public void setUp() {

		multifieldReader = Mockito.mock(MultifieldReader.class);
		accordionModel.setMultifieldReader(multifieldReader);
		accordian = Mockito.mock(Node.class);
		accordionModel.setAccordian(accordian);
		valueMap = Mockito.mock(ValueMap.class);
		multifieldProperty = new HashMap<>();
		multifieldProperty.put("contentTitle", valueMap);
		multifieldProperty.put("awalysEnglish", valueMap);
		Mockito.when(multifieldReader.propertyReader(accordian)).thenReturn(multifieldProperty);

	}

	@Test
	public void init() {

		accordionModel.init();

	}

	@Test
	public void getAccordianList() {

		accordionModel.getAccordianList();

	}

}
