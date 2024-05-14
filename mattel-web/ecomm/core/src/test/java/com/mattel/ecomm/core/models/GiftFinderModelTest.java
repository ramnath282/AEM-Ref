package com.mattel.ecomm.core.models;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.jcr.Node;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.wcm.api.Page;
import com.mattel.ecomm.core.pojos.GiftFinderPojo;
import com.mattel.ecomm.core.services.MultifieldReader;

@RunWith(PowerMockRunner.class)
public class GiftFinderModelTest {
	@InjectMocks
	private GiftFinderModel giftFinderModel;
	@Mock
	private SlingHttpServletRequest request;
	@Mock
	private Resource resource;
	@Mock
	private Page currentPage;
	Node ageFilterDetails;
	Node priceFilterDetails;
	MultifieldReader multifieldReader;
	Map<String, ValueMap> multifieldProperty;
	ValueMap fieldMap;
	Map.Entry<String, ValueMap> entry;
	List<GiftFinderPojo> ageDetailsList;
	List<GiftFinderPojo> priceDetailsList;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws IllegalAccessException {
		multifieldReader = Mockito.mock(MultifieldReader.class);
		MemberModifier.field(GiftFinderModel.class, "resource").set(giftFinderModel, resource);
		ageFilterDetails = Mockito.mock(Node.class);
		priceFilterDetails = Mockito.mock(Node.class);
		ageDetailsList = new LinkedList<>();
		priceDetailsList = new LinkedList<>();
		giftFinderModel.setMultifieldReader(multifieldReader);
		giftFinderModel.setAgeFilterDetails(ageFilterDetails);
		giftFinderModel.setPriceFilterDetails(priceFilterDetails);
		giftFinderModel.setAgeDetailsList(ageDetailsList);
		giftFinderModel.setPriceDetailsList(priceDetailsList);
		fieldMap = Mockito.mock(ValueMap.class);
		entry = Mockito.mock(Entry.class);
		multifieldProperty = new HashMap<>();
		multifieldProperty.put("ageDetails", fieldMap);
		entry.setValue(fieldMap);
		Mockito.when(multifieldReader.propertyReader(ageFilterDetails)).thenReturn(multifieldProperty);
		Mockito.when(entry.getValue()).thenReturn(fieldMap);
		Mockito.when(entry.getValue().get("labelValue", String.class)).thenReturn("BABY GEAR");
	}

	@Test
	public void init() {
		giftFinderModel.init();
	}

	@Test
	public void getAgeDetailsList() {
		giftFinderModel.getAgeDetailsList();
	}

	@Test
	public void getPriceDetailsList() {
		giftFinderModel.getPriceDetailsList();
	}

}
