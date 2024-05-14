package com.mattel.play.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.play.core.helper.PlayHelper;
import com.mattel.play.core.pojos.FooterPojo;
import com.mattel.play.core.services.MultifieldReader;
import com.mattel.play.core.utils.PlaySiteConfigurationUtils;
import com.mattel.play.core.utils.PropertyReaderUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PlaySiteConfigurationUtils.class, PlayHelper.class})
public class FooterModelTest {

	FooterModel footerModel;
	MultifieldReader multifieldReader;
	Node footerLinks;
	Map<String, ValueMap> multifieldProperty;
	ValueMap valueMap;
	List<FooterPojo> footerLinksList;
	FooterPojo footerLink;
	Resource resource;
	String[] excludedBrands;
	
	@Before
	public void setUp() {
		footerModel = new FooterModel();
		footerLinksList = new ArrayList<>();
		footerLink = new FooterPojo();
		multifieldReader = Mockito.mock(MultifieldReader.class);
		valueMap = Mockito.mock(ValueMap.class);
		footerLinks = Mockito.mock(Node.class);
		resource = Mockito.mock(Resource.class);
		PowerMockito.mockStatic(PlaySiteConfigurationUtils.class);
		PowerMockito.mockStatic(PlayHelper.class);
		multifieldProperty = new HashMap<>();
		multifieldProperty.put("linkText", valueMap);
		multifieldProperty.put("linkURL", valueMap);
		multifieldProperty.put("linkTarget", valueMap);
		footerModel.setMultifieldReader(multifieldReader);
		footerModel.setFooterLinks(footerLinks);
		footerModel.setFooterLinksList(footerLinksList);
		footerModel.setPagePath("max");
		footerLink.setLinkText("linkText");
		footerLink.setLinkURL("linkURL");
		footerLink.setLinkTarget("linkTarget");
		footerModel.setResource(resource);
		footerLinksList.add(footerLink);
		excludedBrands = new String[2];
		excludedBrands[0] = "maxsteel";
		excludedBrands[1] = "maxsteel";
		Mockito.when(multifieldReader.propertyReader(footerLinks)).thenReturn(multifieldProperty);
		Mockito.when(PlaySiteConfigurationUtils.getExcludedBrandsCountryDropdown()).thenReturn(excludedBrands);
		Mockito.when(PlayHelper.getBrandName("max")).thenReturn("/home");
		
	}
	
	@Test
	public void init() {

		footerModel.init();
	}
	@Test
	public void getFooterLinksList() {
		footerModel.getFooterLinksList();
	}
	@Test
	public void getLogoURL() {
		footerModel.getLogoURL();
	}
	
}
