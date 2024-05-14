package com.mattel.global.core.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.jcr.Node;

import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mattel.global.core.services.MultifieldReader;

public class RetailersLogosModelTest {
	RetailersLogosModel retailersLogosModel;

	@Mock
	private Node retailerLogoDetails;

	@Mock
	private MultifieldReader multifieldReader;

	@Mock
	private Map<String, ValueMap> retailersValueMap;

	@Mock
	Map.Entry<String, ValueMap> entry;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		retailersLogosModel = new RetailersLogosModel();
		retailersLogosModel.setRetailerLogoDetails(retailerLogoDetails);
		retailersLogosModel.setMultifieldReader(multifieldReader);
		retailersLogosModel.setRetailersLogoDetailPojos(null);
		Mockito.when(multifieldReader.propertyReader(retailerLogoDetails)).thenReturn(retailersValueMap);
	}

	@Test
	public void testNotNullRetailersWhenReailersListIsNotNull() throws Exception {
		Set<Entry<String, ValueMap>> entrySet = new HashSet<Entry<String, ValueMap>>();
		entrySet.add(entry);
		ValueMap valueMap = Mockito.mock(ValueMap.class);
		Mockito.when(entry.getValue()).thenReturn(valueMap);
		Mockito.when(retailersValueMap.entrySet()).thenReturn(entrySet);
		retailersLogosModel.init();
		assertNotNull(retailersLogosModel.getRetailersLogoDetailPojos());
	}

	@Test
	public void testOnClickOption() {
		retailersLogosModel.setOnClickOption("new window");
		assertSame("new window", retailersLogosModel.getOnClickOption());
	}

	@Test
	public void testConfirmationButton() {
		retailersLogosModel.setConfirmationButtonText("confirm");
		assertSame("confirm", retailersLogosModel.getConfirmationButtonText());
	}

	@Test
	public void testCancelButton() {
		retailersLogosModel.setCancelButtonText("cancel");
		assertSame("cancel", retailersLogosModel.getCancelButtonText());
	}

	@Test
	public void testInterstetialTitle() {
		retailersLogosModel.setInterstitialTitle("Interstitial Title");
		assertSame("Interstitial Title", retailersLogosModel.getInterstitialTitle());
	}

	@Test
	public void testInterstetialSubtitle() {
		retailersLogosModel.setInterstitialSubTitle("Interstetial Sub-Title");
		assertSame("Interstetial Sub-Title", retailersLogosModel.getInterstitialSubTitle());
	}
}
