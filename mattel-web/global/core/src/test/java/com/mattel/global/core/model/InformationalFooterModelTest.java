package com.mattel.global.core.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.jcr.Node;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
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

import com.mattel.global.core.pojo.InformationalFooterLinkPojo;
import com.mattel.global.core.pojo.SocialIconsPojo;
import com.mattel.global.core.services.MultifieldReader;
import com.mattel.global.core.utils.GlobalUtils;

@PrepareForTest(GlobalUtils.class)
@RunWith(PowerMockRunner.class)
public class InformationalFooterModelTest {
	@InjectMocks
	private InformationalFooterModel informationalFooterModel; 

	@Mock
	private Resource resource;

	@Mock
	private Node footerLinksOne;

	@Mock
	private Node socialIcons;

	@Mock
	private Node footerLinksTwo;

	@Mock
	private Node footerLinksThree;

	@Mock
	private Node footerLinksFour;

	@Mock
	private MultifieldReader multifieldReader;  

	@Mock
	private ValueMap valueMap; 

	@Mock
	private Map<String, ValueMap> multifieldProperty;
	
	@Mock
	private Map.Entry<String, ValueMap> entry;

	private static final String LINKTEXT = "linkText";
	private static final String LINKTARGET= "linkTarget";
	private static final String ALWAYSENGLISH= "alwaysEnglish";
	private static final String LINKURL = "linkURL";    
	private static final String ICONS= "icons";
	private static final String SOCIALLINKURL = "socialLinkURL";    

	private List<InformationalFooterLinkPojo> footerTextGroup = new ArrayList<>();	
	private List<SocialIconsPojo> socialIconsList = new ArrayList<>();	

	@Before
	public void setup() throws Exception {

		PowerMockito.mockStatic(GlobalUtils.class);	

		MemberModifier.field(InformationalFooterModel.class, "resource").set(informationalFooterModel, resource);
		MemberModifier.field(InformationalFooterModel.class, "footerLinksOne").set(informationalFooterModel, footerLinksOne);
		MemberModifier.field(InformationalFooterModel.class, "socialIcons").set(informationalFooterModel, socialIcons);
		MemberModifier.field(InformationalFooterModel.class, "footerLinksTwo").set(informationalFooterModel, footerLinksTwo);
		MemberModifier.field(InformationalFooterModel.class, "footerLinksThree").set(informationalFooterModel, footerLinksThree);
		MemberModifier.field(InformationalFooterModel.class, "footerLinksFour").set(informationalFooterModel, footerLinksFour);
		MemberModifier.field(InformationalFooterModel.class, "multifieldReader").set(informationalFooterModel, multifieldReader);

		Mockito.when(GlobalUtils.checkLink(InformationalFooterModelTest.LINKURL,resource)).thenReturn(InformationalFooterModelTest.LINKURL);
		Mockito.when(GlobalUtils.checkLink(InformationalFooterModelTest.SOCIALLINKURL,resource)).thenReturn(InformationalFooterModelTest.SOCIALLINKURL);

		Mockito.when(multifieldReader.propertyReader(Mockito.any())).thenReturn(multifieldProperty);
		

		Set<Entry<String, ValueMap>> entrySet = new HashSet<Entry<String, ValueMap>>();
		entrySet.add(entry);
		ValueMap valueMap = Mockito.mock(ValueMap.class);
		Mockito.when(entry.getValue()).thenReturn(valueMap);
		Mockito.when(multifieldProperty.entrySet()).thenReturn(entrySet);
		
		Mockito.when(valueMap.get(InformationalFooterModelTest.ICONS, StringUtils.EMPTY)).thenReturn(InformationalFooterModelTest.ICONS);
		Mockito.when(valueMap.get(InformationalFooterModelTest.LINKTEXT, StringUtils.EMPTY)).thenReturn(InformationalFooterModelTest.LINKTEXT);
		Mockito.when(valueMap.get(InformationalFooterModelTest.LINKTARGET, StringUtils.EMPTY)).thenReturn(InformationalFooterModelTest.LINKTARGET);
		Mockito.when(valueMap.get(InformationalFooterModelTest.ALWAYSENGLISH, StringUtils.EMPTY)).thenReturn(InformationalFooterModelTest.ALWAYSENGLISH);
		Mockito.when(valueMap.get(InformationalFooterModelTest.SOCIALLINKURL, StringUtils.EMPTY)).thenReturn(InformationalFooterModelTest.SOCIALLINKURL);
		Mockito.when(valueMap.get(InformationalFooterModelTest.LINKURL, StringUtils.EMPTY)).thenReturn(InformationalFooterModelTest.LINKURL);
	}

	@Test
	public void testInit() {
		informationalFooterModel.init();
	}

	@Test
	public void testValidateProperties() throws IllegalArgumentException, IllegalAccessException {
		assertEquals(footerTextGroup, informationalFooterModel.getFooterTextGroupOne());
		assertEquals(footerTextGroup, informationalFooterModel.getFooterTextGroupTwo());
		assertEquals(footerTextGroup, informationalFooterModel.getFooterTextGroupThree());
		assertEquals(footerTextGroup, informationalFooterModel.getFooterTextGroupFour());
		assertEquals(socialIconsList, informationalFooterModel.getSocialIconsList());
	}
}
