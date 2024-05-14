package com.mattel.global.core.model;

import com.adobe.granite.crypto.CryptoSupport;
import com.mattel.global.core.pojo.PrimaryPreferencesMattelBrandsPojo;
import com.mattel.global.core.services.MultifieldReader;
import com.mattel.global.core.utils.CryptoSupportUtils;
import com.mattel.global.core.utils.GlobalPropertyReaderUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.jcr.Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CryptoSupportUtils.class)
public class EmailPreferencesModelTest {

	@InjectMocks
	private EmailPreferencesModel emailPreferencesModel;
	@Mock	
	SlingHttpServletRequest request;
	@Mock
	Resource resource;
	@Mock
	private CryptoSupport cryptoSupport;
	@Mock
	Node primaryPreferencesList;
	@Mock MultifieldReader multifieldReader;
	@Mock
	GlobalPropertyReaderUtils globalPropertyReaderUtils;
	String key;
	String hash;
	String locale;
	String sourceId;
	ValueMap valueMap;
	Map<String, ValueMap> multifieldProperty;
	Map.Entry<String, ValueMap> entry;
    List<PrimaryPreferencesMattelBrandsPojo> preferencesList = new ArrayList<PrimaryPreferencesMattelBrandsPojo>();	
	PrimaryPreferencesMattelBrandsPojo primaryPreferencesMattelBrandsPojo;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() throws IllegalArgumentException, IllegalAccessException {
		MemberModifier.field(EmailPreferencesModel.class, "resource").set(emailPreferencesModel, resource);
		MemberModifier.field(EmailPreferencesModel.class, "request").set(emailPreferencesModel, request);
		MemberModifier.field(EmailPreferencesModel.class, "multifieldReader").set(emailPreferencesModel,multifieldReader);
		MemberModifier.field(EmailPreferencesModel.class, "primaryPreferencesList").set(emailPreferencesModel,primaryPreferencesList);

		Mockito.when(globalPropertyReaderUtils.getPrefAPIKey()).thenReturn("py37r2fs7jtyvucd6fn5898b");
		Mockito.when(globalPropertyReaderUtils.getPrefAPIUrl()).thenReturn("https://api.sdn.mattel.com");

		Mockito.when(request.getParameter("Key")).thenReturn("dmFpcmFtdXRodS5nc0BtYXR0ZWwuY29t");
		Mockito.when(request.getParameter("hash")).thenReturn("1b4ece33629bf716c1b68815dda1e85c59c959bae5651c6222df942710c6bba8");
		Mockito.when(request.getParameter("locale")).thenReturn("en-us");
		Mockito.when(request.getParameter("Source")).thenReturn("MTAxMjA");

		PowerMockito.mockStatic(CryptoSupportUtils.class);
		Mockito.when(CryptoSupportUtils.encryptString(emailPreferencesModel.getDecodedEmailId())).thenReturn("encrypted_value");
		ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class);
		Resource imageRes = Mockito.mock(Resource.class);
		Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
		Mockito.when(resourceResolver.resolve(Mockito.anyString())).thenReturn(imageRes);
		Mockito.when(imageRes.getPath()).thenReturn("resource_path");

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
		Assert.assertEquals(true, emailPreferencesModel.getIsValidEmail());
		Assert.assertNotNull(emailPreferencesModel.getDecodedEmailId());
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
