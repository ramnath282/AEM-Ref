package com.mattel.ecomm.core.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.mattel.ecomm.core.pojos.PrimaryPreferencesMattelBrandsPojo;
import com.mattel.ecomm.core.services.MultifieldReader;
import com.mattel.ecomm.core.utils.EcommConfigurationUtils;

@RunWith(PowerMockRunner.class)
public class EmailPreferencesModelTest {
	
	@InjectMocks
	private EmailPreferencesModel emailPreferencesModel;
	@Mock	
	SlingHttpServletRequest request;
	@Mock
	Resource resource;
	@Mock
	Node primaryPreferencesList;
	@Mock
	MultifieldReader multifieldReader;
	String key;
	String hash;
    String locale;
    String sourceId;
	ValueMap valueMap;
	Map<String, ValueMap> multifieldProperty;
	Map.Entry<String, ValueMap> entry;
    List<PrimaryPreferencesMattelBrandsPojo> preferencesList = new ArrayList<PrimaryPreferencesMattelBrandsPojo>();	
	PrimaryPreferencesMattelBrandsPojo primaryPreferencesMattelBrandsPojo;
	EcommConfigurationUtils ecommConfigurationUtils = new EcommConfigurationUtils();
	
	@SuppressWarnings("unchecked")
	@Before
	public void setup() throws IllegalArgumentException, IllegalAccessException {
		MemberModifier.field(EmailPreferencesModel.class, "resource").set(emailPreferencesModel, resource);
		MemberModifier.field(EmailPreferencesModel.class, "request").set(emailPreferencesModel, request);
		MemberModifier.field(EmailPreferencesModel.class, "multifieldReader").set(emailPreferencesModel,multifieldReader);
		MemberModifier.field(EmailPreferencesModel.class, "primaryPreferencesList").set(emailPreferencesModel,primaryPreferencesList);
		Mockito.when(request.getParameter("Key")).thenReturn("dmFpcmFtdXRodS5nc0BtYXR0ZWwuY29t");
		Mockito.when(request.getParameter("hash")).thenReturn("1b4ece33629bf716c1b68815dda1e85c59c959bae5651c6222df942710c6bba8");
		Mockito.when(request.getParameter("locale")).thenReturn("en-us");
		Mockito.when(request.getParameter("Source")).thenReturn("MTAxMjA");	
		
		multifieldProperty = new HashMap<>();
		entry = Mockito.mock(Map.Entry.class);
		valueMap = Mockito.mock(ValueMap.class);
		multifieldProperty.put("", valueMap);

		Mockito.when(multifieldReader.propertyReader(primaryPreferencesList)).thenReturn(multifieldProperty);
		Mockito.when(entry.getValue()).thenReturn(valueMap);

	}
	
	@Test
	public void init() {
		emailPreferencesModel.postConstruct();
	}
	
	@Test
	public void differentHash() {
		Mockito.when(request.getParameter("hash")).thenReturn("1b4ece33629bf716c1b68815dda1e85");
		emailPreferencesModel.postConstruct();
	}
	
	@Test
	public void blankKey() {
		Mockito.when(request.getParameter("Key")).thenReturn("");
		emailPreferencesModel.postConstruct();
	}


	@Test
	public void getPopulatePrimaryPreferencesList() {
		emailPreferencesModel.populatePrimaryPreferencesList(preferencesList);
	}
}
