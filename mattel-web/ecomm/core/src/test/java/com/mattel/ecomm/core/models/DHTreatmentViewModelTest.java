package com.mattel.ecomm.core.models;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mattel.ecomm.coreservices.core.interfaces.DHTreatmentViewService;
import com.mattel.ecomm.coreservices.core.pojos.DHService;
import com.mattel.ecomm.coreservices.core.pojos.DHTreatmentServiceAttributes;
import com.mattel.ecomm.coreservices.core.pojos.DHTreatmentViewResponse;
import com.mattel.ecomm.coreservices.core.pojos.Price;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;


@RunWith(PowerMockRunner.class)
public class DHTreatmentViewModelTest {

	@Mock
	DHTreatmentViewService dhTreatmentViewService;
	@Mock
	SlingHttpServletRequest request;
	@Mock
	PropertyReaderService propertyReaderService;
	@InjectMocks
	private DHTreatmentViewModel dhTreatmentViewModel;
	private DHService dhService;
	
	@Before
	public void setUp() throws Exception {
		dhService=new DHService();
		List<DHTreatmentServiceAttributes> attributes = new ArrayList<>();
		DHTreatmentServiceAttributes serviceAttributes = new DHTreatmentServiceAttributes();
		serviceAttributes.setName("name");
		serviceAttributes.setComparable(true);
		serviceAttributes.setDisplayable(true);
		serviceAttributes.setIdentifier("123213");
		serviceAttributes.setSearchable(true);
		serviceAttributes.setSequence("1");
		serviceAttributes.setStoreDisplay(true);
		serviceAttributes.setUniqueID("123123213");
		serviceAttributes.setUsage("usage");
		serviceAttributes.setValues(null);
		attributes.add(serviceAttributes);
		dhService.setAttributes(attributes );
		dhService.setBuyable("true");
		dhService.setCatalogEntryTypeCode("124");
		dhService.setHasSingleSKU(true);
		dhService.setName("TreatmentName");
		dhService.setPartNumber("12345");
		List<Price> priceList = new ArrayList<>();
		Price price = new Price();
		price.setCurrency("2");
		price.setDescription("description");
		price.setUsage("usage");
		price.setValue("212");
		priceList.add(price);
		dhService.setResourceId("7821312");
		dhService.setShortDescription("shortDescription");
		dhService.setStoreID("10657");
		dhService.setThumbnail("thumbnail");
		dhService.setUniqueID("000");
		DHTreatmentViewResponse dhCategoryViewResponse = new DHTreatmentViewResponse();
		List<DHService> catalogEntryView=new ArrayList<>();
		catalogEntryView.add(dhService);
		dhCategoryViewResponse.setCatalogEntryView(catalogEntryView);
		Mockito.when(dhTreatmentViewService.fetch(Mockito.any())).thenReturn(dhCategoryViewResponse);
		Mockito.when(propertyReaderService.getScene7Url()).thenReturn("scene7Url");
	}

	@Test
	public void testGetDhTreatmentServiceUIResponseList() throws JsonProcessingException {
		dhTreatmentViewModel.init();
		Assert.assertEquals("scene7Url",dhTreatmentViewModel.getScene7Url());
		Assert.assertNotNull(dhTreatmentViewModel.getDhTreatmentServiceUIResponseList());
	}

}
